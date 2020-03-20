package com.aion.server.database.dto;

import lombok.experimental.UtilityClass;

import java.util.List;

import static com.aion.server.database.dto.SQLQuery.*;

@UtilityClass
public class SQLQueryBuilder {

    public static SQLQuery buildSelectQuery(List<String> columns,
                                            List<String> from,
                                            List<Where> where) {
        return new SQLQuery(from)
                .setColumns(columns)
                .setCondition(where);
    }

    public static SQLQuery buildInsertQuery(List<String> columns,
                                            List<String> from,
                                            List<String> insertValues) {
        return new SQLQuery(from)
                .setColumns(columns)
                .setInsertValues(insertValues);
    }

    public static SQLQuery buildDeleteQuery(List<String> from,
                                            List<Where> where) {

        return new SQLQuery(from)
                .setCondition(where);
    }

    public static SQLQuery buildUpdateQuery(List<String> from,
                                            List<Where> set,
                                            List<Where> where) {
        return new SQLQuery(from)
                .setSet(set)
                .setCondition(where);
    }
}
