package com.aion.server.component.mail.infra.builders;

import com.aion.server.component.mail.infra.dto.MessageData;
import lombok.extern.slf4j.Slf4j;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class MessageBuilder {

    private final MessageData messageData;
    private final Session session;

    public static Message build(final MessageData messageData,
                                final Session session) throws MessagingException {

        return new MessageBuilder(messageData, session)
                .buildToMessage();
    }

    private MessageBuilder(MessageData messageData,
                           Session session) {

        this.messageData = messageData;
        this.session = session;
    }

    private Message buildToMessage() throws MessagingException {
        InternetAddress[] internetAddresses = sendTo(messageData.getTo());
        Message message = new MimeMessage(MessageContentBuilder.build(session, messageData.getMailTemplate(), messageData.getValuesToFilWith()));
        message.setFrom(new InternetAddress(messageData.getFrom()));
        message.setRecipients(Message.RecipientType.TO, internetAddresses);
        message.setSentDate(new Date());
        message.setSubject(messageData.getFrom());
        return message;
    }

    private static InternetAddress[] sendTo(String[] to) throws AddressException {
        List<InternetAddress> internetAddresses = new ArrayList<>();

        for (String one : to) {
            internetAddresses.add(new InternetAddress(one));
        }

        return internetAddresses.toArray(new InternetAddress[0]);
    }
}
