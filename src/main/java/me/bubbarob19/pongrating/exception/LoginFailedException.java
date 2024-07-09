package me.bubbarob19.pongrating.exception;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException() {
        super("Invalid Credentials");
    }

    public LoginFailedException(String message) {
        super(message);
    }
}
