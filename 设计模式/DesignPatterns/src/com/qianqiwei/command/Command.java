package com.qianqiwei.command;

import com.qianqiwei.command.setup.SetupFactory;
import com.qianqiwei.command.setup.SimpleSetupChain;
import com.qianqiwei.command.setup.SoftAware;
import com.qianqiwei.command.setupInterface.SetupChain;
import com.qianqiwei.command.setupInterface.Soft;

//执行一个指令开启一个线程
public class Command {

    private Soft soft = new SoftAware();

    public void Commit() {
        new Thread(() -> {
            SetupChain setupChain = new SimpleSetupChain();
            SetupFactory factory = new SetupFactory();
            setupChain.add(factory.setup()).add(factory.setup01()).add(factory.setup02()).add(factory.setup03())
                    .add(factory.setup04())
                    .add(factory.setup05())
                    .add(factory.setup06())
                    .add(factory.setup07());
            setupChain.next(soft);
        }).start();
    }

    public void rollback() {
        new Thread(() -> {
            soft.setStatus(false);
        }).start();
    }


}
