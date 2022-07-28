create table pms_spu_info
(
    id              bigint auto_increment comment '商品id'
        primary key,
    spu_name        varchar(200)   null comment '商品名称',
    spu_description varchar(1000)  null comment '商品描述',
    catalog_id      bigint         null comment '所属分类id',
    brand_id        bigint         null comment '品牌id',
    weight          decimal(18, 4) null,
    publish_status  tinyint        null comment '上架状态[0 - 下架，1 - 上架]',
    create_time     datetime       null,
    update_time     datetime       null
)
    comment 'spu信息';

