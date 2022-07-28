create table ums_integration_change_history
(
    id           bigint auto_increment comment 'id'
        primary key,
    member_id    bigint       null comment 'member_id',
    create_time  datetime     null comment 'create_time',
    change_count int          null comment '变化的值',
    note         varchar(255) null comment '备注',
    source_tyoe  tinyint      null comment '来源[0->购物；1->管理员修改;2->活动]'
)
    comment '积分变化历史记录';

