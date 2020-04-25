package com.aion.server.service;

import com.aion.server.DataBaseApp;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"spring.config.location=classpath:application-db.yml"})
@ContextConfiguration(classes = {DataBaseApp.class})
public class ShardServiceTest {

    @Autowired
    private ShardService shardService;

    @Test
    public void should_give_shards() {
        Assert.assertTrue(shardService.giveShardsToPlayer(1, 1));
    }

}
