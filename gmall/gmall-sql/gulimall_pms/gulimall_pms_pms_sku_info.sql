create table pms_sku_info
(
    sku_id          bigint auto_increment comment 'skuId'
        primary key,
    spu_id          bigint         null comment 'spuId',
    sku_name        varchar(255)   null comment 'sku名称',
    sku_desc        varchar(2000)  null comment 'sku介绍描述',
    catalog_id      bigint         null comment '所属分类id',
    brand_id        bigint         null comment '品牌id',
    sku_default_img varchar(255)   null comment '默认图片',
    sku_title       varchar(255)   null comment '标题',
    sku_subtitle    varchar(2000)  null comment '副标题',
    price           decimal(18, 4) null comment '价格',
    sale_count      bigint         null comment '销量'
)
    comment 'sku信息';

