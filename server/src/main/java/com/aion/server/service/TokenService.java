package com.aion.server.service;

import com.aion.server.database.dto.SQLQuery;
import com.aion.server.database.dto.SQLQueryBuilder;
import com.aion.server.database.infra.DBClient;
import com.aion.server.database.infra.SQLQueryAdaptor;
import com.aion.server.handler.dto.InputUserInfos;
import com.aion.server.handler.dto.OutputUserInfos;
import com.aion.server.handler.exception.UserDoesntExistException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.aion.server.database.config.TableDBConfig.*;
import static com.aion.server.database.config.TableDBConfig.TOKEN_COLUMN;
import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Service
public class TokenService {

    private final DBClient dbClient;

    public TokenService(final DBClient dbClient) {
        this.dbClient = dbClient;
    }

    public boolean checkToken(final String token) throws SQLException {
        return !dbClient.select(selectUsernameFromToken(token), SELECT)
                .isEmpty();
    }

    public OutputUserInfos getUserFromToken(final String token) throws SQLException, UserDoesntExistException {
        final Map<String, String> select = dbClient.select(selectUsernameFromToken(token), SELECT);
        if (select.isEmpty()) {
            throw new UserDoesntExistException(token);
        }
        return new OutputUserInfos(select.get(USERNAME_ID_COLUMN), select.get(USERNAME_COLUMN), select.get(PASSWORD_COLUMN), select.get(TOKEN_COLUMN));
    }

    private SQLQuery toUpdateToken(final String token,
                                   final InputUserInfos userInfos) {
        return SQLQueryBuilder.buildUpdateQuery(
                singletonList(USERS_TABLE),
                singletonList(new SQLQuery.Condition(getSet(token), SQLQuery.ConditionType.EQUAL)),
                singletonList(new SQLQuery.Condition(getWhere(userInfos), SQLQuery.ConditionType.EQUAL))
        );
    }

    private Map<String, String> getSet(final String token) {
        Map<String, String> set = new HashMap<>();
        set.put(TOKEN_COLUMN, token);
        return set;
    }

    private SQLQuery toSelectToken(final InputUserInfos userInfos) {
        return SQLQueryBuilder.buildSelectQuery(
                asList(TOKEN_COLUMN, USERNAME_ID_COLUMN),
                singletonList(USERS_TABLE),
                singletonList(new SQLQuery.Condition(getWhere(userInfos), SQLQuery.ConditionType.EQUAL))
        );
    }

    private Map<String, String> getWhere(final InputUserInfos userInfos) {
        String encryptedPassword = EncryptionService.toEncode(userInfos.getPassword());
        Map<String, String> where = new HashMap<>();
        where.put(USERNAME_COLUMN, userInfos.getUsername());
        where.put(PASSWORD_COLUMN, encryptedPassword);
        return where;
    }

    private SQLQuery selectUsernameFromToken(final String token) {
        return SQLQueryBuilder.buildSelectQuery(
                asList(USERNAME_ID_COLUMN, USERNAME_COLUMN, PASSWORD_COLUMN, TOKEN_COLUMN),
                singletonList(USERS_TABLE),
                singletonList(new SQLQuery.Condition(getWhereToken(token), SQLQuery.ConditionType.EQUAL))
        );
    }

    private Map<String, String> getWhereToken(final String token) {
        Map<String, String> where = new HashMap<>();
        where.put(TOKEN_COLUMN, token);
        return where;
    }


}
