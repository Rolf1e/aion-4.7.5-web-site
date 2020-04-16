package com.aion.server.service;

import com.aion.server.database.dto.SQLQuery;
import com.aion.server.database.dto.SQLQueryBuilder;
import com.aion.server.database.infra.DBClient;
import com.aion.server.service.infra.dto.InputUserInfos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.aion.server.database.config.TableDBConfig.*;
import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord.SELECT;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Slf4j
@Service
public class LoginService {

    private final DBClient dbClient;

    public LoginService(DBClient dbClient) {
        this.dbClient = dbClient;
    }

    //TODO use OutputUser
    public boolean checkRegistered(final InputUserInfos userInfos) {
        final String encryptedPassword = EncryptionService.toEncode(userInfos.getPassword());
        try {
            return !dbClient.select(toSelectUser(userInfos, encryptedPassword), SELECT)
                    .isEmpty();
        } catch (SQLException e) {
            log.error("Can not reach player database", e);
            return false;
        }
    }

    private SQLQuery toSelectUser(final InputUserInfos userInfos,
                                  final String encryptedPassword) {

        return SQLQueryBuilder.buildSelectQuery(
                asList(USERNAME_COLUMN, PASSWORD_COLUMN),
                singletonList(USERS_TABLE),
                singletonList(new SQLQuery.Condition(getWhere(userInfos, encryptedPassword), SQLQuery.ConditionType.EQUAL))
        );
    }

    private Map<String, String> getWhere(final InputUserInfos userInfos,
                                         final String encryptedPassword) {

        Map<String, String> where = new HashMap<>();
        where.put(USERNAME_COLUMN, userInfos.getUsername());
        where.put(PASSWORD_COLUMN, encryptedPassword);
        return where;
    }
}
