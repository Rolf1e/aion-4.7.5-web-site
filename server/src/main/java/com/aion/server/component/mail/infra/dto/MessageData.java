package com.aion.server.component.mail.infra.dto;

import lombok.Data;

@Data
public class MessageData {

    private final String[] to;
    private final String from;
    private final String subject;
    private final MailTemplate mailTemplate;

    public MessageData(String[] to,
                       String from,
                       MailTemplate mailTemplate) {
        this.to = to;
        this.from = from;
        this.subject = mailTemplate.getSubject();
        this.mailTemplate = mailTemplate;
    }
}
