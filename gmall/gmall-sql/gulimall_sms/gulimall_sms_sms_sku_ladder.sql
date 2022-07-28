create table sms_sku_ladder
(
    id         bigint auto_increment comment 'id'
        primary key,
    sku_id     bigint         null comment 'spu_id',
    full_count int            null comment '满几件',
    discount   decimal(4, 2)  null comment '打几折',
    price      decimal(18, 4) null comment '折后价',
    add_other  tinyint(1)     null comment '是否叠加其他优惠[0-不可叠加，1-可叠加]'
)
    comment '商品阶梯价格';

