package com.qianqiwei.command.setupInterface;

public interface SetupChain {
   public SetupChain add(SoftSetup setup);
   public void next(Soft soft);
}
