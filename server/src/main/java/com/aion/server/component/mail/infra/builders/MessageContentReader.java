package com.aion.server.component.mail.infra.builders;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class MessageContentReader {

    private final String fileName;

    MessageContentReader(String fileName) {
        this.fileName = fileName;
    }

    List<String> getContent() throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }
}
