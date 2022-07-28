package com.qianqiwei.factory.abstractFactory;

public class ASUSComputer extends AbstractComputer{
    @Override
    public CPU cpu() {
        return new AMDCPU();
    }

    @Override
    public RAM ram() {
        return new SamsungRAM();
    }

    @Override
    public ROM rom() {
        return new SamsungROM();
    }
}
