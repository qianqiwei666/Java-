create table sms_home_adv
(
    id           bigint auto_increment comment 'id'
        primary key,
    name         varchar(100) null comment '名字',
    pic          varchar(500) null comment '图片地址',
    start_time   datetime     null comment '开始时间',
    end_time     datetime     null comment '结束时间',
    status       tinyint(1)   null comment '状态',
    click_count  int          null comment '点击数',
    url          varchar(500) null comment '广告详情连接地址',
    note         varchar(500) null comment '备注',
    sort         int          null comment '排序',
    publisher_id bigint       null comment '发布者',
    auth_id      bigint       null comment '审核者'
)
    comment '首页轮播广告';

