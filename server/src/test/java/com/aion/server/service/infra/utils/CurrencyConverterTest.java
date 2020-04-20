package com.aion.server.service.infra.utils;

import org.junit.Assert;
import org.junit.Test;


public class CurrencyConverterTest {

    /**
     * 1 shard = 0.02€
     * 50 Shards = 1€
     * 500 Shards = 10€
     */
    @Test
    public void should_convert() {
        final CurrencyConverter currencyConverter = new CurrencyConverter();

        Assert.assertEquals(1.0, currencyConverter.convert(0.02), 0);
        Assert.assertEquals(50.0, currencyConverter.convert(1), 0);
        Assert.assertEquals(500.0, currencyConverter.convert(10), 0);
    }
}
