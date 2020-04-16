package com.aion.server.service.infra.exception;

public class UserExistException extends Exception {

    public UserExistException(String user) {
        super("User already exist " + user);
    }
}
