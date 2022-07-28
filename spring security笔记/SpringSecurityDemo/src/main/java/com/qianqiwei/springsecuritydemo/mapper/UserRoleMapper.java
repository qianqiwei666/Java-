package com.qianqiwei.springsecuritydemo.mapper;

import com.qianqiwei.springsecuritydemo.pojo.Role;
import com.qianqiwei.springsecuritydemo.pojo.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 15915
* @description 针对表【user_role】的数据库操作Mapper
* @createDate 2022-04-13 11:14:57
* @Entity com.qianqiwei.springsecuritydemo.pojo.UserRole
*/
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}




