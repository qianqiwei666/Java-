create table ums_member_login_log
(
    id          bigint auto_increment comment 'id'
        primary key,
    member_id   bigint      null comment 'member_id',
    create_time datetime    null comment '创建时间',
    ip          varchar(64) null comment 'ip',
    city        varchar(64) null comment 'city',
    login_type  tinyint(1)  null comment '登录类型[1-web，2-app]'
)
    comment '会员登录记录';

