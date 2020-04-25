package com.aion.server.service.infra.exception;

public class ShopPerformPurchaseException extends ShopException {

    public ShopPerformPurchaseException(final long itemId,
                                        final int userId) {

        super("Failed to perform purchase user : " + userId + " item" + itemId);
    }
}
