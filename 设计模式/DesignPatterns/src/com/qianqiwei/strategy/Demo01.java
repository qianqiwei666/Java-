package com.qianqiwei.strategy;

import java.util.Arrays;

public class Demo01 {
    /** 策略模式
     * 作用:通过不同的实现类,对我们的逻辑进行更改以及增强。
     */

    //定义一个猫类
    private static class Cat{
        private String name;
        private int weight;
        public Cat(int weight,String name){
            this.weight=weight;
            this.name=name;
        }

        public int getWeight() {
            return weight;
        }
        //对猫进行比较
        public int compareTo(Cat cat){
            if (this.weight> cat.getWeight()) return 1;
            else if (this.weight< cat.getWeight()) return -1;
            return 0;
        }

        @Override
        public String toString() {
            return "Cat{" +
                    "name='" + name + '\'' +
                    ", weight=" + weight +
                    '}';
        }
    }

    //进行冒泡排序对猫的重量进行排序
    public Cat[] sort(Cat array[]) {
        for (int m = 1; m < array.length; m++) {
            for (int x = 0; x < array.length - m; x++) {
               if (array[x].compareTo(array[x+1])==1){
                   Cat temp=array[x];
                   array[x]=array[x+1];
                   array[x+1]=temp;
               }
            }
        }
        return array;
    }

    public static void main(String[] args) {
        Demo01 demo01=new Demo01();
        Cat cats[]=new Cat[]{new Cat(23,"小丽"),new Cat(87,"小可"),new Cat(64,"小爱"),new Cat(82,"小英"),new Cat(97,"小上")};
        System.out.println(Arrays.toString(demo01.sort(cats)));
    }
}
