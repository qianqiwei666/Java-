create table pms_spu_info_desc
(
    spu_id  bigint   not null comment '商品id'
        primary key,
    decript longtext null comment '商品介绍'
)
    comment 'spu信息介绍';

