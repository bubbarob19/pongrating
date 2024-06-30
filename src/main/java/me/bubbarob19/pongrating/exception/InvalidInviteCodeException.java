package me.bubbarob19.pongrating.exception;

public class InvalidInviteCodeException extends RuntimeException {
    public InvalidInviteCodeException(String code) {
        super("The invite code \"" + code + "\" is not valid!");
    }

    public InvalidInviteCodeException() {
        super("An invite code was not provided");
    }
}
