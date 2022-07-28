create table oms_order_setting
(
    id                    bigint auto_increment comment 'id'
        primary key,
    flash_order_overtime  int     null comment '秒杀订单超时关闭时间(分)',
    normal_order_overtime int     null comment '正常订单超时时间(分)',
    confirm_overtime      int     null comment '发货后自动确认收货时间（天）',
    finish_overtime       int     null comment '自动完成交易时间，不能申请退货（天）',
    comment_overtime      int     null comment '订单完成后自动好评时间（天）',
    member_level          tinyint null comment '会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】'
)
    comment '订单配置信息';

