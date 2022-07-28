package com.qianqiwei.springsecuritydemo.service;

import com.qianqiwei.springsecuritydemo.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 15915
* @description 针对表【role】的数据库操作Service
* @createDate 2022-04-13 11:14:54
*/
public interface RoleService extends IService<Role> {
    public List<Role> selectByUserID(int id);
}
