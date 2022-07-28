create table sys_role
(
    role_id        bigint auto_increment
        primary key,
    role_name      varchar(100) null comment '角色名称',
    remark         varchar(100) null comment '备注',
    create_user_id bigint       null comment '创建者ID',
    create_time    datetime     null comment '创建时间'
)
    comment '角色';

