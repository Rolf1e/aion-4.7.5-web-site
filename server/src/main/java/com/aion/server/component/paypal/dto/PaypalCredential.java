package com.aion.server.component.paypal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaypalCredential {

    private final String clientId;
    private final String cliendSecret;
}
