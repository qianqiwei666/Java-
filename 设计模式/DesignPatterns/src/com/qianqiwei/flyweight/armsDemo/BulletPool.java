package com.qianqiwei.flyweight.armsDemo;

import java.util.ArrayList;
import java.util.List;

public class BulletPool {
    private List<Bullet> bullets=new ArrayList<>();
    {
        BulletFactory factory=new BulletFactory();
        bullets.add(factory.createBullet01());
        bullets.add(factory.createBullet02());
        bullets.add(factory.createBullet03());
        bullets.add(factory.createBullet04());
        bullets.add(factory.createBullet05());
    }
    public Bullet getBullet(){
        for (Bullet bullet:bullets){
            if (!bullet.isShoot()){
                bullet.setShoot(true);
                return bullet;
            }
        }
        throw new RuntimeException("子弹已经射完");
    }
}
