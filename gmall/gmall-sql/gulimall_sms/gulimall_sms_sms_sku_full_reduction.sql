create table sms_sku_full_reduction
(
    id           bigint auto_increment comment 'id'
        primary key,
    sku_id       bigint         null comment 'spu_id',
    full_price   decimal(18, 4) null comment '满多少',
    reduce_price decimal(18, 4) null comment '减多少',
    add_other    tinyint(1)     null comment '是否参与其他优惠'
)
    comment '商品满减信息';

