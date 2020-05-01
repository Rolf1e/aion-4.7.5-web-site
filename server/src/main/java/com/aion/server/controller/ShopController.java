package com.aion.server.controller;

import com.aion.server.database.entity.login.AccountData;
import com.aion.server.database.entity.game.Shop;
import com.aion.server.service.*;
import com.aion.server.service.infra.dto.AionItem;
import com.aion.server.service.infra.dto.ShardsPurchase;
import com.aion.server.service.infra.exception.*;
import com.aion.server.service.infra.utils.CurrencyConverter;
import com.aion.server.service.infra.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class ShopController {

    private final ShopService shopService;
    private final PaypalService paypalService;
    private final ShardService shardService;
    private final LoginService loginService;
    private final PlayerInformationService playerInformationService;
    private final TokenRefresherService tokenRefresherService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/purchase/shards", consumes = "application/json", produces = "application/json")
    public String purchaseShards(@RequestBody ShardsPurchase purchase) {
        try {
            final Date updatedAt = tokenRefresherService.refreshToken(purchase.getToken()).getUpdatedAt();
            if (!updatedAt.equals(DateUtils.getCurrentDate())) {
                log.info("Token has been renewed {}", purchase.getUserId());
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
        } catch (TokenRefresherException e) {
            log.error("Failed to verify token for user {}", purchase.getUserId());
            return "Failed to verify user token";
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
            final AccountData accountData = tokenRefresherService.refreshToken(item.getToken());
            if (!accountData.getUpdatedAt().equals(DateUtils.getCurrentDate())) {
                log.info("Token has been renewed {}", item.getIdPlayer());
            }

            if (playerInformationService.checkPlayerExist(item.getRecipient())
                    && shopService.canPerform(item, accountData)) {
                shopService.registerItem(item, accountData);
                return "Successfully registered item in db";
            }
            log.info("You may not perform purchase & or your player doesn't exist");
            return "You may not perform purchase & or your player doesn't exist";

        } catch (LoginException e) {
            log.error("Failed to find user {} in database", item.getIdPlayer(), e);
            return "Failed to find user";
        } catch (ShopException e) {
            log.error("Failed to find item {} for user {}", item.getIdItem(), item.getIdPlayer(), e);
            return "Failed to find item";
        } catch (TokenRefresherException e) {
            log.error("Failed to verify token for user {}", item.getIdPlayer());
            return "Failed to verify user token";
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/list-shop")
    public List<Shop> getListShopItem(@RequestParam(value = "category", defaultValue = "all") final String category) {
        if (category.equals("all")) {
            log.info("Get shop list");
            return shopService.getShopList();
        }
        log.info("Get shop list by {}", category);
        return shopService.getShopListByCategory(category);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/list-shop/category")
    public List<String> getListCategory() {
        log.info("Get shop list by categories");
        return shopService.getShopCategoryList();
    }
}
