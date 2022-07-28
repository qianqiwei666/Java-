package com.qianqiwei.iterator;

//创建简易的数组链表
public class ArrayList<T> implements Collections<T> {
    //创建数组
    private T[] arrays;
    //创建下标
    private int index;
    //创建默认容量
    private int capacity=10;

    public ArrayList() {
        //默认数组容量为10
        if (arrays == null) arrays = (T[]) new Object[capacity];
        index=0;
    }
    public void add(T value){
        //如果数组满了就扩容
        if (capacity==index) {
            //扩容1.5倍
            capacity=capacity+(capacity<<1);
            T newArray[]=(T[]) new Object[capacity];
            System.arraycopy(arrays,0,newArray,0,arrays.length);
            arrays=newArray;
        }
        arrays[index]=value;
        index++;
    }

    public int size(){
        return index;
    }

    public  Iterator iterator(){
        return new ArrayListIterator();
    }

    //每个集合都分别实现了Iterator来进行自己的遍历
    private class ArrayListIterator<T> implements Iterator<T>{
        private T []array= (T[]) arrays;
        private int size=index-1;
        private int counter=-1;


        @Override
        public boolean hasNext() {
            if(counter<size){
                counter++;
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            return array[counter];
        }
    }






}
