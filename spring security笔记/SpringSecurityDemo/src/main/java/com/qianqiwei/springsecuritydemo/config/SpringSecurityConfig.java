package com.qianqiwei.springsecuritydemo.config;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import com.qianqiwei.springsecuritydemo.config.authencationFilter.TokenFilter;
import com.qianqiwei.springsecuritydemo.config.authencationHandler.LessPermissionsHandler;
import com.qianqiwei.springsecuritydemo.config.loginAuthencation.LoginHandler;
import com.qianqiwei.springsecuritydemo.config.loginAuthencation.RegisterHandler;
import com.qianqiwei.springsecuritydemo.config.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    //获取自定义用户管理器
    @Autowired
    private UserDetailsService userDetailsManager;
    //获取用户校验处理器
    @Autowired
    private AuthenticationProvider authencationUser;
    //通常这里一般配置忽略请求
    @Override
    public void configure(WebSecurity web) throws Exception {
        //放行资源,这里不走AbstractAuthenticationProcessingFilter
        web.ignoring().antMatchers("/app/login/**","/app/checkCode/**","/getCode/**","/app/register/**");
    }

    //配置请求
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //去掉的配置:
        //路径拦截去除原因:每次请求都要带token,来进行验证。不需要验证的直接使用忽略请求
        //登录去除原因:自定义Controller已经帮我们进行登录验证,以及成功处理错误处理。并且我重写了AbstractAuthenticationProcessingFilter中的doFilter方法。自带的处理器已经失效
        //去掉SpringSecurity的Session原因: 我们的验证由token来进行的。
        //不需要authenticationEntryPoint,我已经对没有登录的用户进行了处理,token校验的时候
        http
                .exceptionHandling()
                .accessDeniedHandler(new LessPermissionsHandler())//配置角色权限处理器
                .and()
                .addFilterBefore(corsFilter(),UsernamePasswordAuthenticationFilter.class) //一定要加过滤跨域,不然token访问不到
                .addFilterAt(new TokenFilter(), UsernamePasswordAuthenticationFilter.class) //设置token过滤器
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//关闭Spring自带的session
                .and()
                .csrf()
                .disable();//关闭CSRF后续自己配置CSRF校验过滤器
    }

    //由于需要用到SpringSecurity的AuthenticationManager必须在SpringSecurity配置类中进行注入
    //配置额外的参数
    @Bean
    public LoginHandler loginHandler(){
        //准备过期时间
        int expireTime=30;
        //准备盐
        Keys keys=new Keys();
        LoginHandler loginHandler=new LoginHandler();
        //设置加密算法
        loginHandler.setSignatureAlgorithm(SignatureAlgorithm.HS256);
        //设置时间
        loginHandler.setExpireTime(expireTime);
        //设置加密类型
        loginHandler.setAlg("HS256");
        //设置盐
        loginHandler.setSale(keys.getSale());
        try {
            loginHandler.setAuthenticationManager(authenticationManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginHandler;
    }

    @Bean
    public RegisterHandler registerHandler(){

        RegisterHandler registerHandler=new RegisterHandler();
        //准备过期时间
        int expireTime=30;
        //准备盐
        Keys keys=new Keys();
        //设置盐
        registerHandler.setSale(keys.getSale());
        //设置过期时间
        registerHandler.setExpireTime(expireTime);
        //设置加密类型
        registerHandler.setAlg("HS256");
        //设置加密算法
        registerHandler.setSignatureAlgorithm(SignatureAlgorithm.HS256);
        return registerHandler;
    }

    //配置权限管理器
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //设置UserDetailsService,authenticationProvider,进行用户信息的认证,查看是否合法。
        auth.userDetailsService(userDetailsManager).and().authenticationProvider(authencationUser);
    }

    //配置密钥
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }


    @Bean
    public ShearCaptcha shearCaptcha(){
        return CaptchaUtil.createShearCaptcha(300, 120, 4, 4);
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 允许cookies跨域
        config.addAllowedOrigin("*");// 允许向该服务器提交请求的URI，*表示全部允许。。这里尽量限制来源域，比如http://xxxx:8080 ,以降低安全风险。。
        config.addAllowedHeader("*");// 允许访问的头信息,*表示全部
        config.setMaxAge(18000L);// 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.addAllowedMethod("*");// 允许提交请求的方法，*表示全部允许，也可以单独设置GET、PUT等
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
