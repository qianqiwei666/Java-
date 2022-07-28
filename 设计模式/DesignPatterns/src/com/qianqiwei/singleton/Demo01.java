package com.qianqiwei.singleton;

public class Demo01 {
    //创建Demo01的静态实例
    /**单例模式
     * 作用:类只会load到内存中一次,在load过程中Demo01就已经实例化了,能保证线程安全。
     * 问题:如果不需要这个实例不就浪费了内存空间?
     * 解决:提出了懒加载模式,当需要类的时候就会创建。
     */
    private static final Demo01 demo01=new Demo01();
    //设置私有构造方法
    private Demo01(){

    }
    public static Demo01 getInstance(){return demo01;}
}
