package com.aion.server.handler;

import com.aion.server.database.dto.SQLQuery;
import com.aion.server.database.infra.DBClient;
import com.aion.server.handler.dto.InputUserInfos;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Map;

import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord.*;

@Slf4j
public abstract class AbstractRequestHandler {

    protected final DBClient dbClient;
    protected InputUserInfos userInfos;

    protected AbstractRequestHandler(DBClient dbClient,
                                     InputUserInfos userInfos) {

        this.dbClient = dbClient;
        this.userInfos = userInfos;
    }

    protected AbstractRequestHandler(DBClient dbClient) {
        this.dbClient = dbClient;
    }

    protected Map<String, String> select(SQLQuery query) throws SQLException {
        return dbClient.select(query, SELECT);
    }

    protected void insert(SQLQuery query) throws SQLException {
        dbClient.insert(query, INSERT);
    }

    protected void update(SQLQuery query) throws SQLException {
        dbClient.insert(query, UPDATE);
    }
}
