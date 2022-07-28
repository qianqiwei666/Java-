create table sys_captcha
(
    uuid        char(36)   not null comment 'uuid'
        primary key,
    code        varchar(6) not null comment '验证码',
    expire_time datetime   null comment '过期时间'
)
    comment '系统验证码';

