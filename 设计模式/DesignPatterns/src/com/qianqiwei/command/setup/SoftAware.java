package com.qianqiwei.command.setup;

import com.qianqiwei.command.setupInterface.Soft;

public class SoftAware implements Soft {
    //运行状态
    private boolean status = true;

    public void setup1() throws Exception {
        System.out.println("释放dll中.....");

    }

    public void setup2() throws Exception {
        System.out.println("安装软件库中.......");

    }

    public void setup3() throws Exception {
        System.out.println("安装所需的环境......");

    }

    public void setup4() throws Exception {
        System.out.println("释放音乐资源......");

    }

    public void setup5() throws Exception {
        System.out.println("释放图片资源.....");

    }

    public void setup6() throws Exception {
        System.out.println("校验文件中.....");

    }

    public void setup7() throws Exception {
        System.out.println("软件安装成功!");
    }

    @Override
    public void destroy1() {
        System.out.println("清除文件中......");
    }

    @Override
    public void destroy2() {
        System.out.println("正在卸载软件库......");
    }

    @Override
    public void destroy3() {
        System.out.println("正在删除环境......");

    }

    @Override
    public void destroy4() {
        System.out.println("正在删除音乐文件......");
    }

    @Override
    public void destroy5() {
        System.out.println("正在删除图片文件......");
    }

    @Override
    public void destroy6() {
        System.out.println("清除其他文件");
    }

    @Override
    public void destroy7() {
        System.out.println("已取消操作");
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
