package com.qianqiwei.springsecuritydemo.config.authencation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

//进行权限认证
@Slf4j
@Component
public class AuthencationUser implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsManager;

    //进行权限校验
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("数据校验中.....");
        //获取用户名与密码
        String username=authentication.getPrincipal().toString();
        String password=authentication.getCredentials().toString();
        //从userDetailsManager拿取从数据库查询的数据
        UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
        log.info("检查用户状态中.....");
        //获取用户的状态进行限制
        if (!userDetails.isAccountNonExpired()) throw new AccountExpiredException("账户过期");
        if (!userDetails.isAccountNonLocked()) throw new  LockedException("当前账户被锁定");
        if (!userDetails.isCredentialsNonExpired()) throw new CredentialsExpiredException("密码过期");
        if (!userDetails.isEnabled()) throw new LockedException("当前用户被锁定");
        log.info("用户状态正常");
        //比对密码
        log.info("进行密码校验中.....");
        if (userDetails.getPassword().equals(password)){
            log.info("密码比对成功,准备返回UsernamePasswordAuthenticationToken");
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(username,password,userDetails.getAuthorities());
            return usernamePasswordAuthenticationToken;
        }
        log.info("用户密码错误");
        throw new BadCredentialsException("密码出错了");
    }


    //是否使用自定义的验证(如果要自定义必须开启)
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
