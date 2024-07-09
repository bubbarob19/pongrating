package me.bubbarob19.pongrating.auth;

import lombok.RequiredArgsConstructor;
import me.bubbarob19.pongrating.exception.AlreadyRegisteredException;
import me.bubbarob19.pongrating.exception.InvalidInviteCodeException;
import me.bubbarob19.pongrating.exception.LoginFailedException;
import me.bubbarob19.pongrating.model.Role;
import me.bubbarob19.pongrating.model.User;
import me.bubbarob19.pongrating.repository.PlayerRepository;
import me.bubbarob19.pongrating.repository.UserRepository;
import me.bubbarob19.pongrating.service.InviteCodeService;
import me.bubbarob19.pongrating.service.PlayerService;
import org.bson.types.ObjectId;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PlayerService playerService;
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final InviteCodeService inviteCodeService;

    public AuthenticationResponse register(RegisterRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent())
            throw new AlreadyRegisteredException("The email " + request.getEmail() + " is already registered!");

        if (request.getInviteCode() == null)
            throw new InvalidInviteCodeException();

        if (!inviteCodeService.isInviteCodeValid(request.getInviteCode()))
            throw new InvalidInviteCodeException(request.getInviteCode());

        inviteCodeService.useCode(request.getInviteCode());

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .playerId(new ObjectId().toString())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .authorities(
                        List.of(Role.USER)
                )
                .build();
        repository.save(user);
        playerService.createPlayer(user);
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .playerId(user.getPlayerId())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            User user = repository.findByEmail(request.getEmail()).orElseThrow();
            if (playerRepository.findById(user.getPlayerId()).isEmpty()) {
                playerService.createPlayer(user);
            }
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);
            return AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .playerId(user.getPlayerId())
                    .build();
        } catch (Exception e) {
            throw new LoginFailedException();
        }
    }

    public ValidateTokenResponse isTokenValid(ValidateTokenRequest request) {
        boolean isTokenValid = jwtService.isTokenValid(
                    request.getToken(),
                    request.getEmail()
        );
        return ValidateTokenResponse.builder()
                .valid(isTokenValid)
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        Optional<User> userOptional = repository.findByEmail(request.getEmail());
        boolean isTokenValid = jwtService.isRefreshTokenValid(
                request.getRefreshToken(),
                request.getEmail()
        );

        if (userOptional.isEmpty())
            throw new LoginFailedException("User not found");
        if (!isTokenValid)
            throw new LoginFailedException("Invalid refresh token");

        User user = userOptional.get();
        String accessToken = jwtService.generateAccessToken(user);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(request.getRefreshToken())
                .playerId(user.getPlayerId())
                .build();
    }
}
