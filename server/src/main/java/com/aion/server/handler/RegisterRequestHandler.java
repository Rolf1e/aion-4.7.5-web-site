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

import static com.aion.server.database.config.LoginConfig.*;
import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord.INSERT;
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
            if (!dbStateController.getState()) {
                openDBConnection();
            }
            if (!checkRegistered()) {
                registerUserToDB();
                return new TokenRequestHandler(dbClient, userInfos)
                        .getUserWithToken();
            }
            throw new UserExistException(userInfos.getUsername());
        } catch (SQLException e) {
            log.error("Can not reach player database", e);
            return new OutputUserInfos();
        } finally {
            if (dbStateController.getState()) {
                closeDBConnection();
            }
        }
    }

    public boolean checkRegistered() throws SQLException {
        return new LoginRequestHandler(dbClient, userInfos)
                .checkRegistered();
    }

    private void registerUserToDB() throws SQLException {
        dbClient.insert(toInsertUser(), INSERT);
    }

    private SQLQuery toInsertUser() {
        return SQLQueryBuilder.buildInsertQuery(
                asList(USERNAME_COLUMN, PASSWORD_COLUMN),
                Collections.singletonList(USERS_TABLE),
                asList(userInfos.getUsername(), encryptedPassword)
        );
    }

}
