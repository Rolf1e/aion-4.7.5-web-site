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
@EnableJpaRepositories(basePackages = "com.aion.server.database.repositories.login",
        entityManagerFactoryRef = "loginEntityManagerFactory",
        transactionManagerRef = "loginTransactionManager")
public class LoginConf {

    @Primary
    @Bean(name = "loginDataSource")
    @ConfigurationProperties(prefix = "login.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .build();
    }

    @Primary
    @Bean(name = "loginEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean loginEntityManagerFactory(final EntityManagerFactoryBuilder builder,
                                                                          @Qualifier("loginDataSource") final DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("com.aion.server.database.entity.login")
                .persistenceUnit("login")
                .build();
    }

    @Primary
    @Bean(name = "loginTransactionManager")
    public PlatformTransactionManager loginTransactionManager(@Qualifier("loginEntityManagerFactory") final EntityManagerFactory barEntityManagerFactory) {
        return new JpaTransactionManager(barEntityManagerFactory);
    }
}
