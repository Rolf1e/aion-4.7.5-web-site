package com.aion.server.handler;

import com.aion.server.database.infra.DBClient;
import com.aion.server.database.infra.DBStateController;
import com.aion.server.handler.dto.InputUserInfos;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j
public abstract class AbstractRequestHandler {

    protected DBStateController dbStateController;

    protected DBClient dbClient;
    protected InputUserInfos userInfos;

    public AbstractRequestHandler(DBClient dbClient,
                                  InputUserInfos userInfos) {

        this.dbClient = dbClient;
        this.userInfos = userInfos;
        dbStateController = DBStateController.getInstance();
    }

    public AbstractRequestHandler(DBClient dbClient) {
        this.dbClient = dbClient;
        dbStateController = DBStateController.getInstance();
    }

    protected void openDBConnection() {
        try {
            dbClient.connect();
            dbStateController.setState(true);
        } catch (SQLException e) {
            log.error("Failed to open connection login database for user {}", userInfos.getUsername(), e);
        }
    }

    protected void closeDBConnection() {
        try {
            dbClient.disconnect();
            dbStateController.setState(false);
        } catch (SQLException e) {
            log.error("Failed to close connection with login database for user {}", userInfos.getUsername(), e);
        }
    }
}
