package com.qianqiwei.bridge;

public class Demo01 {

    /**桥接模式
     *
     */
    private abstract static class Traffic{
        protected String name;
        protected String type;
        protected String status;
    }

    private abstract static class Status{
        protected Traffic traffic;
    }

    private static class Plan extends Traffic{


        public Plan(String name, String type, String status) {
            this.name = name;
            this.type = type;
            this.status = status;
        }

        public Plan() {
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }


    }

    private static class Car extends Traffic{

        public Car(String name, String type, String status) {
            this.name = name;
            this.type = type;
            this.status = status;
        }

        public Car() {
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    private static class Safe extends Status{
        public Safe(Traffic traffic){
            traffic.status="安全";
            this.traffic=traffic;
        }
    }
    private static class Waring extends Status{
        public Waring(Traffic traffic){
            traffic.status="警告";
            this.traffic=traffic;
        }
    }

    private static class Danger extends Status{
        public Danger(Traffic traffic){
            traffic.status="危险";
            this.traffic=traffic;
        }
    }

    public static void main(String[] args) {
        Status status=new Danger(new Car("小汽车","东风雪铁龙",null));
        System.out.println(status.traffic.status);
    }



}
