create table oms_order_operate_history
(
    id           bigint auto_increment comment 'id'
        primary key,
    order_id     bigint       null comment '订单id',
    operate_man  varchar(100) null comment '操作人[用户；系统；后台管理员]',
    create_time  datetime     null comment '操作时间',
    order_status tinyint      null comment '订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】',
    note         varchar(500) null comment '备注'
)
    comment '订单操作历史记录';

