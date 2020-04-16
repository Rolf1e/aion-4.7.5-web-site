package com.aion.server.component.paypal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiConfig {

    private final String baseUrl;
    private final String webUrl;
}
