package com.qianqiwei.springsecuritydemo.mapper;

import com.qianqiwei.springsecuritydemo.pojo.Role;
import com.qianqiwei.springsecuritydemo.pojo.UserStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 15915
* @description 针对表【user_status】的数据库操作Mapper
* @createDate 2022-04-13 11:14:49
* @Entity com.qianqiwei.springsecuritydemo.pojo.UserStatus
*/
@Mapper
public interface UserStatusMapper extends BaseMapper<UserStatus> {

}




