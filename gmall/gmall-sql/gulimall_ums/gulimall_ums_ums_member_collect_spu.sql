create table ums_member_collect_spu
(
    id          bigint       not null comment 'id'
        primary key,
    member_id   bigint       null comment '会员id',
    spu_id      bigint       null comment 'spu_id',
    spu_name    varchar(500) null comment 'spu_name',
    spu_img     varchar(500) null comment 'spu_img',
    create_time datetime     null comment 'create_time'
)
    comment '会员收藏的商品';

