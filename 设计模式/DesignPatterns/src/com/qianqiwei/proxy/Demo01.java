package com.qianqiwei.proxy;

import java.lang.reflect.Proxy;

public class Demo01 {


    private static interface UserService{
        public void say();
        public void doMore();
    }
    private static class UserServiceImpl implements UserService{

        @Override
        public void say() {
            System.out.println("我说了");
        }

        @Override
        public void doMore() {
            System.out.println("我做了");
        }
    }

    public static void main(String[] args) {
        UserService userService=new UserServiceImpl();
        UserService o = (UserService) Proxy.newProxyInstance(Demo01.class.getClassLoader(), new Class[]{UserService.class}, (proxy, method, args1) -> {
            System.out.println("代理之前");
            Object invoke = method.invoke(userService, args);
            System.out.println("代理之后");
            return invoke;
        });
        o.doMore();
        o.say();
    }
}
