package com.qianqiwei.springsecuritydemo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

/**
 * 
 * @TableName user_role
 */
@TableName(value ="user_role")
public class UserRole implements Serializable {
    /**
     * 
     */
    private Integer userId;

    /**
     * 
     */
    private Integer roleId;

    @TableField(exist = false)
    List<Role> roleList;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public UserRole(Integer userId, Integer roleId, List<Role> roleList) {
        this.userId = userId;
        this.roleId = roleId;
        this.roleList = roleList;
    }

    public UserRole() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public UserRole(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "userId=" + userId +
                ", roleId=" + roleId +
                ", roleList=" + roleList +
                '}';
    }
}