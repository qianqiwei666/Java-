package com.qianqiwei.build.build1;

public class Cat {
     public TabbyCat tabbyCat;
     public Wildcat wildcat;



}
class TabbyCat{
    private String name;
    private int age;
    private String type;

    public TabbyCat(String name, int age, String type) {
        this.name = name;
        this.age = age;
        this.type = type;
    }

    public TabbyCat() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
class Wildcat{
    private String name;
    private int age;
    private String type;

    public Wildcat(String name, int age, String type) {
        this.name = name;
        this.age = age;
        this.type = type;
    }

    public Wildcat() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}