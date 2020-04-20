package com.aion.server.component.paypal.infra;

import com.aion.server.component.paypal.dto.PaypalCredential;
import com.paypal.core.PayPalHttpClient;

public class PaypalClient {

    private final PaypalCredential credential;
    private final PayPalHttpClient client;

    public PaypalClient(PaypalCredential credential,
                        PayPalHttpClient client) {

        this.credential = credential;
        this.client = client;
    }

    public PayPalHttpClient getClient() {
        return client;
    }
}
