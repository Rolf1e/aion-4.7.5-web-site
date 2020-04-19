package com.aion.server.service;

import com.aion.server.service.infra.dto.ShardsPurchase;
import com.aion.server.service.infra.utils.JsonParserPaypal;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.core.request.AccessTokenRequest;
import com.paypal.http.HttpResponse;
import com.paypal.http.exceptions.SerializeException;
import com.paypal.http.serializer.Json;
import com.paypal.orders.Order;
import com.paypal.orders.OrdersGetRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class PaypalService {

    private final PayPalHttpClient httpClient;
    private final PayPalEnvironment payPalEnvironment;

    public PaypalService(@Qualifier("sandboxHttpClient") final PayPalHttpClient httpClient,
                         @Qualifier("sandbox") final PayPalEnvironment payPalEnvironment) {

        this.httpClient = httpClient;
        this.payPalEnvironment = payPalEnvironment;
    }

    public int checkPurchase(final ShardsPurchase purchase) throws IOException {
        final OrdersGetRequest request = new OrdersGetRequest(purchase.getTransactionId());
        final HttpResponse<Order> response = httpClient.execute(request);
        return parseFromJson(response);
    }

    public String getToken() throws IOException {
        final AccessTokenRequest tokenRequest = new AccessTokenRequest(payPalEnvironment);
        return httpClient.execute(tokenRequest)
                .result()
                .accessToken();
    }

    private int parseFromJson(final HttpResponse<Order> response) throws SerializeException {
        final JSONObject jsonObject = new JSONObject(new Json().serialize(response.result()));
        return Integer.parseInt(JsonParserPaypal.getValueFromPurchaseUnits(jsonObject));
    }
}
