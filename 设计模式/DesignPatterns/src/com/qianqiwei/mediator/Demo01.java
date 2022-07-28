package com.qianqiwei.mediator;

public class Demo01 {
    /**调停者
     * 作用:解耦类与类之间的关系,将类之间的调用进行集中化管理。
     * 其中类与类之间也可以进行集中化调用
     */

    public static interface Traffic{
        public String getName();
    }

    public static class Plan implements Traffic{
        private String name="飞机";

        public String getName() {
            return name;
        }
    }

    public static class Car implements Traffic{
        private String name="汽车";
        public String getName() {
            return name;
        }
    }

    public static class Message{
        public void message(Traffic traffic){
            System.out.println(traffic.getName()+"成功的启动了....");
        }
    }


    //对类之间的关系进行集中化管理
    public static class Faced{
        public void start(Traffic traffic){
            //这里获取公交的名字
            String traffic_name=traffic.getName();
            System.out.println("准备"+traffic_name);
            Message message=new Message();
            //这里Message调用接口实现类来调用名字
            message.message(traffic);
        }
    }

    public static void main(String[] args) {
        Faced faced=new Faced();
        faced.start(new Plan());
    }

}
