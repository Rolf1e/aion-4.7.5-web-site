package com.aion.server.service.infra.exception;

public class ShopInsertionException extends ShopException {

    public ShopInsertionException(final long itemId,
                                  final long userId) {

        super("Failed to insert item " + itemId + " for user " + userId);
    }
}
