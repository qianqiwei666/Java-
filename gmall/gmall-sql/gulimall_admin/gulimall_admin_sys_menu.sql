create table sys_menu
(
    menu_id   bigint auto_increment
        primary key,
    parent_id bigint       null comment '父菜单ID，一级菜单为0',
    name      varchar(50)  null comment '菜单名称',
    url       varchar(200) null comment '菜单URL',
    perms     varchar(500) null comment '授权(多个用逗号分隔，如：user:list,user:create)',
    type      int          null comment '类型   0：目录   1：菜单   2：按钮',
    icon      varchar(50)  null comment '菜单图标',
    order_num int          null comment '排序'
)
    comment '菜单管理';

INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (1, 0, '系统管理', null, null, 0, 'system', 0);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (2, 1, '管理员列表', 'sys/user', null, 1, 'admin', 1);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (3, 1, '角色管理', 'sys/role', null, 1, 'role', 2);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (4, 1, '菜单管理', 'sys/menu', null, 1, 'menu', 3);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (5, 1, 'SQL监控', 'http://localhost:8080/renren-fast/druid/sql.html', null, 1, 'sql', 4);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (6, 1, '定时任务', 'job/schedule', null, 1, 'job', 5);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (7, 6, '查看', null, 'sys:schedule:list,sys:schedule:info', 2, null, 0);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (8, 6, '新增', null, 'sys:schedule:save', 2, null, 0);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (9, 6, '修改', null, 'sys:schedule:update', 2, null, 0);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (10, 6, '删除', null, 'sys:schedule:delete', 2, null, 0);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (11, 6, '暂停', null, 'sys:schedule:pause', 2, null, 0);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (12, 6, '恢复', null, 'sys:schedule:resume', 2, null, 0);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (13, 6, '立即执行', null, 'sys:schedule:run', 2, null, 0);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (14, 6, '日志列表', null, 'sys:schedule:log', 2, null, 0);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (15, 2, '查看', null, 'sys:user:list,sys:user:info', 2, null, 0);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (16, 2, '新增', null, 'sys:user:save,sys:role:select', 2, null, 0);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (17, 2, '修改', null, 'sys:user:update,sys:role:select', 2, null, 0);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (18, 2, '删除', null, 'sys:user:delete', 2, null, 0);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (19, 3, '查看', null, 'sys:role:list,sys:role:info', 2, null, 0);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (20, 3, '新增', null, 'sys:role:save,sys:menu:list', 2, null, 0);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (21, 3, '修改', null, 'sys:role:update,sys:menu:list', 2, null, 0);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (22, 3, '删除', null, 'sys:role:delete', 2, null, 0);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (23, 4, '查看', null, 'sys:menu:list,sys:menu:info', 2, null, 0);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (24, 4, '新增', null, 'sys:menu:save,sys:menu:select', 2, null, 0);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (25, 4, '修改', null, 'sys:menu:update,sys:menu:select', 2, null, 0);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (26, 4, '删除', null, 'sys:menu:delete', 2, null, 0);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (27, 1, '参数管理', 'sys/config', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', 1, 'config', 6);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (29, 1, '系统日志', 'sys/log', 'sys:log:list', 1, 'log', 7);
INSERT INTO gulimall_admin.sys_menu (menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (30, 1, '文件上传', 'oss/oss', 'sys:oss:all', 1, 'oss', 6);
