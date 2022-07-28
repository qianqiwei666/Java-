## 一、概要

#### 一、什么是JDK与JRE？

> 1. 关系: JDK包含JRE
> 2. JDK作用:作为Java开发工具包,以及一些调试工具
> 3. JRE作用:包含JVM虚拟机以及Java常用的类库

#### 二、最常用的虚拟机

> 1. Hotspod--Oracle官方
> 2. Jrockit--BEM 曾经世界上最快的JVM虚拟机,后来被Oracle收购,合并Hotspod
> 3. J9--IBM
> 4. Microsoft VM--微软
> 5. TaoBao VM--阿里
> 6. Liquid VM--直接正对硬件, 运行速度快
> 7. azul --好像也是微软的
> 8. zing -- 性能最高的JVM,但是要收费

#### 三、关于Java是解释性语言还是编译性语言

##### 一、Java默认情况下是混合模式

> 1. 解释器+热点代码编辑
> 2. 当代码执行的时候是由解释器执行,当JVM发现某一段代码,或者某一段方法执行次数特别多,就把这一段代码或方法编译到本地文件,下次执行就执行本地代码。

##### 二、怎么设置混合模式,解释运行,编译运行?

> 1. -Xmixed: 混合模式 默认。
> 2. -Xint: 使用解释模式 启动很快,执行稍慢。
> 3. -Xcomp: 使用纯编译模式 启用很慢,执行很快。

## 二、Class(字节码文件)

#### 一、字节码文件加载到内存的步骤

> class--->loading--->linking(verification-->preparation-->resolution)--->initalizing--->GC

##### 详解:

> 1. loading: 加载到内存
> 2. verification 验证字节码文件开头是否是 cafebaby
> 3. preparation 为静态变量赋初始值
> 3. resolution Java虚拟机将常量池内的符号引用替换为直接引用的过程(补充:直接应用到内存地址)
> 3. initalizing 为静态变量赋值,同时执行静态代码块

#### 二、什么是双亲委派

> 当前ClassLoader加载class文件,如果加载不了就会找找父加载器去加载,如果父加载器从缓存中找不到这个class,就会找当前加载器的父加载器加载,直到最顶层加载器,如果顶层加载器还是加载不了,就会不断让子加载器加载,如果最底层加载器如果还是加载不了,就会报ClassNotFound异常。一个循环的过程。

备注: 

> 为什么要用双亲委派? 安全

#### 三、自定义ClassLoader

一、Classloader loadClass源码

```java
protected Class<?> loadClass(String name, boolean resolve)
    throws ClassNotFoundException
{
    synchronized (getClassLoadingLock(name)) {
        //首先看此文件是否加载到当前ClassLoader缓存区,如果加载到缓存区就直接返回
        Class<?> c = findLoadedClass(name);
        //如果没有就找父加载器
        if (c == null) {
            long t0 = System.nanoTime();
            try {
                if (parent != null) {
                    //这里的Parent指的是 AppClassLoader
                    //如果有父加载器，就继续加载
                    c = parent.loadClass(name, false);
                } else {
                    c = findBootstrapClassOrNull(name);
                }
            } catch (ClassNotFoundException e) {
                // ClassNotFoundException thrown if class not found
                // from the non-null parent class loader
            }

            if (c == null) {
                // If still not found, then invoke findClass in order
                // to find the class.
                long t1 = System.nanoTime();
                //自定义ClassLoader就是重写的这个方法
                c = findClass(name);
                // this is the defining class loader; record the stats
                PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                PerfCounter.getFindClasses().increment();
            }
        }
        if (resolve) {
            resolveClass(c);
        }
        return c;
    }
}
```



