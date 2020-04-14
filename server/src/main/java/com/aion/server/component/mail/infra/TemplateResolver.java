package com.aion.server.component.mail.infra;

import org.apache.logging.log4j.core.lookup.StrSubstitutor;

import java.util.Map;

public class TemplateResolver {

    private final String template;
    private final Map<String, String> toFilTemplate;

    public TemplateResolver(String template,
                            Map<String, String> toFilTemplate) {

        this.template = template;
        this.toFilTemplate = toFilTemplate;
    }

    public String substituteString() {
        return new StrSubstitutor(toFilTemplate)
                .replace(template);
    }
}
