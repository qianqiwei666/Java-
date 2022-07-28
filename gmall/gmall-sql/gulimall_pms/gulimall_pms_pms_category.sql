create table pms_category
(
    cat_id        bigint auto_increment comment '分类id'
        primary key,
    name          char(50)  null comment '分类名称',
    parent_cid    bigint    null comment '父分类id',
    cat_level     int       null comment '层级',
    show_status   tinyint   null comment '是否显示[0-不显示，1显示]',
    sort          int       null comment '排序',
    icon          char(255) null comment '图标地址',
    product_unit  char(50)  null comment '计量单位',
    product_count int       null comment '商品数量'
)
    comment '商品三级分类';

