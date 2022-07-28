package com.qianqiwei.springsecuritydemo.config.loginAuthencation;




import cn.hutool.captcha.ShearCaptcha;
import com.qianqiwei.springsecuritydemo.utils.HttpStatusForProject;
import com.qianqiwei.springsecuritydemo.utils.JWTUtils;
import io.jsonwebtoken.SignatureAlgorithm;
import jdk.nashorn.internal.parser.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

//继承主要是为了获取验证管理器
@Slf4j
public class LoginHandler  {

    //JWT工具
    @Autowired
    private JWTUtils jwtUtils;

    //验证码
    @Autowired
    private ShearCaptcha shearCaptcha;

    //设置JWT参数
    //设置加密类型
    private String alg;
    //设置加密算法
    private SignatureAlgorithm signatureAlgorithm;
    //设置盐
    private String sale;
    //设置过期时间
    private int expireTime;


    private AuthenticationManager authenticationManager;

    public void setAuthenticationManager(AuthenticationManager authenticationManager){
        this.authenticationManager=authenticationManager;
    }

    public ResponseEntity login( String username, String password, String code) {
        log.info("正在进行用户登录");
        log.info("验证码校验");
        //校验验证码是否正确
        if (!shearCaptcha.verify(code)) {
            log.info("验证码不正确");
            return  ResponseEntity.status(HttpStatusForProject.RequestReject).body("验证码出错了");
        }
        log.info("验证码正确");
        //通过用户名与密码创建UsernamePasswordToken
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = null;
        try {
            //进行用户名与密码校验
            authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            //校验成功
            log.info("校验成功");
            //获取用户名
            String token_username=(String)authenticate.getPrincipal();
            log.info("生成token中");
            //获取角色
            Collection<? extends GrantedAuthority> authorities = authenticate.getAuthorities();
            //生成token
            String token = createToken(token_username, authorities);
            //将token进行返回
            log.info("token创建完成返回token到客户端");
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            //登录失败进行处理
            log.info("登录失败");
            log.info("返回错误信息");
            return ResponseEntity.status(HttpStatusForProject.UsernamePasswordFail).body(e.getMessage());
        }
    }

    private String createToken(String username,Collection<? extends GrantedAuthority> authorities){
        StringBuffer temp_roles=new StringBuffer();
        authorities.stream().forEach(item->{
            temp_roles.append(item.getAuthority());
            temp_roles.append(",");
        });
        //替换最后的逗号(生成token角色排版)
        String roles = temp_roles.substring(0, temp_roles.length() - 1);
        //设置token的过期时间
        LocalDateTime date = LocalDateTime.now(); //获取到现在的时间
        LocalDateTime time = date.plusSeconds(expireTime);//设置过期时间(秒)
        Date expireDate = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
        //设置Token
        Map<String,Object> header=new HashMap<>();
        header.put("alg","HS256");
        header.put("typ","JWT");
        //创建载荷
        Map<String,Object> payload=new HashMap<>();
        payload.put("username",username);
        payload.put("roles",roles);
        //创建好token
        String s = jwtUtils.create(header, payload, signatureAlgorithm, sale, expireDate);
        return s;
    }


    public void setAlg(String alg) {
        this.alg = alg;
    }

    public void setSignatureAlgorithm(SignatureAlgorithm signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }
}
