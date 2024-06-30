package me.bubbarob19.pongrating.service;

import lombok.AllArgsConstructor;
import me.bubbarob19.pongrating.exception.InvalidInviteCodeException;
import me.bubbarob19.pongrating.model.InviteCode;
import me.bubbarob19.pongrating.repository.InviteCodeRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class InviteCodeService {
    private static final int CODE_LENGTH = 20;
    private static final boolean USE_LETTERS = true;
    private static final boolean USE_NUMBERS = true;

    private InviteCodeRepository repository;

    public InviteCode generateInviteCode() {
        InviteCode inviteCode = InviteCode.builder()
                .id(RandomStringUtils.random(CODE_LENGTH, USE_LETTERS, USE_NUMBERS))
                .build();

        repository.save(inviteCode);
        return inviteCode;
    }

    public boolean isInviteCodeValid(String code) {
        Optional<InviteCode> inviteCode = repository.findById(code);
        return inviteCode.isPresent() && !inviteCode.get().isUsed();
    }

    public void useCode(String code) {
        InviteCode inviteCode = repository.findById(code)
                .orElseThrow(() -> new InvalidInviteCodeException(code));

        inviteCode.setUsed(true);
        repository.save(inviteCode);
    }
}
