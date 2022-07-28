create table sms_member_price
(
    id                bigint auto_increment comment 'id'
        primary key,
    sku_id            bigint         null comment 'sku_id',
    member_level_id   bigint         null comment '会员等级id',
    member_level_name varchar(100)   null comment '会员等级名',
    member_price      decimal(18, 4) null comment '会员对应价格',
    add_other         tinyint(1)     null comment '可否叠加其他优惠[0-不可叠加优惠，1-可叠加]'
)
    comment '商品会员价格';

