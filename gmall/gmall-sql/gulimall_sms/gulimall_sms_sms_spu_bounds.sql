create table sms_spu_bounds
(
    id          bigint auto_increment comment 'id'
        primary key,
    spu_id      bigint         null,
    grow_bounds decimal(18, 4) null comment '成长积分',
    buy_bounds  decimal(18, 4) null comment '购物积分',
    work        tinyint(1)     null comment '优惠生效情况[1111（四个状态位，从右到左）;0 - 无优惠，成长积分是否赠送;1 - 无优惠，购物积分是否赠送;2 - 有优惠，成长积分是否赠送;3 - 有优惠，购物积分是否赠送【状态位0：不赠送，1：赠送】]'
)
    comment '商品spu积分设置';

