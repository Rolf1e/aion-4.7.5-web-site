package com.aion.server.database.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LoginConfig {

    //TABLE
    public static final String USERS_TABLE = "USERS";

    //COLUMNS
    public static final String USERNAME_COLUMN = "USERNAME";
    public static final String PASSWORD_COLUMN = "PASSWORD";
    public static final String TOKEN_COLUMN = "TOKEN";
}
