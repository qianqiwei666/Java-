create table wms_ware_order_task
(
    id               bigint auto_increment comment 'id'
        primary key,
    order_id         bigint       null comment 'order_id',
    order_sn         varchar(255) null comment 'order_sn',
    consignee        varchar(100) null comment '收货人',
    consignee_tel    char(15)     null comment '收货人电话',
    delivery_address varchar(500) null comment '配送地址',
    order_comment    varchar(200) null comment '订单备注',
    payment_way      tinyint(1)   null comment '付款方式【 1:在线付款 2:货到付款】',
    task_status      tinyint      null comment '任务状态',
    order_body       varchar(255) null comment '订单描述',
    tracking_no      char(30)     null comment '物流单号',
    create_time      datetime     null comment 'create_time',
    ware_id          bigint       null comment '仓库id',
    task_comment     varchar(500) null comment '工作单备注'
)
    comment '库存工作单';

