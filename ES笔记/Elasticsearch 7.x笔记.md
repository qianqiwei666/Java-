# 一、搭建

## 一、下载 Elasticsearch

https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-8.2.0-linux-x86_64.tar.gz

## 二、修改内存大小

> 1. 找到config目录下的jvm.options文件
> 2. 修改内存

## 三、Elasticsearch在Linux中用户问题

### 一、创建组

> groupadd es

### 二、创建用户

> useradd [用户名] -g es

### 三、设置用户密码

> passwd [用户名] [你的密码] 

> 注意:密码不能存在用户名,密码必须至少有一个大写字母！

### 四、设置组以及用户名到ES

>  chown -R [组名]:[用户名] [ES路径]   //例如:/opt/Elasticsearch/elasticsearch-8.2.0

### 五、切换用户名

> su [用户名]

### 六、删除用户

> userdel -r [用户名]

###  七、修改本地jdk

####  一、如果发生以下问题就要修改!

>  例如could not find java in bundled JDK at /opt/Elasticsearch/elasticsearch-8.2.0/jdk/bin/java

#### 二、在bin中找到elasticsearch-env

```bash
#添加这段话
ES_JAVA_HOME/opt/Elasticsearch/elasticsearch-8.2.0/jdk

if [ ! -z "$ES_JAVA_HOME" ]; then
  JAVA="$ES_JAVA_HOME/bin/java"
  JAVA_TYPE="ES_JAVA_HOME"
else
  # use the bundled JDK (default)
  if [ "$(uname -s)" = "Darwin" ]; then
    # macOS has a different structure
    JAVA="$ES_HOME/jdk.app/Contents/Home/bin/java"
  else
    JAVA="$ES_HOME/jdk/bin/java"
  fi
  JAVA_TYPE="bundled JDK"
fi
```

## 八、关于9200访问权限修改

### 一、文件

> 在config文件中找到elasticsearch.yml

### 二、修改

``` bash
#----------------------- BEGIN SECURITY AUTO CONFIGURATION -----------------------
#
# The following settings, TLS certificates, and keys have been automatically      
# generated to configure Elasticsearch security features on 08-05-2022 06:38:25
#
# --------------------------------------------------------------------------------

# Enable security features
# 找到这里修改为false
xpack.security.enabled: false

xpack.security.enrollment.enabled: true

# Enable encryption for HTTP API client connections, such as Kibana, Logstash, and Agents
xpack.security.http.ssl:
  enabled: true
  keystore.path: certs/http.p12

# Enable encryption and mutual authentication between cluster nodes
xpack.security.transport.ssl:
  enabled: true
  verification_mode: certificate
  keystore.path: certs/transport.p12
  truststore.path: certs/transport.p12
# Create a new cluster with the current node only
# Additional nodes can still join the cluster later
cluster.initial_master_nodes: ["QIANQIWEI"]

# Allow HTTP API connections from anywhere
# Connections are encrypted and require user authentication
http.host: 0.0.0.0

# Allow other nodes to join the cluster from anywhere
# Connections are encrypted and mutually authenticated
#transport.host: 0.0.0.0

#----------------------- END SECURITY AUTO CONFIGURATION -------------------------

```



# 二、概念

## 一、什么是结构化数据

### 一、结构化数据

> 比如名称对应年龄,性别,这样的数据一般保存在MySQL数据库中。
>
> 可以理解为以表格形式存储数据。key(结构)是固定的

### 二、非结构化数据

> 比如图片,报表等没有一定规律的数据。不能以二维的结构存储。
>
> key(结构)不是固定的,可以拓展。

### 三、半结构化数据

> 比如xml,html,保存各种各样的数据,一般存放到NoSQL中,以Key,value的形式存储。

## 二、什么是关系型数据库与非关系型数据库

### 一、关系型数据库

> 比如MySQL,SQLServer等,以表格存储数据
>
> 优点:存储格式一样(方便管理),多用于复杂查询。
>
> 缺点:I/O瓶颈,不能支持高并发。拓展性弱:字段都提前规定好了,不利于后期拓展。

### 二、非关系型数据库

> 比如redis,MongoDB,通常以key,value的形式存储数据。
>
> 优点:读写速度快,可以将内存作为储存载体,支持高并发。查询速度比较快。

## 三、理解ES

![1](D:\笔记\ES笔记\1.png)

