create table wms_purchase
(
    id            bigint auto_increment
        primary key,
    assignee_id   bigint         null,
    assignee_name varchar(255)   null,
    phone         char(13)       null,
    priority      int            null,
    status        int            null,
    ware_id       bigint         null,
    amount        decimal(18, 4) null,
    create_time   datetime       null,
    update_time   datetime       null
)
    comment '采购信息';

