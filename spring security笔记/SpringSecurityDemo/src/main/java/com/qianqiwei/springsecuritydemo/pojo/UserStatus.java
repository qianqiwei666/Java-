package com.qianqiwei.springsecuritydemo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName user_status
 */
@TableName(value ="user_status")
public class UserStatus implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer userId;

    /**
     * 
     */
    private Integer accountnonexpired;

    /**
     * 
     */
    private Integer accountnonlocked;

    /**
     * 
     */
    private Integer credentialsnonexpired;

    /**
     * 
     */
    private Integer enabled;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public UserStatus() {
    }

    public UserStatus(Integer userId, Integer accountnonexpired, Integer accountnonlocked, Integer credentialsnonexpired, Integer enabled) {
        this.userId = userId;
        this.accountnonexpired = accountnonexpired;
        this.accountnonlocked = accountnonlocked;
        this.credentialsnonexpired = credentialsnonexpired;
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "UserStatus{" +
                "userId=" + userId +
                ", accountnonexpired=" + accountnonexpired +
                ", accountnonlocked=" + accountnonlocked +
                ", credentialsnonexpired=" + credentialsnonexpired +
                ", enabled=" + enabled +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAccountnonexpired() {
        return accountnonexpired;
    }

    public void setAccountnonexpired(Integer accountnonexpired) {
        this.accountnonexpired = accountnonexpired;
    }

    public Integer getAccountnonlocked() {
        return accountnonlocked;
    }

    public void setAccountnonlocked(Integer accountnonlocked) {
        this.accountnonlocked = accountnonlocked;
    }

    public Integer getCredentialsnonexpired() {
        return credentialsnonexpired;
    }

    public void setCredentialsnonexpired(Integer credentialsnonexpired) {
        this.credentialsnonexpired = credentialsnonexpired;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }
}