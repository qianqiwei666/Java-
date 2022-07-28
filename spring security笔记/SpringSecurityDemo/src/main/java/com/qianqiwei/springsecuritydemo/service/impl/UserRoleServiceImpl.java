package com.qianqiwei.springsecuritydemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianqiwei.springsecuritydemo.pojo.UserRole;
import com.qianqiwei.springsecuritydemo.service.UserRoleService;
import com.qianqiwei.springsecuritydemo.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 15915
* @description 针对表【user_role】的数据库操作Service实现
* @createDate 2022-04-13 11:14:57
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{


}




