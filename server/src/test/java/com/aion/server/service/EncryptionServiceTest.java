package com.aion.server.service;

import com.aion.server.service.infra.exception.EncodeException;
import org.junit.Assert;
import org.junit.Test;

public class EncryptionServiceTest {

    @Test
    public void should_encryt_well() throws EncodeException {
        Assert.assertEquals("H3Hg9KybR82TvyaeQBerqrnTvWM=", EncryptionService.toEncode("bonjour"));
        Assert.assertEquals("k2dSNMk7TwuLeHeDkOhivh6BuOQ=", EncryptionService.toEncode("#$NGLFSDNdojfskd59-0435"));
        Assert.assertEquals("7V9O9P22IgmxfoSPBobM8KIx9PQ=", EncryptionService.toEncode("W09985W4B KLlkjnhsdo8yf-45=1=@$(%$%^303"));
        Assert.assertEquals("qUqP5cyxm6YcTAhz05Hph5gvu9M=", EncryptionService.toEncode("test"));
    }
}
