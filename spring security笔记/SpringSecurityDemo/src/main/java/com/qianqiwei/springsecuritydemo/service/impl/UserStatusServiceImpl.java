package com.qianqiwei.springsecuritydemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianqiwei.springsecuritydemo.pojo.UserStatus;
import com.qianqiwei.springsecuritydemo.service.UserStatusService;
import com.qianqiwei.springsecuritydemo.mapper.UserStatusMapper;
import org.springframework.stereotype.Service;

/**
* @author 15915
* @description 针对表【user_status】的数据库操作Service实现
* @createDate 2022-04-13 11:14:49
*/
@Service
public class UserStatusServiceImpl extends ServiceImpl<UserStatusMapper, UserStatus>
    implements UserStatusService{

}




