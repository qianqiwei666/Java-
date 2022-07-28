package com.qianqiwei.prototype;

public class Entry {
    /**原型模式
     * 原理:当调用clone方法的时候,jvm会对当前类复制一份产生新的对象到内存里。
     * 注意:如果要对类进行克隆,必须实现Cloneable接口方法,运行的时候会检查,必须重写clone方法。
     * 其属性引用指向的是同一个。
     */
    public static void main(String[] args) throws CloneNotSupportedException {
        Demo01 demo01=new Demo01("刘德华",67,"北京西城",new Demo02());
        Demo01 demo011 =(Demo01) demo01.clone();
        //克隆出来之虽然属性以及方法不变,但是由于重新分配了内存空间,导致引用指向不同。
        System.out.println(demo01==demo011);
        //但是其内部引用指向不变
        System.out.println(demo01.getDemo02()==demo011.getDemo02());
    }
}
