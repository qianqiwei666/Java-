package com.qianqiwei.server03;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan
public class Server03Application {

    public static void main(String[] args) {
        SpringApplication.run(Server03Application.class, args);
    }

}
