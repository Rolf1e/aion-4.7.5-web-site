package com.aion.server.database.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class SQLQuery {

    private List<String> toSelect;
    private List<String> fromTable;
    private List<Where> where;

    @AllArgsConstructor
    @Getter
    public static class Where {
        private Map<String, String> condition;
        private ConditionType type;
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
