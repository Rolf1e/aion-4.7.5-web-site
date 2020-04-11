package com.aion.server.handler;

import com.aion.server.database.dto.SQLQuery;
import com.aion.server.database.dto.SQLQueryBuilder;
import com.aion.server.database.infra.DBClient;
import com.aion.server.handler.dto.AionItem;
import com.aion.server.handler.dto.InputUserInfos;
import com.aion.server.handler.dto.OutputUserInfos;
import com.aion.server.service.EncryptionService;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.aion.server.database.config.TableDBConfig.*;
import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord.INSERT;
import static java.util.Arrays.asList;
import static java.util.Collections.*;

@Slf4j
public class ShopRequestHandler extends AbstractRequestHandler {

    private AionItem aionItem;

    public ShopRequestHandler(DBClient dbClient,
                              AionItem aionItem,
                              OutputUserInfos outputUserInfos) {

        super(dbClient, new InputUserInfos(outputUserInfos));
        this.aionItem = aionItem;
    }

    public boolean registerItem() {
        try {
            insert(toInsertItem());

            return true;
        } catch (SQLException e) {
            log.error("Failed to add item to player {}", aionItem.getIdPlayer(), e);
        }
        return false;
    }

    private SQLQuery toInsertItem() {
        return SQLQueryBuilder.buildInsertQuery(
                asList(PLAYER_ID_COLUMN, ITEM_ID_COLUMN, ITEM_COUNT_COLUMN),
                singletonList(SHOP_TABLE),
                asList(userInfos.getId(), aionItem.getIdItem(), aionItem.getCountItem())
        );
    }

    private SQLQuery toSelectMoneyForUser() {
        return SQLQueryBuilder.buildSelectQuery(
                asList(USERNAME_COLUMN, AMOUNT_MONEY_USER),
                singletonList(USERS_TABLE),
                singletonList(new SQLQuery.Condition(getWhere(), SQLQuery.ConditionType.EQUAL))
        );
    }

    private Map<String, String> getWhere() {
        Map<String, String> where = new HashMap<>();
        where.put(USERNAME_COLUMN, userInfos.getUsername());
        where.put(TOKEN_COLUMN, userInfos.getToken());
        return where;
    }
}
