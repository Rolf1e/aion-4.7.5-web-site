package com.aion.server.database.infra;

import com.aion.server.database.dto.SQLQuery;

import java.util.Map;

public interface DBClient {

    void connect();

    void disconnect();

    void execute(SQLQuery query);

}
