create table pms_product_attr_value
(
    id         bigint auto_increment comment 'id'
        primary key,
    spu_id     bigint       null comment '商品id',
    attr_id    bigint       null comment '属性id',
    attr_name  varchar(200) null comment '属性名',
    attr_value varchar(200) null comment '属性值',
    attr_sort  int          null comment '顺序',
    quick_show tinyint      null comment '快速展示【是否展示在介绍上；0-否 1-是】'
)
    comment 'spu属性值';

