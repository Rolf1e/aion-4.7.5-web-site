package com.aion.server.service.infra.exception;

public class ShopPerformPurchaseException extends ShopException {

    public ShopPerformPurchaseException(final long itemId,
                                        final long userId) {

        super("Failed to perform purchase user : " + userId + " item" + itemId);
    }
}
