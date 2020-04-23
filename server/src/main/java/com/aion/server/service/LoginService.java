package com.aion.server.service;

import com.aion.server.database.dto.SQLQuery;
import com.aion.server.database.dto.SQLQueryBuilder;
import com.aion.server.database.infra.DBClient;
import com.aion.server.service.infra.dto.InputUserInfos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
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

    public LoginService(@Qualifier("ac47_server_ls") final DBClient dbClient) {
        this.dbClient = dbClient;
    }

    public boolean checkRegistered(final InputUserInfos userInfos) {
        final String encryptedPassword = EncryptionService.toEncode(userInfos.getPassword());
        try {
            return !dbClient.select(toSelectUser(userInfos, encryptedPassword), SELECT)
                    .get(0)
                    .isEmpty();
        } catch (SQLException e) {
            log.error("Can not reach player database", e);
            return false;
        }
    }

    public boolean checkAccountIsActivated(final int idPlayer) {
        try {
            final Map<String, String> response = dbClient.select(toSelectActivate(idPlayer), SELECT).get(0);

            if (response.get(ACCOUNT_ACTIVATED_COLUMN).equals("1")) {
                return true;
            }
        } catch (SQLException e) {
            log.error("Failed to check account is activated for user {}", idPlayer, e);
        }
        return false;
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

    private SQLQuery toSelectActivate(final int idPlayer) {
        return SQLQueryBuilder.buildSelectQuery(
                singletonList(ACCOUNT_ACTIVATED_COLUMN),
                singletonList(USERS_TABLE),
                singletonList(new SQLQuery.Condition(getWhereIdPlayer(idPlayer), SQLQuery.ConditionType.EQUAL))
        );
    }

    private Map<String, String> getWhereIdPlayer(final int idPlayer) {
        Map<String, String> where = new HashMap<>();
        where.put(USERNAME_ID_COLUMN, String.valueOf(idPlayer));
        return where;
    }


}
