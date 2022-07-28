package com.qianqiwei.prototype;

public class Demo01  implements Cloneable{
    private String username;
    private int age;
    private String address;
    Demo02 demo02;


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Demo01{" +
                "username='" + username + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", demo02=" + demo02 +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Demo02 getDemo02() {
        return demo02;
    }

    public void setDemo02(Demo02 demo02) {
        this.demo02 = demo02;
    }

    public Demo01() {
    }

    public Demo01(String username, int age, String address, Demo02 demo02) {
        this.username = username;
        this.age = age;
        this.address = address;
        this.demo02 = demo02;
    }
}
