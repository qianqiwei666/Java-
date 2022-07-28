create table pms_category_brand_relation
(
    id           bigint auto_increment
        primary key,
    brand_id     bigint       null comment '品牌id',
    catelog_id   bigint       null comment '分类id',
    brand_name   varchar(255) null,
    catelog_name varchar(255) null
)
    comment '品牌分类关联';

