package com.qianqiwei;

import org.redisson.api.RedissonClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({RedisUtilsProperties.class})
@Import({RedisUtilsConfigurer.class})
public class RedisUtilsConfiguration {

    @Bean
    public RedissonClient myRedissonClient(RedisUtilsProperties redisUtilsProperties,RedisUtilsConfigurer redisUtilsConfigurer){
        return redisUtilsConfigurer.connection(redisUtilsProperties.getAddress(),redisUtilsProperties.getPort());
    }


}
