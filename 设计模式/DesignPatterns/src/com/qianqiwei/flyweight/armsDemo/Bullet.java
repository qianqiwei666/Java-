package com.qianqiwei.flyweight.armsDemo;

public class Bullet {
    public String name;
    private boolean isShoot;

    public Bullet(String name, boolean isShoot) {
        this.name = name;
        this.isShoot = isShoot;
    }


    public Bullet() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShoot() {
        return isShoot;
    }

    public void setShoot(boolean shoot) {
        isShoot = shoot;
    }
}
