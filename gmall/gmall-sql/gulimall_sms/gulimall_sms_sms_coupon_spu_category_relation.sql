create table sms_coupon_spu_category_relation
(
    id            bigint auto_increment comment 'id'
        primary key,
    coupon_id     bigint      null comment '优惠券id',
    category_id   bigint      null comment '产品分类id',
    category_name varchar(64) null comment '产品分类名称'
)
    comment '优惠券分类关联';

