create table wms_purchase_detail
(
    id          bigint auto_increment
        primary key,
    purchase_id bigint         null comment '采购单id',
    sku_id      bigint         null comment '采购商品id',
    sku_num     int            null comment '采购数量',
    sku_price   decimal(18, 4) null comment '采购金额',
    ware_id     bigint         null comment '仓库id',
    status      int            null comment '状态[0新建，1已分配，2正在采购，3已完成，4采购失败]'
);

