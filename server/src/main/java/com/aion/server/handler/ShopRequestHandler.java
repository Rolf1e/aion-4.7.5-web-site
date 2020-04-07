package com.aion.server.handler;

import com.aion.server.database.infra.DBClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShopRequestHandler extends AbstractRequestHandler {


    public ShopRequestHandler(DBClient dbClient) {
        super(dbClient);
    }
}
