package com.aion.server.controller;

import com.aion.server.database.entity.login.AccountData;
import com.aion.server.database.entity.game.Shop;
import com.aion.server.service.*;
import com.aion.server.service.infra.dto.AionItemPurchase;
import com.aion.server.service.infra.dto.ShardsPurchase;
import com.aion.server.service.infra.exception.*;
import com.aion.server.service.infra.utils.CurrencyConverter;
import com.aion.server.service.infra.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @CrossOrigin(origins = "http://51.178.130.119:3000")
    @PostMapping(value = "/purchase/shards", consumes = "application/json", produces = "application/json")
    public ShardsPurchase purchaseShards(@RequestBody ShardsPurchase purchase) {
        final String transactionId = purchase.getTransactionId();
        final String token = purchase.getToken();
        try {
            final AccountData accountData = tokenRefresherService.refreshToken(token);
            final Date updatedAt = accountData.getUpdatedAt();
            final long accountDataId = accountData.getId();
            if (!updatedAt.equals(DateUtils.resolveDate(updatedAt))) {
                log.info("Token has been renewed {}", accountDataId);
            }

            if (!loginService.checkAccountIsActivated(accountData.getId())) {
                log.info("Player {} has not activate is account ", accountDataId);
                return new ShardsPurchase(transactionId, accountData.getToll(), true, "Player's account is not activated");
            }

            final double amountResponse = paypalService.checkPurchase(purchase);
            if (amountResponse == 0) {
                log.info("Failed to purchase shards for transaction accountDataId {}", transactionId);
                return new ShardsPurchase(transactionId, accountData.getToll(), true, "Failed to purchase shards for transaction accountDataId " + transactionId);
            }
            final int convert = (int) new CurrencyConverter().convert(amountResponse);

            if (!shardService.checkAlreadyUsed(transactionId)
                    && shardService.giveShardsToPlayer(accountDataId, convert)) {
                shardService.savePayments(transactionId, accountDataId);
                log.info("{} purchased {} shards for {} €", accountDataId, convert, amountResponse);
                return new ShardsPurchase(transactionId, accountData.getToll(), false, "You purchased " + convert + " shards for " + amountResponse + "€");
            }
            log.info("Attempt to reuse transaction {} to by shards", transactionId);
            return new ShardsPurchase(transactionId, accountData.getToll(), true, "This transaction " + transactionId + " may already have been handle, please contact our team ou discord if it's not normal");
        } catch (TokenRefresherException e) {
            log.error("Failed to verify token {}", token);
            return new ShardsPurchase(transactionId, 0, true, "Failed to verify user token");
        } catch (IOException e) {
            log.error("Failed to getDetails on purchase {}", transactionId, e);
            return new ShardsPurchase(transactionId, 0, true, "A problem happened in getting details from Paypal for purchase " + transactionId + " please contact our support on discord");
        }
    }

    @CrossOrigin(origins = "http://51.178.130.119:3000")
    @PostMapping(value = "/buy", consumes = "application/json", produces = "application/json")
    public AionItemPurchase buyItem(@RequestBody AionItemPurchase item) {
        final String token = item.getToken();
        try {
            final AccountData accountData = tokenRefresherService.refreshToken(token);
            final Date updatedAt = accountData.getUpdatedAt();
            if (!updatedAt.equals(DateUtils.resolveDate(updatedAt))) {
                log.info("Token has been renewed {}", accountData.getId());
            }

            if (!loginService.checkAccountIsActivated(accountData.getId())) {
                log.info("Player {} has not activate is account ", accountData.getId());
                return new AionItemPurchase(item, accountData.getToll(), true, "Player has not activate is account");
            }

            if (playerInformationService.checkPlayerExist(item.getRecipient())
                    && shopService.canPerform(item, accountData)) {
                shopService.registerItem(item, accountData);
                return new AionItemPurchase(item, accountData.getToll(), false, "Successfully registered item in db");
            }
            log.info("User {} failed to perform purchase ", accountData.getId());
            return new AionItemPurchase(item, accountData.getToll(), true, "You don't have enough shards and|or your player doesn't exist");

        } catch (LoginException e) {
            log.error("Failed to find user {} in database for token {}", token, e);
            return new AionItemPurchase(item, 0, true, "Failed to find user");
        } catch (ShopException e) {
            log.error("Failed to find item {} for user token {}", item.getIdItem(), token, e);
            return new AionItemPurchase(item, 0, true, "Failed to find item");
        } catch (TokenRefresherException e) {
            log.error("Failed to verify token {}", token);
            return new AionItemPurchase(item, 0, true, "Failed to verify user token");
        }
    }

    @CrossOrigin
    @GetMapping(value = "/list-shop")
    public List<Shop> getListShopItem(@RequestParam(value = "category", defaultValue = "all") final String category) {
        if (category.equals("all")) {
            log.info("Get shop list");
            return shopService.getShopList();
        }
        log.info("Get shop list by {}", category);
        return shopService.getShopListByCategory(category);
    }

    @CrossOrigin
    @GetMapping(value = "/list-shop/category")
    public List<String> getListCategory() {
        log.info("Get shop list by categories");
        return shopService.getShopCategoryList();
    }
}
