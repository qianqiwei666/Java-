create table pms_attr_attrgroup_relation
(
    id            bigint auto_increment comment 'id'
        primary key,
    attr_id       bigint null comment '属性id',
    attr_group_id bigint null comment '属性分组id',
    attr_sort     int    null comment '属性组内排序'
)
    comment '属性&属性分组关联';

