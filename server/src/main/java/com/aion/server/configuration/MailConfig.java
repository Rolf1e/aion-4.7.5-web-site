package com.aion.server.configuration;

import com.aion.server.component.mail.config.MailServerConf;
import com.aion.server.component.mail.config.auth.AuthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.aion.server.component.mail.config.MailServerConf.GMAIL;

@Configuration
public class MailConfig {

    @Bean(name = "gmailConfig")
    public MailServerConf gmailConfig() {
        return GMAIL;
    }

    @Bean
    public AuthHandler authHandler() {
        return AuthHandler.getInstance();
    }
}