```json
"ES":{
   "index":{
       "type1":{
           "Doc1":{
              "key1":"value1",
              "key2":"value2"
           },
            "Doc2":{
              "key1":"value1",
              "key2":"value2",
              "key3":"value3"
           }
       },
       "type2":{
           
       }
   },
   "index2":{
       "type1":{
           
       },
       "type2":{
           
       }
   } 
}
```

> ES慢慢的没有type的概念,以后添加数据就直接在index下添加(doc)。

## 四、索引

###  一、正排索引

> 通过key查询value,key来建立起索引,如果要查询value你得一个一个遍历key来查询value。

### 二、倒排索引

> 将value作为key,key作为value,value来建立起索引,你要查询数据直接通过value来查询。

# 三、入门

## 一、注意事项

> put:一般用于添加index
>
> delete:一般用于删除index
>
> get:一般用于查询index

## 二、创建index(put)

``` tex
http://127.0.0.1:9200/[index]
```

> 提示:如果index相同返回400提示当前存在此index。并且不会覆盖原有的index。

## 三、获取index(get)

``` tex
http://127.0.0.1:9200/[index]
```

### 一、响应:

```json
{
    "index02": {
        "aliases": {},
        "mappings": {},
        "settings": {
            "index": {
                "routing": {
                    "allocation": {
                        "include": {
                            "_tier_preference": "data_content"
                        }
                    }
                },
                "number_of_shards": "1",
                "provided_name": "index02",
                "creation_date": "1652009223907",
                "number_of_replicas": "1",
                "uuid": "BK9q6jVtRXymoRZgX6gNeQ",
                "version": {
                    "created": "8020099"
                }
            }
        }
    }
}
```

## 四、展示index中所有的信息(get)

``` tex
http://127.0.0.1:9200/_cat/indices?v
```

### 一、响应

``` apl
health status index   uuid                   pri rep docs.count docs.deleted store.size pri.store.size
yellow open   index02 BK9q6jVtRXymoRZgX6gNeQ   1   1          0            0       225b           225b
yellow open   index01 SHe-tFcWR-CiV5XDtk1wqA   1   1          0            0       225b           225b

```

## 五、删除index(delete)

``` tex
http://127.0.0.1:9200/[index]
```

### 一、示例

``` apl
{"acknowledged":true}
```

## 六、在index下添加数据(post)

```tex
http://127.0.0.1:9200/[index]/_doc
```

###  一、示例:

> 请求体必须以json的格式发送。并且可以重复插入(注意)。

![2](D:\笔记\ES笔记\2.png)

### 二、响应

``` json
{
    "_index": "index01", //index
    "_id": "-SKEo4ABT7MPlrC1NvrM", //唯一docid
    "_version": 1,
    "result": "created",
    "_shards": {
        "total": 2,
        "successful": 1,
        "failed": 0
    },
    "_seq_no": 0, //index中存在的条数。
    "_primary_term": 1
}
```



## 七、在index下添加数据使用自定义的id(post,put)

```tex
http://127.0.0.1:9200/[index]/_doc/[id]
```

###  一、示例:

> 在同一个id中创建多条数据,并不会起冲突,也不会覆盖,只会增加version(版本号)。

![3](D:\笔记\ES笔记\3.png)

### 二、响应

``` json
{
    "_index": "index01",
    "_id": "1", //自定义的id
    "_version": 1, //版本号,修改或者插入了,通过id记录
    "result": "created",
    "_shards": {
        "total": 2,
        "successful": 1,
        "failed": 0
    },
    "_seq_no": 16, //index下有多少条数据。
    "_primary_term": 1
}
```

## 八、获取index下指定id的值(get)

```tex
http://127.0.0.1:9200/[index]/_doc/[id]
```

###  一、响应:

``` json
{
    "_index": "index01",
    "_id": "1001",
    "_version": 8,
    "_seq_no": 15,
    "_primary_term": 1,
    "found": true, //是否查找到了
    "_source": {
        "username": "qianqiwei",
        "password": "qianqiwei",
        "address": "湖北宜昌",
        "age": 19
    }
}
```

## 九、查询index下所有的数据(get)

```tex
http://127.0.0.1:9200/[index]/_search
```

###  一、示例:

