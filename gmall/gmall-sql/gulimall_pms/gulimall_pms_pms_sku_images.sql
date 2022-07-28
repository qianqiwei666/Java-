create table pms_sku_images
(
    id          bigint auto_increment comment 'id'
        primary key,
    sku_id      bigint       null comment 'sku_id',
    img_url     varchar(255) null comment '图片地址',
    img_sort    int          null comment '排序',
    default_img int          null comment '默认图[0 - 不是默认图，1 - 是默认图]'
)
    comment 'sku图片';

