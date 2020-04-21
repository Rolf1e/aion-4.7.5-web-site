package com.aion.server.component.mail.infra.builders;

import com.aion.server.component.mail.infra.FileContentReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileContentReaderTest {

    @Test
    public void should_load_content() throws IOException {
        final List<String> expected = Arrays.asList("Test de lecture\r", "Lecture\r", "\r", "Fin Lecture.\r");
        final FileContentReader reader = new FileContentReader("/templates/dummy.txt");
        Assert.assertEquals(expected, reader.getContent());
    }

}
