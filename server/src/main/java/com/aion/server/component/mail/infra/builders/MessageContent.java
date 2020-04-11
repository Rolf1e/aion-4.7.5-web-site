package com.aion.server.component.mail.infra.builders;

import javax.mail.Message;

public class MessageContent {

    private Message finalMessage;

    MessageContent(Message finalMessage) {
        this.finalMessage = finalMessage;
    }

    public Message getFinalMessage() {
        return finalMessage;
    }

    public void setFinalMessage(Message finalMessage) {
        this.finalMessage = finalMessage;
    }
}
