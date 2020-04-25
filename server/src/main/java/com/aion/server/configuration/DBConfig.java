package com.aion.server.configuration;

import com.aion.server.database.dto.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfig {

    @Bean
    public Authentication authentication(@Value("${database.user}") final String user,
                                         @Value("${database.password}") final String pwd,
                                         @Value("${database.host}") final String host,
                                         @Value("${database.port}") final String port) {

        return new Authentication(user, pwd, host, port);
    }
}
