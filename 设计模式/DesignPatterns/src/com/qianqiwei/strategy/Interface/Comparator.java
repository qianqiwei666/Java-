package com.qianqiwei.strategy.Interface;

@FunctionalInterface
public interface Comparator<T> {
    /**
     *定义一个比较器
     */
    public int compare(T item1,T item2);
}
