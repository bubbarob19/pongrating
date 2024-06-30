package me.bubbarob19.pongrating.auth;

import lombok.RequiredArgsConstructor;
import me.bubbarob19.pongrating.exception.AlreadyRegisteredException;
import me.bubbarob19.pongrating.exception.InvalidInviteCodeException;
import me.bubbarob19.pongrating.model.Role;
import me.bubbarob19.pongrating.model.User;
import me.bubbarob19.pongrating.repository.UserRepository;
import me.bubbarob19.pongrating.service.InviteCodeService;
import me.bubbarob19.pongrating.service.PlayerService;
import org.bson.types.ObjectId;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PlayerService playerService;
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
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = repository.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
