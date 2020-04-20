package com.aion.server.component.mail.infra;

public enum ContentType {
    HTML("text/html");

    private String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
