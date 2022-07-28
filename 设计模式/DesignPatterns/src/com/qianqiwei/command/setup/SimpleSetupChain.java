package com.qianqiwei.command.setup;

import com.qianqiwei.command.setupInterface.SetupChain;
import com.qianqiwei.command.setupInterface.Soft;
import com.qianqiwei.command.setupInterface.SoftSetup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimpleSetupChain implements SetupChain {

    private List<SoftSetup> softSetups=new ArrayList<>();
    private Iterator<SoftSetup> iterator;

    @Override
    public SimpleSetupChain add(SoftSetup setup) {
        softSetups.add(setup);
        return this;
    }

    @Override
    public void next(Soft soft) {
        if (iterator==null) iterator=softSetups.iterator();
        if (iterator.hasNext()) iterator.next().execute(soft,this);
    }
}
