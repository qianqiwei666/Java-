create table oms_order_return_apply
(
    id              bigint auto_increment comment 'id'
        primary key,
    order_id        bigint         null comment 'order_id',
    sku_id          bigint         null comment '退货商品id',
    order_sn        char(32)       null comment '订单编号',
    create_time     datetime       null comment '申请时间',
    member_username varchar(64)    null comment '会员用户名',
    return_amount   decimal(18, 4) null comment '退款金额',
    return_name     varchar(100)   null comment '退货人姓名',
    return_phone    varchar(20)    null comment '退货人电话',
    status          tinyint(1)     null comment '申请状态[0->待处理；1->退货中；2->已完成；3->已拒绝]',
    handle_time     datetime       null comment '处理时间',
    sku_img         varchar(500)   null comment '商品图片',
    sku_name        varchar(200)   null comment '商品名称',
    sku_brand       varchar(200)   null comment '商品品牌',
    sku_attrs_vals  varchar(500)   null comment '商品销售属性(JSON)',
    sku_count       int            null comment '退货数量',
    sku_price       decimal(18, 4) null comment '商品单价',
    sku_real_price  decimal(18, 4) null comment '商品实际支付单价',
    reason          varchar(200)   null comment '原因',
    description述    varchar(500)   null comment '描述',
    desc_pics       varchar(2000)  null comment '凭证图片，以逗号隔开',
    handle_note     varchar(500)   null comment '处理备注',
    handle_man      varchar(200)   null comment '处理人员',
    receive_man     varchar(100)   null comment '收货人',
    receive_time    datetime       null comment '收货时间',
    receive_note    varchar(500)   null comment '收货备注',
    receive_phone   varchar(20)    null comment '收货电话',
    company_address varchar(500)   null comment '公司收货地址'
)
    comment '订单退货申请';

