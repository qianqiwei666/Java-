create table ums_member_level
(
    id                      bigint auto_increment comment 'id'
        primary key,
    name                    varchar(100)   null comment '等级名称',
    growth_point            int            null comment '等级需要的成长值',
    default_status          tinyint        null comment '是否为默认等级[0->不是；1->是]',
    free_freight_point      decimal(18, 4) null comment '免运费标准',
    comment_growth_point    int            null comment '每次评价获取的成长值',
    priviledge_free_freight tinyint        null comment '是否有免邮特权',
    priviledge_member_price tinyint        null comment '是否有会员价格特权',
    priviledge_birthday     tinyint        null comment '是否有生日特权',
    note                    varchar(255)   null comment '备注'
)
    comment '会员等级';

