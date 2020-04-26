package com.aion.server.controller;

import com.aion.server.database.entity.login.AccountData;
import com.aion.server.database.entity.game.Shop;
import com.aion.server.service.*;
import com.aion.server.service.infra.dto.AionItem;
import com.aion.server.service.infra.dto.ShardsPurchase;
import com.aion.server.service.infra.exception.LoginException;
import com.aion.server.service.infra.exception.ShopException;
import com.aion.server.service.infra.exception.UserDoesntExistException;
import com.aion.server.service.infra.exception.UserExistException;
import com.aion.server.service.infra.utils.CurrencyConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import java.util.List;
import java.util.Optional;

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
    @PostMapping(value = "/purchase/shards", consumes = "application/json", produces = "application/json")
    public String purchaseShards(@RequestBody ShardsPurchase purchase) {
//        try {
        if (!tokenService.checkToken(purchase.getToken())) {
            log.info("Failed to verify token for user {}", purchase.getUserId());
            return "Failed to verify user token";
        }

        if (!loginService.checkAccountIsActivated(purchase.getUserId())) {
            log.info("Player {} has not activate is account ", purchase.getUserId());
            return "Player has not activate is account";
        }

//            final int amountResponse = paypalService.checkPurchase(purchase);
        final int amountResponse = 10;
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
//        } catch (IOException e) {
//            log.error("Failed to getDetails on purchase {}", purchase.getTransactionId(), e);
//        }
        return "A problem happened in getting details from Paypal for purchase " + purchase.getTransactionId() + " please contact our support on discord";
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/buy", consumes = "application/json", produces = "application/json")
    public String buyItem(@RequestBody AionItem item) {
        try {
            if (!tokenService.checkToken(item.getToken())) {
                log.info("Failed to verify token for user {}", item.getIdPlayer());
                return "Failed to verify user token";
            }
            final Optional<AccountData> userFromToken = tokenService.getUserFromToken(item.getToken());
            if (userFromToken.isPresent()) {
                final AccountData accountData = userFromToken.get();
                if (shopService.canPerform(item, accountData)) {
                    shopService.registerItem(item, accountData);
                    return "Successfully registered item in db";
                }
            }
        } catch (LoginException e) {
            log.error("Failed to find user {} in database", item.getIdPlayer(), e);
            return "Failed to find user";
        } catch (ShopException e) {
            log.error("Failed to find item {} for user {}", item.getIdItem(), item.getIdPlayer(), e);
            return "Failed to find item";
        }
        return "Failed to purchase item, I don't know why";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/listshop")
    public List<Shop> getListShopItem(@RequestParam(value = "category", defaultValue = "all") final String category) {
        if (category.equals("all")) {
            log.info("Get shop list");
            return shopService.getShopList();
        }
        log.info("Get shop list by {}", category);
        return shopService.getShopListByCategory(category);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/listshopcategory")
    public List<String> getListCategory() {
        log.info("Get shop list by categories");
        return shopService.getShopCategoryList();
    }
}
