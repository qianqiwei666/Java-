package com.qianqiwei.strategy;

import java.util.Arrays;


public class Demo02 {
    /**
     * 作用:如果某给类要进行排序,必须实现Comparable接口。
     * 优点:方法变得更通用,只要实现Comparable接口就可以对类进行排序
     * 缺点:比较大小的逻辑是写死的,不能更改。
     */

    //实现Comparable接口,并且传入当前类
    private static class Circle implements Comparable<Circle> {
        private int r;

        public Circle(int r) {
            this.r = r;
        }

        public double area() {
            return (r * r) * Math.PI;
        }


        @Override
        public String toString() {
            return "Circle面积{" +
                    "r=" + area() +
                    '}';
        }

        @Override
        public int compareTo(Circle o) {
           if (this.area()>o.area()) return 1;
           else if (this.area()<o.area()) return -1;
           return 0;
        }
    }

    //进行冒泡排序对实现Comparable的类进行排序
    public Object[] sort(Comparable array[]) {
        for (int m = 1; m < array.length; m++) {
            for (int x = 0; x < array.length - m; x++) {
                if (array[x].compareTo(array[x+1])==1){
                    Comparable temp= array[x];
                    array[x]=array[x+1];
                    array[x+1]=temp;
                }

            }
        }
        return array;
    }

    public static void main(String[] args) {
        Demo02 demo02 = new Demo02();
        Circle circles[] = new Circle[]{new Circle(12), new Circle(56), new Circle(13), new Circle(25), new Circle(98), new Circle(102)};
        Object[] sort = demo02.sort(circles);
        System.out.println(Arrays.toString(sort));
    }

}
