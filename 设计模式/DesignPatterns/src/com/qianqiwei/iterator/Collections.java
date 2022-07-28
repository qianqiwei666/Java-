package com.qianqiwei.iterator;

public interface Collections<T> {
    public int size();
    public void add(T value);
    public Iterator<T> iterator();
}
