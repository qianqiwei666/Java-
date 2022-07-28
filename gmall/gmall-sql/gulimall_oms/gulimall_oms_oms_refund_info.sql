create table oms_refund_info
(
    id              bigint auto_increment comment 'id'
        primary key,
    order_return_id bigint         null comment '退款的订单',
    refund          decimal(18, 4) null comment '退款金额',
    refund_sn       varchar(64)    null comment '退款交易流水号',
    refund_status   tinyint(1)     null comment '退款状态',
    refund_channel  tinyint        null comment '退款渠道[1-支付宝，2-微信，3-银联，4-汇款]',
    refund_content  varchar(5000)  null
)
    comment '退款信息';

