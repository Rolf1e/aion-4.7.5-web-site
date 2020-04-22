package com.aion.server.service;

import com.aion.server.database.dto.SQLQuery;
import com.aion.server.database.dto.SQLQueryBuilder;
import com.aion.server.database.infra.DBClient;
import com.aion.server.service.infra.dto.AionItem;
import com.aion.server.service.infra.dto.InputUserInfos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.aion.server.database.config.TableDBConfig.*;
import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Slf4j
@Service
public class ShopService {

    private final DBClient gameDB;
    private final DBClient loginDB;

    public ShopService(@Qualifier("ac47_server_gs") final DBClient gameDB,
                       @Qualifier("ac47_server_ls") final DBClient loginDB) {

        this.gameDB = gameDB;
        this.loginDB = loginDB;
    }

    public boolean canPerform(final AionItem aionItem,
                              final InputUserInfos userInfos) throws SQLException {

        final Map<String, String> moneyAmount = getUserMoney(userInfos);
        final Map<String, String> itemPrice = getItemPrice(aionItem);

        if (!moneyAmount.isEmpty() && !itemPrice.isEmpty()) {
            final String playerWallet = moneyAmount.get(SHARD_COLUMN);
            final String price = itemPrice.get(ITEM_PRICE_COLUMN);
            if (Integer.parseInt(playerWallet) >= Integer.parseInt(price) * Integer.parseInt(aionItem.getCountItem())) {
                return true;
            }
            log.error("Not enough Shards");
            return false;
        }
        log.error("Failed to read money");
        return false;
    }

    private Map<String, String> getItemPrice(final AionItem aionItem) throws SQLException {
        return gameDB.select(toSelectItemPrice(aionItem), SELECT);
    }

    private Map<String, String> getUserMoney(final InputUserInfos userInfos) throws SQLException {
        return loginDB.select(toSelectMoneyForUser(userInfos), SELECT);
    }

    public void registerItem(final AionItem aionItem,
                             final InputUserInfos userInfos) {
        try {
            final String playerWallet = getUserMoney(userInfos).get(SHARD_COLUMN);
            final String price = getItemPrice(aionItem).get(ITEM_PRICE_COLUMN);
            final int moneyToSubtract = Integer.parseInt(playerWallet) - Integer.parseInt(price) * Integer.parseInt(aionItem.getCountItem());
            loginDB.insert(toUpdateMoneyOfPlayer(userInfos, moneyToSubtract), UPDATE);
            log.info("Subtract player money {}", userInfos.getId());
            //TODO create player_shop
            gameDB.insert(toInsertItem(aionItem, userInfos), INSERT);
            log.info("Give item {} to player {}", aionItem.getIdItem(), userInfos.getId());
        } catch (SQLException e) {
            log.error("Failed to add item to player {}", aionItem.getIdPlayer(), e);
        }
    }

    private SQLQuery toInsertItem(final AionItem aionItem,
                                  final InputUserInfos userInfos) {

        return SQLQueryBuilder.buildInsertQuery(
                asList(RECIPIENT_COLUMN, ITEM_DESCRIPTION_COLUMN, ITEM_ID_COLUMN, SHOP_COUNT_COLUMN),
                singletonList(SHOP_TABLE),
                asList(userInfos.getUsername(), aionItem.getIdItem(), aionItem.getIdItem(), aionItem.getCountItem())
        );
    }

    private SQLQuery toSelectMoneyForUser(final InputUserInfos userInfos) {
        return SQLQueryBuilder.buildSelectQuery(
                asList(USERNAME_COLUMN, SHARD_COLUMN),
                singletonList(USERS_TABLE),
                singletonList(new SQLQuery.Condition(getMoneyUserWhere(userInfos), SQLQuery.ConditionType.EQUAL))
        );
    }

    private Map<String, String> getMoneyUserWhere(final InputUserInfos userInfos) {
        Map<String, String> where = new HashMap<>();
        where.put(USERNAME_COLUMN, userInfos.getUsername());
        where.put(TOKEN_COLUMN, userInfos.getToken());
        return where;
    }

    private SQLQuery toSelectItemPrice(final AionItem aionItem) {
        return SQLQueryBuilder.buildSelectQuery(
                asList(ITEM_PRICE_COLUMN),
                singletonList(ITEM_TABLE),
                singletonList(new SQLQuery.Condition(getItemPriceWhere(aionItem), SQLQuery.ConditionType.EQUAL))
        );
    }

    private Map<String, String> getItemPriceWhere(final AionItem aionItem) {
        Map<String, String> where = new HashMap<>();
        where.put(ITEM_ID_COLUMN, aionItem.getIdItem());
        return where;
    }

    private SQLQuery toUpdateMoneyOfPlayer(final InputUserInfos userInfos,
                                           final int amount) {
        return SQLQueryBuilder.buildUpdateQuery(
                singletonList(USERS_TABLE),
                singletonList(new SQLQuery.Condition(getSet(amount), SQLQuery.ConditionType.EQUAL)),
                singletonList(new SQLQuery.Condition(getMoneyUserWhere(userInfos), SQLQuery.ConditionType.EQUAL))
        );
    }

    private Map<String, String> getSet(final int amount) {
        Map<String, String> set = new HashMap<>();
        set.put(SHARD_COLUMN, String.valueOf(amount));
        return set;
    }
}
