package com.aion.server.database.infra;

import com.aion.server.database.dto.SQLQuery;
import com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DBClient {

    void connect() throws SQLException;

    void disconnect() throws SQLException;

    List<Map<String, String>> select(SQLQuery query, SQLKeyWord keyWord) throws SQLException;

    int insert(SQLQuery query, SQLKeyWord keyWord) throws SQLException;


}
