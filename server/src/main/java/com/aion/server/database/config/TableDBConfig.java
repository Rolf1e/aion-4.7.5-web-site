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
    public static final String SHOP_TABLE = "WEBSHOP";
    //COLUMNS
    public static final String PLAYER_ID_COLUMN = "id";
    public static final String RECIPIENT_COLUMN = "recipient";
    public static final String SHOP_ITEM_DESC_COLUMN = "item_desc";
    public static final String ITEM_PLAYER_ID_COLUMN = "item_id";
    public static final String SHOP_COUNT_COLUMN = "count";
    public static final String SHARD_COLUMN = "toll";

    //ITEMS
    //TABLE
    public static final String ITEM_TABLE = "SHOP";
    //COLUMNS
    public static final String OBJECT_ID_COLUMN = "object_id";
    public static final String ITEM_ID_COLUMN = "item_id";
    public static final String ITEM_NAME_COLUMN = "item_name";
    public static final String ITEM_CATEGORY_COLUMN = "item_category";
    public static final String ITEM_DESCRIPTION_COLUMN = "item_desc";
    public static final String ITEM_COUNT_COLUMN = "item_count";
    public static final String ITEM_PRICE_COLUMN = "price";
    public static final String ITEM_PATH_TO_IMAGE_COLUMN = "item_image_path";


}
