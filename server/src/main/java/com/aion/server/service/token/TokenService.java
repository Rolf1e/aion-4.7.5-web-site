package com.aion.server.service.token;

import com.aion.server.database.dto.SQLQuery;
import com.aion.server.database.dto.SQLQueryBuilder;
import com.aion.server.database.infra.DBClient;
import com.aion.server.service.dto.InputUserInfos;
import com.aion.server.service.dto.OutputUserInfos;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.aion.server.database.config.LoginConfig.*;
import static com.aion.server.database.dto.SQLQuery.Condition;
import static com.aion.server.database.dto.SQLQuery.ConditionType;
import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord.INSERT;
import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord.SELECT;
import static java.util.Collections.singletonList;

@Slf4j
public class TokenService {

    private InputUserInfos userInfos;
    private DBClient dbClient;

    public TokenService(InputUserInfos userInfos,
                        DBClient dbClient) {

        this.userInfos = userInfos;
        this.dbClient = dbClient;
    }

    public OutputUserInfos getUserWithToken() {
        return new OutputUserInfos(userInfos.getUsername(), userInfos.getPassword(), getToken());
    }

    private String getToken() {
        try {
            openDBConnection();
            final Map<String, String> select = select();
            if (select.isEmpty()) {
                return "Bad credentials";
            }
            String token = select.get(TOKEN_COLUMN);
            if (token.equals("")) {
                token = TokenGenerator.generate();
                registerToken(token);
            }
            return token;
        } catch (SQLException e) {
            log.error("Can not retrieve token for user {}", userInfos.getUsername(), e);
            return "";
        } finally {
            closeDBConnection();
        }
    }

    private Map<String, String> select() throws SQLException {
        return dbClient.select(toSelectToken(), SELECT);
    }

    private void registerToken(String token) throws SQLException {
        dbClient.insert(toInsertToken(token), INSERT);
    }

    private SQLQuery toInsertToken(String token) {
        return SQLQueryBuilder.buildInsertQuery(
                Collections.singletonList(TOKEN_COLUMN),
                Collections.singletonList(USERS_TABLE),
                Collections.singletonList(token)
        );
    }

    private SQLQuery toSelectToken() {
        return SQLQueryBuilder.buildSelectQuery(
                singletonList(TOKEN_COLUMN),
                singletonList(USERS_TABLE),
                singletonList(new Condition(getWhere(), ConditionType.EQUAL))
        );
    }

    private Map<String, String> getWhere() {
        Map<String, String> where = new HashMap<>();
        where.put(USERNAME_COLUMN, userInfos.getUsername());
        where.put(PASSWORD_COLUMN, userInfos.getPassword());//todo Ask for auth encryption
        return where;
    }

    private void openDBConnection() {
        try {
            dbClient.connect();
        } catch (SQLException e) {
            log.error("Failed to open connection login database for user {}", userInfos.getUsername(), e);
        }
    }

    private void closeDBConnection() {
        try {
            dbClient.disconnect();
        } catch (SQLException e) {
            log.error("Failed to close connection with login database for user {}", userInfos.getUsername(), e);
        }
    }

}
