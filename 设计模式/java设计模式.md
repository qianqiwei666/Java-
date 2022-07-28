# 一、常用的设计模式

## 一、单例模式

### 一、作用

> 调用该类只会调用一次,或者说只会生成一次实例。

### 二、用途

> SpringIOC容器中的单例模式。

### 三、代码实例

#### 一、基于属性创建

```java
public class Demo01 {
    //创建Demo01的静态实例
    /**单例模式
     * 作用:类只会load到内存中一次,在load过程中Demo01就已经实例化了,能保证线程安全。
     * 问题:如果不需要这个实例不就浪费了内存空间?
     * 解决:提出了懒加载模式,当需要类的时候就会创建。
     */
    private static final Demo01 demo01=new Demo01();
    //设置私有构造方法
    private Demo01(){

    }
    public static Demo01 getInstance(){return demo01;}
}
```

#### 二、懒加载

```java
public class Demo02 {
    /**
     * 作用:当我们想用到该对象的时候就可以创建实例(手动控制线程安全)
     * 缺点:我们要进行上锁,导致性能可能下降
     */
    //保证线程之间的可见性
    private  static Demo02 Instance;
    private Demo02(){

    }
    public static Demo02 getInstance(){
        //线程在这里进行判断是否已经实例化了
        if (Instance==null){
            //线程在这里抢锁
            synchronized (Demo02.class){
                //当其中一个线程进行实例话后,其他的线程也该同步Instance,避免多次new
                if (Instance==null){
                    Instance=new Demo02();
                }
            }
        }
        return Instance;
    }
}
```

#### 三、懒加载(plus)

```java
public class Demo03 {

    /**
     * 完美解决方案
     * 作用:在我们需要获取该类对象的时候,该类才会被实例化。JVM保障线程安全(JVM只会加载一次类,静态属性也就会加载一次)。
     * 优点:静态内部类是不会被初始化的,当我们调用的时候才会初始化。
     */


    private Demo03() {

    }

    private static class Ins {
        private final static Demo03 demo01 = new Demo03();
    }

    public static Demo03 getInstance() {
        return Ins.demo01;
    }
}
```

## 二、工厂模式

### 一、作用

> 创建类实例的工厂

### 二、用途

> SpringIOC创建Bean

### 三、代码示例

> 东西有点多请看源码......

## 三、策略模式

### 一、作用

> 实现一个接口,拥有不同的实现类,每个类对接口的方法进行不同的逻辑。

### 二、用途

> 某个功能要升级或者维护。

### 三、代码示例

```java
public class Demo03<T> {
    /**
     * 作用:拿取两个不同的类去进行比较
     * 优点:上一个Demo来说,比对的类不用实现接口,可以自定义比对逻辑。
     */

    private static class Dog {
        private String name;
        private int age;

        public Dog(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Dog{" +
                    "名字='" + name + '\'' +
                    ", 年龄=" + age +
                    '}';
        }
    }

    //进行冒泡排序对实现Comparator的类进行排序
    public Object[] sort(T[] array, Comparator<T> comparator) {
        for (int m = 1; m < array.length; m++) {
            for (int x = 0; x < array.length - m; x++) {
                if (comparator.compare(array[x], array[x + 1]) == 1) {
                    T temp = array[x];
                    array[x] = array[x + 1];
                    array[x + 1] = temp;
                }
            }
        }
        return array;
    }

    public static void main(String[] args) {
        Demo03<Dog> demo03 = new Demo03<>();
        Dog dogs[] = new Dog[]{new Dog("小八", 56), new Dog("小人", 67), new Dog("小九", 23), new Dog("小王", 45), new Dog("小太阳", 12)};
        //将比较逻辑抽离出来
        Comparator<Dog> comparator = (item1, item2) -> {
            if (item1.age > item2.age) return 1;
            else if (item1.age < item2.age) return -1;
            return 0;
        };
        Object[] sort = demo03.sort(dogs, comparator);
        System.out.println(Arrays.toString(sort));
    }

}
```



## 四、调停者

### 一、作用

> 专门调节类之间的关系

### 二、用途

> 比如消息中间件,Feign等

### 三、代码示例

```java
public class Demo01 {
    /**调停者
     * 作用:解耦类与类之间的关系,将类之间的调用进行集中化管理。
     */

    public static interface Traffic{
        public String getName();
    }

    public static class Plan implements Traffic{
        private String name="飞机";

        public String getName() {
            return name;
        }
    }

    public static class Car implements Traffic{
        private String name="汽车";
        public String getName() {
            return name;
        }
    }

    public static class Message{
        public void message(String name){
            System.out.println(name+"成功的启动了....");
        }
    }


    //对类之间的关系进行集中化管理
    public static class Faced{
        public void start(Traffic traffic){
            //这里获取公交的名字
            String traffic_name=traffic.getName();
            System.out.println("准备"+traffic_name);
            Message message=new Message();
            //这里Message获取公交的名字
            message.message(traffic_name);
        }
    }

    public static void main(String[] args) {
        Faced faced=new Faced();
        faced.start(new Plan());
    }

}
```

