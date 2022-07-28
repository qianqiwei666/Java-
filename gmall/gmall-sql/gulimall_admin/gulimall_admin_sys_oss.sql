create table sys_oss
(
    id          bigint auto_increment
        primary key,
    url         varchar(200) null comment 'URL地址',
    create_date datetime     null comment '创建时间'
)
    comment '文件上传';

