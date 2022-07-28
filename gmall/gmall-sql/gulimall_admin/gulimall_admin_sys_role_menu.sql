create table sys_role_menu
(
    id      bigint auto_increment
        primary key,
    role_id bigint null comment '角色ID',
    menu_id bigint null comment '菜单ID'
)
    comment '角色与菜单对应关系';

