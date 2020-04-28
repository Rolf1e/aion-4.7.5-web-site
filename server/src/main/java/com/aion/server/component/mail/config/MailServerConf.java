package com.aion.server.component.mail.config;

import lombok.Getter;

@Getter
public enum MailServerConf {
    GMAIL("smtp.gmail.com", "465");

    public static String MAIL_SENDER = "aion.shard@gmail.com";

    private String hostSender;
    private String portSender;


    MailServerConf(String hostSender,
                   String portSender) {

        this.hostSender = hostSender;
        this.portSender = portSender;
    }
}
