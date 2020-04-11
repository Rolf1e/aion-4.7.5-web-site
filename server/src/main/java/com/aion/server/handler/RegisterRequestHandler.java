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

import static com.aion.server.database.config.TableDBConfig.*;
import static java.util.Arrays.asList;

@Slf4j
public class RegisterRequestHandler extends AbstractRequestHandler {

    private String encryptedPassword;

    public RegisterRequestHandler(DBClient dbClient,
                                  InputUserInfos userInfos) {

        super(dbClient, userInfos);
        encryptedPassword = EncryptionService.toEncode(userInfos.getPassword());
    }

    public OutputUserInfos registerNewUser() throws UserExistException {
        try {
            if (!checkRegistered()) {
                insert(toInsertUser());
                return new TokenRequestHandler(dbClient, userInfos)
                        .getUserWithToken();
            }
            throw new UserExistException(userInfos.getUsername());
        } catch (SQLException e) {
            log.error("Can not reach player database", e);
            return new OutputUserInfos();
        }
    }

    public boolean checkRegistered() {
        return new LoginRequestHandler(dbClient, userInfos)
                .checkRegistered();
    }

    private SQLQuery toInsertUser() {
        return SQLQueryBuilder.buildInsertQuery(
                asList(USERNAME_COLUMN, PASSWORD_COLUMN),
                Collections.singletonList(USERS_TABLE),
                asList(userInfos.getUsername(), encryptedPassword)
        );
    }

}
