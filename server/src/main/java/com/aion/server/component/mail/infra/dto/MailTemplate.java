package com.aion.server.component.mail.infra.dto;

import java.util.Arrays;
import java.util.List;

public enum MailTemplate {

    CONFIRM_LOGIN("loginConfirm.template", Arrays.asList(
            "link.verification", "date"));

    private final String fileNameTemplate;
    public final List<String> toReplace;

    MailTemplate(String fileNameTemplate,
                 List<String> toReplace) {

        this.fileNameTemplate = fileNameTemplate;
        this.toReplace = toReplace;
    }

    public String getFileNameTemplate() {
        return fileNameTemplate;
    }

    public List<String> getToReplace() {
        return toReplace;
    }
}
