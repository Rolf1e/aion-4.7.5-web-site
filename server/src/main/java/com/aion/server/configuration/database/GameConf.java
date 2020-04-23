package com.aion.server.configuration.database;

import com.aion.server.database.config.DataBaseConfiguration;
import com.aion.server.database.dto.Authentication;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.aion.server.database.repositories.game")
public class GameConf {

    private static final String JDBC = "jdbc:";

    @Bean(name = "gameDatabase")
    public DataSource gameDataSource(final Authentication authentication,
                                     @Qualifier("server") final DataBaseConfiguration configuration) {

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(configuration.getDataBaseDriver());
        dataSourceBuilder.url(JDBC + configuration.getDataBaseType() + "://" + authentication.getHost() + ":" + authentication.getPort() + "/" + configuration.getDatabaseName());
        dataSourceBuilder.username(authentication.getUser());
        dataSourceBuilder.password(authentication.getPassword());
        return dataSourceBuilder.build();
    }


    @Bean(name = "server")
    public DataBaseConfiguration configurationServer(@Value("${database.driver}") final String driver,
                                                     @Value("${database.type}") final String type,
                                                     @Value("${database.dbname.server}") final String dbName) {

        return new DataBaseConfiguration(driver, type, dbName);
    }
}
