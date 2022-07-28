create table wms_ware_info
(
    id       bigint auto_increment comment 'id'
        primary key,
    name     varchar(255) null comment '仓库名',
    address  varchar(255) null comment '仓库地址',
    areacode varchar(20)  null comment '区域编码'
)
    comment '仓库信息';

