package com.aion.server.service.infra.utils;

import org.junit.Assert;
import org.junit.Test;

public class TokenGeneratorTest {

    @Test
    public void should_generate_token() {
        final String token = TokenGenerator.generate();
        Assert.assertEquals(255, token.length());
        System.out.println(token);
    }
}
