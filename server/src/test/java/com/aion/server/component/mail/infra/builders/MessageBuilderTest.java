package com.aion.server.component.mail.infra.builders;

import com.aion.server.component.mail.infra.dto.MailTemplate;
import com.aion.server.component.mail.infra.dto.MessageData;
import org.junit.Assert;
import org.junit.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import java.util.Collections;
import java.util.Properties;

public class MessageBuilderTest {

    @Test
    public void should_build_mail() throws MessagingException {

        final MessageData messageData = new MessageData(new String[]{"rolfie@mail.com"},
                "ephrimes@mail.com",
                MailTemplate.DUMMY,
                Collections.emptyList());


        final Message build = MessageBuilder.build(messageData, Session.getInstance(new Properties()));

        Assert.assertEquals("ephrimes@mail.com",build.getFrom()[0].toString());
    }
}
