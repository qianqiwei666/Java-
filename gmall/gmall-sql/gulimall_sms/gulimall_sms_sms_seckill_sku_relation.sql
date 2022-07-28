create table sms_seckill_sku_relation
(
    id                   bigint auto_increment comment 'id'
        primary key,
    promotion_id         bigint  null comment '活动id',
    promotion_session_id bigint  null comment '活动场次id',
    sku_id               bigint  null comment '商品id',
    seckill_price        decimal null comment '秒杀价格',
    seckill_count        decimal null comment '秒杀总量',
    seckill_limit        decimal null comment '每人限购数量',
    seckill_sort         int     null comment '排序'
)
    comment '秒杀活动商品关联';

