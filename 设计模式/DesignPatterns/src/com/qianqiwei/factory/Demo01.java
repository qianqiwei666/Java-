package com.qianqiwei.factory;

public class Demo01 {
    /**工厂模式
     * 作用:主要生产对象,包括单例模式,只要能够产生对象就是工厂模式。
     */

    //创建简单的工厂模式
    private static class Factory{
        public Cat createCat(){
            System.out.println("正在产生猫对象");
            return new Cat();
        }

        public Dog createDog(){
            System.out.println("正在产生狗对象");
            return new Dog();
        }

    }
    private static class Cat{
        public void show(){
            System.out.println("我是一只猫");
        }
    }
    private static class Dog{
        public void show(){
            System.out.println("我是一只狗子");
        }
    }

    public static void main(String[] args) {
        Factory factory=new Factory();
        Cat cat = factory.createCat();
        cat.show();
        Dog dog = factory.createDog();
        dog.show();
    }



}
