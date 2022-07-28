package com.qianqiwei.strategy;

import com.qianqiwei.strategy.Interface.Comparator;

import java.util.Arrays;

public class Demo03<T> {
    /**
     * 作用:拿取两个不同的类去进行比较
     * 优点:上一个Demo来说,比对的类不用实现接口,可以自定义比对逻辑。
     */

    private static class Dog {
        private String name;
        private int age;

        public Dog(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Dog{" +
                    "名字='" + name + '\'' +
                    ", 年龄=" + age +
                    '}';
        }
    }

    //进行冒泡排序对实现Comparator的类进行排序
    public Object[] sort(T[] array, Comparator<T> comparator) {
        for (int m = 1; m < array.length; m++) {
            for (int x = 0; x < array.length - m; x++) {
                if (comparator.compare(array[x], array[x + 1]) == 1) {
                    T temp = array[x];
                    array[x] = array[x + 1];
                    array[x + 1] = temp;
                }
            }
        }
        return array;
    }

    public static void main(String[] args) {
        Demo03<Dog> demo03 = new Demo03<>();
        Dog dogs[] = new Dog[]{new Dog("小八", 56), new Dog("小人", 67), new Dog("小九", 23), new Dog("小王", 45), new Dog("小太阳", 12)};
        //将比较逻辑抽离出来
        Comparator<Dog> comparator = (item1, item2) -> {
            if (item1.age > item2.age) return 1;
            else if (item1.age < item2.age) return -1;
            return 0;
        };
        Object[] sort = demo03.sort(dogs, comparator);
        System.out.println(Arrays.toString(sort));
    }

}
