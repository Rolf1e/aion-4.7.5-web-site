package com.aion.server.service.token;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenGenerator {

    //TODO
    public static String generate() {
        return new TokenGenerator()
                .generateToken();
    }

    private String generateToken() {
        return "";
    }
}
