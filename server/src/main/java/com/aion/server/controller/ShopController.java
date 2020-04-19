package com.aion.server.controller;

import com.aion.server.service.*;
import com.aion.server.service.infra.dto.AionItem;
import com.aion.server.service.infra.dto.InputUserInfos;
import com.aion.server.service.infra.dto.ShardsPurchase;
import com.aion.server.service.infra.exception.UserDoesntExistException;
import com.aion.server.service.infra.utils.CurrencyConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@Slf4j
@RestController
@AllArgsConstructor
public class ShopController {

    private final TokenService tokenService;
    private final ShopService shopService;
    private final PaypalService paypalService;
    private final ShardService shardService;
    private final LoginService loginService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/purchase/shards")
    public String purchaseShards(@RequestBody ShardsPurchase purchase) {
        try {
            if (!tokenService.checkToken(purchase.getToken())) {
                log.info("Failed to verify token for user {}", purchase.getUserId());
                return "Failed to verify user token";
            }

            if (!loginService.checkAccountIsActivated(purchase.getUserId())) {
                log.info("Player {} has not activate is account ", purchase.getUserId());
                return "Player has not activate is account";
            }

            final int amountResponse = paypalService.checkPurchase(purchase);
            if (amountResponse == 0) {
                log.info("Failed to purchase shards for transaction id {}", purchase.getTransactionId());
                return "Failed to purchase shards for transaction id " + purchase.getTransactionId();
            }
            if (amountResponse == purchase.getTransactionAmount()) {
                final int convert = (int) new CurrencyConverter().convert(amountResponse);

                if (shardService.giveShardsToPlayer(purchase.getUserId(), convert)) {
                    return "You purchased " + convert + " shards for " + purchase.getTransactionAmount() + "";
                }
            }
        } catch (IOException e) {
            log.error("Failed to getDetails on purchase {}", purchase.getTransactionId(), e);
        } catch (SQLException e) {
            log.error("Failed to connect to user database to check token for user {}", purchase.getUserId(), e);
            return "Failed to connect to user database to check token";
        }
        return "A problem happened in getting details from Paypal for purchase " + purchase.getTransactionId() + " please contact our support on discord";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/buy")
    public String buyItem(@RequestBody AionItem item) {
        try {
            if (!tokenService.checkToken(item.getToken())) {
                log.info("Failed to verify token for user {}", item.getIdPlayer());
                return "Failed to verify user token";
            }
            final InputUserInfos inputUserInfos = new InputUserInfos(tokenService.getUserFromToken(item.getToken()));
            if (shopService.canPerform(item, inputUserInfos)) {
                shopService.registerItem(item, inputUserInfos);
                return "Successfully registered item in db";
            }
        } catch (SQLException e) {
            log.error("Failed to connect to user database to check token for user {}", item.getIdItem(), e);
            return "Failed to connect to user database to check token";
        } catch (UserDoesntExistException e) {
            log.error("There is no user with id {} doesn't exist", item.getIdItem(), e);
        }
        return "Failed to register item";
    }
}

