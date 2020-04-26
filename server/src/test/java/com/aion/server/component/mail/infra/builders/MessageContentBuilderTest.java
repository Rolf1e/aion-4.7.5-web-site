package com.aion.server.component.mail.infra.builders;

import com.aion.server.component.mail.infra.dto.MailTemplate;
import org.junit.Test;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

public class MessageContentBuilderTest {

    @Test
    public void should_build() throws MessagingException, IOException {
        final MimeMessage build = MessageContentBuilder.build(Session.getInstance(new Properties()),
                MailTemplate.DUMMY,
                Collections.emptyList());

    }
}
