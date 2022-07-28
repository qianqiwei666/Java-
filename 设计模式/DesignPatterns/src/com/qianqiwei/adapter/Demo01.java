package com.qianqiwei.adapter;

public class Demo01 {
    /**适配器模式
     * 将类转化为其他自己想要的类
     */
    private static class Plan {
        private String name;
        private String type;
        private int width;
        private int height;


        public Plan(String name, String type, int width, int height) {
            this.name = name;
            this.type = type;
            this.width = width;
            this.height = height;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }

    private static class TrafficMessage {
        private String name;
        private String type;

        public TrafficMessage(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class Message{
        public TrafficMessage convert(Plan plan){
            TrafficMessage trafficMessage=new TrafficMessage(plan.getName(),plan.getType());
            return trafficMessage;
        }
    }

    public static void main(String[] args) {
        Message message=new Message();
        Plan plan=new Plan("小飞机","波音747",123,432);
        TrafficMessage convert = message.convert(plan);
        System.out.println(convert.getName());
    }
}
