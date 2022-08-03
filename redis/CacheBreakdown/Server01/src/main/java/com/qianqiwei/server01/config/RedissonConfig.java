package com.qianqiwei.server01.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.qianqiwei.redis")
public class RedissonConfig {
    private String address;
    private int port;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public RedissonConfig() {
    }

    public RedissonConfig(String address, int port) {
        this.address = address;
        this.port = port;
    }
}
