package com.aion.server.database.infra;

import com.aion.server.database.config.DataBaseConfiguration;
import com.aion.server.database.dto.Authentication;
import com.aion.server.database.dto.SQLQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class H2Client implements DBClient {

    private Authentication authentication;
    private DataBaseConfiguration configuration;
    private Statement statement;

    public H2Client(Authentication authentication,
                    DataBaseConfiguration configuration) {

        this.authentication = authentication;
        this.configuration = configuration;
    }

    public void connect() {
        try {
            Connection connection = getConnection(authentication, configuration);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException e) {
            log.error("Can not get connexion wtih database {}", authentication.getHost(), e);
        }
        log.info("Successfully get a connection with database {}", authentication.getHost());
    }

    public void disconnect() {

    }

    public void execute(SQLQuery query) {
//        statement.execute()
    }

    private static Connection getConnection(Authentication auth,
                                            DataBaseConfiguration config) throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Class driverClass = Class.forName(config.getDataBaseDriver());
        DriverManager.registerDriver((Driver) driverClass.newInstance());
        String url = "jdbc:" + config.getDataBaseType() + "://" + auth.getHost() + ":" + auth.getPort();
        return DriverManager.getConnection(url, auth.getUser(), auth.getPassword());
    }


}
