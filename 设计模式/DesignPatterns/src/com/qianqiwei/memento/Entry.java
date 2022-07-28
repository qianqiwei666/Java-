package com.qianqiwei.memento;

import java.io.*;
import java.util.ArrayList;

public class Entry {


    /**
     * 作用:将对象存储到硬盘中做记录。
     * 注意:需要存储的对象必须序列化。
     */


    private static class Demo01 implements Serializable{

    }

    private static class Demo02 implements Serializable{

    }


    public static void main(String[] args) throws IOException {
        //通过list集合将需要序列化的对象存储到硬盘中。需要序列化的对象必须实现Serializable接口
        ArrayList arrayList=new ArrayList();
        Demo01 demo01 = new Demo01();
        Demo02 demo02 = new Demo02();
        arrayList.add(demo01);
        arrayList.add(demo02);
        File file = new File("D:\\file.data");
        FileOutputStream outputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(arrayList);

    }
}
