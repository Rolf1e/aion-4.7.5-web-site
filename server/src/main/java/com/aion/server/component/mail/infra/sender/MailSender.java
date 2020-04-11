package com.aion.server.component.mail.infra.sender;

import com.aion.server.component.mail.config.MailConf;
import com.aion.server.component.mail.config.auth.AuthHandler;
import com.aion.server.component.mail.infra.builders.MessageBuilder;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.Properties;

public class MailSender {

    public static void sendMailTo(MailConf mailsConfig,
                                  MessageBuilder messageBuilder) throws MessagingException {

        AuthHandler authHandler = AuthHandler.getInstance();

        Properties props = setProperties(mailsConfig);
        Session session = Session.getInstance(props);
        Message messageToSend = messageBuilder
                .setSession(session)
                .build();
        Transport.send(messageToSend, authHandler.getUser(), authHandler.getPwd());
    }

    private static Properties setProperties(MailConf mailsConfig) {
        Properties properties = new Properties();

        properties.put("mail.smtp.host", mailsConfig.getHostSender());
        properties.put("mail.smtp.port", mailsConfig.getPortSender());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.debug", "true");

        return properties;
    }
}
