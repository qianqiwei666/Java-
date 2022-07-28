create table ums_member_collect_subject
(
    id           bigint auto_increment comment 'id'
        primary key,
    subject_id   bigint       null comment 'subject_id',
    subject_name varchar(255) null comment 'subject_name',
    subject_img  varchar(500) null comment 'subject_img',
    subject_urll varchar(500) null comment '活动url'
)
    comment '会员收藏的专题活动';

