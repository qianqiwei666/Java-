package com.qianqiwei.flyweight.armsDemo;

public class BulletFactory {

    public Bullet createBullet01() {
        Bullet bullet = new Bullet("原子弹", false);
        return bullet;
    }

    public Bullet createBullet02() {
        Bullet bullet = new Bullet("装甲弹", false);
        return bullet;
    }

    public Bullet createBullet03() {
        Bullet bullet = new Bullet("合金弹", false);
        return bullet;
    }

    public Bullet createBullet04() {
        Bullet bullet = new Bullet("子弹", false);
        return bullet;
    }

    public Bullet createBullet05() {
        Bullet bullet = new Bullet("导弹", false);
        return bullet;
    }
}
