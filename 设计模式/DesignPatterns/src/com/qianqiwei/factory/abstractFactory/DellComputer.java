package com.qianqiwei.factory.abstractFactory;

public class DellComputer extends AbstractComputer {
    @Override
    public CPU cpu() {
        return new IntelCPU();
    }

    @Override
    public RAM ram() {
        return new SamsungRAM();
    }

    @Override
    public ROM rom() {
        return new ToshibaROM();
    }
}
