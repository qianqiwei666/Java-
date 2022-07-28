package com.qianqiwei.factory.abstractFactory;

public abstract class AbstractComputer {
    /**抽象工厂模式
     * 作用:通过不同的AbstractComputer实例,调用同样的方法可以获得不同的结果,通常对产品的类型进行管理(一组)
     * 注意:一般特别抽象的很笼统的一般设为接口,比较具体的通常同抽象类。(形容词用接口,名词用抽象类)
     */
    public abstract CPU cpu();
    public abstract RAM ram();
    public abstract ROM rom();

}
