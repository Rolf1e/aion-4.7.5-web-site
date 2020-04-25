package com.aion.server.service.infra.utils;

import lombok.experimental.UtilityClass;
import org.json.JSONObject;

@UtilityClass
public class JsonParserPaypal {

    private static final String JSON_FIELD_PURCHASE_UNITS = "purchase_units";
    private static final String JSON_FIELD_REFERENCE_ID = "reference_id";
    private static final String JSON_FIELD_ID = "id";
    private static final String JSON_FIELD_AMOUNT = "amount";
    private static final String JSON_FIELD_CURRENCY_CODE = "currency_code";
    private static final String JSON_FIELD_VALUE = "value";

    public static String getValueFromPurchaseUnits(final JSONObject jsonObject) {
        return getAmount((JSONObject) jsonObject.getJSONArray(JSON_FIELD_PURCHASE_UNITS).get(0));
    }

    private static String getAmount(final JSONObject jsonObject) {
        return getValue((JSONObject) jsonObject.get(JSON_FIELD_AMOUNT));
    }

    private static String getValue(final JSONObject jsonObject) {
        return (String) jsonObject.get(JSON_FIELD_VALUE);
    }
}
