package com.aion.server.database.infra;

import com.aion.server.database.dto.SQLQuery;

import java.sql.SQLException;
import java.util.Map;

public interface DBClient {

    void connect() throws SQLException;

    void disconnect() throws SQLException;

    Map<String, String> execute(SQLQuery query) throws SQLException;

}
