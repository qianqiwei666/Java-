package com.qianqiwei.springsecuritydemo.controller;


import cn.hutool.captcha.ShearCaptcha;
import com.qianqiwei.springsecuritydemo.config.loginAuthencation.LoginHandler;
import com.qianqiwei.springsecuritydemo.config.loginAuthencation.RegisterHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@CrossOrigin
public class MainController {

    //登录处理
    @Autowired
    private LoginHandler loginHandler;

    //注册处理
    @Autowired
    private RegisterHandler registerHandler;
    //验证码
    @Autowired
    private ShearCaptcha shearCaptcha;

    //登录
    @PostMapping("/app/login")
    public ResponseEntity login(@Param("username")String username,@Param("password")String password,@Param("code") String code){
        return loginHandler.login(username,password,code);
    }

    //注册
    @PostMapping("/app/register")
    public ResponseEntity register(@Param("username")String username,@Param("password")String password,@Param("code")String code){
        return registerHandler.register(username,password,code);
    }

    //返回验证码给前端
    @GetMapping("/getCode")
    public ResponseEntity getCode(){
        shearCaptcha.createCode();
        return ResponseEntity.ok().body(shearCaptcha.getImageBase64());
    }

    //前台返回测试请求 主要用来进行权限验证
    //或者关系
    @PreAuthorize("hasRole('admin') and hasRole('root')")
    @GetMapping("/agent")
    public ResponseEntity agent(){
        log.info("前台访问到了探针!");
        return ResponseEntity.ok("测试!");
    }



}
