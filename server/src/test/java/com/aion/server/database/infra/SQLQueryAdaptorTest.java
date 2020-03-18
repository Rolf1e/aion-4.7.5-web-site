package com.aion.server.database.infra;

import com.aion.server.database.dto.SQLQuery;
import org.junit.Assert;
import org.junit.Test;

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

        Assert.assertEquals("SELECT test FROM TEST WHERE test = test;",SQLQueryAdaptor.getQueryAsString(sqlQuery));
    }
}
