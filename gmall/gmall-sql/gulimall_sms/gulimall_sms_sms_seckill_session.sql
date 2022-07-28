create table sms_seckill_session
(
    id          bigint auto_increment comment 'id'
        primary key,
    name        varchar(200) null comment '场次名称',
    start_time  datetime     null comment '每日开始时间',
    end_time    datetime     null comment '每日结束时间',
    status      tinyint(1)   null comment '启用状态',
    create_time datetime     null comment '创建时间'
)
    comment '秒杀活动场次';

