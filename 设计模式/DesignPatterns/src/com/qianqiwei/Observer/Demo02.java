package com.qianqiwei.Observer;

import java.util.ArrayList;
import java.util.List;

public class Demo02 {

    /**
     * 概念:监听器监听事件源所做出的反应而做出响应
     *
     */


    //事件源
    private static class Window{
        private List<Listener> list=new ArrayList<>();
        {
            list.add(new keyListener());
            list.add(new Listener() {
                @Override
                public void listen(EventObject event) {
                    System.out.println("你好我也被调用了");
                }
            });
        }
        public void order(){
            Event event=new EventObject(this);
            list.stream().forEach(item->{
                item.listen((EventObject) event);
            });
        }

    }

    private static interface Event{
        public Object getRecourse();
    }

    //创建事件对象
    private static class EventObject implements Event{
        private Object Recourse;

        public EventObject(Object recourse){
            this.Recourse=recourse;
        }
        @Override
        public Object getRecourse() {
            return this.Recourse;
        }
    }



    private static interface Listener{
        public void listen(EventObject event);
    }


    //创建监听者
    private static class keyListener implements Listener{

        @Override
        public void listen(EventObject event) {
            System.out.println(event.getRecourse());
        }
    }

    public static void main(String[] args) {
        Window window=new Window();
        window.order();
    }




}