```java
public class MyClassLoader extends ClassLoader{

    @Override
    protected Class<?> findClass(String name){
        //找到类文件
        File classFile=new File("D:\\",name.replaceAll("\\.","/").concat(".class"));
        System.out.println(classFile.getAbsolutePath());
        try {
            //读取文件
            FileInputStream fileInputStream=new FileInputStream(classFile);
            //创建二进制数组流
            ByteArrayOutputStream arrayOutputStream=new ByteArrayOutputStream();
            int bytes=0;
            while ((bytes=fileInputStream.read())!=-1){
                arrayOutputStream.write(bytes);
            }
            //获取二进制流
            byte b[]=arrayOutputStream.toByteArray();
            //关闭流
            arrayOutputStream.close();
            fileInputStream.close();
            //创建对象
            return defineClass(name,b,0,b.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return super.findClass(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
```

> 备注: 当自定义Classloader时,首先让父加载器去加载字节码文件,走双亲委派机制,最后调用find方法(自定义Classloader重写的方法),自定义ClassLoader。

#### 四、热部署

##### 一、打破双亲委派机制

```java
public class MyClassLoader extends ClassLoader {

    private String basePath;

    public MyClassLoader(String basePath) {
        this.basePath = basePath;
    }

    //查找资源
    private File findClassFile(String name) {
        File file = new File(basePath, name.replaceAll("\\.", "/").concat(".class"));
        return file.exists() ? file : null;
    }

    //生成Class
    private Class<?> createClass(File file, String name) {
        Class<?> clazz = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte bytes[] = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            //通过字节流Class
            clazz= defineClass(name, bytes, 0, bytes.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        File classFile = findClassFile(name);
        //如果找到了class文件,直接跳过双亲委派机制
        if (classFile!=null){
            return createClass(classFile,name);
        }
        //没有找到默认ClassLoader加载(双亲委派)
        return super.loadClass(name);
    }
}
```









#### 五、重点

> 1. 只要被当前类加载器加载过后就不会去找父类加载。
> 2. classloader自下往上查找查找缓存区里时候存在当前类，说白了自己是否被加载过,如果加载过就直接返回。
> 3. classloader自下往上查找 实际去查找并且去创建类。
> 4. preparation 这一步给静态变量赋初始值,initalizing 给静态变量赋值。顺序不能颠倒。
> 5. new 对象时中间发生过程: 向内存申请空间--->给成员变量赋默认值--->调用构造方法---->给成员变量赋值



## 三、JMM

#### 一、什么是指令重排序?

##### 代码说明问题(实例)

```java
public class MyClassLoader  {
    public static void main(String[] args) {
        //new 初始化: 申请内存-->给属性赋默认值-->调用构造方法给属性赋值-->将对象内存地址赋值给myClassLoader
        MyClassLoader myClassLoader=new MyClassLoader();
        //什么是指令重排序? 意思就是调用构造方法之前就将内存地址赋给了myClassLoader
    }
}
```

#### 二、怎么解决cpu线程处理数据不一致问题

##### 一、概念

> 1. cpu内部处理数据最快的部分是寄存器,其次是一缓，二缓。例如三缓,内存,储存器(磁盘)等与cpu共享。
> 2. 数据都会不断load到最快的缓存或者容器中(数据--load-->内存--load-->三缓....)。直到被处理。

##### 二、怎么解决cpu读取数据不一致问题

> 1. cpu内部处理共享数据时,会经过一条总线,以前cpu会在总线加上锁,为了避免cpu读取数据不一致的问题。
> 2. 后来cpu内部处理共享数据时就会遵循一致性协议,例如intel 遵循MESI协议,现在的cpu缓存锁（MESI）+总线锁，来保证数据的一致性。

#### 三、缓存行

> 1. 当cpu内部(二缓)要读取内存或者三缓的数据时以cache为基本单位，目前缓存行的大小一般为64字节(512位)
> 2. 什么是缓存行? 当cpu内部缓存(二缓)读取三缓数据的时候,就会读取当前数据后(64-当前数据字节大小) 的数据.
> 3. 当CPU更新了缓存行,就会通知其他CPU缓存去读取缓存行。

#### 四、伪共享

##### 一、为什么是会产生伪共享?

