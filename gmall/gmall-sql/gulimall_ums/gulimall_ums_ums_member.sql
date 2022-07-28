create table ums_member
(
    id          bigint auto_increment comment 'id'
        primary key,
    level_id    bigint       null comment '会员等级id',
    username    char(64)     null comment '用户名',
    password    varchar(64)  null comment '密码',
    nickname    varchar(64)  null comment '昵称',
    mobile      varchar(20)  null comment '手机号码',
    email       varchar(64)  null comment '邮箱',
    header      varchar(500) null comment '头像',
    gender      tinyint      null comment '性别',
    birth       date         null comment '生日',
    city        varchar(500) null comment '所在城市',
    job         varchar(255) null comment '职业',
    sign        varchar(255) null comment '个性签名',
    source_type tinyint      null comment '用户来源',
    integration int          null comment '积分',
    growth      int          null comment '成长值',
    status      tinyint      null comment '启用状态',
    create_time datetime     null comment '注册时间'
)
    comment '会员';

