package com.aion.server.service.infra.exception;


public class TokenRefresherException extends Exception {

    public TokenRefresherException() {
        super("Failed to refresh token");
    }
}