## 五、责任链

### 一、作用

> 对介质进行层层过滤,得到最终结果

### 二、用途

> SpringSecurity中的对请求进行过滤生成UsernamePasswordAuthenticationToken放入上下文中。

### 三、代码演示

```java
public class Demo01 {

    /**过滤链
     * 作用:过滤请求。
     * 原理:通过递归。
     */

    private static interface HttpServletRequest{
        public String getName();
        public void setName(String name);
    }

    private static interface HttpServletResponse{
        public String getName();
        public void setName(String name);
    }

    private static class ServletRequest implements HttpServletRequest{
        private String name;


        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setName(String name) {
            this.name=name;
        }
    }

    private static class ServletResponse implements HttpServletResponse{
        private String name;


        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setName(String name) {
            this.name=name;
        }
    }


    private static interface Filter{
        public void doFilter(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain);
    }

    private static interface FilterChain{
        public void doFilter(HttpServletRequest request,HttpServletResponse response);
    }


    private static class MyFilter01 implements Filter{

        @Override
        public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
            request.setName("张三");
            filterChain.doFilter(request,response);
            System.out.println(response.getName());
        }
    }

    private static class MyFilter02 implements Filter{

        @Override
        public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
            request.setName("张三"+"你好");
            filterChain.doFilter(request,response);
            System.out.println(response.getName());
        }
    }

    private static class MyFilter03 implements Filter{

        @Override
        public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
            request.setName("张三"+"你好"+"我的世界");
            filterChain.doFilter(request,response);
            System.out.println(response.getName());
        }
    }



    //获取过滤链
    private static class MyFilterChain implements FilterChain{
        private List<Filter> filters=new ArrayList<>();
        private Iterator<Filter> filterIterator;
        public MyFilterChain addFilter(Filter filter){
            filters.add(filter);
            return this;
        }
        @Override
        public void doFilter(HttpServletRequest request, HttpServletResponse response) {
            //创建Filter迭代器
            if (filterIterator==null) this.filterIterator=filters.listIterator();
            //为response设置数据
            response.setName(request.getName());
            if (filterIterator.hasNext())filterIterator.next().doFilter(request,response,this);
        }
    }



    public static void main(String[] args) {
        MyFilterChain filterChain=new MyFilterChain();
        filterChain.addFilter(new MyFilter01()).addFilter(new MyFilter02()).addFilter(new MyFilter03());
        HttpServletRequest request=new ServletRequest();
        request.setName("刘德华");
        HttpServletResponse response=new ServletResponse();
        filterChain.doFilter(request,response);

    }


}
```

## 六、观察者

### 一、作用

> 观察者观察被观察者做出的行为做出响应

### 二、用途

> js中的事件监听以及AWT事件监听

### 三、代码演示

```java
public class Demo02 {

    /**
     * 概念:监听器监听事件源所做出的反应而做出响应
     * 
     */


    //事件源
    private static class Window{
        private List<Listener> list=new ArrayList<>();
        {
            list.add(new keyListener());
            list.add(new Listener() {
                @Override
                public void listen(EventObject event) {
                    System.out.println("你好我也被调用了");
                }
            });
        }
        public void order(){
            Event event=new EventObject(this);
            list.stream().forEach(item->{
                item.listen((EventObject) event);
            });
        }

    }

    private static interface Event{
        public Object getRecourse();
    }

    //创建事件对象
    private static class EventObject implements Event{
        private Object Recourse;

        public EventObject(Object recourse){
            this.Recourse=recourse;
        }
        @Override
        public Object getRecourse() {
            return this.Recourse;
        }
    }



    private static interface Listener{
        public void listen(EventObject event);
    }


    //创建监听者
    private static class keyListener implements Listener{

        @Override
        public void listen(EventObject event) {
            System.out.println(event.getRecourse());
        }
    }

    public static void main(String[] args) {
        Window window=new Window();
        window.order();
    }




}
```

## 七、Flyweight享元

### 一、作用

> 将对象封装到池子里,要用的时候从池子里拿就行

### 二、用途

> 线程池

### 三、代码展示

> 详情看源代码

### 四、注意:String引用指向

#### 一、字面量比对

> 当我们创建字符串时,字符串就会放进字符串常量池里面。当字符串与字符串之间进行比对时(除了new),比对的是字符串常量池里的字面量。

> ```java
> String a="你好";
> String b="你好";
> System.out.println(a==b); //true: 在字符串常量池里指向的是一个字面量。
> ```

