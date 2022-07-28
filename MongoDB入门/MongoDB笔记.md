

# 一、安装

## 一、Windows下安装

### 一、安装及启动

> 1. 当前目录有安装包。记得安装完成之后配置环境变量。
> 2. 在C盘根目录创建文件data/db
> 3. 安装完成之后,一定要启动服务。cmd命令mongod

### 二、更改db文件路径

> 1. 启动时: mongod --dbpath [data路径]--port [自定义端口号]

## 二、Linux下安装

> 1. 官方下载MongoDB tar包
>
> 2. 解压文件,在mongoDB文件夹中创建mongodb.conf文件。
>
> 3. 在mongodb.conf文件输入以下内容,并创建dbpath目录或文件logpath目录或文件,。
>
>    ```yaml
>    dbpath=[数据库路径]
>    logpath=[数据库日志路径]
>    port=[端口号]   
>    fork=true    #是否后台运行?
>    journal=false
>    ```
>
> 4. 在bin目录下启动mongodb:  ./mongod  --config [配置文件]

# 二、概念

> 1. database:相当于MySQL的库。
> 2. collection:相当于MySQL表。
> 3. document:相当与表中的字段以及数据。

# 三、操作文档

[操作手册(中文)](https://mongodb.net.cn/manual/introduction/)





