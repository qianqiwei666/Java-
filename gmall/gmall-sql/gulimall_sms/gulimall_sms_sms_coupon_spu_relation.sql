create table sms_coupon_spu_relation
(
    id        bigint auto_increment comment 'id'
        primary key,
    coupon_id bigint       null comment '优惠券id',
    spu_id    bigint       null comment 'spu_id',
    spu_name  varchar(255) null comment 'spu_name'
)
    comment '优惠券与产品关联';

