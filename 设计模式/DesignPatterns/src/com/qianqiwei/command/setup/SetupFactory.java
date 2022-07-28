package com.qianqiwei.command.setup;

import com.qianqiwei.command.setupInterface.SetupChain;
import com.qianqiwei.command.setupInterface.Soft;
import com.qianqiwei.command.setupInterface.SoftSetup;

public class SetupFactory {


    //创建Setup工厂
    public SoftSetup setup(){
        return new Setup();
    }
    public SoftSetup setup01(){
        return new Setup01();
    }
    public SoftSetup setup02(){
        return new Setup02();
    }
    public SoftSetup setup03(){
        return new Setup03();
    }
    public SoftSetup setup04(){
        return new Setup04();
    }
    public SoftSetup setup05(){
        return new Setup05();
    }
    public SoftSetup setup06(){
        return new Setup06();
    }

    public SoftSetup setup07(){
        return new Setup07();
    }

    class Setup implements SoftSetup {
        @Override
        public void execute(Soft soft, SetupChain setupChain) {
            System.out.println("正在安装软件中.....");
            setupChain.next(soft);
            if (!soft.isStatus()){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                soft.destroy1();
                System.out.println("完成!");
            }
        }
    }

    class Setup01 implements SoftSetup {

        @Override
        public void execute(Soft soft, SetupChain setupChain) {
            try {
                if (!soft.isStatus()) throw new Exception("用户取消操作,正在回滚.....");
                soft.setup1();
            } catch (Exception e) {
                soft.setStatus(false);
                System.out.println(e.getMessage());
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setupChain.next(soft);
            if (!soft.isStatus()){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                soft.destroy2();
            }
        }
    }

    class Setup02 implements SoftSetup {

        @Override
        public void execute(Soft soft, SetupChain setupChain) {
            try {
                if (!soft.isStatus()) throw new Exception("用户取消操作,正在回滚.....");
                soft.setup2();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                soft.setStatus(false);
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setupChain.next(soft);
            if (!soft.isStatus()){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                soft.destroy3();
            }
        }
    }

    class Setup03 implements SoftSetup {

        @Override
        public void execute(Soft soft, SetupChain setupChain) {
            try {
                if (!soft.isStatus()) throw new Exception("用户取消操作,正在回滚.....");
                soft.setup3();
            } catch (Exception e) {
                soft.setStatus(false);
                System.out.println(e.getMessage());
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setupChain.next(soft);
            if (!soft.isStatus()){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                soft.destroy4();
            }
        }
    }

    class Setup04 implements SoftSetup {

        @Override
        public void execute(Soft soft, SetupChain setupChain) {
            try {
                if (!soft.isStatus()) throw new Exception("用户取消操作,正在回滚.....");
                soft.setup4();
            } catch (Exception e) {
                soft.setStatus(false);
                System.out.println(e.getMessage());
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setupChain.next(soft);
            if (!soft.isStatus()){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                soft.destroy5();
            }
        }
    }

    class Setup05 implements SoftSetup {

        @Override
        public void execute(Soft soft, SetupChain setupChain) {
            try {
                if (!soft.isStatus()) throw new Exception("用户取消操作,正在回滚.....");
                soft.setup5();
            } catch (Exception e) {
                soft.setStatus(false);
                System.out.println(e.getMessage());
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setupChain.next(soft);
            if (!soft.isStatus()){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                soft.destroy6();
            }
        }
    }

    class Setup06 implements SoftSetup {

        @Override
        public void execute(Soft soft, SetupChain setupChain) {
            try {
                if (!soft.isStatus()) throw new Exception("用户取消操作,正在回滚.....");
                soft.setup6();
            } catch (Exception e) {
                soft.setStatus(false);
                System.out.println(e.getMessage());
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setupChain.next(soft);
            if (!soft.isStatus()){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                soft.destroy7();
            }
        }
    }

    class Setup07 implements SoftSetup {

        @Override
        public void execute(Soft soft, SetupChain setupChain) {
            try {
                if (!soft.isStatus()) throw new Exception("用户取消操作,正在回滚.....");
                soft.setup7();
            } catch (Exception e) {
                soft.setStatus(false);
                System.out.println(e.getMessage());
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setupChain.next(soft);
        }
    }




}
