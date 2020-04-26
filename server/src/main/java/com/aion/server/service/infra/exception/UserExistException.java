package com.aion.server.service.infra.exception;

public class UserExistException extends LoginException {

    public UserExistException(String user) {
        super("User already exist " + user);
    }
}
