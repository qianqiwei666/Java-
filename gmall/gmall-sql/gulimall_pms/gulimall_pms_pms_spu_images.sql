create table pms_spu_images
(
    id          bigint auto_increment comment 'id'
        primary key,
    spu_id      bigint       null comment 'spu_id',
    img_name    varchar(200) null comment '图片名',
    img_url     varchar(255) null comment '图片地址',
    img_sort    int          null comment '顺序',
    default_img tinyint      null comment '是否默认图'
)
    comment 'spu图片';

