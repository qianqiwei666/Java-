# 一、使用(Dubbo3)

### 一、配置yaml文件(入门)

``` yaml
spring:
  application:
    name: Consumer01 #配置应用名称

dubbo:
  protocol:
    host: localhost #配置主机名
    port: 20880     #配置dubbo协议端口号 -1~20880(注意如果在同一台机器上配置,每个端口都要改)
    name: dubbo     #配置协议名称(dubbo3)
  scan:
    base-packages: com.qianqiwei.dubboconsumer01.service #扫描service(在哪里进行服务调用就扫哪里) 这个可选,以后用@EnableDubbo来扫包
    
  registry:
    address: zookeeper://www.qianqiwei.com:2181 #配置注册中心
   consumer:
    timeout: 3000   #配置consumer调用provider超时时间
server:
  port: 8080 #设置端口号

```

### 二、导入依赖

#### 一、zookeeper为注册中心时

``` xml
  <!-- https://mvnrepository.com/artifact/org.apache.dubbo/dubbo-spring-boot-starter -->
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-spring-boot-starter</artifactId>
            <version>3.0.6</version>
        </dependency>
      <!-- https://mvnrepository.com/artifact/org.apache.curator/curator-x-discovery -->
<dependency>
    <groupId>org.apache.curator</groupId>
    <artifactId>curator-x-discovery</artifactId>
    <version>4.2.0</version>
</dependency>

```

#### 二、nacos为注册中心时

```xml
<!-- https://mvnrepository.com/artifact/org.apache.dubbo/dubbo-spring-boot-starter -->
<dependency>
    <groupId>org.apache.dubbo</groupId>
    <artifactId>dubbo-spring-boot-starter</artifactId>
    <version>3.0.6</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.apache.dubbo/dubbo-registry-zookeeper -->
<dependency>
    <groupId>org.apache.dubbo</groupId>
    <artifactId>dubbo-registry-nacos</artifactId>
    <version>3.0.6</version>
</dependency>
```

#### 三、暴露接口



# 二、高级用法

## 一、配置文件说明

### 一、配置文件(Consumer)

```yaml
spring:
  application:
    name: ConsumerClient
dubbo:
  application:
    name: ConsumerClient-consumer
  protocol:
    port: -1
    name: dubbo
  registry:
    address: nacos://127.0.0.1:8848
  consumer:
    timeout: 3000  #超时时间
    retries: 5     #每超时一次调用一次(这里调用五次)
    loadbalance: random  #负载均衡机制:random,roundrobin,leastactive，分别表示：随机，轮询，最少活跃调用 注意:provider的version一定要相同!
    check: true   #每次开机检查当前需要调用的provider是否已经注册。
    version: x.0  #consumer的版本号

server:
  port: 8081
```



### 二、配置文件(provider)

```yaml
spring:
  application:
    name: ProviderClient02
dubbo:
  application:
    name: ProviderClient02-provider
  protocol:
    port: -1
    name: dubbo
  registry:
    address: nacos://127.0.0.1:8848
  provider:
    threads: 1  #当前服务同时能接多少个请求,超过这个数量,进入等待队列,等待前一个任务完成
    threadpool: fixed #fixed/cached/limit(2.5.3以上)/eager(2.6.x以上)
    version: 2.0
#    accepts: 1  #最大连接数,跟线程池差不多,线程池满了也不干嘛,也不走拒绝策略,等着前面的线程释放!
server:
  port: 8083
```

### 三、配置文件注意事项

> 如果在配置文件配完之后,然后再在注解上配置,优先注解配置生效!

## 二、文档说明

[更多高级用法](https://dubbo.apache.org/zh/docs/advanced/preflight-check/)

## 三、代码示例

> 笔记中源码
