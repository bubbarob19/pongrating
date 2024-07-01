package me.bubbarob19.pongrating.model;

public enum Role {
    USER,
    ADMIN;

    private static final String PREFIX = "ROLE_";

    public String getName() {
        return PREFIX + name();
    }
}
