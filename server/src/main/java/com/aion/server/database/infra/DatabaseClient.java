package com.aion.server.database.infra;

import com.aion.server.database.config.DataBaseConfiguration;
import com.aion.server.database.dto.Authentication;
import com.aion.server.database.dto.SQLQuery;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.aion.server.database.infra.SQLConnectionBuilder.getConnection;
import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord;
import static com.aion.server.database.infra.SQLQueryAdaptor.getQueryAsString;

@Slf4j
public class DatabaseClient implements DBClient {

    private Authentication authentication;
    private DataBaseConfiguration configuration;
    private Connection connection;
    private Statement statement;

    public DatabaseClient(Authentication authentication,
                          DataBaseConfiguration configuration) {

        this.authentication = authentication;
        this.configuration = configuration;
    }

    @Override
    public void connect() throws SQLException {
        connection = getConnection(authentication, configuration);
        statement = connection.createStatement();
    }

    @Override
    public void disconnect() throws SQLException {
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public List<Map<String, String>> select(SQLQuery query,
                                      SQLKeyWord keyWord) throws SQLException {

        ResultSet resultSet = statement.executeQuery(getQueryAsString(query, keyWord));
        return getResult(resultSet, query.getColumns());
    }

    @Override
    public int insert(SQLQuery query,
                      SQLKeyWord keyWord) throws SQLException {

        return statement.executeUpdate(getQueryAsString(query, keyWord));
    }

    private List<Map<String, String>> getResult(ResultSet resultSet,
                                          List<String> select) throws SQLException {
        final List<Map<String, String>> wholeResult = new ArrayList<>();
        while (resultSet.next()) {
            final Map<String, String> queryResult = new HashMap<>();
            for (String column : select) {
                queryResult.put(column, resultSet.getString(column));
            }
            wholeResult.add(queryResult);
        }
        resultSet.close();
        return wholeResult;
    }
}
