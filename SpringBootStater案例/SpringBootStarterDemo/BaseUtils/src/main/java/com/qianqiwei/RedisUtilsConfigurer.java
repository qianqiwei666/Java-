package com.qianqiwei;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.redisson.config.Config;

public class RedisUtilsConfigurer {
    public RedissonClient connection(String localhost, String port) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + localhost + ":" + port);
        return Redisson.create(config);
    }
}
