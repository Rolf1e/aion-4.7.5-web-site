package com.aion.server.configuration;

import com.aion.server.component.mail.config.MailServerConf;
import com.aion.server.component.mail.infra.sender.MailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.aion.server.component.mail.config.MailServerConf.GMAIL;

@Configuration
public class MailConfig {

    @Bean(name = "gmailConfig")
    public MailServerConf gmailConfig() {
        return GMAIL;
    }

    @Bean(name = "gmailSender")
    public MailSender gmailSender(final MailServerConf mailServerConf) {
        return new MailSender(mailServerConf);
    }
}
