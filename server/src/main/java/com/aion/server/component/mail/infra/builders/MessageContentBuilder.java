package com.aion.server.component.mail.infra.builders;

import com.aion.server.component.mail.exceptions.WrongSizeTemplateException;
import com.aion.server.component.mail.infra.ContentType;
import com.aion.server.component.mail.infra.dto.MailTemplate;
import com.aion.server.component.mail.infra.template.TemplateHandler;
import lombok.extern.slf4j.Slf4j;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
class MessageContentBuilder {

    private final List<String> contentToConvert;
    private final Session session;
    private final MailTemplate mailTemplate;
    private final List<String> valuesToFillWith;

    static MimeMessage build(final Session session,
                             final MailTemplate mailTemplate,
                             final List<String> valuesToFillWith) throws MessagingException {

        return new MessageContentBuilder(session, mailTemplate, valuesToFillWith)
                .buildToMineMessage();
    }

    private MessageContentBuilder(final Session session,
                                  final MailTemplate mailTemplate,
                                  final List<String> valuesToFillWith) {

        this.session = session;
        this.mailTemplate = mailTemplate;
        this.valuesToFillWith = valuesToFillWith;
        this.contentToConvert = getContent();
    }

    private List<String> getContent() {
        try {
            final List<String> noFilledContent = new FileContentReader(mailTemplate.getFileNameTemplate())
                    .getContent();
            return getContentFilled(noFilledContent);
        } catch (IOException e) {
            log.error("Failed to read {}", mailTemplate.getFileNameTemplate(), e);
        } catch (WrongSizeTemplateException e) {
            log.error("Failed to parse template {} ", mailTemplate.getFileNameTemplate(), e);
        }
        return Collections.emptyList();
    }

    private MimeMessage buildToMineMessage() throws MessagingException {
        final MimeMessage finalMessage = new MimeMessage(session);
        final Multipart multipart = new MimeMultipart();

        for (String part : contentToConvert) {
            multipart.addBodyPart(getMessagePart(part));
        }
        finalMessage.setContent(multipart, ContentType.HTML.getContentType());

        return finalMessage;
    }

    private MimeBodyPart getMessagePart(final String part) throws MessagingException {
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setText(part);
        return mimeBodyPart;
    }

    private List<String> getContentFilled(final List<String> noFilledContent) throws WrongSizeTemplateException {
        final StringBuilder noFilledContentToString = new StringBuilder();
        noFilledContent.forEach(line -> noFilledContentToString.append(line).append("\n"));
        final String filledContent = new TemplateHandler(noFilledContentToString.toString(), valuesToFillWith, mailTemplate.getFields())
                .fillText();

        return new ArrayList<>(Arrays.asList(filledContent.split("\n")));
    }
}
