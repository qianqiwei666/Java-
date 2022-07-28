package com.qianqiwei.springsecuritydemo.config.loginAuthencation;

import cn.hutool.captcha.ShearCaptcha;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qianqiwei.springsecuritydemo.pojo.Role;
import com.qianqiwei.springsecuritydemo.pojo.UserMessage;
import com.qianqiwei.springsecuritydemo.pojo.UserRole;
import com.qianqiwei.springsecuritydemo.pojo.UserStatus;
import com.qianqiwei.springsecuritydemo.service.RoleService;
import com.qianqiwei.springsecuritydemo.service.UserMessageService;
import com.qianqiwei.springsecuritydemo.service.UserRoleService;
import com.qianqiwei.springsecuritydemo.service.UserStatusService;
import com.qianqiwei.springsecuritydemo.utils.HttpStatusForProject;
import com.qianqiwei.springsecuritydemo.utils.JWTUtils;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
public class RegisterHandler {

    @Autowired
    private UserMessageService userMessageServiceImpl;

    @Autowired
    private UserStatusService userStatusServiceImpl;

    @Autowired
    private UserRoleService userRoleServiceImpl;

    @Autowired
    private RoleService roleServiceImpl;

    @Autowired
    private ShearCaptcha shearCaptcha;

    //JWT工具
    @Autowired
    private JWTUtils jwtUtils;

    //设置加密类型
    private String alg;
    //设置加密算法
    private SignatureAlgorithm signatureAlgorithm;
    //设置盐
    private String sale;
    //设置过期时间
    private int expireTime;


    public ResponseEntity register(String username, String password,String code) {
        if (hasUsername(username)) {
            log.info("数据库中已有该用户");
            log.info("正在返回状态码");
            return ResponseEntity.status(HttpStatusForProject.UserAlreadyExists).body("已有当前用户");
        }
        if (!shearCaptcha.verify(code)) return ResponseEntity.status(HttpStatusForProject.RequestReject).body("验证码错误");
        try {
            insertBath(username,password);
            log.info("添加用户成功");
        }catch (Exception e){
            log.info("存储用户异常,正在进行回滚中.....");
            log.info("回滚成功!");
            log.info("返回错误状态码");
            return  ResponseEntity.status(HttpStatusForProject.AuthenticationFail).body("注册失败请重试");
        }
        log.info("生成token中");
        UserMessage userMessage = userMessageServiceImpl.selectMessageByUsername(username);
        String register_username=userMessage.getUsername();
        List<Role> roleList = roleServiceImpl.selectByUserID(userMessage.getId());
        String token=createToken(register_username,roleList);
        log.info("token生成成功返回到客户端.....");
        return ResponseEntity.ok().body(token);
    }

    public String createToken(String username,List<Role> roleList){
        StringBuffer temp_roles=new StringBuffer();
        roleList.stream().forEach(item->{
            temp_roles.append(item.getRoleName());
            temp_roles.append(",");
        });
        String roles = temp_roles.substring(0, temp_roles.length() - 1);
        //创建token头
        Map<String,Object> header=new HashMap<>();
        header.put("alg",alg);
        header.put("typ","JWT");
        //创建载荷
        Map<String,Object> payload=new HashMap<>();
        payload.put("username",username);
        payload.put("roles",roles);
        //设置token的过期时间
        LocalDateTime date = LocalDateTime.now(); //获取到现在的时间
        LocalDateTime time = date.plusSeconds(expireTime);//设置过期时间(秒)
        Date expireDate = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
        //创建好token
        String s = jwtUtils.create(header, payload, signatureAlgorithm, sale, expireDate);
        return s;
    }


    public boolean hasUsername(String username) {
        log.info("正在检测用户是否存在");
        QueryWrapper<UserMessage> messageQueryWrapper=new QueryWrapper<>();
        messageQueryWrapper.eq("username",username);
        UserMessage one = userMessageServiceImpl.getOne(messageQueryWrapper);
        return one!=null?true:false;
    }


    @Transactional
    public void insertBath(String username, String password) {
        log.info("正在存入用户");
        //插入当前用户
        userMessageServiceImpl.save(new UserMessage(null, username, password));
        QueryWrapper<UserMessage> userMessageQueryWrapper = new QueryWrapper<>();
        userMessageQueryWrapper.eq("username", username);
        UserMessage one = userMessageServiceImpl.getOne(userMessageQueryWrapper);
        int user_id = one.getId();
        userStatusServiceImpl.save(new UserStatus(user_id, null, null, null, null));
        userRoleServiceImpl.save(new UserRole(user_id, 1));
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
