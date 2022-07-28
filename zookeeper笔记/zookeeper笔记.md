# 一、前置

## 一、关系

### 一、主从关系

> 一主多从。

> 从:客户端如果要去zookeeper中查询数据,就会从Follower(从)中获取数据
>
> 主:客户端要去进行写操作的时候,将数据通过Follower交给Leader去进行写入,然后同步到Follower。

注意:

> 只有Leader才能进行写入。

### 二、节点

> 临时节点: 客户端与服务端链接的时候会建立一个会话(session),会话之间会上锁(分布式锁)。会话结束链接断开,锁释放,随之节点被释放。
>
> 永久节点:持久化到硬盘上。
>
> 序列节点:未写完

## 二、Zookeeper如何保证高可用

### 一、状态(2种)

> 可用状态: 正常运行。
>
> 不可用状态: Leader挂掉之后。

### 二、自我修复(paxos算法)

> 当Leader突然挂掉之后,会很快的从集群中选取新的Leader。
>
> 在选举的过程中不会对外提供数据,直到选举出新的Leader,同步完成数据之后,在对外提供服务。
>
> 其中Follower来进行选举(不包括observer角色节点)。如果(集群总数/2+1)台Follower同时选举领导者,选举成功。Leader诞生!

## 三、怎么当分布式锁

> 

## 四、特点

### 一、读取速度

> Follower越多读取速度就越快。

### 二、节点存储

> 每个节点只能存储1m的数据。

### 三、保证

> 数据的一致性:主Follower能同时保证所有数据同步。
>
> 顺序一致性:由于写操作是Leader来完成的(单个保证线程安全),当写入数据的时候会保证数据的顺序。
>
> 原子性: 数据读写要么成功要么失败。当Follower向Leader传输数据的时候,Leader挂了,就会回滚。删除数据。
>
> 统一视图:客户端无论从哪个节点获取数据，都可以获取到。同时也会保存session。
>
> 可靠性:将日志记录到磁盘里。
>
> 及时性:能保证所有的节点能够及时的同步。

### 四、角色

> Leader:只能写入数据,保证数据的顺序性,及时性,原子性,还能保证数据的一致性。
>
> Follower:只能读取数据(速度极快),当Leader挂掉之后,Follower可以进行选举产生新的Leader
>
> Observer: 只能进行读,不能参与选举。



## 五、主从执行流程

> 写操作:
>
> 1. client链接zookeeper集群中的某一个Follower
> 2. Follower将写操作交给Leader
> 3. Leader会保存一个事务id
> 4. Leader会维护一个队列,将日志写入到所有的Follower中。
> 5. Follower会将自己的状态通过队列会送到Leader里(一半以上即可)
> 6. Leader会将写入的数据通过队列写到所有Follower内存中。
> 7. Follower会返回状态到客户端。
> 8. 客户端会进行回调



## 六、2PC

> 当Leader进行写操作的时候,首先是向Follower发送日志,当Follower返回状态给Leader时候,Leader才会将数据写入到Follower里。这叫二阶段提交。

## 七、Watch监控

> 当zookeeper中的数据发生改变就会回调到watch函数里。

## 八、注意

> 不要把zookeeper当数据库用。

# 二、安装zookeeper

## 一、下载安装包

> 从官网下载安装包,解压到/opt目录。

## 二、配置配置文件

> conf目录下替换zoo_sample.cfg为zoo.cfg

### 一、配置文件详情

``` yaml
# The number of milliseconds of each tick
tickTime=2000  #心跳时间(毫秒) 查看客户端是否连接超时或者宕机。
# The number of ticks that the initial
# synchronization phase can take
initLimit=10   #主从数据传递忍耐延迟(tickTime)*(initLimit*1000)毫秒
# The number of ticks that can pass between
# sending a request and getting an acknowledgement
syncLimit=5    #异步同步忍耐延迟(tickTime)*(syncLimit*1000)毫秒
# the directory where the snapshot is stored.
# do not use /tmp for storage, /tmp here is just
# example sakes.
dataDir=/tmp/zookeeper   #持久化内存目录
# the port at which the clients will connect
clientPort=2181      #端口号
# the maximum number of client connections.
# increase this if you need to handle more clients
#maxClientCnxns=60
#
# Be sure to read the maintenance section of the
# administrator guide before turning on autopurge.
#
# https://zookeeper.apache.org/doc/current/zookeeperAdmin.html#sc_maintenance
#
# The number of snapshots to retain in dataDir
#autopurge.snapRetainCount=3
# Purge task interval in hours
# Set to "0" to disable auto purge feature
#autopurge.purgeInterval=1

## Metrics Providers
#
```

### 二、配置集群

> 在配置文件中添加如下

> 端口号:
>
> 2888:当当前集群中没有Leader,通过这个端口进行通信投票选举出Leader。
>
> 3888:主Follower之间通信端口。

``` yaml
server.1=node01:2888:3888
server.2=node02:2888:3888
server.3=node03:2888:3888
server.4=node04:2888:3888
server.5=node05:2888:3888
```

> 还可以配置2888：3888、2889：3889、2890：3890端口号。

> 在持久化目录下创建文件myid并且存放Leaderid (dataDir目录下)

### 三、启动

> ./zkServer.sh  start-foreground

## 三、使用

#### 一、启动脚手架

> ./zkCli.sh

#### 二、命令使用

> 创建节点: create [节点状态] [节点] [数据]   //节点状态: 1 
>
> 查询节点子节点: ls [父节点]
>
> 设置节点数据: set [节点] [数据]
>
> 获取节点数据: get[节点]
>
> 删除尾节点: delete [尾节点]
>
> 删除根节点: deleteall[根节点]

