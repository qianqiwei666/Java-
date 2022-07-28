package com.qianqiwei.springsecuritydemo.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import io.jsonwebtoken.impl.DefaultJwtParser;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


@Component
public class JWTUtils {
    public String create(Map<String,Object> header, Map<String,Object> payload, SignatureAlgorithm signatureAlgorithm, String salt,Date expireTime){
        DefaultJwtBuilder defaultJwtBuilder=new DefaultJwtBuilder();
        //生成token
        JwtBuilder jwtBuilder = defaultJwtBuilder
                .setHeader(header)  //设置header
                .setClaims(payload) //设置payload
                .signWith(SignatureAlgorithm.HS256, salt)//设置加密算法以及Secret
                .setExpiration(expireTime); //设置过期时间
        //获取token
        String compact = jwtBuilder.compact();
        return compact;
    }

    public Jwt ParseToken(String token,String salt){
        DefaultJwtParser defaultJwtParser=new DefaultJwtParser();
        //获取到盐
        defaultJwtParser.setSigningKey(salt);
        //解析token
        Jwt parse = defaultJwtParser.parse(token);
        return parse;
    }




}
