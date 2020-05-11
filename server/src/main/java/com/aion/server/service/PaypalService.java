package com.aion.server.service;

import com.aion.server.service.infra.dto.ShardsPurchase;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.core.request.AccessTokenRequest;
import com.paypal.http.HttpResponse;
import com.paypal.payments.Capture;
import com.paypal.payments.CapturesGetRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class PaypalService {

    private final PayPalHttpClient httpClient;
    private final PayPalEnvironment payPalEnvironment;

    public PaypalService(@Qualifier("httpClient") final PayPalHttpClient httpClient,
                         @Qualifier("environnement") final PayPalEnvironment payPalEnvironment) {

        this.httpClient = httpClient;
        this.payPalEnvironment = payPalEnvironment;
    }

    public double checkPurchase(final ShardsPurchase purchase) throws IOException {
        final CapturesGetRequest request = new CapturesGetRequest(purchase.getTransactionId());
        final HttpResponse<Capture> response = httpClient.execute(request);
        return parseFromJson(response);
    }

    public String getToken() throws IOException {
        final AccessTokenRequest tokenRequest = new AccessTokenRequest(payPalEnvironment);
        return httpClient.execute(tokenRequest)
                .result()
                .accessToken();
    }

    private double parseFromJson(final HttpResponse<Capture> response) {
        return Double.parseDouble(response.result().amount().value());
    }
}
