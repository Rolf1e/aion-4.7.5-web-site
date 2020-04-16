package com.aion.server.database.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class SQLQuery {

    private List<String> columns;
    private List<String> from;
    private List<Condition> where;
    private List<String> insertValues;
    private List<Condition> set;

    SQLQuery(List<String> from) {
        this.from = from;
    }

    @AllArgsConstructor
    @Getter
    public static class Condition {
        private Map<String, String> condition;
        private ConditionType type;
    }

    SQLQuery setWhere(List<Condition> where) {
        this.where = where;
        return this;
    }

    SQLQuery setInsertValues(List<String> insertValues) {
        this.insertValues = insertValues;
        return this;
    }

    SQLQuery setColumns(List<String> columns) {
        this.columns = columns;
        return this;
    }

    SQLQuery setSet(List<Condition> set) {
        this.set = set;
        return this;
    }

    public enum ConditionType {

        In("IN"),
        LIKE("LIKE"),
        AND("AND"),
        OR("OR"),
        NOT("NOT"),
        IS_NULL("IS NULL"),
        IS_NOT_NULL("IS NOT NULL"),
        BETWEEN("BETWEEN"),
        NOT_BETWEEN("NOT BETWEEN"),
        EXISTS("EXISTS"),

        EQUAL("="),
        NOT_EQUAL("!="),
        SUPERIOR_EQUAL("<="),
        INFERIOR_EQUAL(">="),
        SUPERIOR("<"),
        INFERIOR(">");

        private String type;

        ConditionType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
