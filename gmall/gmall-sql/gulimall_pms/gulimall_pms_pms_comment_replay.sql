create table pms_comment_replay
(
    id         bigint auto_increment comment 'id'
        primary key,
    comment_id bigint null comment '评论id',
    reply_id   bigint null comment '回复id'
)
    comment '商品评价回复关系';

