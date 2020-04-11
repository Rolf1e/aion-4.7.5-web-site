package com.aion.server.component.mail.infra.builders;

import com.aion.server.component.mail.exceptions.WrongSizeTemplateException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;

public class TemplateHandlerTest {

    @Test(expected = WrongSizeTemplateException.class)
    public void should_not_create() throws WrongSizeTemplateException {
        new TemplateHandler(Collections.emptyList(), Collections.singletonList("I control this honor, it's called distant procedure."));
    }

    @Test
    public void should_parse_to_map() throws WrongSizeTemplateException {
        Map<String, String> toMap = new TemplateHandler(
                asList("Red alert, moon!",
                        "Heuretes messiss, tanquam teres mineralis."),
                asList("When the cannibal screams for port royal, all shores command undead, swashbuckling sea-dogs.",
                        "Brevis pulchritudine interdum pugnas elogium est.")
        ).getToMap();

        Assert.assertEquals(getExpected(), toMap);
    }

    private Map<String, String> getExpected() {
        Map<String, String> expected = new HashMap<>();
        expected.put("Red alert, moon!", "When the cannibal screams for port royal, all shores command undead, swashbuckling sea-dogs.");
        expected.put("Heuretes messiss, tanquam teres mineralis.", "Brevis pulchritudine interdum pugnas elogium est.");
        return expected;
    }
}
