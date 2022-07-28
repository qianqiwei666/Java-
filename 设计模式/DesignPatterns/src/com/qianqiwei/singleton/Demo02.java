package com.qianqiwei.singleton;

public class Demo02 {
    /**
     * 作用:当我们想用到该对象的时候就可以创建实例(手动控制线程安全)
     * 缺点:我们要进行上锁,导致性能可能下降
     */
    //保证线程之间的可见性
    private  static Demo02 Instance;
    private Demo02(){

    }
    public static Demo02 getInstance(){
        //线程在这里进行判断是否已经实例化了
        if (Instance==null){
            //线程在这里抢锁
            synchronized (Demo02.class){
                //当其中一个线程进行实例话后,其他的线程也该同步Instance,避免多次new
                if (Instance==null){
                    Instance=new Demo02();
                }
            }
        }
        return Instance;
    }
}
