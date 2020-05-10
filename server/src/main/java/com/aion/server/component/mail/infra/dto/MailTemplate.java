package com.aion.server.component.mail.infra.dto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum MailTemplate {

    DUMMY("/templates/dummy.txt",
            Collections.emptyList(),
            "Dummy template"),
    CONFIRM_LOGIN("/templates/mail/loginConfirm.html",
            Arrays.asList("link.verification", "date"),
            "Aion Shards - Confirm account !");

    private final String fileNameTemplate;
    private final List<String> fields;
    private final String subject;


    MailTemplate(String fileNameTemplate,
                 List<String> fields,
                 String subject) {

        this.fileNameTemplate = fileNameTemplate;
        this.fields = fields;
        this.subject = subject;
    }

    public String getFileNameTemplate() {
        return fileNameTemplate;
    }

    public List<String> getFields() {
        return fields;
    }

    public String getSubject() {
        return subject;
    }
}
