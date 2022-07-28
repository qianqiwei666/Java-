package com.qianqiwei.command;

import com.qianqiwei.command.setup.SetupFactory;
import com.qianqiwei.command.setup.SimpleSetupChain;
import com.qianqiwei.command.setup.SoftAware;
import com.qianqiwei.command.setupInterface.SetupChain;
import com.qianqiwei.command.setupInterface.Soft;

import java.util.List;

public class Entry {
    public static void main(String[] args) throws InterruptedException {
        Command command=new Command();
        command.Commit();
        Thread.sleep(3000);
        command.rollback();
    }
}
