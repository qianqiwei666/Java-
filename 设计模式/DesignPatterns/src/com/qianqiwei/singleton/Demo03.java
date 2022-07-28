package com.qianqiwei.singleton;

import java.util.concurrent.atomic.AtomicInteger;

public class Demo03 {

    /**
     * 完美解决方案
     * 作用:在我们需要获取该类对象的时候,该类才会被实例化。JVM保障线程安全(JVM只会加载一次类,静态属性也就会加载一次)。
     * 优点:静态内部类是不会被初始化的,当我们调用的时候才会初始化。
     */


    private Demo03() {

    }

    private static class Ins {
        private final static Demo03 demo01 = new Demo03();
    }

    public static Demo03 getInstance() {
        return Ins.demo01;
    }
}
