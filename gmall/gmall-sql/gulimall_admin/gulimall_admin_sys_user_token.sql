create table sys_user_token
(
    user_id     bigint       not null
        primary key,
    token       varchar(100) not null comment 'token',
    expire_time datetime     null comment '过期时间',
    update_time datetime     null comment '更新时间',
    constraint token
        unique (token)
)
    comment '系统用户Token';

