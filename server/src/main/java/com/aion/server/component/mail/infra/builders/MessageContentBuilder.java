package com.aion.server.component.mail.infra.builders;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;

public class MessageContentBuilder {

    private List<String> contentToConvert;
    private Session session;

    public MessageContent build() throws MessagingException {
        return new MessageContent(writeWholeMessage());
    }

    public MessageContentBuilder(List<String> contentToConvert,
                                 Session session) {
        this.contentToConvert = contentToConvert;
        this.session = session;
    }

    public MessageContentBuilder() {
    }


    public MessageContentBuilder setContentToConvert(List<String> contentToConvert) {
        this.contentToConvert = contentToConvert;
        return this;
    }

    public MessageContentBuilder setSession(Session session) {
        this.session = session;
        return this;
    }

    public MimeMessage writeWholeMessage() throws MessagingException {
        MimeMessage finalMessage = new MimeMessage(session);
        Multipart multipart = new MimeMultipart();

        for (String part : contentToConvert) {
            multipart.addBodyPart(getMessagePart(part));
        }
        finalMessage.setContent(multipart);

        return finalMessage;
    }

    private MimeBodyPart getMessagePart(String part) {
        MimeBodyPart mimeBodyPart = new MimeBodyPart();

        try {
            mimeBodyPart.setText(part);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return mimeBodyPart;
    }
}
