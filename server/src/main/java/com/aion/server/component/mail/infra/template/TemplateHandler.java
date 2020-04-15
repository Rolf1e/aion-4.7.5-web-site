package com.aion.server.component.mail.infra.template;

import com.aion.server.component.mail.exceptions.WrongSizeTemplateException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class TemplateHandler {

    private final String text;
    private final List<String> valuesToFillWith;
    private final List<String> fields;

    public String fillText() throws WrongSizeTemplateException {
        return new TemplateResolver(text, getMap())
                .substituteString();
    }

    private Map<String, String> getMap() throws WrongSizeTemplateException {
        return new TemplateFiller(fields, valuesToFillWith)
                .getToMap();
    }
}
