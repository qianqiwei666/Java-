package com.qianqiwei.webproject.controller;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class WebController {

    @Autowired
    private  RedissonClient myRedissonClient;

    @GetMapping("/index")
    public Mono<ResponseEntity<String>> index(){
        Mono<ResponseEntity<String>> mono=Mono.create(responseEntityMonoSink -> {
            responseEntityMonoSink.success(ResponseEntity.ok().body("测试成功!"));
        });
        Flux<Object> objectFlux = Flux.create(fluxSink -> {
            fluxSink.next(test1());
            fluxSink.next(test());
            fluxSink.complete();
        });
        objectFlux.subscribe(o -> {
            System.out.println(o);
        });
        return mono;
    }

    public String test1(){
        return "测试阶段一";
    }
    private String test(){
        myRedissonClient.getBucket("ooxxxxxx").set("xoxoxoxxoxoxoo");
        return "插入成功!";
    }

}
