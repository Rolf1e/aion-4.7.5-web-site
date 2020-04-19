package com.aion.server.service;

import com.aion.server.database.dto.SQLQuery;
import com.aion.server.database.dto.SQLQueryBuilder;
import com.aion.server.database.infra.DBClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.aion.server.database.config.TableDBConfig.*;
import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord.SELECT;
import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord.UPDATE;
import static java.util.Collections.singletonList;

@Slf4j
@Service
public class ShardService {

    private final DBClient dbClient;

    public ShardService(final DBClient dbClient) {
        this.dbClient = dbClient;
    }

    public boolean giveShardsToPlayer(final int idPlayer,
                                      final int shardAmount) {

        try {
            final Map<String, String> reponse = dbClient.select(toSelectOldAmount(idPlayer), SELECT);
            if (reponse.isEmpty()) {
                log.info("Failed to get old shard amount for player {}", idPlayer);
                return false;
            }
            final String stringOldAmount = reponse.get(SHARD_COLUMN);
            int oldAmount = Integer.parseInt(stringOldAmount);

            dbClient.insert(updateShardsInDb(oldAmount + shardAmount, idPlayer), UPDATE);
            log.info("Give {} shards to player {}", shardAmount, idPlayer);
            return true;
        } catch (SQLException e) {
            log.error("Failed to give {} shards to player {}", shardAmount, idPlayer, e);
        }
        return false;
    }

    private SQLQuery updateShardsInDb(final int shardAmount,
                                      final int playerId) {
        return SQLQueryBuilder.buildUpdateQuery(
                singletonList(USERS_TABLE),
                singletonList(new SQLQuery.Condition(getSet(shardAmount), SQLQuery.ConditionType.EQUAL)),
                singletonList(new SQLQuery.Condition(getWhere(playerId), SQLQuery.ConditionType.EQUAL))
        );
    }

    private Map<String, String> getSet(final int shardAmount) {
        Map<String, String> set = new HashMap<>();
        set.put(SHARD_COLUMN, String.valueOf(shardAmount));
        return set;
    }

    private SQLQuery toSelectOldAmount(final int playerId) {
        return SQLQueryBuilder.buildSelectQuery(
                singletonList(SHARD_COLUMN),
                singletonList(USERS_TABLE),
                singletonList(new SQLQuery.Condition(getWhere(playerId), SQLQuery.ConditionType.EQUAL))
        );
    }

    private Map<String, String> getWhere(final int playerId) {
        Map<String, String> where = new HashMap<>();
        where.put(USERNAME_ID_COLUMN, String.valueOf(playerId));
        return where;
    }
}