> 1. 概念:当两个cpu内部缓存读取不同值,恰巧两个值在同一缓存行之内,就会产生伪共享。
> 2. 详解:当一个cpu更改了一个值A并且A在M缓存行里,就会通知其他cpu缓存去更行(重新读取)这块缓存行。当其他cpu缓存的值B也在M缓存行里,就会更新M缓存行,但是当前CPU并不需要更改这个值,就会造成不必要的开销。

#### 五、乱序问题

##### 一、什么是乱序问题

> 1. 当cpu接受到多条指令时,首先会从内存中找数据,放入自己的缓存中,由于CPU的缓存速度至少比内存速度快上100倍,就会首先执行其他指令(非依赖关系),就导致了乱序问题。

#### 六、保障有序性

##### 一、硬件内存屏障

> 1. sfrench (save): 在sfrench指令前的写操作必须在sfrench指令后的写操作之前完成。
> 2. Ifrench(load): 在Ifrench指令前的读操作必须在Ifrench指令后的读操作之前完成。
> 3. mfrench: 在mfrench指令前的读写操作必须在mfrench指令后的读写操作之前完成。

##### 二、JVM级别规范

> 1. loadload屏障: 对应这样一条语句 load1; loadload; load2 在load2读读取操作要读取的数据之前,要保证load1要读取的数据读取完成 。
> 2. storestore屏障: 对应这样一条语句 store1; storestore; store2 在store2写入数据之前,要保证store1写入操作对其他CPU可见。
> 3. loadStore屏障:对应这样一条语句 load1; loadStore; store2 在store2写入之前,要保证load1读取数据读取完成。
> 4. storeLoad屏障: 对应这样一条语句 store1; loadStore; load2 在load2读取数据之前,要保证store1写入对所有处理器可见。

##### 三、Volatile怎么实现指令重排序?

1、读操作: StoreStore  Volatile  Storeload 

2、写操作: LoadLoad Volatile LoadStore

#### 七、查看虚拟机的配置

> java -XX:+PrintCommandLineFlags -version

> 配置详情: 
>
> 1. InitialHeapSize初始化堆内存大小
> 2. MaxHeapSize最大堆内存大小
> 3. UseCompressedClassPointers对象头
> 4. UseCompressedOops oops

#### 八、普通对象

> 1. 对象头: markword 一般大小为8个字节
> 2. ClassPointer指针:UseCompressedClassPointers开启占4个字节,不开启占8个字节(new对象指向原始对象)
> 3. 实例数据(属性):  引用类型: UseCompressedOops开启占4个字节,.不开启占8个字节。值类型大小不变
> 4. Padding对齐,8的倍数: 一般按块读取数据。

#### 九、数组对象

> 1. 对象头:markword 8个字节
> 2. ClassPointer同上
> 3. 数组的长度 4个字节
> 4. 数组的数据
> 5. 对齐 8的倍数。

#### 十、如何知道对象的大小

##### 一、创建Jar包

1,代码部分

```java
public class ObjectSize {
    private static Instrumentation instrumentation;

    public ObjectSize() {
    }

    public static void premain(String agentArgs, Instrumentation _instrumentation) {
        instrumentation = _instrumentation;
    }

    public static long sizeOf(Object o) {
        long objectSize = instrumentation.getObjectSize(o);
        return objectSize;
    }
}
```

2,META-INF下创建mf文件

```yaml
Mainifest-version: 1.0
Created-By: qianqiwei
Premain-Class: com.qianqiwei.ObjectSize
```

3,打成jar包运用到其他项目中

> 注意加参数,不加参数报空指针:
>
> -javaagent: 你的jar包路径

#### 十一、对象定位

##### 一、句柄池

> T t =new T();
>
> t分别指向new 的对象和T.class

##### 二、直接指针

> T t =new T();
>
> t指向T对象,然后指向T.class



## 四、 Java指令集

#### 一、Program Counter(简称PC)

> 主要用来存放指令集

##### 1、Java虚拟机运行过程

> while(not end){
>
> 1,从PC中找到对应位置指令集;
>
> 2,执行指令
>
> 3,PC++   //计数
>
> }

