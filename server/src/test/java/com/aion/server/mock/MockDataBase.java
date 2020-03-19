package com.aion.server.mock;

import com.aion.server.database.dto.SQLQuery;
import com.aion.server.database.dto.SQLQueryBuilder;
import com.aion.server.database.infra.DBClient;
import com.aion.server.database.infra.SQLQueryAdaptor;
import com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.aion.server.component.login.LoginConfig.*;
import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord.*;
import static org.mockito.Mockito.when;

public class MockDataBase {

    public static Connection mockSQLConnection() throws SQLException {
        Connection connection = Mockito.mock(Connection.class);

        when(connection.createStatement())
                .thenReturn(mockStatement());

        return connection;
    }

    public static DBClient mockDBClient() throws SQLException {
        DBClient dbClient = Mockito.mock(DBClient.class);

        when(dbClient.select(getSqlQuery(), SELECT))
                .thenReturn(getResponse());

        return dbClient;
    }

    private static Statement mockStatement() throws SQLException {
        Statement statement = Mockito.mock(Statement.class);

        when(statement.executeQuery(SQLQueryAdaptor.getQueryAsString(getSqlQuery(), SELECT)))
                .thenReturn(null);

        when(statement.executeUpdate(null))//todo
                .thenReturn(null);
        return statement;
    }

    private static SQLQuery getSqlQuery() {
        Map<String, String> where = new HashMap<>();
        where.put("username", "tigran");
        where.put("password", "tigran");

        return SQLQueryBuilder.buildSelectQuery(
                Arrays.asList(USERNAME_COLUMN, PASSWORD_COLUMN),
                Collections.singletonList(USERS_TABLE),
                Collections.singletonList(new SQLQuery.Where(where, SQLQuery.ConditionType.EQUAL))
        );
    }

    private static Map<String, String> getResponse() {
        Map<String, String> response = new HashMap<>();
        response.put("username", "tigran");
        response.put("password", "tigran");
        return response;
    }


}
