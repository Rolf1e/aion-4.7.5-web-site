package com.aion.server.service.login;

import com.aion.server.controller.dto.Login;
import com.aion.server.database.dto.SQLQuery;
import com.aion.server.database.dto.SQLQueryBuilder;
import com.aion.server.database.infra.DBClient;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord;

@Slf4j
public class LoginResolver {

    private DBClient dbClient;
    private Login login;

    public LoginResolver(DBClient dbClient,
                         Login login) {

        this.dbClient = dbClient;
        this.login = login;
    }

    public Boolean checkExist() {
        try {
            openDBConnection();
            return !dbClient.select(getSqlQuery(), SQLKeyWord.SELECT).isEmpty();
        } catch (SQLException e) {
            log.error("Failed to retrieve from database for user {}", login.getUsername(), e);
        } finally {
            /* I prefer to handle dbClose manually because website isn't supposed
             * to have a great traffic */
            closeDBConnection();
        }
        return false;
    }

    private SQLQuery getSqlQuery() {
        Map<String, String> where = new HashMap<>();
        where.put("username", login.getUsername());
        where.put("password", login.getPassword());

        return SQLQueryBuilder.buildSelectQuery(
                Arrays.asList(LoginConfig.USERNAME_COLUMN, LoginConfig.PASSWORD_COLUMN),
                Collections.singletonList(LoginConfig.USERS_TABLE),
                Collections.singletonList(new SQLQuery.Condition(where, SQLQuery.ConditionType.EQUAL))
        );
    }

    private void openDBConnection() {
        try {
            dbClient.connect();
        } catch (SQLException e) {
            log.error("Failed to open connection login database for user {}", login.getUsername(), e);
        }
    }

    private void closeDBConnection() {
        try {
            dbClient.disconnect();
        } catch (SQLException e) {
            log.error("Failed to close connection with login database for user {}", login.getUsername(), e);
        }
    }


}
