package com.qianqiwei.build.build1;

public class Entry {
    public static void main(String[] args) {
        AbstractCatBuilder builder=new SimpleBuildCat();
        Cat build = builder.BuildWildcat().BuildTabbyCat().build();
        System.out.println(build);
        System.out.println(build.tabbyCat.getName());
        System.out.println(build.wildcat.getName());
    }
}
