package com.aion.server.component.mail.infra.dto;

import lombok.Data;

import java.util.List;

@Data
public class MessageData {

    private final String[] to;
    private final String from;
    private final String subject;
    private final MailTemplate mailTemplate;
    private final List<String> valuesToFilWith;

    public MessageData(String[] to,
                       String from,
                       MailTemplate mailTemplate,
                       List<String> valuesToFilWith) {

        this.to = to;
        this.from = from;
        this.subject = mailTemplate.getSubject();
        this.mailTemplate = mailTemplate;
        this.valuesToFilWith = valuesToFilWith;
    }
}
