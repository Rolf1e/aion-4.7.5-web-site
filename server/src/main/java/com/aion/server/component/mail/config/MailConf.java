package com.aion.server.component.mail.config;

import lombok.Getter;

@Getter
public enum MailConf {
    GMAIL("smtp.gmail.com", "587");
    private String hostSender;
    private String portSender;

    MailConf(String hostSender,
             String portSender) {

        this.hostSender = hostSender;
        this.portSender = portSender;

    }
}
