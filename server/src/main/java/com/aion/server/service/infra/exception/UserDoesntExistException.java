package com.aion.server.service.infra.exception;

public class UserDoesntExistException extends Exception {

    public UserDoesntExistException(String token) {
        super("User does not exist for token :" + token);
    }

}
