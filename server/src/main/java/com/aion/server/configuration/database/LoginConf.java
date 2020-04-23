package com.aion.server.configuration.database;

import com.aion.server.database.config.DataBaseConfiguration;
import com.aion.server.database.dto.Authentication;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.aion.server.database.repositories.login")
public class LoginConf {

    private static final String JDBC = "jdbc:";

    @Primary
    @Bean(name = "loginDatabase")
    public DataSource loginDataSource(final Authentication authentication,
                                      @Qualifier("login") final DataBaseConfiguration configuration) {

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(configuration.getDataBaseDriver());
        dataSourceBuilder.url(JDBC + configuration.getDataBaseType() + "://" + authentication.getHost() + ":" + authentication.getPort() + "/" + configuration.getDatabaseName());
        dataSourceBuilder.username(authentication.getUser());
        dataSourceBuilder.password(authentication.getPassword());
        return dataSourceBuilder.build();
    }

    @Bean(name = "login")
    public DataBaseConfiguration configurationLogin(@Value("${database.driver}") final String driver,
                                                    @Value("${database.type}") final String type,
                                                    @Value("${database.dbname.login}") final String dbName) {

        return new DataBaseConfiguration(driver, type, dbName);
    }
}
