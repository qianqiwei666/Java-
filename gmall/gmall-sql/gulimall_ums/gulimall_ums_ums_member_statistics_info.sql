create table ums_member_statistics_info
(
    id                    bigint auto_increment comment 'id'
        primary key,
    member_id             bigint         null comment '会员id',
    consume_amount        decimal(18, 4) null comment '累计消费金额',
    coupon_amount         decimal(18, 4) null comment '累计优惠金额',
    order_count           int            null comment '订单数量',
    coupon_count          int            null comment '优惠券数量',
    comment_count         int            null comment '评价数',
    return_order_count    int            null comment '退货数量',
    login_count           int            null comment '登录次数',
    attend_count          int            null comment '关注数量',
    fans_count            int            null comment '粉丝数量',
    collect_product_count int            null comment '收藏的商品数量',
    collect_subject_count int            null comment '收藏的专题活动数量',
    collect_comment_count int            null comment '收藏的评论数量',
    invite_friend_count   int            null comment '邀请的朋友数量'
)
    comment '会员统计信息';

