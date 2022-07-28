package com.qianqiwei.Observer;


import java.util.ArrayList;
import java.util.List;

public class Demo01 {
    /**观察者模式
     * 意思:观察者观察被观察者做出的行为从而被观察者捕获
     *
     */


    //创建事件
    private static class TrafficEvent {
        public void say() {
            System.out.println("发动了!!!");
        }
    }

    //创建被观察者
    private static class Instructor {
        //创建观察者集合
        List<Traffic> list = new ArrayList<>();

        {
            list.add(new Plan());
            list.add(new Car());
        }

        public void order() {
            System.out.println("下达指令");
            list.stream().forEach(item->{
                item.run(new TrafficEvent());
            });
            System.out.println("指令执行完毕");
        }
    }

    //创建观察者
    private static interface Traffic {
        public void run(TrafficEvent event);
    }

    private static class Plan implements Traffic {
        public void run(TrafficEvent event) {
            event.say();
            System.out.println("飞机起飞");
        }
    }

    private static class Car implements Traffic {

        @Override
        public void run(TrafficEvent event) {
            event.say();
            System.out.println("汽车启动");
        }
    }

    public static void main(String[] args) {
        Instructor instructor=new Instructor();
        instructor.order();
    }


}
