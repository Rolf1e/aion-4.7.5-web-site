package com.aion.server.component.mail.infra.builders;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageBuilder {

    private String[] to;
    private String from;
    private String subject;
    private List<String> content;
    private Session session;

    public Message build() throws MessagingException {
        MessageContent messageContent = new MessageContentBuilder()
                .setContentToConvert(content)
                .setSession(session)
                .build();

        InternetAddress[] internetAddresses = sendTo(to);

        Message message = new MimeMessage((MimeMessage) messageContent.getFinalMessage());
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, internetAddresses);
        message.setSentDate(new Date());
        message.setSubject(subject);

        return message;
    }

    public MessageBuilder() {
    }

    public MessageBuilder setTo(String[] to) {
        this.to = to;
        return this;
    }

    public MessageBuilder setFrom(String from) {
        this.from = from;
        return this;
    }

    public MessageBuilder setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public MessageBuilder setContent(List<String> content) {
        this.content = content;
        return this;
    }

    public MessageBuilder setSession(Session session) {
        this.session = session;
        return this;
    }

    private static InternetAddress[] sendTo(String[] to) throws AddressException {
        List<InternetAddress> internetAddresses = new ArrayList<>();

        for (String one : to) {
            internetAddresses.add(new InternetAddress(one));
        }

        return internetAddresses.toArray(new InternetAddress[0]);
    }
}
