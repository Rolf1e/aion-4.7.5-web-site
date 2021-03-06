package com.aion.server.service;

import com.aion.server.database.entity.login.AccountData;
import com.aion.server.database.entity.game.Shop;
import com.aion.server.database.entity.game.WebShop;
import com.aion.server.database.repositories.game.ShopRepository;
import com.aion.server.database.repositories.game.WebShopRepository;
import com.aion.server.database.repositories.login.AccountDataRepository;
import com.aion.server.service.infra.dto.AionItemPurchase;
import com.aion.server.service.infra.exception.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ShopService {

    private final AccountDataRepository accountDataRepository;
    private final WebShopRepository webShopRepository;
    private final ShopRepository shopRepository;

    public boolean canPerform(final AionItemPurchase aionItemPurchase,
                              final AccountData accountData) throws LoginException, ShopException {

        final long playerWallet = getUserMoney(accountData);
        final long itemPrice = getItemPrice(aionItemPurchase);
        return playerWallet >= itemPrice;
    }

    public WebShop registerItem(final AionItemPurchase aionItemPurchase,
                                final AccountData accountData) throws LoginException, ShopException {

        if (canPerform(aionItemPurchase, accountData)) {
            accountData.takeToll(getItemPrice(aionItemPurchase));
            accountDataRepository.save(accountData);
            log.info("Subtract player money {}", accountData.getId());
            final Optional<Shop> itemById = getItemByObjectId(aionItemPurchase);
            if (itemById.isPresent()) {
                final Shop item = itemById.get();
                final WebShop webShop = new WebShop(aionItemPurchase.getRecipient(), item.getItemDescription(),
                        item.getItemId(), aionItemPurchase.getCountItem() * item.getItemCount(), item.getItemPrice());
                log.info("Give item {} to player {} from account {}", aionItemPurchase.getObjectId(), aionItemPurchase.getRecipient(), accountData.getId());
                return webShopRepository.save(webShop);
            }
            throw new ShopInsertionException(aionItemPurchase.getObjectId(), accountData.getId());
        }
        throw new ShopPerformPurchaseException(aionItemPurchase.getObjectId(), accountData.getId());
    }

    public List<Shop> getShopList() {
        return (List<Shop>) shopRepository.findAll();
    }

    public List<String> getShopCategoryList() {
        return shopRepository.getItemCategory();
    }

    public List<Shop> getShopListByCategory(final String category) {
        return shopRepository.findAllByItemCategory(category);
    }

    private long getItemPrice(final AionItemPurchase aionItemPurchase) throws ItemDoesntExistException {
        final Optional<Shop> itemById = getItemByObjectId(aionItemPurchase);
        if (itemById.isPresent()) {
            return itemById.get().getItemPrice();
        }
        throw new ItemDoesntExistException(aionItemPurchase.getObjectId());
    }

    private Optional<Shop> getItemByObjectId(final AionItemPurchase aionItemPurchase) {
        return shopRepository.findByObjectId(aionItemPurchase.getObjectId());
    }

    private long getUserMoney(final AccountData accountData) throws UserDoesntExistException {
        final Optional<AccountData> accountDataById = accountDataRepository.findById(accountData.getId());
        if (accountDataById.isPresent()) {
            return accountDataById.get().getToll();
        }
        throw new UserDoesntExistException(accountData);
    }
}
