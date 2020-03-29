package com.aion.server.handler.exception;

public class UserExistException extends Exception {

    public UserExistException(String user) {
        super("User already exist " + user);
    }
}
