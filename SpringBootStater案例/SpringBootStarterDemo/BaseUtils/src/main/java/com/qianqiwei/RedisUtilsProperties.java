package com.qianqiwei;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "com.qianqiwei.redisutils")
public class RedisUtilsProperties {
    private String address;
    private String port;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public RedisUtilsProperties() {
    }

    public RedisUtilsProperties(String address, String port) {
        this.address = address;
        this.port = port;
    }
}
