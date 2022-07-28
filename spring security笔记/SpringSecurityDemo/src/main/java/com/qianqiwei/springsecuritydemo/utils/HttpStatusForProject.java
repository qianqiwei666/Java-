package com.qianqiwei.springsecuritydemo.utils;

public class HttpStatusForProject {
    //验证无效
    static public int AuthenticationFail=401;
    //用户角色权限不够
    static public int UserRolePermissions=403;
    //验证码错误
    static public int RequestReject=406;
    //用户已存在
    static public int UserAlreadyExists=408;
    //用户名密码错误
    static public int UsernamePasswordFail=402;



}
