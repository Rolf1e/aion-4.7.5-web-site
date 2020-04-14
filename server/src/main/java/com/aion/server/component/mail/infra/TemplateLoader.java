package com.aion.server.component.mail.infra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TemplateLoader {

    private String fileName;

    public TemplateLoader(String fileName) {
        this.fileName = fileName;
    }

    public String load() throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
}
