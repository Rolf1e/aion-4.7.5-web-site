package com.aion.server.component.mail.infra.builders;

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
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }
}