```json
{
    "took": 383,
    "timed_out": false,
    "_shards": {
        "total": 1,
        "successful": 1,
        "skipped": 0,
        "failed": 0
    },
    "hits": {
        "total": {
            "value": 10,
            "relation": "eq"
        },
        "max_score": 1.0,
        "hits": [
            {
                "_index": "index01",
                "_id": "-SKEo4ABT7MPlrC1NvrM",
                "_score": 1.0,
                "_source": {
                    "username": "qianqiwei",
                    "password": "qianqiwei",
                    "address": "湖北宜昌",
                    "age": 19
                }
            },
            {
                "_index": "index01",
                "_id": "-iKHo4ABT7MPlrC13Pom",
                "_score": 1.0,
                "_source": {
                    "username": "qianqiwei",
                    "password": "qianqiwei",
                    "address": "湖北宜昌",
                    "age": 19
                }
            }
            }
        ]
    }
}
```

## 十、修改index下指定id的数据(put)(全量)

```tex
http://127.0.0.1:9200/[index]/_doc/[id]
```

###  一、示例:

![4](D:\笔记\ES笔记\4.png)

### 三、响应:

```json
{
    "_index": "index01",
    "_id": "1",   
    "_version": 13,
    "result": "updated", //操作
    "_shards": {
        "total": 2,
        "successful": 1, //成功操作个数
        "failed": 0
    },
    "_seq_no": 40,
    "_primary_term": 1
}
```

## 十一、修改index下指定id数据(post)(局部)

``` tex
http://127.0.0.1:9200/[index]/_update/[id]
```

###  一、示例:

![5](D:\笔记\ES笔记\5.png)

### 二、响应

``` json
{
    "_index": "index01",
    "_id": "1",
    "_version": 15,
    "result": "noop",
    "_shards": {
        "total": 0,
        "successful": 0,
        "failed": 0
    },
    "_seq_no": 42,
    "_primary_term": 1
}
```

## 十二、在index下删除数据指定Doc(delete)

```tex
http://127.0.0.1:9200/[index]/_doc/[id]
```

###  一、响应:

```json
{
    "_index": "index01",
    "_id": "2",
    "_version": 4,
    "result": "deleted", //当前状态,删除不存在的数据not_found
    "_shards": {
        "total": 2,
        "successful": 1,
        "failed": 0
    },
    "_seq_no": 52,
    "_primary_term": 1
}
```

## 十三、条件查询(get) 不推荐

```tex
http://127.0.0.1:9200/[index]/_search?q=[key:value]  //这里的value是模糊查询
```

###  一、响应:

```json
{
    "took": 2,
    "timed_out": false,
    "_shards": {
        "total": 1,
        "successful": 1,
        "skipped": 0,
        "failed": 0
    },
    "hits": {
        "total": {
            "value": 1,
            "relation": "eq"
        },
        "max_score": 1.6562483,
        "hits": [
            {
                "_index": "index01",
                "_id": "2",
                "_score": 1.6562483,
                "_source": {
                    "username": "钱琪炜",
                    "address": "宜昌"
                }
            }
        ]
    }
}
```



## 十四、条件查询(post)

```tex
http://127.0.0.1:9200/index01/_search
```

###  一、示例:

![6](D:\笔记\ES笔记\6.png)

### 二、响应

```json
{
    "took": 1,
    "timed_out": false,
    "_shards": {
        "total": 1,
        "successful": 1,
        "skipped": 0,
        "failed": 0
    },
    "hits": {
        "total": {
            "value": 1,
            "relation": "eq"
        },
        "max_score": 1.3950763,
        "hits": [
            {
                "_index": "index01",
                "_id": "4",
                "_score": 1.3950763,
                "_source": {
                    "username": "李白",
                    "address": "武陵"
                }
            }
        ]
    }
}
```

## 十五、分页查询(post)

> 查询公式:(页码-1)*每页查询的条数。
>
> form=(页码-1)*size

```tex
http://127.0.0.1:9200/index01/_search
```

###  一、示例:

![7](D:\笔记\ES笔记\7.png)

```json
{
  "query":{
      //查询所有的数据
      "match_all":{
         
      }
  },
    //从那条数据开始查询
   "from":0,
    //一次性查多少条
   "size":2
}
```

### 二、响应

```json
{
    "took": 1,
    "timed_out": false,
    "_shards": {
        "total": 1,
        "successful": 1,
        "skipped": 0,
        "failed": 0
    },
    "hits": {
        "total": {
            "value": 5, //一共多少条
            "relation": "eq"
        },
        "max_score": 1.0,
        "hits": [
            {
                "_index": "index01",
                "_id": "1",
                "_score": 1.0,
                "_source": {
                    "username": "刘德华",
                    "address": "香港"
                }
            },
            {
                "_index": "index01",
                "_id": "2",
                "_score": 1.0,
                "_source": {
                    "username": "钱琪炜",
                    "address": "宜昌"
                }
            }
        ]
    }
}
```

