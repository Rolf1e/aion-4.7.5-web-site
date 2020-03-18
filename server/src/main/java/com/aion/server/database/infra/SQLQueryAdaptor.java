package com.aion.server.database.infra;

import com.aion.server.database.dto.SQLQuery;

import java.util.List;
import java.util.Map;

public class SQLQueryAdaptor {

    private SQLQuery sqlQuery;

    public static String getQueryAsString(SQLQuery sqlQuery) {
        return new SQLQueryAdaptor(sqlQuery)
                .getQueryAsString();
    }

    private SQLQueryAdaptor(SQLQuery sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    private String getQueryAsString() {
        return getSelect() + getFrom() + getWhere();
    }

    private String getSelect() {
        StringBuilder select = new StringBuilder("SELECT ");

        for (String toSelect : sqlQuery.getToSelect()) {
            select.append(toSelect)
                    .append(",");
        }

        select.replace(select.length() - 1, select.length(), " ");//replace last ','
        return select.toString();
    }

    private String getFrom() {
        StringBuilder from = new StringBuilder("FROM ");

        for (String fromTo : sqlQuery.getFromTable()) {
            from.append(fromTo)
                    .append(",");
        }

        from.replace(from.length() - 1, from.length(), " ");//replace last ','
        return from.toString();
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
                        .append(" = ")
                        .append("'")
                        .append(entry.getValue())
                        .append("' ")
                        .append(SQLQuery.ConditionType.AND)
                        .append(" ");
            }
        }

        asString.replace(asString.length() - 5, asString.length(), ";");//replace last ','
        return asString.toString();
    }


}
