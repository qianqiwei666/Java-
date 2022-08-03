package com.qianqiwei.server01;

import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Server01ApplicationTests {

    @Autowired
    private RedissonClient redissonClient;
    @Test
    void contextLoads() {
        redissonClient.getBucket("username").set("qianqiwei");
    }

}
