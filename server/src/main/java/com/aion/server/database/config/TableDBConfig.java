package com.aion.server.database.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TableDBConfig {

    //USERS
    //TABLE
    public static final String USERS_TABLE = "USERS";
    //COLUMNS
    public static final String USERNAME_ID_COLUMN = "ID";
    public static final String USERNAME_COLUMN = "USERNAME";
    public static final String PASSWORD_COLUMN = "PASSWORD";
    public static final String TOKEN_COLUMN = "TOKEN";


    //SHOP
    //TABLE
    public static final String SHOP_TABLE = "PLAYER_SHOP";
    //COLUMNS
    public static final String PLAYER_ID_COLUMN = "PLAYERID";
    public static final String ITEM_PLAYER_ID_COLUMN = "ITEMID";
    public static final String ITEM_COUNT_COLUMN = "ITEMCOUNT";
    public static final String AMOUNT_MONEY_USER = "AMOUNT_WEB_MONEY";


    //ITEMS
    //TABLE
    public static final String ITEM_TABLE = "ITEM";
    //COLUMNS
    public static final String ITEM_ID_COLUMN = "ID";
    public static final String ITEM_NAME_COLUMN = "NAME";
    public static final String ITEM_CATEGORY_COLUMN = "CATEGORY";
    public static final String ITEM_DESCRIPTION_COLUMN = "DESCRIPTION";
    public static final String ITEM_PRICE_COLUMN = "PRICE";
    public static final String ITEM_PATH_TO_IMAGE_COLUMN = "PATHTOIMAGE";


}
