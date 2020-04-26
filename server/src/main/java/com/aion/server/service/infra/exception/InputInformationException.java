package com.aion.server.service.infra.exception;

public class InputInformationException extends Exception {

    public InputInformationException() {
        super("Password or Username are not provided ");
    }
}
