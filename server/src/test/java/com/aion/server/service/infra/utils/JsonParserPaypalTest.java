package com.aion.server.service.infra.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class JsonParserPaypalTest {

    @Test
    public void should_parse() {
        final JSONObject tomJsonObject = getFakeJson();
        Assert.assertEquals("100.00", JsonParserPaypal.getValueFromPurchaseUnits(tomJsonObject));
    }

    private JSONObject getFakeJson() {
        final JSONObject transactionDetails = new JSONObject();
        final JSONArray purchaseUnits = new JSONArray();
        final JSONObject purchaseObject = new JSONObject();
        final JSONObject amount = new JSONObject();

        amount.put("value", "100.00");
        purchaseObject.put("reference_id", "d9f80740-38f0-11e8-b467-0ed5f89f718b");
        purchaseObject.put("id", "O-9RR85649FD345344Y");
        purchaseObject.put("amount", amount);

        purchaseUnits.put(purchaseObject);

        transactionDetails.put("purchase_units", purchaseUnits);
        transactionDetails.put("id", "5O190127TN364715T");

        return transactionDetails;
    }
}
