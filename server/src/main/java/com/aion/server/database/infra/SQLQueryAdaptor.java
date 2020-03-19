package com.aion.server.database.infra;

import com.aion.server.database.dto.SQLQuery;

import java.util.List;
import java.util.Map;

import static com.aion.server.database.infra.SQLQueryAdaptor.SQLKeyWord.*;

public class SQLQueryAdaptor {

    private SQLQuery sqlQuery;
    private SQLKeyWord keyWord;

    public static String getQueryAsString(SQLQuery sqlQuery,
                                          SQLKeyWord keyWord) {

        return new SQLQueryAdaptor(sqlQuery, keyWord)
                .getQueryAsString();
    }

    private SQLQueryAdaptor(SQLQuery sqlQuery,
                            SQLKeyWord keyWord) {

        this.sqlQuery = sqlQuery;
        this.keyWord = keyWord;
    }

    private String getQueryAsString() {
        switch (keyWord) {
            case SELECT:
                return getSelect();
            case INSERT:
                return getInsert();
            case DELETE:
                return getDelete();
            case UPDATE:
                return getUpdate();
            default:
                return "";//impossible to reach
        }
    }

    private String getSelect() {
        return keyWord.getKeyWord() + " " + getColumns() +
                "FROM " + getFrom() + getWhere();
    }

    private String getInsert() {
        return keyWord.getKeyWord() + " INTO " + getFrom() + " (" + getColumns() + ") " +
                "VALUES (" + getInsertValues() + ");";
    }

    private String getDelete() {
        return keyWord.getKeyWord() + " FROM " + getFrom() + " " + getWhere();
    }

    private String getUpdate() {
        return keyWord.getKeyWord() + " " + getFrom() + " SET " + getSet() + " " + getWhere();
    }

    private String getFrom() {
        if (keyWord.equals(INSERT) ||
                keyWord.equals(DELETE)) {
            return sqlQuery.getFrom().get(0);//we can't insert in multiples tables
        }
        StringBuilder from = new StringBuilder();

        for (String fromTo : sqlQuery.getFrom()) {
            from.append(fromTo)
                    .append(",");
        }
        from.replace(from.length() - 1, from.length(), " ");//replace last ','

        return from.toString();
    }

    private String getColumns() {
        StringBuilder columns = new StringBuilder();

        for (String column : sqlQuery.getColumns()) {
            columns.append(column)
                    .append(",");
        }
        columns.replace(columns.length() - 1, columns.length(), " ");

        return columns.toString();
    }

    private String getWhere() {
        List<SQLQuery.Where> whereList = sqlQuery.getWhere();
        if (whereList == null || whereList.isEmpty()) {
            return ";";
        }

        StringBuilder asString = new StringBuilder("WHERE ");

        for (SQLQuery.Where where : whereList) {
            for (Map.Entry<String, String> entry : where.getCondition().entrySet()) {
                asString.append(entry.getKey())
                        .append(" ")
                        .append(SQLQuery.ConditionType.EQUAL.getType())
                        .append(" '").append(entry.getValue()).append("' ")
                        .append(SQLQuery.ConditionType.AND)
                        .append(" ");
            }
        }
        asString.replace(asString.length() - 5, asString.length(), ";");//replace last ','

        return asString.toString();
    }

    private String getInsertValues() {
        StringBuilder values = new StringBuilder();

        for (String value : sqlQuery.getInsertValues()) {
            values.append("'")
                    .append(value)
                    .append("',");
        }
        values.delete(values.length() - 1, values.length());

        return values.toString();
    }

    private String getSet() {
        return "";
    }

    public enum SQLKeyWord {
        SELECT("SELECT"),
        UPDATE("UPDATE"),
        INSERT("INSERT"),
        DELETE("DELETE");

        private String keyWord;

        SQLKeyWord(String keyWord) {
            this.keyWord = keyWord;
        }

        public String getKeyWord() {
            return keyWord;
        }
    }
}
