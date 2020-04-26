package com.aion.server.configuration.database;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.aion.server.database.repositories.game"},
        entityManagerFactoryRef = "gameEntityManagerFactory",
        transactionManagerRef = "gameTransactionManager")
public class GameConf {

    @Bean(name = "gameDataSource")
    @ConfigurationProperties(prefix = "game.datasource")
    public DataSource gameDataSource() {
        return DataSourceBuilder
                .create()
                .build();
    }

    @Bean(name = "gameEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean gameEntityManagerFactory(final EntityManagerFactoryBuilder builder,
                                                                       @Qualifier("gameDataSource") final DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("com.aion.server.database.entity.game")
                .persistenceUnit("game")
                .build();
    }

    @Bean(name = "gameTransactionManager")
    public PlatformTransactionManager gameTransactionManager(@Qualifier("gameEntityManagerFactory") final EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
