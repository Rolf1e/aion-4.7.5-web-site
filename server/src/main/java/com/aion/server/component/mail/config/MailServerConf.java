package com.aion.server.component.mail.config;

import lombok.Getter;

@Getter
public enum MailServerConf {
    LAPOSTE("smtp.laposte.net", "587"),
    GMAIL("smtp.gmail.com", "587");

    private String hostSender;
    private String portSender;

    MailServerConf(String hostSender,
                   String portSender) {

        this.hostSender = hostSender;
        this.portSender = portSender;
    }

    public static String MAIL_SENDER = "aion.shard@gmail.com";
}
