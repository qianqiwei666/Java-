## 一、部署

#### 一、准备工作

##### 一、下载

> 1. 自行下载RocktetMQ[最新安装包]([Downloading the Apache RocketMQ Releases - Apache RocketMQ](https://rocketmq.apache.org/dowloading/releases/))
> 2. 下载maven

##### 二、配置

1、给maven换源:

``` xml
<mirror>
      <id>alimaven</id>
      <name>aliyun maven</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public</url>
      <mirrorOf>central</mirrorOf>        
</mirror>
```

2、指定maven存储地址:

``` xml
<localRepository>存储仓库地址</localRepository>
```

3、配置maven环境变量:

``` xml
export M2_HOME=/opt/software/mvn/apache-maven-3.8.4
export PATH=$PATH:$M2_HOME/bin
```

4、编译源代码:

```bash
mvn -Prelease-all -DskipTests clean install -U
```

##### 三、启动

1、启动脚本(bin目录): 启动注册中心

>  ./mqnamesrv

2、启动脚本(bin目录): 链接注册中心

> ./mqbroker -n localhost:9876

备注:

> 1. 如果机器内存太小:在rocketMQ bin目录下 修改runserver.sh,runbroker.sh文件分配堆内存大小,否则启动失败。
> 2. runserver相当于开了个注册中心。默认注册端口号:9876

##### 四、测试

1、指定测试服务

> 1. 在tools.sh 中加上: export NAMESRV_ADDR=localhost:9876    作用于指定注册服务。

2、测试服务:

> 1. 启动tools:  
> 2. 以生产者启动: ./tools.sh org.apache.rocketmq.example.quickstart.Producer
> 3. 以消费者启动:  ./tools.sh org.apache.rocketmq.example.quickstart.Consumer

3、benchmark测试:

> 1. benchmark目录下执行:
> 2. 给定权限: 给定生产者权限：[chmod 777 producer.sh]  给定消费者权限: [chmod 777 consumer.sh]
> 3. 分别执行[./producer.sh],[./consumer.sh]

##### 五、编译控制台

> 1. 源代码下载,[RocketMQ-dashboard](https://github.com/apache/rocketmq-dashboard)。
> 2. 解压之后进入解压目录找到 rocketmq-console目录进入。
> 3. 编译: mvn clean package -Dmaven.test.skip=true。
> 4. 进入**当前**target目录执行jar包。

##### 六、控制台配置

>  1、配置ops，更改为RocketMQ注册服务地址。或者启动时配置 : java -jar rocketmq-dashboard-1.0.0.jar  --rocketmq.config.namesrvAddr=127.0.0.1:9876

##### 七、部署到云服务器注意事项

> 1. 放行10909端口(RocketMQ VIP 端口)、9876端口(服务注册端口)。
> 2. 更改broker配置文件，默认ip访问内网，外网访问不到，更改配置文件。
> 3. broker更改配置文佳注意事项: 启动broker使用此命令使用更改配置文件启动, ./mqbroker -n localhost:9876 -c ../conf/broker.conf

配置文件更改:

```yaml
brokerClusterName = brokerCluster01   #集群名字
brokerName = broker01                 #broker名字
brokerId = 0                           #broker id
deleteWhen = 04             
fileReservedTime = 48           
brokerRole = ASYNC_MASTER              #broker角色
#在此处添加配置
namesrvAddr=1.117.202.198:8890         #配置:broker ip和端口号(如果外网访问一定要配置外网ip,默认内网ip)  
brokerIP1=1.117.202.198                #配置ip地址
listenPort=8890                        #配置端口号
flushDiskType = ASYNC_FLUSH
```



## 二、前置

#### 一、topic跟queue(JMS)

> 1. topic: 发布消息时不能保证每一条订阅者收到（类似于UDP）。
> 2. queue: 发布消息点对点传输,能保证数据的可靠性。（类似于TCP）。
> 3. 在rocketMQ中没有topic。

#### 二、RocketMQ在服务之间的作用

##### 一、应用结构

> 服务之间调用不在强耦合。

##### 二、流量削峰

> 1. 早期: 在流量高峰时期，服务服务方会使用令牌桶算法进行限流，保证服务正常运行，但是会损失一部分请求。
> 2. 现在: 当流量达到顶峰的时候,消息中间件缓存大量的请求以供生产者调用,可以避免服务器任务过多而进行限流,匀速消费（类似于负载均衡,谁有空就调谁）。并且生产者可以动态拓展。

##### 三、缓存机制

> 1. 当producer发送请求给broker，broker发送确认请求给producer，broker会持久化message到内存和磁盘，

#### 三、角色

> 1. producer:专门用来生产消息。
> 2. consumer:专门用来消费消息。
> 3. brocker:主要存储topic。
> 4. nameServer(注册中心):主要告诉producer与consumer,topic在哪个brocker上。

#### 四、nameServer

> 1. 无状态:只会将节点数据信息存储到内存中。不会将其持久化。
> 2. 独立:每一台nameServer之间不会传递数据(相互独立),这样有个好处保证了高可用,但是不保证数据的一致性,也无需保证。

#### 五、borker

> 1. 主从模式:master负责读写,slave负责同步数据,对外界提供读。
> 2. 遍历:每个borker都对将自己的信息上传到每一个broker之中。
> 3. 链接:broker与服务之间会建立起长链接,发送心跳来维持状态。

#### 六、基本执行流程

> 1. consumer(发送消息)--->nameServer(寻找topic在哪个broker上)--->topic接收到消息----->producer如果订阅了这个topic将受到消息。
> 2. producer同理。

#### 七、broker刷盘机制

> 当producer将消息发送到broker中,broker确认消息之后就会将消息持久化到硬盘之中。
>
> consumer从borker拉去消息的时候,broker会创建当前消息的副本以供 consumer拉取。

#### 八、group

> broker只认组:每一组都可以从拉取broker的信息。

## 三、整合Java

#### 一、简单整合

##### 一、producer

```java
public class Producer {

    public static void main(String[] args) throws Exception {

        //初始化Producer
        DefaultMQProducer defaultMQProducer=new DefaultMQProducer();
        //设置分组
        defaultMQProducer.setProducerGroup("ProducerGroup01");
        //设置注册中心的地址
        defaultMQProducer.setNamesrvAddr("www.qianqiwei.com:9876");
        //启动producer
        defaultMQProducer.start();
        //设置发送消息超时时长
        defaultMQProducer.setSendMsgTimeout(1000*10);
        //创建消息
        //topic: 创建订阅消息地址 body:发送的数据(只接受字节流)
        Message message1 = new Message("subscription01","钱琪炜1".getBytes(StandardCharsets.UTF_8));
        Message message2 = new Message("subscription01","钱琪炜2".getBytes(StandardCharsets.UTF_8));
        Message message3 = new Message("subscription01","钱琪炜3".getBytes(StandardCharsets.UTF_8));
        Message message4 = new Message("subscription01","钱琪炜4".getBytes(StandardCharsets.UTF_8));
        //发送消息同步,设置一秒一秒的发送
        defaultMQProducer.send(message1);
        TimeUnit.SECONDS.sleep(1);
        defaultMQProducer.send(message2);
        TimeUnit.SECONDS.sleep(1);
        defaultMQProducer.send(message3);
        TimeUnit.SECONDS.sleep(1);
        defaultMQProducer.send(message4);
        //关闭
        defaultMQProducer.shutdown();
    }
}

```

##### 二、consumer

```java
public class Consumer {
    public static void main(String[] args) throws MQClientException {
        //初始化consumer
        DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer();
        //注册到注册中心
        pushConsumer.setNamesrvAddr("www.qianqiwei.com:9876");
        //设置分组
        pushConsumer.setConsumerGroup("ConsumerGroup01");
        //拉取消息 topic: 关注的消息地址  subExpression: *表示不过滤消息
        pushConsumer.subscribe("broker01", "*");
        //收取消息
        //默认情况下这个消息只会被一个消费者消费
        pushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt message : msgs) {
                    System.out.println(new String(message.getBody()));
                }
                //只要被消费了，其他人不能在消费(除了不同组之外)
                //ConsumeConcurrentlyStatus.CONSUME_SUCCESS:消费者成功的消费了,失败消费，ConsumeConcurrentlyStatus.RECONSUME_LATER:稍后尝试消费(没有被成功的消费,直到消费成功。)。
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });
        //启动consumer
        pushConsumer.start();

    }
}
```

#### 二、批量传输消息

##### 一、Producer

```java
public class Producer {

    public static void main(String[] args) throws Exception {

        //初始化Producer
        DefaultMQProducer defaultMQProducer=new DefaultMQProducer();
        //设置分组
        defaultMQProducer.setProducerGroup("ProducerGroup01");
        //设置注册中心的地址
        defaultMQProducer.setNamesrvAddr("www.qianqiwei.com:9876");
        //启动producer
        defaultMQProducer.start();
        //设置发送消息超时时长
        defaultMQProducer.setSendMsgTimeout(1000*10);
        //批量创建消息
        //Message: topic: 创建订阅消息地址 body:发送的数据(只接受字节流)
        List<Message> messages=new ArrayList<>();
        messages.add(new Message("subscription01","钱琪炜1".getBytes(StandardCharsets.UTF_8)));
        messages.add(new Message("subscription01","钱琪炜2".getBytes(StandardCharsets.UTF_8)));
        messages.add(new Message("subscription01","钱琪炜3".getBytes(StandardCharsets.UTF_8)));
        messages.add(new Message("subscription01","钱琪炜4".getBytes(StandardCharsets.UTF_8)));
        defaultMQProducer.send(messages);
        //关闭
        defaultMQProducer.shutdown();
    }
}
```



##### 二、Consumer

```java
public class Consumer {
    public static void main(String[] args) throws MQClientException {
        //初始化consumer
        DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer();
        //注册到注册中心
        pushConsumer.setNamesrvAddr("www.qianqiwei.com:9876");
        //设置分组
        pushConsumer.setConsumerGroup("ConsumerGroup01");
        //拉取消息 topic: 订阅消息的地址  subExpression: *表示不过滤消息
        pushConsumer.subscribe("subscription01", "*");
        //收取消息
        //默认情况下这个消息只会被一个消费者消费
        pushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt message : msgs) {
                    System.out.println(new String(message.getBody()));
                }
                //只要被消费了，其他人不能在消费(除了不同组之外)
                //ConsumeConcurrentlyStatus.CONSUME_SUCCESS:消费者成功的消费了,失败消费，ConsumeConcurrentlyStatus.RECONSUME_LATER:稍后尝试消费。
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });
        //启动consumer
        pushConsumer.start();

    }
}
```

> 注意:传输的消息大小控制在1m以内。

#### 三、异步传输消息

```java
public class Producer {
    public static void main(String[] args) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        DefaultMQProducer producer=new DefaultMQProducer();
        producer.setProducerGroup("ProducerGroup01");
        producer.setNamesrvAddr("www.qianqiwei.com:9876");
        producer.start();
        List<Message> messages=new ArrayList<>();
        messages.add(new Message("topic01","刘德华".getBytes(StandardCharsets.UTF_8)));
        messages.add(new Message("topic01","王二小".getBytes(StandardCharsets.UTF_8)));
        messages.add(new Message("topic01","张三".getBytes(StandardCharsets.UTF_8)));
        messages.add(new Message("topic01","李四".getBytes(StandardCharsets.UTF_8)));
        messages.add(new Message("topic01","王五".getBytes(StandardCharsets.UTF_8)));
        //异步发送消息
        producer.send(messages, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("消息发送成功");
            }
            @Override
            public void onException(Throwable e) {
                System.out.println("消息发送失败");
            }
        });
        System.out.println("发送消息中");
    }
}
```

> 注意:同步传输消息还得等broker确认消息之后才能执行下一步。异步相反。

#### 四、单项传输消息

```java
public class Producer {
    public static void main(String[] args) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        DefaultMQProducer producer=new DefaultMQProducer();
        producer.setProducerGroup("ProducerGroup01");
        producer.setNamesrvAddr("www.qianqiwei.com:9876");
        producer.start();
        List<Message> messages=new ArrayList<>();
        messages.add(new Message("topic01","刘德华".getBytes(StandardCharsets.UTF_8)));
        messages.add(new Message("topic01","王二小".getBytes(StandardCharsets.UTF_8)));
        messages.add(new Message("topic01","张三".getBytes(StandardCharsets.UTF_8)));
        messages.add(new Message("topic01","李四".getBytes(StandardCharsets.UTF_8)));
        messages.add(new Message("topic01","王五".getBytes(StandardCharsets.UTF_8)));
        //发送单向消息
        producer.sendOneway(new Message("topic01","王二小".getBytes(StandardCharsets.UTF_8)));
        System.out.println("发送消息成功");
    }
}
```

> 注意:单向消息并不需要broker接收确认,所以说容易丢数据。

#### 五、设置消费模式

> 1. 前言: producer推送消息给broker确认后，broker将消息和topic缓存到磁盘中,broker再发送到consumer中。
> 2. 集群模式:在集群模式中,broker会将消息投给多个不同的集群中(同一组)，当投递给某个服务,在规定时间之内没有被接受,就会随机从新投递给其他服务器，以此类推。直到被接受。在集群模式中只有一台服务消费完消息即可。
> 3. 广播模式: 在广播模式中,broker会将消息投给服务，每个服务都可以接受消息。但是不能保证所有消息都被接受(网路延迟等等)。也不会从新去投递。

```java
//集群消息
defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
//广播消息
defaultMQPushConsumer.setMessageModel(MessageModel.BROADCASTING);
```

#### 六、计划发送消息

##### 一、Producer

```java
  DefaultMQProducer producer = new Producer().createProducer("192.168.67.198:9876", "ProducerGroup01");
        String topic = "Topic-test";
        for (int i = 1; i <= 10; i++) {
            Message message = new Message();
            //这里是延迟级别。
            message.setDelayTimeLevel(2);
            message.setTopic(topic);
            message.setBody(("钱琪炜" + i).getBytes(StandardCharsets.UTF_8));
            producer.send(message);
            System.out.println("发送成功!");
        }
        producer.shutdown();
```

#### 七、消息过滤(SQL)

> 需要在 broker.conf配置文件中开启: enablePropertyFilter=true
>
> 启动需要此配置文件。

##### 一、Producer

```java
 public static void main(String[] args) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setProducerGroup("producerGroup01");
        producer.setNamesrvAddr("192.168.161.82:9876");
        producer.start();
        String Topic = "topic01";
        for (int i = 0; i < 1000; i++) {
            Message message = new Message(Topic, ("num:" + i).getBytes());
            //给message设置属性以后过滤。
            message.putUserProperty("num", String.valueOf(i));
            producer.send(message);
        }
        producer.shutdown();

    }
```

#####  二、producer

```java
 public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer01=new DefaultMQPushConsumer();
        consumer01.setConsumerGroup("consumerGroup01");
        consumer01.setNamesrvAddr("192.168.161.82:9876");

        String Topic="topic01";
        //过滤请求
        MessageSelector messageSelector = MessageSelector.bySql("num <=500");
        consumer01.subscribe(Topic,messageSelector);
        consumer01.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                list.forEach(item->{
                    System.out.println(new String(item.getBody()));
                });
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer01.start();



    }
```

#### 八、事务消息

##### 一、前置

> 1. producer发送半消息给broker  半消息指的是不能被consumer消费的消息。
> 2. borker接收到消息之后返回ACK
> 3. producer去执行本地的事务
>    1. 本地事务执行失败,或者某些原因需要等待造成的超时,没有及时的去发送状态给borker
>    2. broker就会进行回查,调用producer的本地方法。要求producer返回状态给broker。
> 4. 本地事务执行成功,或者失败,返回状态(commit,rollback)给broker,broker处理消息是否被发送。
>    1. commit:事务提交成功,broker将消息发送给consumer。
>    2. rollback:事务执行失败,broker销毁消息(也就是半消息)。

##### 二、producer

```java
public static void main(String[] args) throws MQClientException {
    TransactionMQProducer producer=new TransactionMQProducer();
    producer.setProducerGroup("producerGroup01");
    producer.setNamesrvAddr("192.168.1.6:9876");

    producer.setTransactionListener(new TransactionListener() {
        //broker接受到半消息后,调用这个方法。
        @Override
        public LocalTransactionState executeLocalTransaction(Message message, Object o) {
            System.out.println(message.getTags());
            //如果接受的是TAGA就返回commit给broker,并将消息发送给provider
            if ("TAGA".equals(message.getTags())){
                return LocalTransactionState.COMMIT_MESSAGE;
            }
            //如果接受的是TAGB就返回unknown给broker,broker进行回查
            if ("TAGB".equals(message.getTags())){
                try {
                    //一直不给broker发送状态,broker开启定时任务来回查消息状态
                    TimeUnit.SECONDS.sleep(100000000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return LocalTransactionState.COMMIT_MESSAGE;
            }
            //如果接受的是TAGC就返回rollback给broker,broker将删除半消息
            if ("TAGC".equals(message.getTags())){
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
            return null;
        }
        //broker回查provider事务状态
        @Override
        public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
            System.out.println(messageExt.getTags()+":"+"进行回查");
            if ("TAGB".equals(messageExt.getTags())){
                return LocalTransactionState.COMMIT_MESSAGE;
            }
            return null;
        }
    });
    //开启生产者
    producer.start();

    //分别演示不同的tag不同的发送结果
    String tags[]={"TAGA","TAGB","TAGC"};
    for (int i=0;i<tags.length;i++){
        //发送半消息给broker
        producer.sendMessageInTransaction(new Message("topic01",tags[i],("qianqiwei"+i).getBytes()),null);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
```

##### 三、consumer

```java
public static void main(String[] args) throws MQClientException {
    DefaultMQPushConsumer consumer=new DefaultMQPushConsumer();
    consumer.setConsumerGroup("consumerGroup01");
    consumer.setNamesrvAddr("192.168.1.6:9876");
    consumer.subscribe("topic01","*");
    consumer.registerMessageListener(new MessageListenerConcurrently() {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
            //事务消息成功的被消费了
            list.forEach(item->{
                System.out.println(item.getTags()+":"+new String(item.getBody()));
            });
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    });
    consumer.start();
}
```
