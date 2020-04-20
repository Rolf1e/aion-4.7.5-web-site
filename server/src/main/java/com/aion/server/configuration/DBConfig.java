package com.aion.server.configuration;

import com.aion.server.database.config.DataBaseConfiguration;
import com.aion.server.database.dto.Authentication;
import com.aion.server.database.infra.DBClient;
import com.aion.server.database.infra.DatabaseClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DBConfig {

    @Bean(destroyMethod = "disconnect", initMethod = "connect", name = "ac47_server_ls")
    public DBClient ac47_server_ls(final Authentication authentication,
                                   @Qualifier("login") final DataBaseConfiguration configuration) {

        return new DatabaseClient(authentication, configuration);
    }

    @Bean(destroyMethod = "disconnect", initMethod = "connect", name = "ac47_server_gs")
    public DBClient ac47_server_gs(final Authentication authentication,
                                   @Qualifier("server") final DataBaseConfiguration configuration) {

        return new DatabaseClient(authentication, configuration);
    }

    @Bean(destroyMethod = "disconnect", initMethod = "connect", name = "web_site")
    public DBClient website(final Authentication authentication,
                            @Qualifier("website") final DataBaseConfiguration configuration) {

        return new DatabaseClient(authentication, configuration);
    }

    @Bean(name = "login")
    public DataBaseConfiguration configurationLogin(@Value("${database.driver}") final String driver,
                                                    @Value("${database.type}") final String type,
                                                    @Value("${database.dbname.login}") final String dbName) {

        return new DataBaseConfiguration(driver, type, dbName);
    }

    @Bean(name = "server")
    public DataBaseConfiguration configurationServer(@Value("${database.driver}") final String driver,
                                                     @Value("${database.type}") final String type,
                                                     @Value("${database.dbname.server}") final String dbName) {

        return new DataBaseConfiguration(driver, type, dbName);
    }

    @Bean(name = "website")
    public DataBaseConfiguration configurationLS(@Value("${database.driver}") final String driver,
                                                 @Value("${database.type}") final String type,
                                                 @Value("${database.dbname.website}") final String dbName) {

        return new DataBaseConfiguration(driver, type, dbName);
    }


    @Bean
    public Authentication authentication(@Value("${database.user}") final String user,
                                         @Value("${database.password}") final String pwd,
                                         @Value("${database.host}") final String host,
                                         @Value("${database.port}") final String port) {

        return new Authentication(user, pwd, host, port);
    }
}
