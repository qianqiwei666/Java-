create table wms_ware_order_task_detail
(
    id          bigint auto_increment comment 'id'
        primary key,
    sku_id      bigint       null comment 'sku_id',
    sku_name    varchar(255) null comment 'sku_name',
    sku_num     int          null comment '购买个数',
    task_id     bigint       null comment '工作单id',
    ware_id     bigint       null comment '仓库id',
    lock_status int          null comment '1-已锁定  2-已解锁  3-扣减'
)
    comment '库存工作单';

