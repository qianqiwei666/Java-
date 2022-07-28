package com.qianqiwei.iterator;

public class Entry {
    public static void main(String[] args) {
        /**迭代器
         * 作用:每个集合实现Iterator接口来进行自己的逻辑遍历
         */
        Collections<Integer> collections=new LinkedList<>();
        for (int i=0;i<10000_0000;i++){
            collections.add(i);
        }

    }
}
