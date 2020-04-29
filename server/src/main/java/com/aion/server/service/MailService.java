package com.aion.server.service;

import com.aion.server.component.mail.config.MailServerConf;
import com.aion.server.component.mail.config.auth.AuthHandler;
import com.aion.server.component.mail.infra.builders.MessageBuilder;
import com.aion.server.component.mail.infra.dto.MessageData;
import com.aion.server.component.mail.infra.dto.WebMailData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.Properties;

@Service
@AllArgsConstructor
public class MailService {

    private final MailServerConf mailsConfig;
    private final AuthHandler authHandler;

    public void sendMailTo(final MessageData messageData) throws MessagingException {
        final Session session = Session.getInstance(setProperties(mailsConfig));
        Transport.send(MessageBuilder.build(messageData, session),
                authHandler.getUser(), authHandler.getPwd());
    }

    public void sendMailTo(final WebMailData webMailData) throws MessagingException {
        final Session session = Session.getInstance(setProperties(mailsConfig));
        Transport.send(MessageBuilder.build(webMailData, session),
                authHandler.getUser(), authHandler.getPwd());
    }

    private Properties setProperties(MailServerConf mailsConfig) {
        final Properties properties = new Properties();

        properties.put("mail.smtp.host", mailsConfig.getHostSender());
        properties.put("mail.smtp.port", mailsConfig.getPortSender());
        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.debug", "true");
        properties.put("mail.smtp.ssl.enable", "true");

        return properties;
    }
}
