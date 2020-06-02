---
layout: post
category: java
title: 被缠上了，小王问我怎么在 Spring Boot 中使用 JDBC 连接 MySQL
tagline: by 沉默王二
tags: 
  - java
---

上次帮小王[入了 Spring Boot 的门](https://mp.weixin.qq.com/s/Ra9zotaUTsm3z-UMimB5pg)后，他觉得我这个人和蔼可亲、平易近人，于是隔天小王又微信我说：“二哥，快教教我，怎么在 Spring Boot  项目中使用 JDBC 连接 MySQL 啊？”

<!--more-->

收到问题的时候，我有点头大，难道以后就要被小王缠上了？


![](http://www.itwanger.com/assets/images/2020/06/java-springboot-jdbc-mysql-01.png)

没等我发牢骚，小王就紧接着说：“二哥，你先别生气，上次你帮了我的忙后，我在心里感激了你一晚上，想着第一次遇到这么亲切的大佬，一定要抱紧大腿。。。。。”

马屁拍到这份上，我的气自然也就消了。随后，我花了五分钟的时间帮他解决了苦恼，没成想，他又发给我了一个小红包，表示对我的感谢。并建议我再写一篇文章出来，因为他觉得像他这样的小白还有很多。没办法，我关上门，开了灯，开始了今天这篇文章的创作。

### 01、初始化 MySQL 数据库

既然要连接 MySQL，那么就需要先在电脑上安装 MySQL 服务（本文暂且跳过），并且创建数据库和表。

```sql
CREATE DATABASE `springbootdemo`;
DROP TABLE IF EXISTS `mysql_datasource`;
CREATE TABLE `mysql_datasource` (
  `id` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```


### 02、使用 Spring Initlallzr 创建 Spring Boot 项目

创建一个 Spring Boot 项目非常简单，通过 Spring Initlallzr（[https://start.spring.io/](https://start.spring.io/)）就可以了。

![](http://www.itwanger.com/assets/images/2020/06/java-springboot-jdbc-mysql-02.png)



勾选 Lombok、Web、MySQL Driver、Actuator、JDBC 等五个依赖。

1）Lombok 是一种 Java 实用工具，可用来帮助开发人员消除 Java 的一些冗余代码，比如说可以通过注解生成 getter/setter。使用之前需要先在 IDE 中安装插件。

2）Web 表明该项目是一个 Web 项目，便于我们直接通过 URL 来实操。

3）MySQL Driver：连接 MySQL 服务器的驱动器。

4）Actuator 是 Spring Boot 提供的对应用系统的自省和监控的集成功能，可以查看应用配置的详细信息，例如自动化配置信息、创建的 Spring beans 以及一些环境属性等。

5）JDBC：本篇文章我们通过 JDBC 来连接和操作数据库。

选项选择完后，就可以点击【Generate】按钮生成一个初始化的 Spring Boot 项目了。生成的是一个压缩包，导入到 IDE 的时候需要先解压。

### 03、编辑 application.properties 文件

项目导入成功后，等待 Maven 下载依赖，完成后编辑 application.properties 文件，配置 MySQL 数据源信息。

```
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/springbootdemo
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```

1）`spring.datasource.` 为固定格式。

2）URL 为 MySQL 的连接地址。

3）username 为数据库的访问用户名。

4）password 为数据库的访问密码。

5）driver-class-name 用来指定数据库的驱动器。也可以不指定，Spring Boot 会根据 URL（有 mysql 关键字） 自动匹配驱动器。

### 04、编辑 Spring Boot 项目

为了便于我们操作，我们对 SpringBootMysqlApplication 类进行编辑，增加以下内容。

```java
@SpringBootApplication
@RestController
public class SpringBootMysqlApplication {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("insert")
    public String insert() {
        String id = UUID.randomUUID().toString();
        String sql = "insert into mysql_datasource (id,name) values ('"+id+"','沉默王二')";
        jdbcTemplate.execute(sql);
        return "插入完毕";
    }

}
```



1）@SpringBootApplication、@RestController、@RequestMapping 注解在[之前的文章]()中已经介绍过了，这里不再赘述。

2）@Autowired：顾名思义，用于自动装配 Java Bean。

3）JdbcTemplate：Spring 对数据库的操作在 jdbc 上做了深层次的封装，利用 Spring 的注入功能可以把 DataSource 注册到 JdbcTemplate 之中。JdbcTemplate 提供了四个常用的方法。

①、execute() 方法：用于执行任何 SQL 语句。

②、update() 方法：用于执行新增、修改、删除等 SQL 语句。

③、query() 方法：用于执行查询相关 SQL 语句。

④、call() 方法：用于执行存储过程、函数相关 SQL 语句。

本例中我们使用 execute() 方法向 mysql_datasource 表中插入一行数据 `{id:uuid, name:'沉默王二'}`。

### 05、运行 Spring Boot 项目

接下来，我们直接运行 `SpringBootMysqlApplication` 类，这样一个 Spring Boot 项目就启动成功了。

![](http://www.itwanger.com/assets/images/2020/06/java-springboot-jdbc-mysql-03.png)


这时候，我们可以直接浏览器的 URL 中键入 `http://localhost:8080/insert` 测试 MySQL 的插入语句是否执行成功。很遗憾，竟然出错了。


![](http://www.itwanger.com/assets/images/2020/06/java-springboot-jdbc-mysql-04.png)

该怎么办呢？这需要我们在连接字符串中显式指定时区，修改 spring.datasource.url 为以下内容。

```
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/springbootdemo?serverTimezone=UTC
```

重新运行该项目后再次访问，发现数据插入成功了。

![](http://www.itwanger.com/assets/images/2020/06/java-springboot-jdbc-mysql-05.png)

为了确保数据是否真的插入成功了，我们通过 Navicat（一款强大的数据库管理和设计工具）来查看一下。

![](http://www.itwanger.com/assets/images/2020/06/java-springboot-jdbc-mysql-06.png)

情况不妙，中文乱码了。该怎么办呢？需要我们在连接字符串中显式指定字符集，修改 spring.datasource.url 为以下内容。

```
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/springbootdemo?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC
```

重新运行该项目后再次访问，发现中文不再乱码了。

![](http://www.itwanger.com/assets/images/2020/06/java-springboot-jdbc-mysql-07.png)


快给自己点个赞。


![](http://www.itwanger.com/assets/images/2020/06/java-springboot-jdbc-mysql-08.gif)



### 06、鸣谢



我是沉默王二，一枚有趣的程序员。如果觉得文章对你有点帮助，请微信搜索「 **沉默王二** 」第一时间阅读，回复【**666**】更有我为你精心准备的 500G 高清教学视频（已分门别类）。

>本文 [GitHub](https://github.com/qinggee/itwanger.github.io) 已经收录，有大厂面试完整考点，欢迎 Star。

**原创不易，莫要白票，请你为本文点个赞吧**，这将是我写作更多优质文章的最强动力。