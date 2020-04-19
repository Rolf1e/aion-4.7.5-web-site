package com.aion.server.service.infra.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShardsPurchase {

    private String token;
    private int userId;
    private double transactionAmount;
    private String transactionId;
}
