package com.aion.server.handler;

import com.aion.server.database.dto.SQLQuery;
import com.aion.server.database.dto.SQLQueryBuilder;
import com.aion.server.database.infra.DBClient;
import com.aion.server.handler.dto.InputUserInfos;
import com.aion.server.service.EncryptionService;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.aion.server.database.config.TableDBConfig.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Slf4j
public class LoginRequestHandler extends AbstractRequestHandler {

    private String encryptedPassword;

    public LoginRequestHandler(DBClient dbClient,
                               InputUserInfos userInfos) {

        super(dbClient, userInfos);
        encryptedPassword = EncryptionService.toEncode(userInfos.getPassword());
    }

    //TODO use OutputUser
    public boolean checkRegistered() {
        try {
            return !select(toSelectUser())
                    .isEmpty();
        } catch (SQLException e) {
            log.error("Can not reach player database", e);
            return false;
        }
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
}
