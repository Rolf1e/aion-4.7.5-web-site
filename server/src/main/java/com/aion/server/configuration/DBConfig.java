package com.aion.server.configuration;

import com.aion.server.database.config.DataBaseConfiguration;
import com.aion.server.database.dto.Authentication;
import com.aion.server.database.infra.DBClient;
import com.aion.server.database.infra.DatabaseClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfig {

    @Bean(destroyMethod = "disconnect", initMethod = "connect")
    public DBClient dbClient(final Authentication authentication,
                             final DataBaseConfiguration configuration) {

        return new DatabaseClient(authentication, configuration);
    }

    @Bean
    public Authentication authentication(@Value("${database.user}") final String user,
                                         @Value("${database.password}") final String pwd,
                                         @Value("${database.host}") final String host,
                                         @Value("${database.port}") final String port) {

        return new Authentication(user, pwd, host, port);
    }

    @Bean
    public DataBaseConfiguration configuration(@Value("${database.driver}") final String driver,
                                               @Value("${database.type}") final String type,
                                               @Value("${database.dbname}") final String dbName) {

        return new DataBaseConfiguration(driver, type, dbName);
    }
}
