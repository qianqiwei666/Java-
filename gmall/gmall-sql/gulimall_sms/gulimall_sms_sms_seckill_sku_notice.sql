create table sms_seckill_sku_notice
(
    id            bigint auto_increment comment 'id'
        primary key,
    member_id     bigint     null comment 'member_id',
    sku_id        bigint     null comment 'sku_id',
    session_id    bigint     null comment '活动场次id',
    subcribe_time datetime   null comment '订阅时间',
    send_time     datetime   null comment '发送时间',
    notice_type   tinyint(1) null comment '通知方式[0-短信，1-邮件]'
)
    comment '秒杀商品通知订阅';

