package com.aion.server.component.mail.config.auth;

import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class AuthHandler {

    private static AuthHandler instance = null;

    private final String user;
    private final String pwd;

    public static AuthHandler getInstance() {
        if (instance == null) {
            Properties properties = loadProperties();
            instance = new AuthHandler(properties.getProperty("identifiant"),
                    properties.getProperty("password"));
        }
        return instance;
    }

    private AuthHandler(String user, String pwd) {
        this.user = user;
        this.pwd = pwd;
    }

    public String getUser() {
        return user;
    }

    public String getPwd() {
        return pwd;
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("server/src/main/resources/application.yml"));
        } catch (IOException e) {
            log.error("Failed to load mail properties", e);
        }
        return properties;
    }
}
