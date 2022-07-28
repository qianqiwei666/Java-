package com.qianqiwei.springsecuritydemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianqiwei.springsecuritydemo.pojo.UserMessage;
import com.qianqiwei.springsecuritydemo.service.UserMessageService;
import com.qianqiwei.springsecuritydemo.mapper.UserMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 15915
* @description 针对表【user_message】的数据库操作Service实现
* @createDate 2022-04-13 11:14:43
*/
@Service
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage>
    implements UserMessageService{

    @Autowired
    private UserMessageMapper userMessageMapper;

    @Override
    public UserMessage selectMessageByUsername(String username) {
        return userMessageMapper.selectMessageByUsername(username);
    }
}




