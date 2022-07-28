create table sms_coupon
(
    id                bigint auto_increment comment 'id'
        primary key,
    coupon_type       tinyint(1)     null comment '优惠卷类型[0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券]',
    coupon_img        varchar(2000)  null comment '优惠券图片',
    coupon_name       varchar(100)   null comment '优惠卷名字',
    num               int            null comment '数量',
    amount            decimal(18, 4) null comment '金额',
    per_limit         int            null comment '每人限领张数',
    min_point         decimal(18, 4) null comment '使用门槛',
    start_time        datetime       null comment '开始时间',
    end_time          datetime       null comment '结束时间',
    use_type          tinyint(1)     null comment '使用类型[0->全场通用；1->指定分类；2->指定商品]',
    note              varchar(200)   null comment '备注',
    publish_count     int            null comment '发行数量',
    use_count         int            null comment '已使用数量',
    receive_count     int            null comment '领取数量',
    enable_start_time datetime       null comment '可以领取的开始日期',
    enable_end_time   datetime       null comment '可以领取的结束日期',
    code              varchar(64)    null comment '优惠码',
    member_level      tinyint(1)     null comment '可以领取的会员等级[0->不限等级，其他-对应等级]',
    publish           tinyint(1)     null comment '发布状态[0-未发布，1-已发布]'
)
    comment '优惠券信息';

