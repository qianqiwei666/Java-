package com.qianqiwei.strategy;

public class Demo04<T> {
    /**
     * 举一反三
     * 案例中要有体现
     * 作用:在我们的项目之中,可以通过实现接口,对其中的逻辑进行修改。
     */
    private interface Do<T> {
        public boolean done(T value, T value2);
    }

    public void show(Do<T> item,T item2,T item3) {
       if (item.done(item2,item3)){
           System.out.println("success") ;
           return;
       }
        System.out.println("fail");
    }

    public static void main(String[] args) {
        //自定义完成逻辑
        Do<Integer> integerDo=(value, value2) -> {
            if (value>value2) return true;
            return false;
        };
        //
        Demo04<Integer> demo04=new Demo04<>();
        demo04.show(integerDo,2,4);
    }
}
