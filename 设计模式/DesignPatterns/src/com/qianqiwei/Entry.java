package com.qianqiwei;


import com.qianqiwei.factory.abstractFactory.*;
import com.qianqiwei.iterator.ArrayList;
import com.qianqiwei.iterator.Collections;
import com.qianqiwei.iterator.Iterator;
import com.qianqiwei.iterator.LinkedList;
import com.qianqiwei.strategy.Demo01;
import com.qianqiwei.strategy.Interface.Comparator;

import java.util.Arrays;
import java.util.concurrent.Executors;

public class Entry {

    public static void main(String[] args) {
        Collections<Integer> collections=new LinkedList<>();
        for (int i=0;i<100;i++){
            collections.add(i);
        }
        Iterator iterator=collections.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

}