#### 二、引用比对

##### 一、问题

```java
String a="你好";
String b=new String("你好");
System.out.println(a==b); //false: 引用与字面量指向的比对。
```

##### 二、解决方法

```java
String a="你好";
String b=new String("你好");
System.out.println(a==b.intern()); //true: intern()优先字符串常量池进行比对
```

## 八、代理模式

### 一、作用

> 对我们的类进行加强

### 二、用途

> SpringAOP

### 三、代码示例

#### 一、JDK动态代理

```java
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
```

#### 二、CGLIB动态代理

```java
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
```

## 九、迭代器

### 一、作用

> 集合的遍历

### 二、用途

> 遍历集合

### 三、代码示例

> 看源码

## 十、构造器

### 一、作用

> 方便的去构建类

### 二、用途

> 构建类

### 三、代码示例

> 看源码



## 十一、适配器

### 一、作用

> 对类之间进行转换

### 二、用途

> 对类之间关系做缓和。

### 三、代码示例

> 看源码

### 四、注意

> 我们常见的后面带有Adapter啥模式也不是,反而后面带有bridge才是适配器模式。

> 后缀带有Adapter其实主要是为了屏蔽接口的方法



## 十二、桥接模式

### 一、作用

> 为了更好的对类进行管理。

### 二、用途

> 暂时未知,了解即可

### 三、代码示例

> 看源码

```java
public class Demo01 {

    /**桥接模式
     *
     */
    private abstract static class Traffic{
        protected String name;
        protected String type;
        protected String status;
    }

    private abstract static class Status{
        protected Traffic traffic;
    }

    private static class Plan extends Traffic{


        public Plan(String name, String type, String status) {
            this.name = name;
            this.type = type;
            this.status = status;
        }

        public Plan() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }


    }

    private static class Car extends Traffic{

        public Car(String name, String type, String status) {
            this.name = name;
            this.type = type;
            this.status = status;
        }

        public Car() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    private static class Safe extends Status{
        public Safe(Traffic traffic){
            traffic.status="安全";
            this.traffic=traffic;
        }
    }
    private static class Waring extends Status{
        public Waring(Traffic traffic){
            traffic.status="警告";
            this.traffic=traffic;
        }
    }

    private static class Danger extends Status{
        public Danger(Traffic traffic){
            traffic.status="危险";
            this.traffic=traffic;
        }
    }

    public static void main(String[] args) {
        Status status=new Danger(new Car("小汽车","东风雪铁龙",null));
        System.out.println(status.traffic.status);
    }



}
```



## 十三、Command

### 一、作用

> 下达指令执行响应的操纵逻辑,并且可以回滚。

### 二、用途

> Spring事务

### 三、代码示例

> 参考源码软件安装过程。



## 十四、原型模式

### 一、作用

> 对象之间的拷贝

### 二、用途

> 对象拷贝

### 三、代码示例

```java
public class Demo01  implements Cloneable{
    private String username;
    private int age;
    private String address;
    Demo02 demo02;


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }S

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
```



```java
public class Entry {
    /**原型模式
     * 原理:当调用clone方法的时候,jvm会对当前类复制一份产生新的对象到内存里。
     * 注意:如果要对类进行克隆,必须实现Cloneable接口方法,运行的时候会检查,必须重写clone方法。
     * 其属性引用指向的是同一个。
     */
    public static void main(String[] args) throws CloneNotSupportedException {
        Demo01 demo01=new Demo01("刘德华",67,"北京西城",new Demo02());
        Demo01 demo011 =(Demo01) demo01.clone();
        //克隆出来之虽然属性以及方法不变,但是由于重新分配了内存空间,导致引用指向不同。
        System.out.println(demo01==demo011);
        //但是其内部引用指向不变
        System.out.println(demo01.getDemo02()==demo011.getDemo02());
    }
}
```

## 十五、备忘录

### 一、作用

> 将对象存储到硬盘中,做保存以及回滚。

### 二、用途

> 记录日志或者存档。

### 三、代码示例



## 十五、模板方法

### 一、作用

> 父类抽象类调用抽象方法可以执行。

### 二、用途

> 父类抽象类调用抽象方法可以执行。

### 三、代码示例

```java
public abstract class Demo {
    public abstract void show();

    public void reject(){
        System.out.println("调用成功!");
        show();
    }

    public static void main(String[] args) {
        Demo demo=new Demo02();
        demo.reject();
    }
}
class Demo02 extends Demo{

    @Override
    public void show() {
        System.out.println("执行结束!");
    }
}
```



## 十六、状态

### 一、作用

> 每个实现的方法作用都不一样

### 二、用途

> 每个实现的方法作用都不一样

### 三、代码示例

> 看源码。
