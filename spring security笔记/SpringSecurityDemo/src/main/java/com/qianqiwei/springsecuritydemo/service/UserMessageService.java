package com.qianqiwei.springsecuritydemo.service;

import com.qianqiwei.springsecuritydemo.pojo.UserMessage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
* @author 15915
* @description 针对表【user_message】的数据库操作Service
* @createDate 2022-04-13 11:14:43
*/

public interface UserMessageService extends IService<UserMessage> {
    public UserMessage selectMessageByUsername(String username);

}
