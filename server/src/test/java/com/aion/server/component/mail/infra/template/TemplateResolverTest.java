package com.aion.server.component.mail.infra.template;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TemplateResolverTest {

    @Test
    public void should_replace() {
        final String expected = "A remplacer : When the cannibal screams for port royal, all shores command undead, swashbuckling sea-dogs. et autre a remplacer Brevis pulchritudine interdum pugnas elogium est.";
        final Map<String, String> entry = new HashMap<>();
        entry.put("test1", "When the cannibal screams for port royal, all shores command undead, swashbuckling sea-dogs.");
        entry.put("test2", "Brevis pulchritudine interdum pugnas elogium est.");
        final String template = "A remplacer : ${test1} et autre a remplacer ${test2}";
        final String substitued = new TemplateResolver(template, entry)
                .substituteString();

        Assert.assertEquals(expected, substitued);
    }
}
