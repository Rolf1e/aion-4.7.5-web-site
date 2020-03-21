package com.aion.server.database.dto;

import lombok.experimental.UtilityClass;

import java.util.List;

import static com.aion.server.database.dto.SQLQuery.*;

@UtilityClass
public class SQLQueryBuilder {

    public static SQLQuery buildSelectQuery(List<String> columns,
                                            List<String> from,
                                            List<Condition> where) {
        return new SQLQuery(from)
                .setColumns(columns)
                .setWhere(where);
    }

    public static SQLQuery buildInsertQuery(List<String> columns,
                                            List<String> from,
                                            List<String> insertValues) {
        return new SQLQuery(from)
                .setColumns(columns)
                .setInsertValues(insertValues);
    }

    public static SQLQuery buildDeleteQuery(List<String> from,
                                            List<Condition> where) {

        return new SQLQuery(from)
                .setWhere(where);
    }

    public static SQLQuery buildUpdateQuery(List<String> from,
                                            List<Condition> set,
                                            List<Condition> where) {
        return new SQLQuery(from)
                .setSet(set)
                .setWhere(where);
    }
}
