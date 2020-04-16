package com.aion.server.controller;

import com.aion.server.service.ShopService;
import com.aion.server.service.TokenService;
import com.aion.server.service.infra.dto.AionItem;
import com.aion.server.service.infra.dto.InputUserInfos;
import com.aion.server.service.infra.exception.UserDoesntExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@Slf4j
@RestController
public class ShopController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ShopService shopService;

    @PostMapping(value = "/buy")
    public String buyItem(@RequestBody AionItem item) {
        try {
            if (!tokenService.checkToken(item.getToken())) {
                log.error("Failed to verify user token {}", item.getToken());
                return "Failed to verify user token";
            }
            final InputUserInfos inputUserInfos = new InputUserInfos(tokenService.getUserFromToken(item.getToken()));
            if (shopService.canPerform(item, inputUserInfos)) {
                shopService.registerItem(item, inputUserInfos);
                return "Successfully registered item in db";
            }

        } catch (SQLException e) {
            log.error("Failed to connect to user database to check token {}", item.getToken(), e);
            return "Failed to connect to user database to check token";
        } catch (UserDoesntExistException e) {
            e.printStackTrace();
        }
        return "Failed to register item";
    }
}

