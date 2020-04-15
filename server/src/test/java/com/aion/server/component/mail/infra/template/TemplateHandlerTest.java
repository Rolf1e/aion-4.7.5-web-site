package com.aion.server.component.mail.infra.template;

import com.aion.server.component.mail.exceptions.WrongSizeTemplateException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class TemplateHandlerTest {

    @Test
    public void should_get_template_filled() throws WrongSizeTemplateException {
        final String expected = "To fill hello , this is rolfie";

        final String actual = new TemplateHandler("To fill ${test1} , this is ${test2}",
                Arrays.asList("hello", "rolfie"),
                Arrays.asList("test1", "test2"))
                .fillText();

        Assert.assertEquals(expected, actual);
    }
}
