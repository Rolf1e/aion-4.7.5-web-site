package com.aion.server.handler;

import com.aion.server.database.dto.SQLQuery;
import com.aion.server.database.dto.SQLQueryBuilder;
import com.aion.server.database.infra.DBClient;
import com.aion.server.handler.dto.AionItem;
import com.aion.server.handler.dto.InputUserInfos;
import com.aion.server.handler.dto.OutputUserInfos;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.aion.server.database.config.TableDBConfig.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Slf4j
public class ShopRequestHandler extends AbstractRequestHandler {

    private AionItem aionItem;

    public ShopRequestHandler(DBClient dbClient,
                              AionItem aionItem,
                              OutputUserInfos outputUserInfos) {

        super(dbClient, new InputUserInfos(outputUserInfos));
        this.aionItem = aionItem;
    }

    public boolean canPerform() throws SQLException {
        final Map<String, String> moneyAmount = select(toSelectMoneyForUser());
        final Map<String, String> itemPrice = select(toSelectItemPrice());

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

    public void registerItem() {
        try {
            insert(toInsertItem());
        } catch (SQLException e) {
            log.error("Failed to add item to player {}", aionItem.getIdPlayer(), e);
        }
    }

    private SQLQuery toInsertItem() {
        return SQLQueryBuilder.buildInsertQuery(
                asList(PLAYER_ID_COLUMN, ITEM_PLAYER_ID_COLUMN, ITEM_COUNT_COLUMN),
                singletonList(SHOP_TABLE),
                asList(userInfos.getId(), aionItem.getIdItem(), aionItem.getCountItem())
        );
    }

    private SQLQuery toSelectMoneyForUser() {
        return SQLQueryBuilder.buildSelectQuery(
                asList(USERNAME_COLUMN, AMOUNT_MONEY_USER),
                singletonList(USERS_TABLE),
                singletonList(new SQLQuery.Condition(getMoneyUserWhere(), SQLQuery.ConditionType.EQUAL))
        );
    }

    private Map<String, String> getMoneyUserWhere() {
        Map<String, String> where = new HashMap<>();
        where.put(USERNAME_COLUMN, userInfos.getUsername());
        where.put(TOKEN_COLUMN, userInfos.getToken());
        return where;
    }

    private SQLQuery toSelectItemPrice() {
        return SQLQueryBuilder.buildSelectQuery(
                asList(ITEM_PRICE_COLUMN),
                singletonList(ITEM_TABLE),
                singletonList(new SQLQuery.Condition(getItemPriceWhere(), SQLQuery.ConditionType.EQUAL))
        );
    }

    private Map<String, String> getItemPriceWhere() {
        Map<String, String> where = new HashMap<>();
        where.put(ITEM_ID_COLUMN, aionItem.getIdItem());
        return where;
    }


}
