create table pms_spu_comment
(
    id               bigint auto_increment comment 'id'
        primary key,
    sku_id           bigint        null comment 'sku_id',
    spu_id           bigint        null comment 'spu_id',
    spu_name         varchar(255)  null comment '商品名字',
    member_nick_name varchar(255)  null comment '会员昵称',
    star             tinyint(1)    null comment '星级',
    member_ip        varchar(64)   null comment '会员ip',
    create_time      datetime      null comment '创建时间',
    show_status      tinyint(1)    null comment '显示状态[0-不显示，1-显示]',
    spu_attributes   varchar(255)  null comment '购买时属性组合',
    likes_count      int           null comment '点赞数',
    reply_count      int           null comment '回复数',
    resources        varchar(1000) null comment '评论图片/视频[json数据；[{type:文件类型,url:资源路径}]]',
    content          text          null comment '内容',
    member_icon      varchar(255)  null comment '用户头像',
    comment_type     tinyint       null comment '评论类型[0 - 对商品的直接评论，1 - 对评论的回复]'
)
    comment '商品评价';