#### 二、JVM stack

> [好文章](https://www.cnblogs.com/jhxxb/p/11001238.html)

> 一个线程对应一个 JVM Stack。JVM Stack 中包含一组 Stack Frame。线程每调用一个方法就对应着 JVM Stack 中 Stack Frame 的入栈，方法执行完毕或者异常终止对应着出栈（销毁）。
>
> 一个方法对应着一个栈帧。

#### 一、Frame

##### 一、概念

> 1. local variable table (局部变量表)
> 2. Operand Stack(操作数栈)
> 3. Dynamic Linking 

##### 二、什么是 Dynamic Linking？

> 一个线程对应一个jvm stack--->frame(栈针) Dynamic Linking指向常量池符号链接,如果没有解析就动态解析,如果解析了就直接用

##### 三、什么是局部变量表

> 局部变量

##### 四、什么是Operand Stack(操作数栈)

> 操作数栈还会开辟一个栈,用于存储变量值，以及对象的引用地址

详解:

> 1. bipush 往栈中压入数字
> 2. istore   将栈中的数字出栈,赋值给下标为1的局部变量表。

#### 三、native method stack

> 存储java本地方法(C,C++) Java自身的栈。

#### 四、redirect memory

> 直接内存,从Jvm直接访问内核空间的内存 例如NIO

#### 五、Method Area

> 存储class信息以及运行时常量池

##### 一、Method Area的实现

> 1. 小于1.8 permSpace(永久代) 字符串常量位于永久代FGC一般不会去清理
> 2. 大于1.8 metaspace(元空间) 字符串常在堆里,会触发FGC

##### 什么是常量池与运行时常量池区别[彻底弄懂java中的常量池 - 云+社区 - 腾讯云 (tencent.com)](https://cloud.tencent.com/developer/article/1450501)

> 1. 常量池: 指的是class编译期间产生的常量池,在编译阶段,常量池存放的是字面量与符号引用,例如:也就是我们经常申明的： public String s = "abc";中的"abc"
> 2. 运行时常量池: 指的是运行时常量池中保存着一些class文件中描述的符号引用，同时在类的解析阶段还会将这些符号引用翻译出直接引用（直接指向实例对象的指针，内存地址），翻译出来的直接引用也是存储在运行时常量池中。

#### 六、线程共享区域

> 1. 每一个线程都有单独的 PC--->JVM stack(包括对应的栈针)--->native method stack
> 2. 共享: 堆 跟 (permSpace or metaspace )

## 五、GC垃圾回收

#### 一、什么是垃圾?

> 当一个引用不再指向一个对象或者当对象之间循环引用,再无其他引用指向循环引用时,就称为垃圾。

#### 二、对象标记算法

> 1. 引用计数: 在对象上计数,有几个引用指向当前对象就计一个数,当计数器为0的时候(没有引用指向这个对象的时候)就被回收掉。但是如果出现循环引用,再无出现其他引用指向这**一团**循环引用时,就会造成无法回收这一团垃圾。
>
> 2. 根可达算法(Java默认算法): 
>
>    > 1. 线程栈变量: 
>    >
>    >    > 从main方法中会启一个线程，线程栈里包含着一个栈帧,栈帧里包含的对象叫做根对象。
>    >
>    > 2. 静态变量
>    >
>    >    > 当类load到内存后进行一系列初始化后,就会给静态变量赋值,静态变量引用的对象叫做根对象。
>    >
>    > 3. 常量池
>    >
>    >    > 需要用到其他对象的对象叫做根对象。
>    >
>    > 4. JNI指针
>    >
>    >    > 本地方法用到的对象叫做根对象。

#### 三、GC回收算法

##### 1、Mark-Sweep(标记清除)

1、作用

> 将需要回收的对象进行标记然后进行清除

2、优点

> 存活对象比较多的时候效率比较高

3、缺点

> 要进行两次扫描,效率偏低,容易产生碎片

##### 二、Copying（拷贝）

1、作用

> 将要回收的对象回收之后,对空闲区域进行整理拷贝,如下图

![Copying](D:\JVM\Copying.png)

2、优点

> 适用于对象较少的情况,只扫描一次,效率较高,没有碎片。

3、缺点

> 空间浪费:需要复制对象,需要调整对象的引用。

##### 三、Mark-compact(标记-整理)

1、作用

> 记过程仍然与“标记-清除”算法一样，但后续步骤不是直接对可回收对象进行清理，而是让所有存活的对象都向一端移动，然后直接清理掉端边界以外的内存，“标记-整理”算法的示意图如下图所示。

![Mark-compact](D:\JVM\Mark-compact.png)

2、优点

> 不会产生碎片,方便对象分配

3、缺点

> 扫描两次,需要移动对象

#### 四、分代收集算法

![fendai](D:\JVM\fendai.png)

##### 一、新生代

> 当创建对象的时候首先向栈中分配空间，如果分配不下就会分配到**eden**区,当eden区满了之后就会进行Minor GC/YGC,存活的对象就会分配到**survivor1**中,eden就会清空,每一次Minor GC,eden就会将存活的对象拷贝到survivor中,随之survivor1与survivor2存活的对象会互相拷贝(这里用的是拷贝算法),当survivor1与survivor2拷贝次数达到15次的时候,存活的对象就会进入老年代。

##### 二、老年代

> 存储的是存活率高,回收比较少的对象。

##### 三、GC回收名称

> 在年轻代称之为:Minor GC/YGC
>
> 在老年代称之为: MajorGC/FullGC

#### 五、对象如何分配？

##### 一、栈上分配

> 1. 线程私有小对象    线程独有的对象并且占用的空间较少。
> 2. 无逃逸  在一个代码块区中没有其他的引用指向它 用完就GC
> 3. 标量替换 把类中的属性来代替整个对象。

##### 二、线程本地分配TLAB

> 1. 占用eden区,默认1%
> 2. 多线程中不用竞争eden区就可以申请空间,提高效率
> 3. 小对象

![all](D:\JVM\all.png)

#### 六、常见的垃圾回收器

> 1. Serial 年轻代,串行回收  (jdk诞生) 为了提升效率,产生了PS PO 后来为了与CMS 配合，产生了PN CMS开启了并发回收,并发垃圾回收是因为无法忍受STW
> 2. PS 年轻代,并行回收
> 3. ParNew 年轻代 配合CMS的并行回收
> 4. Serial  Old
> 5. ParallelOld 
> 6. ConcurrentMarkSweep 老年代,并行的
> 7. G1(10ms)  算法:三色标记+ SATB
> 8. ZGC(1ms) pk c++ 算法: ColoredPointer+ 写屏障
> 9. shenandosh  算法: ColoredPointer+ 读屏障
> 10. Eplison

![lajihuishouqi](D:\JVM\lajihuishouqi.png)

##### 一、不同垃圾回收器机制

> 1. serial  单线程并且内存小效率高: 单线程的回收,回收的过程中,对象产生停止。之后产生对象继续。
> 2. serial old  在老年代进行回收
> 3. Parallel Scavenge(PS) 默认: 多线程进行垃圾回收。当回收完成之后,生产对象继续。
> 4. Parallel Old 在老年代进行回收
> 5. ParNew Parallel Scavenge的一个变种,支持与CMS的配合
> 6. CMS: 回收与工作线程同时进行(并发)(生产对象)

##### CMS

1、概念

> CMS 在执行垃圾回收的时候,其他工作线程还可以继续执行,互不影响。

2、执行流程

> 初始标记--->并发标记--->重新标记--->并发清理

> 1. 初始标记: 标记根对象。
> 2. 并发标记: 生产对象的同时,也同时标记需要回收的对象。
> 3. 重新标记: 对新产生的垃圾进行标记。
> 4. 并发清理: 会产生浮动垃圾,需要下次GC的时候被清理掉。
