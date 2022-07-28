package com.qianqiwei.springsecuritydemo;

import com.qianqiwei.springsecuritydemo.pojo.UserMessage;
import com.qianqiwei.springsecuritydemo.service.RoleService;
import com.qianqiwei.springsecuritydemo.service.UserMessageService;
import com.qianqiwei.springsecuritydemo.service.UserRoleService;
import com.qianqiwei.springsecuritydemo.service.impl.UserMessageServiceImpl;
import com.qianqiwei.springsecuritydemo.utils.JWTUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest
class SpringSecurityDemoApplicationTests {

@Autowired
private UserMessageService userMessageServiceImpl;

@Autowired
private RoleService roleServiceImpl;


    @Test
    void contextLoads() {
        System.out.println(roleServiceImpl.selectByUserID(userMessageServiceImpl.selectMessageByUsername("qianqiwei").getId()));
    }

}
