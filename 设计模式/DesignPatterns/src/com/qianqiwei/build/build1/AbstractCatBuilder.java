package com.qianqiwei.build.build1;

public abstract class AbstractCatBuilder {

    Cat cat=new Cat();

    public abstract AbstractCatBuilder BuildTabbyCat();

    public abstract AbstractCatBuilder BuildWildcat();

    public abstract Cat build();

}
