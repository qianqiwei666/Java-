create table sys_log
(
    id          bigint auto_increment
        primary key,
    username    varchar(50)   null comment '用户名',
    operation   varchar(50)   null comment '用户操作',
    method      varchar(200)  null comment '请求方法',
    params      varchar(5000) null comment '请求参数',
    time        bigint        not null comment '执行时长(毫秒)',
    ip          varchar(64)   null comment 'IP地址',
    create_date datetime      null comment '创建时间'
)
    comment '系统日志';

