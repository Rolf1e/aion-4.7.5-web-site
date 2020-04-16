package com.aion.server.service;

import com.aion.server.database.dto.SQLQuery;
import com.aion.server.database.dto.SQLQueryBuilder;
import com.aion.server.database.infra.DBClient;
import com.aion.server.service.infra.dto.InputUserInfos;
import com.aion.server.service.infra.dto.OutputUserInfos;
import com.aion.server.service.infra.exception.UserDoesntExistException;
import com.aion.server.service.infra.utils.TokenGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.aion.server.database.config.TableDBConfig.*;
import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord.SELECT;
import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord.UPDATE;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Service
@Slf4j
public class TokenService {

    private final DBClient dbClient;
    private String idUser;

    public TokenService(final DBClient dbClient) {
        this.dbClient = dbClient;
    }

    public boolean checkToken(final String token) throws SQLException {
        return !dbClient.select(selectUsernameFromToken(token), SELECT)
                .isEmpty();
    }

    public OutputUserInfos getUserWithToken(final InputUserInfos userInfos) {
        final String token = getToken(userInfos);
        return new OutputUserInfos(idUser, userInfos.getUsername(), EncryptionService.toDecode(userInfos.getPassword()), token);
    }

    public OutputUserInfos getUserFromToken(final String token) throws SQLException, UserDoesntExistException {
        final Map<String, String> select = dbClient.select(selectUsernameFromToken(token), SELECT);
        if (select.isEmpty()) {
            throw new UserDoesntExistException(token);
        }
        return new OutputUserInfos(select.get(USERNAME_ID_COLUMN), select.get(USERNAME_COLUMN), select.get(PASSWORD_COLUMN), select.get(TOKEN_COLUMN));
    }

    private String getToken(final InputUserInfos userInfos) {
        try {
            final Map<String, String> select = dbClient.select(toSelectToken(userInfos), SELECT);
            if (select.isEmpty()) {
                return "Bad credentials";
            }
            String token = select.get(TOKEN_COLUMN);
            if (token == null) {
                token = TokenGenerator.generate();
                dbClient.insert(toUpdateToken(token, userInfos), UPDATE);
            }
            idUser = select.get(USERNAME_ID_COLUMN);
            return token;
        } catch (SQLException e) {
            log.error("Can not retrieve token for user {}", userInfos.getUsername(), e);
            return "";
        }
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
