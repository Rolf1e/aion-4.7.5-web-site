package com.aion.server.database.infra;

import com.aion.server.database.dto.SQLQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SQLQueryAdaptorTest {

    @Test
    public void should_build_simple_query() {

        Map<String, String> where = new HashMap<>();
        where.put("test", "test");
        SQLQuery sqlQuery = new SQLQuery(
                Collections.singletonList("test"),
                Collections.singletonList("TEST"),
                Collections.singletonList(new SQLQuery.Where(where, SQLQuery.ConditionType.EQUAL))
        );

        Assert.assertEquals("SELECT test FROM TEST WHERE test = 'test';", SQLQueryAdaptor.getQueryAsString(sqlQuery));
    }

    @Test
    public void should_build_complex_query() {
        Map<String, String> where = new HashMap<>();
        where.put("test", "test");
        where.put("test2", "test2");

        SQLQuery sqlQuery = new SQLQuery(
                Arrays.asList("test", "test2"),
                Arrays.asList("TEST", "TEST2"),
                Collections.singletonList(new SQLQuery.Where(where, SQLQuery.ConditionType.EQUAL))
        );

        Assert.assertEquals("SELECT test,test2 FROM TEST,TEST2 WHERE test2 = 'test2' AND test = 'test';", SQLQueryAdaptor.getQueryAsString(sqlQuery));
    }
}
