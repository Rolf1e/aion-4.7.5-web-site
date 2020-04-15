package com.aion.server.component.mail.infra.template;

import com.aion.server.component.mail.exceptions.WrongSizeTemplateException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateFiller {

    private final List<String> fields;
    private final List<String> toReplace;

    TemplateFiller(final List<String> fields,
                   final List<String> toReplace) throws WrongSizeTemplateException {

        if (fields.size() != toReplace.size()) {
            throw new WrongSizeTemplateException();
        }

        this.toReplace = toReplace;
        this.fields = fields;
    }

    Map<String, String> getToMap() {
        Map<String, String> toMap = new HashMap<>();

        for (int i = 0; i < fields.size(); ++i) {
            toMap.put(fields.get(i), toReplace.get(i));
        }

        return toMap;
    }
}
