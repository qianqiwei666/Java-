package com.qianqiwei.flyweight.armsDemo;

public class AK47 {
    private BulletPool pool=new BulletPool();
    public void shoot(){
        Bullet bullet = pool.getBullet();
        System.out.println("子弹名称:"+bullet.getName()+"子弹状态:"+(bullet.isShoot()?"已发射":"未发射"));
        bullet.setShoot(false);
    }
}
