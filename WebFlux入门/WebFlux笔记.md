# 一、概诉

## 一、轮询,长轮询

### 一、轮询

> 轮询:客户端每隔多少秒向服务器发送请求,然后的到数据。
>
> 缺点:
>
> 1. 数据获取不及时:如果服务器产生新的数据,由于固定时间获取,导致数据延迟
> 2. 压力:对服务器的压力变得很大。
> 3. 无效数据:如果客户端没有新的数据,你还获取,获取的数据注定无用！

### 二、长轮询

> 长轮询:在固定时间之内,当客户端与服务端建立起链接,服务器就会hold住这个链接(阻塞),请求数据与响应数据都是通过这个链接进行的。
>
> 优点:数据响应及时。

## 二、长链接与短链接

###  一、长链接

> 长链接:客户端与服务端永久建立起链接,请求数据与响应数据都是通过这个链接来进行的。

### 二、短链接

> 当客户端要发送请求到服务端的时候,这时候才建立起链接,当数据响应完成之后,链接也就断开了

## 三、客户端与服务端建立起链接

### 一、servlet(2.0)

#### 一、执行流程

> 1. 客户端请求数据到服务器(NIO)异步
> 2. 请求打到tomcat上，tomcat从业务线程池分配一个业务线程。
> 3. 业务处理完成之后返回数据到客户端(NIO)异步

#### 二、缺点

##### 一、内存开销

> 每开一个新的线程,至少占用1m的堆内存,在加上Spring的上下文,Mybatis上下文等各个框架的上下文,一个链接就可能达到100m,如果有500个请求,可能达到50个g的内存开销。

##### 二、处理器的开销

> 如果分配的线程够多,业务逻辑也特别复杂,就可能造成cpu上下文频繁切换。导致cpu性能变低。

### 二、servlet(3.0)

####  一、执行流程

> 1. 客户端请求打到tomcat上。
> 2. tomcat会分配一个io线程池来处理链接。(我们可以自行更改io线程池的数量,对请求进行控制)
> 3. 同时会发布事件让业务线程进行业务处理。(从业务线程中分配一个业务线程)
> 4. 返回数据(响应式)到客户端

# 二、RX Java3(了解)

## 一、代码

```java
public class Demo {
    public static void main(String[] args) {
        //1.订阅者首先订阅发布者
        //2.发布者发布事件之后,订阅者得到数据。
        //创建被观察者(发布者)
        Observable<String> objectObservable = Observable.create(emitter -> {
            Thread.sleep(1000);
            emitter.onNext(Thread.currentThread().getName()+":你好");
            Thread.sleep(1000);
            emitter.onNext(Thread.currentThread().getName()+":我好");
            Thread.sleep(1000);
            emitter.onNext(Thread.currentThread().getName()+":他也好");
            Thread.sleep(1000);
            emitter.onError(new Throwable("模拟一个错误!"));
            //发布事件
            emitter.onComplete();
        });


        //创建观察者(订阅者)
        Observer<String> observer = new Observer<>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println("订阅消息:"+Thread.currentThread().getName()+":"+s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("打印失败消息:"+e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };

        //订阅者订阅发布者
        objectObservable.subscribe(observer);


    }
}
```

# 三、Flux与Mono简单使用

## 一、Flux

> 里面可以传一个或多个值。

### 一、多值传输

```java
//创建数据集合
String usernames[]=new String[]{"刘德华","王安石","周建华"};
//创建flux发布者
Flux<String> flux=Flux.just(usernames);
//创建flux订阅者
flux.subscribe(new Consumer<String>() {
    @Override
    public void accept(String s) {
        System.out.println(s);
    }
});
```

### 二、发布订阅

```java
Flux.create(fluxSink -> {
    fluxSink.next("你好");
    fluxSink.complete();
}).subscribe(o -> {
    System.out.println(o);
});
```

### 三、订阅执行流程

```java
Flux.just("你好","我的","世界")
                .doOnSubscribe(subscription -> {
                    System.out.println("订阅中!");
                }).doOnNext(s -> {
                    System.out.println("得到数据:"+s);
                }).doOnComplete(()->{
                    System.out.println("数据执行完成");
                }).subscribe(s -> {
                    System.out.println("订阅者拿取数据:"+s);
                });
```

### 四、注意

> create才是异步的,其他发布都是阻塞式的。虽然是异步的但是都是同一个线程。

## 二、Mono

> Mono只能传一个值

### 一、单值传输

```java
Mono<String> mono=Mono.just("你好");
mono.subscribe(s -> {
    System.out.println(s);
});
```

### 二、发布订阅

```java
Mono.create(monoSink -> {
    //只能输出你好,因为订阅者只能取出一个值。
    monoSink.success("你好");
    monoSink.success("我好");
}).subscribe(o -> {
    System.out.println(o);
});
```

### 三、订阅执行流程

```java
Mono.just("Nihao").doOnSubscribe(subscription -> {
    System.out.println("订阅中!");
}).doOnNext(s -> {
    System.out.println("得到数据:"+s);
}).doOnSuccess(s -> {
    System.out.println("整体执行成功:"+s);
}).subscribe(s -> {
    System.out.println("订阅者拿取数据:"+s);
});
```

### 四、注意

> create才是异步的,其他发布都是阻塞式的。虽然是异步的但是都是同一个线程。

# 四、使用

## 一、思想

> 1. 服务端就是发布者,客户端就是订阅者,发布者数据改变,订阅者数据也发生改变。

## 二、Controller使用

#### 
