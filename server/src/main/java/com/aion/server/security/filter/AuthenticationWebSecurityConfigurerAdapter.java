package com.aion.server.security.filter;

import com.aion.server.database.infra.DBClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
public class AuthenticationWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private DBClient dbClient;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new AuthenticationFilter(dbClient), AnonymousAuthenticationFilter.class);
    }
}
