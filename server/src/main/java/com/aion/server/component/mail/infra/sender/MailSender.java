package com.aion.server.component.mail.infra.sender;

import com.aion.server.component.mail.config.MailServerConf;
import com.aion.server.component.mail.config.auth.AuthHandler;
import com.aion.server.component.mail.infra.builders.MessageBuilder;
import com.aion.server.component.mail.infra.dto.MessageData;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.Properties;

public class MailSender {

    public static void sendMailTo(MailServerConf mailsConfig,
                                  MessageData messageData) throws MessagingException {

        AuthHandler authHandler = AuthHandler.getInstance();
        Properties props = setProperties(mailsConfig);
        Session session = Session.getInstance(props);

        Transport.send(MessageBuilder.build(messageData, session),
                authHandler.getUser(), authHandler.getPwd());
    }

    private static Properties setProperties(MailServerConf mailsConfig) {
        Properties properties = new Properties();

        properties.put("mail.smtp.host", mailsConfig.getHostSender());
        properties.put("mail.smtp.port", mailsConfig.getPortSender());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.debug", "true");

        return properties;
    }
}
