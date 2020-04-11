package com.aion.server.component.mail.infra.builders;

import com.aion.server.component.mail.infra.dto.MailTemplate;
import lombok.extern.slf4j.Slf4j;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
class MessageContentBuilder {

    private final List<String> contentToConvert;
    private final Session session;
    private final MailTemplate mailTemplate;

    static MimeMessage build(final Session session,
                             final MailTemplate mailTemplate) throws MessagingException {

        return new MessageContentBuilder(session, mailTemplate)
                .buildToMineMessage();
    }


    private MessageContentBuilder(final Session session,
                                  final MailTemplate mailTemplate) {

        this.session = session;
        this.mailTemplate = mailTemplate;
        this.contentToConvert = getContent();
    }

    private List<String> getContent() {
        try {
            return new MessageContentReader(mailTemplate.getFileNameTemplate()).getContent();
        } catch (IOException e) {
            log.error("Failed to read {}", mailTemplate.getFileNameTemplate(), e);
        }
        return Collections.emptyList();
    }

    private MimeMessage buildToMineMessage() throws MessagingException {
        MimeMessage finalMessage = new MimeMessage(session);
        Multipart multipart = new MimeMultipart();

        for (String part : contentToConvert) {
            multipart.addBodyPart(getMessagePart(part));
        }
        finalMessage.setContent(multipart);

        return finalMessage;
    }

    private MimeBodyPart getMessagePart(String part) throws MessagingException {
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setText(part);
        return mimeBodyPart;
    }
}
