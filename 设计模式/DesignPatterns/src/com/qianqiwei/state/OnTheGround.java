package com.qianqiwei.state;

public class OnTheGround extends Plan{
    @Override
    public void status() {
        System.out.println("在地上");
    }

    @Override
    public void high() {
        System.out.println("高度0米");
    }
}
