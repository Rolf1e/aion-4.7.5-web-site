package com.aion.server.handler;

import com.aion.server.database.dto.SQLQuery;
import com.aion.server.database.dto.SQLQueryBuilder;
import com.aion.server.database.infra.DBClient;
import com.aion.server.handler.dto.AionItem;
import com.aion.server.handler.dto.InputUserInfos;
import com.aion.server.handler.dto.OutputUserInfos;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Collections;

import static com.aion.server.database.config.TableDBConfig.*;
import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord.INSERT;
import static java.util.Arrays.asList;

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
        if (!dbStateController.getState()) {
            openDBConnection();
        }
        try {
            registerItemToDB();
            return true;
        } catch (SQLException e) {
            log.error("Failed to add item to player {}", aionItem.getIdPlayer(), e);
        }
        return false;
    }

    private void registerItemToDB() throws SQLException {
        dbClient.insert(toInsertItem(), INSERT);
    }

    private SQLQuery toInsertItem() {
        return SQLQueryBuilder.buildInsertQuery(
                asList(PLAYER_ID_COLUMN, ITEM_ID_COLUMN, ITEM_COUNT_COLUMN),
                Collections.singletonList(SHOP_TABLE),
                asList(userInfos.getId(), aionItem.getIdItem(), aionItem.getCountItem())
        );
    }

}
