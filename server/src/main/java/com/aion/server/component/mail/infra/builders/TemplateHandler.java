package com.aion.server.component.mail.infra.builders;

import com.aion.server.component.mail.exceptions.WrongSizeTemplateException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateHandler {

    private List<String> fields;
    private List<String> toReplace;

    public TemplateHandler(List<String> fields,
                           List<String> toReplace) throws WrongSizeTemplateException {

        if (fields.size() != toReplace.size()) {
            throw new WrongSizeTemplateException();
        }

        this.toReplace = toReplace;
        this.fields = fields;
    }

    public Map<String, String> getToMap() {
        Map<String, String> toMap = new HashMap<>();

        for (int i = 0; i < fields.size(); ++i) {
            toMap.put(fields.get(i), toReplace.get(i));
        }

        return toMap;
    }
}
