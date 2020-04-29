package com.aion.server.component.mail.infra.builders;

import com.aion.server.component.mail.infra.dto.MessageData;
import com.aion.server.component.mail.infra.dto.WebMailData;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.net.MimeMessageBuilder;
import org.simplejavamail.converter.internal.mimemessage.MimeMessageProducerHelper;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.aion.server.component.mail.config.MailServerConf.MAIL_SENDER;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageBuilder {

    private final Session session;

    public static Message build(final MessageData messageData,
                                final Session session) throws MessagingException {

        return new MessageBuilder(session)
                .buildToMessage(messageData);
    }

    public static Message build(final WebMailData webMailData,
                                final Session session) throws MessagingException {

        return new MessageBuilder(session)
                .buildToMessage(webMailData);
    }


    private Message buildToMessage(final WebMailData webMailData) throws MessagingException {
        final MimeMessageBuilder mimeMessageBuilder = new MimeMessageBuilder(session)
                .setSubject(webMailData.getSubject())
                .setFrom(MAIL_SENDER);
        for (String recipient : webMailData.getTo()) {
            mimeMessageBuilder.setRecipients(Message.RecipientType.TO, recipient);
        }
        final Message message = mimeMessageBuilder.build();
        message.setText(webMailData.getContent());
        return message;
    }

    private Message buildToMessage(final MessageData messageData) throws MessagingException {
        final InternetAddress[] internetAddresses = sendTo(messageData.getTo());
        final Message message = new MimeMessage(
                MessageContentBuilder.build(session,
                        messageData.getMailTemplate(),
                        messageData.getValuesToFilWith())
        );
        message.setFrom(new InternetAddress(messageData.getFrom()));
        message.setRecipients(Message.RecipientType.TO, internetAddresses);
        message.setSentDate(new Date());
        message.setSubject(messageData.getSubject());
        return message;
    }

    private static InternetAddress[] sendTo(String[] to) throws AddressException {
        final List<InternetAddress> internetAddresses = new ArrayList<>();

        for (String one : to) {
            internetAddresses.add(new InternetAddress(one));
        }

        return internetAddresses.toArray(new InternetAddress[0]);
    }
}
