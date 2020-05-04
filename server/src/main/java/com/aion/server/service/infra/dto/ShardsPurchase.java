package com.aion.server.service.infra.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShardsPurchase {

    private String token;
    private String transactionId;
    private long shardBalance;
    private boolean error;
    private String message;

    public ShardsPurchase(final String transactionId,
                          final long shardBalance,
                          final boolean error,
                          final String message) {

        this.transactionId = transactionId;
        this.error = error;
        this.message = message;
        this.shardBalance = shardBalance;
    }
}
