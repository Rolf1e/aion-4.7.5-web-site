package com.aion.server.component.mail.config.auth;

import lombok.extern.slf4j.Slf4j;
import sun.misc.ClassLoaderUtil;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class AuthHandler {

    private static AuthHandler instance = null;

    private final String user;
    private final String pwd;

    public static AuthHandler getInstance() {
        if (instance == null) {
            Properties properties = loadProperties();
            instance = new AuthHandler(properties.getProperty("mail.identifiant"),
                    properties.getProperty("mail.password"));
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
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            properties.load(classloader.getResourceAsStream("application.properties"));
        } catch (IOException e) {
            log.error("Failed to load mail properties", e);
        }
        return properties;
    }
}
