create table sys_user_role
(
    id      bigint auto_increment
        primary key,
    user_id bigint null comment '用户ID',
    role_id bigint null comment '角色ID'
)
    comment '用户与角色对应关系';

