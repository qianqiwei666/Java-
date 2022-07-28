package com.qianqiwei.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Demo02 {
    private static class Demo {

        public Demo() {
        }

        public void show(String name) {
            System.out.println(name);
        }
    }

    public static void main(String[] args) {

        Demo demo = new Demo();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(demo.getClass());
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("代理前");
                return method.invoke(demo,objects);
            }
        });
        Demo o = (Demo) enhancer.create();
        o.show("你好");
    }
}
