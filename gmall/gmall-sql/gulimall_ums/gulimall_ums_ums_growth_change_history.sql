create table ums_growth_change_history
(
    id           bigint auto_increment comment 'id'
        primary key,
    member_id    bigint     null comment 'member_id',
    create_time  datetime   null comment 'create_time',
    change_count int        null comment '改变的值（正负计数）',
    note         varchar(0) null comment '备注',
    source_type  tinyint    null comment '积分来源[0-购物，1-管理员修改]'
)
    comment '成长值变化历史记录';

