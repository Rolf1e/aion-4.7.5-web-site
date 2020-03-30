package com.aion.server.handler;

import com.aion.server.database.dto.SQLQuery;
import com.aion.server.database.dto.SQLQueryBuilder;
import com.aion.server.database.infra.DBClient;
import com.aion.server.handler.dto.InputUserInfos;
import com.aion.server.handler.dto.OutputUserInfos;
import com.aion.server.handler.utils.TokenGenerator;
import com.aion.server.service.EncryptionService;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.aion.server.database.config.LoginConfig.*;
import static com.aion.server.database.dto.SQLQuery.Condition;
import static com.aion.server.database.dto.SQLQuery.ConditionType;
import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord.SELECT;
import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord.UPDATE;
import static java.util.Collections.singletonList;

@Slf4j
public class TokenRequestHandler extends AbstractRequestHandler {

    public TokenRequestHandler(DBClient dbClient,
                               InputUserInfos userInfos) {

        super(dbClient, userInfos);
    }

    public OutputUserInfos getUserWithToken() {
        return new OutputUserInfos(userInfos.getUsername(), userInfos.getPassword(), getToken());
    }

    private String getToken() {
        try {
            if (!dbStateController.getState()) {
                openDBConnection();
            }
            final Map<String, String> select = select();
            if (select.isEmpty()) {
                return "Bad credentials";
            }
            String token = select.get(TOKEN_COLUMN);
            if (token == null) {
                token = TokenGenerator.generate();
                registerToken(token);
            }
            return token;
        } catch (SQLException e) {
            log.error("Can not retrieve token for user {}", userInfos.getUsername(), e);
            return "";
        } finally {
            if (dbStateController.getState()) {
                closeDBConnection();
            }
        }
    }

    private Map<String, String> select() throws SQLException {
        return dbClient.select(toSelectToken(), SELECT);
    }

    private void registerToken(String token) throws SQLException {
        dbClient.insert(toUpdateToken(token), UPDATE);
    }

    private SQLQuery toUpdateToken(String token) {
        return SQLQueryBuilder.buildUpdateQuery(
                singletonList(USERS_TABLE),
                singletonList(new SQLQuery.Condition(getSet(token), SQLQuery.ConditionType.EQUAL)),
                singletonList(new SQLQuery.Condition(getWhere(), SQLQuery.ConditionType.EQUAL))
        );
    }

    private Map<String, String> getSet(String token) {
        Map<String, String> set = new HashMap<>();
        set.put(TOKEN_COLUMN, token);
        return set;
    }

    private SQLQuery toSelectToken() {
        return SQLQueryBuilder.buildSelectQuery(
                singletonList(TOKEN_COLUMN),
                singletonList(USERS_TABLE),
                singletonList(new Condition(getWhere(), ConditionType.EQUAL))
        );
    }

    private Map<String, String> getWhere() {
        String encryptedPassword = EncryptionService.toEncode(userInfos.getPassword());
        Map<String, String> where = new HashMap<>();
        where.put(USERNAME_COLUMN, userInfos.getUsername());
        where.put(PASSWORD_COLUMN, encryptedPassword);
        return where;
    }

}
