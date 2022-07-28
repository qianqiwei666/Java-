package com.qianqiwei.build.build1;

public class SimpleBuildCat extends AbstractCatBuilder{

    @Override
    public AbstractCatBuilder BuildTabbyCat() {
        TabbyCat tabbyCat=new TabbyCat("狸花猫",12,"狸花猫");
        cat.tabbyCat=tabbyCat;
        return this;
    }

    @Override
    public AbstractCatBuilder BuildWildcat() {
        Wildcat wildcat=new Wildcat("野猫",12,"野猫");
        cat.wildcat=wildcat;
        return this;
    }

    @Override
    public Cat build() {
        return cat;
    }
}
