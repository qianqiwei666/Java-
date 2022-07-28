create table pms_sku_sale_attr_value
(
    id         bigint auto_increment comment 'id'
        primary key,
    sku_id     bigint       null comment 'sku_id',
    attr_id    bigint       null comment 'attr_id',
    attr_name  varchar(200) null comment '销售属性名',
    attr_value varchar(200) null comment '销售属性值',
    attr_sort  int          null comment '顺序'
)
    comment 'sku销售属性&值';

