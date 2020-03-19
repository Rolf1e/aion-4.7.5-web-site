package com.aion.server.database.infra;

import com.aion.server.database.config.DataBaseConfiguration;
import com.aion.server.database.dto.Authentication;
import com.aion.server.database.dto.SQLQuery;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.aion.server.database.infra.SQLConnectionBuilder.getConnection;
import static com.aion.server.database.infra.SQLQueryAdaptor.*;

@Slf4j
public class PostgresClient implements DBClient {

    private Authentication authentication;
    private DataBaseConfiguration configuration;
    private Connection connection;
    private Statement statement;

    public PostgresClient(Authentication authentication,
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
    public Map<String, String> select(SQLQuery query,
                                      SQLKeyWord keyWord) throws SQLException {

        Optional<ResultSet> resultSet = Optional.ofNullable(statement.executeQuery(getQueryAsString(query, keyWord)));
        if (resultSet.isPresent()) {
            return getResult(resultSet.get(), query.getColumns());
        }
        return new HashMap<>();
    }

    @Override
    public int insert(SQLQuery query,
                      SQLKeyWord keyWord) throws SQLException {

        return statement.executeUpdate(getQueryAsString(query, keyWord));
    }

    private Map<String, String> getResult(ResultSet resultSet,
                                          List<String> select) throws SQLException {
        Map<String, String> queryResult = new HashMap<>();
        while (resultSet.next()) {
            for (String column : select) {
                queryResult.put(column, resultSet.getString(column));
            }
        }
        resultSet.close();
        return queryResult;
    }
}
