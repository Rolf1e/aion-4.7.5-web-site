package com.aion.server.database.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TableDBConfig {

    //USERS
    //TABLE
    public static final String USERS_TABLE = "ACCOUNT_DATA";
    //COLUMNS
    public static final String USERNAME_ID_COLUMN = "ID";
    public static final String USERNAME_COLUMN = "NAME";
    public static final String PASSWORD_COLUMN = "PASSWORD";
    public static final String TOKEN_COLUMN = "TOKEN";
    public static final String ACCOUNT_ACTIVATED_COLUMN = "ACTIVATED";
    public static final String MAIL_COLUMN = "EMAIL";
    public static final String TOLL_COLUMN = "TOLL";

    //SHOP
    //TABLE
    public static final String SHOP_TABLE = "PLAYER_SHOP";
    //COLUMNS
    public static final String PLAYER_ID_COLUMN = "PLAYERID";
    public static final String ITEM_PLAYER_ID_COLUMN = "ITEMID";
    public static final String ITEM_COUNT_COLUMN = "ITEMCOUNT";
    public static final String SHARD_COLUMN = "TOLL";


    //ITEMS
    //TABLE
    public static final String ITEM_TABLE = "WEBSHOP";
    //COLUMNS
    public static final String ITEM_ID_COLUMN = "ID";
    public static final String ITEM_NAME_COLUMN = "NAME";
    public static final String ITEM_CATEGORY_COLUMN = "CATEGORY";
    public static final String ITEM_DESCRIPTION_COLUMN = "DESCRIPTION";
    public static final String ITEM_PRICE_COLUMN = "PRICE";
    public static final String ITEM_PATH_TO_IMAGE_COLUMN = "PATHTOIMAGE";


}
