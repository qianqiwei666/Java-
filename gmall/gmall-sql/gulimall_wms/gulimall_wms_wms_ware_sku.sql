create table wms_ware_sku
(
    id           bigint auto_increment comment 'id'
        primary key,
    sku_id       bigint        null comment 'sku_id',
    ware_id      bigint        null comment '仓库id',
    stock        int           null comment '库存数',
    sku_name     varchar(200)  null comment 'sku_name',
    stock_locked int default 0 null comment '锁定库存'
)
    comment '商品库存';

create index sku_id
    on wms_ware_sku (sku_id);

create index ware_id
    on wms_ware_sku (ware_id);

