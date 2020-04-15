package com.aion.server.component.mail.infra.template;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;

import java.util.Map;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TemplateResolver {

    private final String template;
    private final Map<String, String> toFilTemplate;

    String substituteString() {
        return new StrSubstitutor(toFilTemplate)
                .replace(template);
    }
}
