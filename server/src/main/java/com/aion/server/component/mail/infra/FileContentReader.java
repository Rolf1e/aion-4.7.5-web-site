package com.aion.server.component.mail.infra;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class FileContentReader {

    private final String fileName;

    public FileContentReader(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getContent() throws IOException {
        return Arrays.asList(IOUtils.toString(this.getClass().getResourceAsStream(fileName), StandardCharsets.UTF_8)
                .split("\n"));
    }
}
