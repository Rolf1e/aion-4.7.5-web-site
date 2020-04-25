package com.aion.server.service.infra.exception;

public class ItemDoesntExistException extends ShopException {

    public ItemDoesntExistException(final long itemName) {
        super("Item doesnt exist" + itemName);
    }
}
