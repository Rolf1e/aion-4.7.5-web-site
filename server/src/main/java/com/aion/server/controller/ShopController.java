package com.aion.server.controller;

import com.aion.server.database.infra.DBClient;
import com.aion.server.handler.ShopRequestHandler;
import com.aion.server.handler.TokenRequestHandler;
import com.aion.server.handler.dto.AionItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@Slf4j
public class ShopController {

    @Autowired
    private DBClient dbClient;

    @PostMapping(value = "/buy")
    public String buyItem(@RequestBody AionItem item) {
        try {
            final TokenRequestHandler tokenRequestHandler = new TokenRequestHandler(dbClient, item.getToken());
            if (!tokenRequestHandler.checkToken()) {
                log.error("Failed to verify user token {}", item.getToken());
                return "Failed to verify user token";
            }


            final ShopRequestHandler shopRequestHandler = new ShopRequestHandler(dbClient, item, tokenRequestHandler.getUserFromToken());
            if (shopRequestHandler.registerItem()) {
                return "Successfully registered item in db";
            }
        } catch (SQLException e) {
            log.error("Failed to connect to user database to check token {}", item.getToken(), e);
            return "Failed to connect to user database to check token";
        }
        return "Failed to register item";
    }
}