## 十六、通过条件查询分页(post)

```tex
http://127.0.0.1:9200/index01/_search
```

###  一、示例:

![8](D:\笔记\ES笔记\8.png)

```json
{
  "query":{
      "match":{
          //通过字段某一个值就可以查询。
         "username":"琪人"
      }
  },
   "from":0,
   "size":2
}
```

### 二、响应

```json
{
    "took": 2,
    "timed_out": false,
    "_shards": {
        "total": 1,
        "successful": 1,
        "skipped": 0,
        "failed": 0
    },
    "hits": {
        "total": {
            "value": 1, //查询一共有多少个值。
            "relation": "eq"
        },
        "max_score": 1.6562483,
        "hits": [
            {
                "_index": "index01",
                "_id": "2",
                "_score": 1.6562483,
                "_source": {
                    "username": "钱琪炜",
                    "address": "宜昌"
                }
            }
        ]
    }
}
```

## 十七、分页指定显示数据(post)

```tex
http://127.0.0.1:9200/index01/_search
```

###  一、示例:

![9](D:\笔记\ES笔记\9.png)

```json
{
  "query":{
      "match_all":{
        
      }
  },
   "from":0,
   "size":3,
   "_source":[展示数据数组(keys)]
}
```

### 二、响应

```json
{
    "took": 1,
    "timed_out": false,
    "_shards": {
        "total": 1,
        "successful": 1,
        "skipped": 0,
        "failed": 0
    },
    "hits": {
        "total": {
            "value": 5,
            "relation": "eq"
        },
        "max_score": 1.0,
        "hits": [
            {
                "_index": "index01",
                "_id": "1",
                "_score": 1.0,
                "_source": {
                    "address": "香港",
                    "username": "刘德华"
                }
            },
            {
                "_index": "index01",
                "_id": "2",
                "_score": 1.0,
                "_source": {
                    "address": "宜昌",
                    "username": "钱琪炜"
                }
            },
            {
                "_index": "index01",
                "_id": "3",
                "_score": 1.0,
                "_source": {
                    "address": "武陵",
                    "username": "王安石"
                }
            }
        ]
    }
}
```

## 十八、分页排序(post)

```tex
http://127.0.0.1:9200/index01/_search
```

###  一、示例:

```json
{
  "query":{
      "match_all":{
        
      }
  },
   "from":0,
   "size":3,
   "_source":[展示数据数组(keys)],
   "sort":{
       [需要排序的key]:{
           "order":[升序:"asc",降序:"desc"]
       }
   }
}
```

## 十九、多条件查询(post)

```tex
http://127.0.0.1:9200/index01/_search
```

###  一、示例:

![10](D:\笔记\ES笔记\10.png)

```json
{
  "query":{
      "bool":{
          //必须满足一下条件(and),也可以是should(or)只需满足一个条件即可。
          "must":[
              //每一个match对应一个条件
              {
                  "match":{
                      key:value //条件一
                  }
              },
              {
                   "match":{
                      key:value //条件二
                  }
              }
          ]
      }
  }
  
}
```

### 二、响应:

```json
{
    "took": 10,
    "timed_out": false,
    "_shards": {
        "total": 1,
        "successful": 1,
        "skipped": 0,
        "failed": 0
    },
    "hits": {
        "total": {
            "value": 1,
            "relation": "eq"
        },
        "max_score": 3.4480078,
        "hits": [
            {
                "_index": "index01",
                "_id": "2",
                "_score": 3.4480078,
                "_source": {
                    "username": "钱琪炜",
                    "address": "宜昌"
                }
            }
        ]
    }
}
```

## 二十、查询范围进行操作(post)

```tex
http://127.0.0.1:9200/index01/_search
```

###  一、示例:

```json
{
  "query":{
      "bool":{
          //必须满足一下条件(and),也可以是should(or)只需满足一个条件即可。
          "must":[
              //每一个match对应一个条件
              {
                  "match":{
                      key:value //条件一
                  }
              },
              {
                   "match":{
                      key:value //条件二
                  }
              }
          ],
          "filter":{
              //范围过滤
              "range":{
                  //key条件
                  [key]:{
                      "gt":[大于数量],
                      "lt":[小于数量]
                  }
              }
          }
      }
  }
  
}
```

## 二十一、连续字段查询(post)

```tex
http://127.0.0.1:9200/index01/_search
```

###  一、示例:

![11](D:\笔记\ES笔记\11.png)

