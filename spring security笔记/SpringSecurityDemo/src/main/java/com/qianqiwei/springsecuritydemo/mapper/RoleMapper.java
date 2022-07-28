package com.qianqiwei.springsecuritydemo.mapper;

import com.qianqiwei.springsecuritydemo.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.coyote.http11.upgrade.UpgradeServletOutputStream;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 15915
* @description 针对表【role】的数据库操作Mapper
* @createDate 2022-04-13 11:14:54
* @Entity com.qianqiwei.springsecuritydemo.pojo.Role
*/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    public List<Role> selectByUserID(@Param("id") int id);
}




