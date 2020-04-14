package com.aion.server.component.mail.infra.dto;

import java.util.Arrays;
import java.util.List;

public enum MailTemplate {

    CONFIRM_LOGIN("server/src/main/resources/templates/mail/loginConfirm.template",
            Arrays.asList("link.verification", "date"),
            "Mail de confirmation de création de compte");

    private final String fileNameTemplate;
    private final List<String> toReplace;
    private final String subject;


    MailTemplate(String fileNameTemplate,
                 List<String> toReplace,
                 String subject) {

        this.fileNameTemplate = fileNameTemplate;
        this.toReplace = toReplace;
        this.subject = subject;
    }

    public String getFileNameTemplate() {
        return fileNameTemplate;
    }

    public List<String> getToReplace() {
        return toReplace;
    }

    public String getSubject() {
        return subject;
    }
}