```json
{
  "query":{
      //匹配当前连续字段是否存在
     "match_phrase":{
         [key:value]
     }
  }
  
}
```

### 二、响应:

```json
{
    "took": 1,
    "timed_out": false,
    "_shards": {
        "total": 1,
        "successful": 1,
        "skipped": 0,
        "failed": 0
    },
    "hits": {
        "total": {
            "value": 0,
            "relation": "eq"
        },
        "max_score": null,
        "hits": []
    }
}
```

## 二十二、特殊字段高亮展示(post)

```tex
http://127.0.0.1:9200/index01/_search
```

###  一、示例:

![12](D:\笔记\ES笔记\12.png)

```json
{
  "query":{
    "match":{
        key:value
    }
  },
  "highlight":{
      //需要高亮的字段
      "fields":[{
           [key:value]
      },{
          [key:value]
      }]
  }
  
}
```

### 二、响应:

```json
{
    "took": 3,
    "timed_out": false,
    "_shards": {
        "total": 1,
        "successful": 1,
        "skipped": 0,
        "failed": 0
    },
    "hits": {
        "total": {
            "value": 2,
            "relation": "eq"
        },
        "max_score": 1.6562483,
        "hits": [
            {
                "_index": "index01",
                "_id": "2",
                "_score": 1.6562483,
                "_source": {
                    "username": "钱琪炜",
                    "address": "宜昌"
                },
                "highlight": {
                    //高亮
                    "username": [
                        "<em>钱</em>琪炜"
                    ]
                }
            },
            {
                "_index": "index01",
                "_id": "4",
                "_score": 1.3950763,
                "_source": {
                    "username": "李白",
                    "address": "武陵"
                },
                "highlight": {
                    "username": [
                        "李<em>白</em>"
                    ]
                }
            }
        ]
    }
}
```

## 二十三、聚合查询

```tex
http://127.0.0.1:9200/index01/_search
```

###  一、示例:

![13](D:\笔记\ES笔记\13.png)

```json
{
 "aggs":{
     "username_group":{
         "terms":{
             //分组属性
             "field":"username.keyword"
         }
         //可以在此添加分组数据比如("avg","sum"...)
     }
 },
 "size":0
}
```

### 二、响应:

```json
{
    "took": 19,
    "timed_out": false,
    "_shards": {
        "total": 1,
        "successful": 1,
        "skipped": 0,
        "failed": 0
    },
    "hits": {
        "total": {
            "value": 5,
            "relation": "eq"
        },
        "max_score": null,
        "hits": []
    },
    "aggregations": {
        "username_group": {
            "doc_count_error_upper_bound": 0,
            "sum_other_doc_count": 0,
            "buckets": [
                {
                    "key": "刘德华",
                    "doc_count": 1
                },
                {
                    "key": "李白",
                    "doc_count": 1
                },
                {
                    "key": "王五",
                    "doc_count": 1
                },
                {
                    "key": "王安石",
                    "doc_count": 1
                },
                {
                    "key": "钱琪炜",
                    "doc_count": 1
                }
            ]
        }
    }
}
```

# 四、Java Api使用

##  一、开始

### 一、导入依赖

```xml
 <dependencies>
        <!-- https://mvnrepository.com/artifact/org.elasticsearch/elasticsearch -->
        <dependency>
            <groupId>org.elasticsearch</groupId>
            <artifactId>elasticsearch</artifactId>
            <version>7.17.3</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.elasticsearch.client/elasticsearch-rest-high-level-client -->
        <dependency>
            <groupId>org.elasticsearch.client</groupId>
            <artifactId>elasticsearch-rest-high-level-client</artifactId>
            <version>7.17.3</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/log4j/log4j -->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core -->
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-core</artifactId>
            <version>2.17.1</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-api -->
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-api</artifactId>
            <version>2.17.1</version>
        </dependency>
    </dependencies>

```

### 二、配置log4j.properties

```properties
log4j.rootLogger=debug, stdout, R

log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout

# Pattern to output the caller's file name and line number.
log4j.appender.stdout.layout.ConversionPattern=%5p [%t] (%F:%L) - %m%n

log4j.appender.R=org.apache.log4j.RollingFileAppender
log4j.appender.R.File=example.log

log4j.appender.R.MaxFileSize=100KB
# Keep one backup file
log4j.appender.R.MaxBackupIndex=5

log4j.appender.R.layout=org.apache.log4j.PatternLayout
log4j.appender.R.layout.ConversionPattern=%p %t %c - %m%n
```



