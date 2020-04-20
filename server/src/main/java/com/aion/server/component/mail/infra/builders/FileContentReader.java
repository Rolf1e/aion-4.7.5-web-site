package com.aion.server.component.mail.infra.builders;

import org.apache.logging.log4j.core.util.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileContentReader {

    private final String fileName;

    public FileContentReader(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getContent() throws IOException {
//        IOUtils.toString(this.getClass().getResource(fileName));
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }
}

//TODO change for class loader resource
