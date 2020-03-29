package com.aion.server.service;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Base64;

@UtilityClass
public class EncryptionService {

    public static String toEncode(String toEncode) {
        return Base64.getEncoder().encodeToString(toEncode.getBytes());
    }

    public static String toDecode(String toDecode) {
        return Arrays.toString(Base64.getDecoder().decode(toDecode.getBytes()));
    }
}
