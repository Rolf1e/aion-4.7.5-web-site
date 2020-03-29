package com.aion.server.handler;

import com.aion.server.database.dto.SQLQuery;
import com.aion.server.database.dto.SQLQueryBuilder;
import com.aion.server.database.infra.DBClient;
import com.aion.server.handler.dto.InputUserInfos;
import com.aion.server.handler.dto.OutputUserInfos;
import com.aion.server.handler.exception.UserExistException;
import com.aion.server.service.EncryptionService;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.aion.server.database.config.LoginConfig.*;
import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord.*;
import static java.util.Arrays.*;
import static java.util.Collections.singletonList;

@Slf4j
public class RegisterHandler {

    private DBClient dbClient;
    private InputUserInfos userInfos;
    private String encryptedPassword;

    public RegisterHandler(DBClient dbClient,
                           InputUserInfos userInfos) {

        this.dbClient = dbClient;
        this.userInfos = userInfos;
        encryptedPassword = EncryptionService.toEncode(userInfos.getPassword());
    }

    public OutputUserInfos registerNewUser() throws UserExistException {
        try {
            openDBConnection();
            if (!checkRegistered()) {
                registerUserToDB();
                return new TokenHandler(userInfos, dbClient)
                        .getUserWithToken();
            }
            throw new UserExistException(userInfos.getUsername());
        } catch (SQLException e) {
            log.error("Can not reach player database", e);
            return new OutputUserInfos();
        } finally {
            closeDBConnection();
        }
    }

    public boolean checkRegistered() throws SQLException {
        return !dbClient.select(toSelectUser(), SELECT)
                .isEmpty();
    }

    private void registerUserToDB() throws SQLException {
        dbClient.insert(toInsertUser(), INSERT);
    }

    private SQLQuery toSelectUser() {
        return SQLQueryBuilder.buildSelectQuery(
                asList(USERNAME_COLUMN, PASSWORD_COLUMN),
                singletonList(USERS_TABLE),
                singletonList(new SQLQuery.Condition(getWhere(), SQLQuery.ConditionType.EQUAL))
        );
    }

    private Map<String, String> getWhere() {

        Map<String, String> where = new HashMap<>();
        where.put(USERNAME_COLUMN, userInfos.getUsername());
        where.put(PASSWORD_COLUMN, encryptedPassword);
        return where;
    }

    private SQLQuery toInsertUser() {
        return SQLQueryBuilder.buildInsertQuery(
                asList(USERNAME_COLUMN, PASSWORD_COLUMN),
                Collections.singletonList(USERS_TABLE),
                asList(userInfos.getUsername(), encryptedPassword)
        );
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
