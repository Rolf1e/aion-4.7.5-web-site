package com.aion.server.handler.token;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;

@Slf4j
public class TokenGenerator {

    public static String generate() {
        return new TokenGenerator()
                .generateToken();
    }

    private String generateToken() {
        return RandomString.make(255);
    }
}
