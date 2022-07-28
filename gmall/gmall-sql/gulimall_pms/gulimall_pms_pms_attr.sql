create table pms_attr
(
    attr_id      bigint auto_increment comment '属性id'
        primary key,
    attr_name    char(30)     null comment '属性名',
    search_type  tinyint      null comment '是否需要检索[0-不需要，1-需要]',
    icon         varchar(255) null comment '属性图标',
    value_select char(255)    null comment '可选值列表[用逗号分隔]',
    attr_type    tinyint      null comment '属性类型[0-销售属性，1-基本属性，2-既是销售属性又是基本属性]',
    enable       bigint       null comment '启用状态[0 - 禁用，1 - 启用]',
    catelog_id   bigint       null comment '所属分类',
    show_desc    tinyint      null comment '快速展示【是否展示在介绍上；0-否 1-是】，在sku中仍然可以调整'
)
    comment '商品属性';

