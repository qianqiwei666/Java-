create table oms_payment_info
(
    id               bigint auto_increment comment 'id'
        primary key,
    order_sn         char(32)       null comment '订单号（对外业务号）',
    order_id         bigint         null comment '订单id',
    alipay_trade_no  varchar(50)    null comment '支付宝交易流水号',
    total_amount     decimal(18, 4) null comment '支付总金额',
    subject          varchar(200)   null comment '交易内容',
    payment_status   varchar(20)    null comment '支付状态',
    create_time      datetime       null comment '创建时间',
    confirm_time     datetime       null comment '确认时间',
    callback_content varchar(4000)  null comment '回调内容',
    callback_time    datetime       null comment '回调时间'
)
    comment '支付信息表';

