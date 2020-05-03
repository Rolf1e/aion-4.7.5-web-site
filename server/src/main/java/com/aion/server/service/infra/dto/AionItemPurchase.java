package com.aion.server.service.infra.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AionItemPurchase {

    private String token;
    private long idItem;
    private int countItem;
    private String recipient;
    private boolean error;
    private String message;

    public AionItemPurchase(final AionItemPurchase aionItemPurchase,
                            final boolean error,
                            final String message) {

        this.idItem = aionItemPurchase.getIdItem();
        this.countItem = aionItemPurchase.getCountItem();
        this.recipient = aionItemPurchase.getRecipient();
        this.error = error;
        this.message = message;
    }
}
