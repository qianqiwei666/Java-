package com.qianqiwei.state;

public class Fly extends Plan{
    @Override
    public void status() {
        System.out.println("起飞");
    }

    @Override
    public void high() {
        System.out.println("12000米");
    }
}
