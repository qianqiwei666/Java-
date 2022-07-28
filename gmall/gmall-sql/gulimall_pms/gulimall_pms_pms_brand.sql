create table pms_brand
(
    brand_id     bigint auto_increment comment '品牌id'
        primary key,
    name         char(50)      null comment '品牌名',
    logo         varchar(2000) null comment '品牌logo地址',
    descript     longtext      null comment '介绍',
    show_status  tinyint       null comment '显示状态[0-不显示；1-显示]',
    first_letter char          null comment '检索首字母',
    sort         int           null comment '排序'
)
    comment '品牌';

