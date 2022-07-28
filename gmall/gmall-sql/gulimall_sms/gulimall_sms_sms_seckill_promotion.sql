create table sms_seckill_promotion
(
    id          bigint auto_increment comment 'id'
        primary key,
    title       varchar(255) null comment '活动标题',
    start_time  datetime     null comment '开始日期',
    end_time    datetime     null comment '结束日期',
    status      tinyint      null comment '上下线状态',
    create_time datetime     null comment '创建时间',
    user_id     bigint       null comment '创建人'
)
    comment '秒杀活动';

