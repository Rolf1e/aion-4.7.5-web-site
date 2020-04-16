package com.aion.server.service;

import com.aion.server.database.dto.SQLQuery;
import com.aion.server.database.dto.SQLQueryBuilder;
import com.aion.server.database.infra.DBClient;
import com.aion.server.service.infra.dto.AionItem;
import com.aion.server.service.infra.dto.InputUserInfos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.aion.server.database.config.TableDBConfig.*;
import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord.INSERT;
import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord.SELECT;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Slf4j
@Service
public class ShopService {

    private final DBClient dbClient;

    public ShopService(DBClient dbClient) {
        this.dbClient = dbClient;
    }

    public boolean canPerform(final AionItem aionItem,
                              final InputUserInfos userInfos) throws SQLException {

        final Map<String, String> moneyAmount = dbClient.select(toSelectMoneyForUser(userInfos), SELECT);
        final Map<String, String> itemPrice = dbClient.select(toSelectItemPrice(aionItem), SELECT);

        if (!moneyAmount.isEmpty()) {
            final String playerWallet = moneyAmount.get(AMOUNT_MONEY_USER);
            final String price = itemPrice.get(ITEM_PRICE_COLUMN);
            if (Integer.parseInt(playerWallet) >= Integer.parseInt(price)) {
                return true;
            }
        }

        log.error("Failed to read money");
        return false;
    }

    public void registerItem(final AionItem aionItem,
                             final InputUserInfos userInfos) {
        try {
            dbClient.insert(toInsertItem(aionItem, userInfos), INSERT);
        } catch (SQLException e) {
            log.error("Failed to add item to player {}", aionItem.getIdPlayer(), e);
        }
    }

    private SQLQuery toInsertItem(final AionItem aionItem,
                                  final InputUserInfos userInfos) {

        return SQLQueryBuilder.buildInsertQuery(
                asList(PLAYER_ID_COLUMN, ITEM_PLAYER_ID_COLUMN, ITEM_COUNT_COLUMN),
                singletonList(SHOP_TABLE),
                asList(userInfos.getId(), aionItem.getIdItem(), aionItem.getCountItem())
        );
    }

    private SQLQuery toSelectMoneyForUser(final InputUserInfos userInfos) {
        return SQLQueryBuilder.buildSelectQuery(
                asList(USERNAME_COLUMN, AMOUNT_MONEY_USER),
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

}
