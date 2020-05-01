package com.aion.server.service;

import com.aion.server.service.infra.exception.EncodeException;
import com.aion.server.service.infra.utils.EncryptionUtils;
import org.junit.Assert;
import org.junit.Test;

public class EncryptionUtilsTest {

    @Test
    public void should_encryt_well() throws EncodeException {
        Assert.assertEquals("H3Hg9KybR82TvyaeQBerqrnTvWM=", EncryptionUtils.toEncode("bonjour"));
        Assert.assertEquals("k2dSNMk7TwuLeHeDkOhivh6BuOQ=", EncryptionUtils.toEncode("#$NGLFSDNdojfskd59-0435"));
        Assert.assertEquals("7V9O9P22IgmxfoSPBobM8KIx9PQ=", EncryptionUtils.toEncode("W09985W4B KLlkjnhsdo8yf-45=1=@$(%$%^303"));
        Assert.assertEquals("qUqP5cyxm6YcTAhz05Hph5gvu9M=", EncryptionUtils.toEncode("test"));
    }
}
