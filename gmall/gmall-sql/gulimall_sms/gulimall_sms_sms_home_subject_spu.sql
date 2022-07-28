create table sms_home_subject_spu
(
    id         bigint auto_increment comment 'id'
        primary key,
    name       varchar(200) null comment '专题名字',
    subject_id bigint       null comment '专题id',
    spu_id     bigint       null comment 'spu_id',
    sort       int          null comment '排序'
)
    comment '专题商品';

