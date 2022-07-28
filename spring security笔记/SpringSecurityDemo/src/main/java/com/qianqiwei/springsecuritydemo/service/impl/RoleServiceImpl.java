package com.qianqiwei.springsecuritydemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianqiwei.springsecuritydemo.pojo.Role;
import com.qianqiwei.springsecuritydemo.service.RoleService;
import com.qianqiwei.springsecuritydemo.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 15915
* @description 针对表【role】的数据库操作Service实现
* @createDate 2022-04-13 11:14:54
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Autowired
    private RoleMapper roleMapper;

    public List<Role> selectByUserID(int id){
       return roleMapper.selectByUserID(id);
    }

}




