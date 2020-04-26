package com.aion.server.service;

import com.aion.server.database.entity.login.AccountData;
import com.aion.server.database.entity.game.Shop;
import com.aion.server.database.entity.game.WebShop;
import com.aion.server.database.repositories.game.ShopRepository;
import com.aion.server.database.repositories.game.WebShopRepository;
import com.aion.server.database.repositories.login.AccountDataRepository;
import com.aion.server.service.infra.dto.AionItem;
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

    public boolean canPerform(final AionItem aionItem,
                              final AccountData accountData) throws LoginException, ShopException {

        final long playerWallet = getUserMoney(accountData);
        final long itemPrice = getItemPrice(aionItem);
        return playerWallet >= itemPrice;
    }

    public WebShop registerItem(final AionItem aionItem,
                                final AccountData accountData) throws LoginException, ShopException {

        if (canPerform(aionItem, accountData)) {
            accountData.takeToll(getItemPrice(aionItem));
            accountDataRepository.save(accountData);
            log.info("Subtract player money {}", accountData.getId());
            //TODO check if it exist
            final Optional<Shop> itemById = getItemById(aionItem);
            if (itemById.isPresent()) {
                final Shop item = itemById.get();
                final WebShop webShop = new WebShop(aionItem.getRecipient(), item.getItemDescription(),
                        item.getItemId(), aionItem.getCountItem(), item.getItemPrice());
                log.info("Give item {} to player {}", aionItem.getIdItem(), accountData.getId());
                return webShopRepository.save(webShop);
            }
            throw new ShopInsertionException(aionItem.getIdItem(), accountData.getId());
        }
        throw new ShopPerformPurchaseException(aionItem.getIdItem(), accountData.getId());
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

    private long getItemPrice(final AionItem aionItem) throws ItemDoesntExistException {
        final Optional<Shop> itemById = getItemById(aionItem);
        if (itemById.isPresent()) {
            return itemById.get().getItemPrice();
        }
        throw new ItemDoesntExistException(aionItem.getIdItem());
    }

    private Optional<Shop> getItemById(final AionItem aionItem) {
        return shopRepository.findById(aionItem.getIdItem());
    }

    private long getUserMoney(final AccountData accountData) throws UserDoesntExistException {
        final Optional<AccountData> accountDataById = accountDataRepository.findById((long) accountData.getId());
        if (accountDataById.isPresent()) {
            return accountDataById.get().getToll();
        }
        throw new UserDoesntExistException(accountData);
    }
}
