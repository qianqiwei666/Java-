create table sms_coupon_history
(
    id               bigint auto_increment comment 'id'
        primary key,
    coupon_id        bigint      null comment '优惠券id',
    member_id        bigint      null comment '会员id',
    member_nick_name varchar(64) null comment '会员名字',
    get_type         tinyint(1)  null comment '获取方式[0->后台赠送；1->主动领取]',
    create_time      datetime    null comment '创建时间',
    use_type         tinyint(1)  null comment '使用状态[0->未使用；1->已使用；2->已过期]',
    use_time         datetime    null comment '使用时间',
    order_id         bigint      null comment '订单id',
    order_sn         bigint      null comment '订单号'
)
    comment '优惠券领取历史记录';

