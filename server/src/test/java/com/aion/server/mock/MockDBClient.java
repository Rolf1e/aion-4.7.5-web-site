package com.aion.server.mock;

import com.aion.server.database.dto.SQLQuery;
import com.aion.server.database.infra.DBClient;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.aion.server.component.login.LoginConfig.*;
import static org.mockito.Mockito.when;

public class MockDBClient {

    public static DBClient mockDBClient() throws SQLException {
        DBClient dbClient = Mockito.mock(DBClient.class);

        when(dbClient.execute(getSqlQuery()))
                .thenReturn(getResponse());
        return dbClient;
    }

    private static SQLQuery getSqlQuery() {
        Map<String, String> where = new HashMap<>();
        where.put("username", "tigran");
        where.put("password", "tigran");

        return new SQLQuery(
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
