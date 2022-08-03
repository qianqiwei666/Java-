package com.qianqiwei.server02;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan
public class Server02Application {

    public static void main(String[] args) {
        SpringApplication.run(Server02Application.class, args);
    }

}
