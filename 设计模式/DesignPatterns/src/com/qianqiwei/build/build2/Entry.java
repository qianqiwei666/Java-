package com.qianqiwei.build.build2;

public class Entry {
    public static void main(String[] args) {
        Plan build = new Plan.PlanBuilder().buildBasic01(1000, 2000).buildBasic("小飞机", "波音747").build();
        System.out.println(build);

    }
}