注意:

> 1.  -e 临时节点,会话结束数据消失。否则会持久化。当其他节点访问此数据可以访问到。会话消失访问不到
>
>    如果中途某一Follower节点宕机了,切换到另外一台Follower,会话也不会丢失,临时节点下不允许有字节点。
>
>    原因:集群自动保存session并且统一视图。
>
>    -s  当创建节点的时候后面会自动跟上一串数字,为了防止重复插入,不会覆盖创建。
>
>    在分布式下能解决命名重复的问题。



# 三、Java使用zookeeper

## 一、Api使用

### 一、zk创建

```java
ZooKeeper zooKeeper=new ZooKeeper("1","2","3");
```

> 创建ZooKeeper对象的时候要传入三个参数:
>
> 1. 链接地址:如果是多个地址,就会随机选取一个地址进行链接。
> 2. 过期时间:如果客户端多少(毫秒)之内无法进行通讯,自动断开会话。数据在过期时间之后消失。
> 3. 观察链接状态(只会调用一次)。

```java
public class Demo {

    public static void main(String[] args) throws Exception {
        //创建zookeeper
        ZooKeeper zookeeper = createZookeeper(5000);
        if (zookeeper!=null) System.out.println("链接成功!");

    }

    static public ZooKeeper createZookeeper(int timeout){
        //当服务链接成功之后才进行下一步。
        CountDownLatch countDownLatch=new CountDownLatch(1);
        //这里的watch只能收到关于连接的回调。
        //并且watch是异步的。
        Watcher watcher=event -> {
            //获取client链接之后回调状态。
            Watcher.Event.KeeperState state = event.getState();
            if (state== Watcher.Event.KeeperState.SyncConnected){
                System.out.println("服务链接成功!");
                countDownLatch.countDown();
            }else if (state== Watcher.Event.KeeperState.Disconnected){
                System.out.println("会话结束!");
            }

        };
        ZooKeeper zooKeeper= null;
        try {
            zooKeeper = new ZooKeeper("www.qianqiwei.com:2181",timeout,watcher);
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return zooKeeper;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return zooKeeper;
    }
    

}
```

### 二、创建节点

#### 一、同步创建节点

```java
zookeeper.create(1, 2, 3, 4, 5, 6);
```

##### 示例

```java
String str= zookeeper.create("/user", "钱琪炜".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
```

#### 二、异步创建节点

```java
zookeeper.create("/user", "qianqiwei".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new AsyncCallback.StringCallback() {
    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        System.out.println("路径名称"+path);
    }
}, stat1);
```

#### 三、参数详解

> 1. 创建的节点。
> 2. 创建数据的字符数组
> 3. ACL角色
> 4. 创建方式
> 5. 异步回调函数
> 6. 节点的状态(回调之后)

### 三、节点事件

#### 一、获取节点

```java
zookeeper.getData(1,2,3,4,5)
```

##### 一、参数详情

> 1. 要获取的路径
> 2. 事件监听(监听一次)后面有解决方案
> 3. 数据回调函数(可选)
> 4. 节点状态,watch回调之后自动填充state

#### 二、修改节点

``` java
zookeeper.setData(1,2,3)
```

##### 一、参数详情

> 1. 修改的路径
> 2. 新的数据
> 3. 版本号

#### 三、示例

```java
//创建zookeeper
ZooKeeper zookeeper = createZookeeper(5000);
if (zookeeper != null) System.out.println("链接成功!");
Stat stat1=new Stat();
zookeeper.create("/user", "qianqiwei".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new AsyncCallback.StringCallback() {
    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        System.out.println("路径名称"+path);
    }
}, stat1);

//创建线程获取节点事件。
new Thread(() -> {
    Stat stat = new Stat();
    try {
        zookeeper.getData("/user", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                //由于节点获取是一次性的,如下是解决方案！
                zookeeper.getData("/user", this, new AsyncCallback.DataCallback() {
                    @Override
                    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                        System.out.println("路径"+path+"数据:"+new String(data));
                    }
                }, stat);
            }
        }, stat);
    } catch (KeeperException e) {
        e.printStackTrace();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

}).start();


//开启一个新的线程去不断修改节点
new Thread(() -> {
    try {
        int version = 0;
        while (true) {
            try {
                Thread.sleep(1000);
                //版本号必须获取上一个版本号才行
                Stat stat = zookeeper.setData("/user", "刘德华".getBytes(StandardCharsets.UTF_8), version);
                version = stat.getVersion();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    } catch (KeeperException e) {
        e.printStackTrace();
    }
}).start();


while (true) {
    if (zookeeper.getState() == ZooKeeper.States.CLOSED) {
        System.out.println("关闭");
        break;
    }
}
```



### 四、注意

> 1. 监听器:如果节点发生变化或者链接发生变化才能监听得到,说白了change即watch
> 2. callback:只要调用了callback方法就是异步的。

## 二、工具类

> 压缩包看Zookeeper_01.7z



# 四、分布式锁

## 一、规则

> 1. 当我们以SEQUENTIAL创建节点的时候。节点后面会跟上一串数字,从小到大标记。序号最小的优先获取锁。
> 2. 序列号不是最小的节点,就会监听前一个比自己序列号小的节点。
> 3. 如果前一个节点突然消失,当前节点就会被唤醒,首先判断自己是否是最小的,如果是就获取锁,如果不是监听比自己小的。
