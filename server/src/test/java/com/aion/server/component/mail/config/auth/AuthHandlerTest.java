package com.aion.server.component.mail.config.auth;

import org.junit.Assert;
import org.junit.Test;

public class AuthHandlerTest {

    @Test
    public void should_get_auth() {
        final AuthHandler instance = AuthHandler.getInstance();
        Assert.assertEquals("rolfe@mail.com", instance.getUser());
        Assert.assertEquals("test", instance.getPwd());
    }
}
