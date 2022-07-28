create table ums_member_receive_address
(
    id             bigint auto_increment comment 'id'
        primary key,
    member_id      bigint       null comment 'member_id',
    name           varchar(255) null comment '收货人姓名',
    phone          varchar(64)  null comment '电话',
    post_code      varchar(64)  null comment '邮政编码',
    province       varchar(100) null comment '省份/直辖市',
    city           varchar(100) null comment '城市',
    region         varchar(100) null comment '区',
    detail_address varchar(255) null comment '详细地址(街道)',
    areacode       varchar(15)  null comment '省市区代码',
    default_status tinyint(1)   null comment '是否默认'
)
    comment '会员收货地址';

