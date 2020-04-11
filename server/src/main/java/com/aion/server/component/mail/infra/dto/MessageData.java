package com.aion.server.component.mail.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MessageData {

    private final String[] to;
    private final String from;
    private final String subject;
    private final List<String> content;
    private final MailTemplate mailTemplate;
}
