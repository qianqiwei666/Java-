create table pms_attr_group
(
    attr_group_id   bigint auto_increment comment '分组id'
        primary key,
    attr_group_name char(20)     null comment '组名',
    sort            int          null comment '排序',
    descript        varchar(255) null comment '描述',
    icon            varchar(255) null comment '组图标',
    catelog_id      bigint       null comment '所属分类id'
)
    comment '属性分组';

