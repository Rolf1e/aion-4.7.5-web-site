package com.aion.server.database.infra;

import com.aion.server.database.config.DataBaseConfiguration;
import com.aion.server.database.dto.Authentication;
import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@UtilityClass
public class SQLConnectionBuilder {

    public static Connection getConnection(Authentication auth,
                                            DataBaseConfiguration config) throws SQLException {
        String url = "jdbc:" + config.getDataBaseType() + "://" + auth.getHost() + ":" + auth.getPort() + "/" + config.getDatabaseName();
        return DriverManager.getConnection(url, auth.getUser(), auth.getPassword());
    }
}
