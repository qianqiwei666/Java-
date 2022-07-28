create table sms_home_subject
(
    id        bigint auto_increment comment 'id'
        primary key,
    name      varchar(200) null comment '专题名字',
    title     varchar(255) null comment '专题标题',
    sub_title varchar(255) null comment '专题副标题',
    status    tinyint(1)   null comment '显示状态',
    url       varchar(500) null comment '详情连接',
    sort      int          null comment '排序',
    img       varchar(500) null comment '专题图片地址'
)
    comment '首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】';

