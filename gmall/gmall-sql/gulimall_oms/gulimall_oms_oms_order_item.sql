create table oms_order_item
(
    id                 bigint auto_increment comment 'id'
        primary key,
    order_id           bigint         null comment 'order_id',
    order_sn           char(32)       null comment 'order_sn',
    spu_id             bigint         null comment 'spu_id',
    spu_name           varchar(255)   null comment 'spu_name',
    spu_pic            varchar(500)   null comment 'spu_pic',
    spu_brand          varchar(200)   null comment '品牌',
    category_id        bigint         null comment '商品分类id',
    sku_id             bigint         null comment '商品sku编号',
    sku_name           varchar(255)   null comment '商品sku名字',
    sku_pic            varchar(500)   null comment '商品sku图片',
    sku_price          decimal(18, 4) null comment '商品sku价格',
    sku_quantity       int            null comment '商品购买的数量',
    sku_attrs_vals     varchar(500)   null comment '商品销售属性组合（JSON）',
    promotion_amount   decimal(18, 4) null comment '商品促销分解金额',
    coupon_amount      decimal(18, 4) null comment '优惠券优惠分解金额',
    integration_amount decimal(18, 4) null comment '积分优惠分解金额',
    real_amount        decimal(18, 4) null comment '该商品经过优惠后的分解金额',
    gift_integration   int            null comment '赠送积分',
    gift_growth        int            null comment '赠送成长值'
)
    comment '订单项信息';

