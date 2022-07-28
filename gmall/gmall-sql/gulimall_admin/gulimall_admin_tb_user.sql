create table tb_user
(
    user_id     bigint auto_increment
        primary key,
    username    varchar(50) not null comment '用户名',
    mobile      varchar(20) not null comment '手机号',
    password    varchar(64) null comment '密码',
    create_time datetime    null comment '创建时间',
    constraint username
        unique (username)
)
    comment '用户';

INSERT INTO gulimall_admin.tb_user (user_id, username, mobile, password, create_time) VALUES (1, 'mark', '13612345678', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '2017-03-23 22:37:41');
