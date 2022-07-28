create table oms_order_return_reason
(
    id          bigint auto_increment comment 'id'
        primary key,
    name        varchar(200) null comment '退货原因名',
    sort        int          null comment '排序',
    status      tinyint(1)   null comment '启用状态',
    create_time datetime     null comment 'create_time'
)
    comment '退货原因';

