package com.qianqiwei.server01;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan
public class Server01Application {

    public static void main(String[] args) {
        SpringApplication.run(Server01Application.class, args);
    }

}
