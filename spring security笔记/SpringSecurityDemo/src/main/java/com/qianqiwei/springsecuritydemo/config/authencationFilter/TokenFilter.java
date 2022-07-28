package com.qianqiwei.springsecuritydemo.config.authencationFilter;

import com.qianqiwei.springsecuritydemo.config.authencationHandler.TokenAuthencationFail;
import com.qianqiwei.springsecuritydemo.config.security.Keys;
import com.qianqiwei.springsecuritydemo.utils.JWTUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@Slf4j
public class TokenFilter extends AbstractAuthenticationProcessingFilter {

    public TokenFilter() {
        super("/**");
    }

    //JWT工具
    private JWTUtils jwtUtils = new JWTUtils();
    //Token失效处理器
    private TokenAuthencationFail authencationFail = new TokenAuthencationFail();

    //这里获取过滤器执行路径,以及认证管理器
    protected TokenFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    //重写doFilter方法验证token的合法性
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        log.info("正在进行token校验");
        //获取HttpServlet
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        //从请求头获取token
        String token = request.getHeader("Authentication");
        System.out.println(token);
        log.info("检查token的合法性");
        if (token == null) {
            log.info("token为空,返回错误状态码");
            unsuccessfulAuthentication(request, response, "会话无效");
            return;
        }
        //获取盐
        Keys keys = new Keys();
        //解析token
        Jwt jwt = null;
        try {
            jwt = jwtUtils.ParseToken(token, keys.getSale());
        } catch (JwtException e) {
            //token过期
            if (e instanceof ExpiredJwtException) {
                log.info("token过期");
                unsuccessfulAuthentication(request, response, "token过期");
                return;
            } else if (e instanceof MalformedJwtException) { //token无效
                log.info("token无效");
                unsuccessfulAuthentication(request, response, "token无效");
                return;
            }
        }
        log.info("解析token中......");
        //获取token的用户名以及权限
        Map<String, Object> bodyMap = (Map<String, Object>) jwt.getBody();
        //获取用户名
        String username = (String) bodyMap.get("username");
        //获取权限
        String roles = (String) bodyMap.get("roles");
        String role[] = roles.split(",");
        //装载权限
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (String item : role) {
            authorities.add(new SimpleGrantedAuthority(item));
        }
        log.info("token解析完成");
        log.info("用户名:" + username + "权限:" + authorities);
        //创建UsernamePasswordToken
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        //成功
        successfulAuthentication(request, response, chain, usernamePasswordAuthenticationToken);
    }


    //这里主要作为token验证,这个方法暂时用不着
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        return null;
    }


    //验证成功流程,如果成功就将请求放入SpringSecurity上下文中,直接进入下一个过滤器。
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //设置上下文
        SecurityContextHolder.getContext().setAuthentication(authResult);
        log.info("信息交给上下文");
        //直接进入下一个过滤器
        log.info("通过");
        chain.doFilter(request, response);
    }

    //验证失败流程,如果失败进入TokenAuthencationFail中,向前台抛出状态码。
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, String message) throws IOException, ServletException {
        authencationFail.onAuthenticationFailure(request, response, message);
    }
}
