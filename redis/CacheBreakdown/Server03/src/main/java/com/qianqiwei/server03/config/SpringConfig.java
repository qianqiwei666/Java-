package com.qianqiwei.server03.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(value = {RedissonConfig.class})
@Configuration
public class SpringConfig {

    @Autowired
    @Bean
    public RedissonClient redissonClient(RedissonConfig redissonConfig){
        Config config=new Config();
        config.useSingleServer().setAddress("redis://"+redissonConfig.getAddress()+":"+redissonConfig.getPort());
        return Redisson.create(config);
    }
}
