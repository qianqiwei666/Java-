package com.qianqiwei.springsecuritydemo.mapper;

import com.qianqiwei.springsecuritydemo.pojo.Role;
import com.qianqiwei.springsecuritydemo.pojo.UserMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 15915
* @description 针对表【user_message】的数据库操作Mapper
* @createDate 2022-04-13 11:14:43
* @Entity com.qianqiwei.springsecuritydemo.pojo.UserMessage
*/
@Mapper
public interface UserMessageMapper extends BaseMapper<UserMessage> {

    public UserMessage selectMessageByUsername(@Param("username") String username);

}




