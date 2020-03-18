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

        select.delete(select.length() - 2, select.length() - 1);//remove last ','
        return select.toString();
    }

    private String getFrom() {
        StringBuilder from = new StringBuilder("FROM ");

        for (String fromTo : sqlQuery.getToSelect()) {
            from.append(fromTo)
                    .append(",");
        }

        from.delete(from.length() - 2, from.length() - 1);//remove last ','
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
                        .append("',");
            }
        }

        asString.delete(asString.length() - 2, asString.length() - 1)//remove last ','
                .append(";");
        return asString.toString();
    }


}
