package com.qianqiwei.templateMethod;

public abstract class Demo {
    public abstract void show();

    public void reject(){
        System.out.println("调用成功!");
        show();
    }

    public static void main(String[] args) {
        Demo demo=new Demo02();
        demo.reject();
    }
}
class Demo02 extends Demo{

    @Override
    public void show() {
        System.out.println("执行结束!");
    }
}


