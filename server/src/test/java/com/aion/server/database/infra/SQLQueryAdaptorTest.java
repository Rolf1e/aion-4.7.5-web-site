package com.aion.server.database.infra;

import com.aion.server.database.dto.SQLQuery;
import com.aion.server.database.dto.SQLQueryBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord.*;
import static com.aion.server.database.infra.SQLQueryAdaptor.getQueryAsString;

public class SQLQueryAdaptorTest {

    @Test
    public void should_build_simple_select_query() {
        Map<String, String> where = new HashMap<>();
        where.put("test", "test");
        SQLQuery query = SQLQueryBuilder.buildSelectQuery(
                Collections.singletonList("test"),
                Collections.singletonList("TEST"),
                Collections.singletonList(new SQLQuery.Where(where, SQLQuery.ConditionType.EQUAL))
        );

        Assert.assertEquals("SELECT test FROM TEST WHERE test = 'test';", getQueryAsString(query, SELECT));
    }

    @Test
    public void should_build_complex_select_query() {
        Map<String, String> where = new HashMap<>();
        where.put("test", "test");
        where.put("test2", "test2");

        SQLQuery query = SQLQueryBuilder.buildSelectQuery(
                Arrays.asList("test", "test2"),
                Arrays.asList("TEST", "TEST2"),
                Collections.singletonList(new SQLQuery.Where(where, SQLQuery.ConditionType.EQUAL))
        );

        Assert.assertEquals("SELECT test,test2 FROM TEST,TEST2 WHERE test2 = 'test2' AND test = 'test';", getQueryAsString(query, SELECT));
    }

    @Test
    public void should_build_simple_insert_query() {
        SQLQuery query = SQLQueryBuilder.buildInsertQuery(
                Collections.singletonList("test"),
                Collections.singletonList("TEST"),
                Collections.singletonList("toInsert")
        );

        Assert.assertEquals("INSERT INTO TEST (test ) VALUES ('toInsert');", getQueryAsString(query, INSERT));
    }

    @Test
    public void should_build_complex_insert_query() {
        SQLQuery query = SQLQueryBuilder.buildInsertQuery(
                Arrays.asList("test", "test2"),
                Arrays.asList("TEST", "TEST2"), // should not take the second table
                Arrays.asList("toInsert", "toInsert2")
        );

        Assert.assertEquals("INSERT INTO TEST (test,test2 ) VALUES ('toInsert','toInsert2');", getQueryAsString(query, INSERT));
    }

    @Test
    public void should_build_simple_delete_query() {
        Map<String, String> where = new HashMap<>();
        where.put("test", "test");

        SQLQuery query = SQLQueryBuilder.buildDeleteQuery(
                Arrays.asList("TEST", "TEST2"),
                Collections.singletonList(new SQLQuery.Where(where, SQLQuery.ConditionType.EQUAL))
        );

        Assert.assertEquals("DELETE FROM TEST WHERE test = 'test';", getQueryAsString(query, DELETE));
    }

    @Test
    public void should_build_comple_delete_query() {
        Map<String, String> where = new HashMap<>();
        where.put("test", "test");
        where.put("test2", "test2");

        SQLQuery query = SQLQueryBuilder.buildDeleteQuery(
                Arrays.asList("TEST", "TEST2"),
                Collections.singletonList(new SQLQuery.Where(where, SQLQuery.ConditionType.EQUAL))
        );

        Assert.assertEquals("DELETE FROM TEST WHERE test2 = 'test2' AND test = 'test';", getQueryAsString(query, DELETE));
    }
    //TODO add unit test for update
}
