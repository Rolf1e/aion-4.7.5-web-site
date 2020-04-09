package com.aion.server.database.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TableDBConfig {

    //USERS
    //TABLE
    public static final String USERS_TABLE = "USERS";

    //COLUMNS
    public static final String USERNAME_COLUMN = "USERNAME";
    public static final String PASSWORD_COLUMN = "PASSWORD";
    public static final String TOKEN_COLUMN = "TOKEN";

    //USERS
    //SHOP
    public static final String SHOP_TABLE = "SHOP";

    //COLUMNS
    public static final String PLAYER_ID_COLUMN = "PLAYERID";
    public static final String ITEM_ID_COLUMN = "ITEMID";
    public static final String ITEM_COUNT_COLUMN = "ITEMCOUNT";

}
