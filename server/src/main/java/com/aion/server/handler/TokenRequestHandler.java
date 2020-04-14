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

import static com.aion.server.database.config.TableDBConfig.*;
import static com.aion.server.database.dto.SQLQuery.Condition;
import static com.aion.server.database.dto.SQLQuery.ConditionType;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Slf4j
public class TokenRequestHandler extends AbstractRequestHandler {

    private String toCheck;

    public TokenRequestHandler(DBClient dbClient,
                               InputUserInfos userInfos) {

        super(dbClient, userInfos);
    }

    public TokenRequestHandler(DBClient dbClient,
                               String token) {

        super(dbClient);
        this.toCheck = token;
    }

    //TODO add id user
    public OutputUserInfos getUserWithToken() {
        return new OutputUserInfos(userInfos.getUsername(), EncryptionService.toDecode(userInfos.getPassword()), getToken());
    }

    public boolean checkToken() throws SQLException {
        final Map<String, String> select = select(selectUsernameFromToken());
        return !select.isEmpty();
    }

    public OutputUserInfos getUserFromToken() throws SQLException {
        final Map<String, String> select = select(selectUsernameFromToken());
        return new OutputUserInfos(select.get(USERNAME_ID_COLUMN), select.get(USERNAME_COLUMN), select.get(PASSWORD_COLUMN), select.get(TOKEN_COLUMN));
    }

    private String getToken() {
        try {
            final Map<String, String> select = select(toSelectToken());
            if (select.isEmpty()) {
                return "Bad credentials";
            }
            String token = select.get(TOKEN_COLUMN);
            if (token == null) {
                token = TokenGenerator.generate();
                insert(toUpdateToken(token));
            }
            return token;
        } catch (SQLException e) {
            log.error("Can not retrieve token for user {}", userInfos.getUsername(), e);
            return "";
        }
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

    private SQLQuery selectUsernameFromToken() {
        return SQLQueryBuilder.buildSelectQuery(
                asList(USERNAME_ID_COLUMN, USERNAME_COLUMN, PASSWORD_COLUMN, TOKEN_COLUMN),
                singletonList(USERS_TABLE),
                singletonList(new Condition(getWhereToken(), ConditionType.EQUAL))
        );
    }

    private Map<String, String> getWhereToken() {
        Map<String, String> where = new HashMap<>();
        where.put(TOKEN_COLUMN, toCheck);
        return where;
    }
}
