package com.qianqiwei.springsecuritydemo.config.authencation;

import com.qianqiwei.springsecuritydemo.pojo.Role;
import com.qianqiwei.springsecuritydemo.pojo.UserMessage;
import com.qianqiwei.springsecuritydemo.pojo.UserRole;
import com.qianqiwei.springsecuritydemo.service.RoleService;
import com.qianqiwei.springsecuritydemo.service.UserMessageService;
import com.qianqiwei.springsecuritydemo.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class UserDetailsManager implements UserDetailsService {
    @Autowired
    private UserMessageService userMessageServiceImpl;
    @Autowired
    private RoleService roleServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("数据库中获取用户信息......");
        //获取用户信息
        UserMessage userMessage = userMessageServiceImpl.selectMessageByUsername(username);
        if (userMessage==null) throw new UsernameNotFoundException("用户未找到");
        //查询用户权限
        List<Role> roleList=roleServiceImpl.selectByUserID(userMessage.getId());
        //设置权限
        Set<SimpleGrantedAuthority> authorities=new HashSet<>();
        roleList.stream().forEach(item->{
            authorities.add(new SimpleGrantedAuthority(item.getRoleName()));
        });
        //将用户信息整理好后放入UserDetails
        UserDetails userDetails=new UserDetailsMessageBean(
                userMessage.getUsername(),
                userMessage.getPassword(),
                authorities,
                userMessage.getUserStatus().getAccountnonexpired()==1?true:false,
                userMessage.getUserStatus().getAccountnonlocked()==1?true:false,
                userMessage.getUserStatus().getCredentialsnonexpired()==1?true:false,
                userMessage.getUserStatus().getEnabled()==1?true:false
        );
        return userDetails;
    }
}
