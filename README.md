# springboot hbase-phoenix-webapp

本文使用 [mahua](http://mahua.jser.me/) 编辑

## [phoenix](https://phoenix.apache.org/) 是什么?

![phoenix](https://phoenix.apache.org/images/phoenix-logo-small.png)

OLTP and operational analytics for Apache Hadoop.

## 为 hpdb (hbase phoenix database) 数据库提供 java web 接口

* 通过 web 访问 hpdb
* 提供CRUD的RESTful API
* 充分利用 hbase 的高并发、高可用、分布式、无限可扩充的优点
* 充分利用 phoenix 二级索引的高效，在线实时事务处理功能

## 有问题反馈

在使用中有任何问题，欢迎反馈给我，可以用以下联系方式跟我交流

* 邮件: 350137278@qq.com
* QQ: 350137278
* blog: [CSDN 博客](https://blog.csdn.net/ubuntu64fan)

## 感激以下的项目

排名不分先后

* [最初源码来自于 CSDN](https://download.csdn.net/download/hanjiangdudiao11/10746711)
* https://github.com/tspannhw/phoenix/blob/master/pom.xml
* [为开发目的安装单机版的 hbase + phoenix](https://blog.csdn.net/ubuntu64fan/article/details/88063038)
* https://www.cnblogs.com/sweetchildomine/p/9690106.html
* https://springframework.guru/configuring-spring-boot-for-mysql/

## 关于作者

无

## 准备 hbase phoenix 表

```
phoenix 命令行创建一个测试表: ORG_DEPT_NC

   (sqlline.py zkhost:zkport)
   
   sqlline.py localhost:2182
   
   > CREATE TABLE IF NOT EXISTS ORG_DEPT_NC (ID VARCHAR(30) NOT NULL, NAME VARCHAR(30), CONSTRAINT PK PRIMARY KEY (ID));

   > upsert into org_dept_nc(id,name) values('zhang', 'software team');

   > upsert into org_dept_nc(id,name) values('wang', '测试组');
```

## 编译

    # mvn clean compile package
    
## 运行

    # mvn spring-boot:run

## 浏览器访问

    http://code.ztgame.com:8088/hbase/query

    http://code.ztgame.com:8088/hbase/count

    http://code.ztgame.com:8088/hbase/add
    
可以看到json 格式的返回信息！
