---
layout: post
category: java
title: 在 Spring Boot 中使用 HikariCP 连接池
tagline: by 沉默王二
tags: 
  - java
---

上次帮小王解决了如何在 Spring Boot 中使用 JDBC 连接 MySQL 后，我就一直在等，等他问我第三个问题，比如说如何在 Spring Boot 中使用 HikariCP 连接池。但我等了四天也没有等到任何音讯，似乎他从我的世界里消失了，而我却仍然沉醉在他拍我马屁的美妙感觉里。

<!--more-->

![](http://www.itwanger.com/assets/images/2020/06/java-hikaricp-01.png)

突然感觉，没有小王的日子里，好空虚。怎么办呢？想来想去还是写文章度日吧，积极创作的过程中，也许能够摆脱对小王的苦苦思念。写什么好呢？

想来想去，就写如何在 Spring Boot 中使用 HikariCP 连接池吧。毕竟实战项目当中，肯定不能使用 JDBC，连接池是必须的。而 HikariCP 据说非常的快，快到 Spring Boot 2 默认的数据库连接池也从 Tomcat 切换到了 HikariCP（喜新厌旧的臭毛病能不能改改）。

HikariCP 的 GitHub 地址如下：

[https://github.com/brettwooldridge/HikariCP](https://github.com/brettwooldridge/HikariCP)

目前星标 12K，被使用次数更是达到了 43.1K。再来看看它的自我介绍。


![](http://www.itwanger.com/assets/images/2020/06/java-hikaricp-02.png)

牛逼的不能行啊，原来 Hikari 来源于日语，“光”的意思，这意味着快得像光速一样吗？讲真，看简介的感觉就好像在和我的女神“汤唯”握手一样刺激和震撼。

既然 Spring Boot 2 已经默认使用了 HikariCP，那么使用起来也相当的轻松惬意，只需要简单几个步骤。

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

![](http://www.itwanger.com/assets/images/2020/06/java-hikaricp-03.png)




勾选 Web、JDBC、MySQL Driver 等三个依赖。

1）Web 表明该项目是一个 Web 项目，便于我们直接通过 URL 来实操。

3）MySQL Driver：连接 MySQL 服务器的驱动器。

5）JDBC：Spring Boot 2 默认使用了 HikariCP，所以 HikariCP 会默认在 spring-boot-starter-jdbc 中附加依赖，因此不需要主动添加 HikariCP 的依赖。

PS：怎么证明这一点呢？项目导入成功后，在 pom.xml 文件中，按住鼠标左键 + Ctrl 键访问 spring-boot-starter-jdbc 依赖节点，可在 spring-boot-starter-jdbc.pom 文件中查看到 HikariCP 的依赖信息。

![](http://www.itwanger.com/assets/images/2020/06/java-hikaricp-04.png)


选项选择完后，就可以点击【Generate】按钮生成一个初始化的 Spring Boot 项目了。生成的是一个压缩包，导入到 IDE 的时候需要先解压。


### 03、编辑 application.properties 文件

项目导入成功后，等待 Maven 下载依赖，完成后编辑 application.properties 文件，配置 MySQL 数据源信息。


```
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/springbootdemo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=123456
```

是不是有一种似曾相识的感觉（和[上一篇]()中的数据源配置一模一样）？为什么呢？答案已经告诉过大家了——默认、默认、默认，重要的事情说三遍，Spring Boot 2 默认使用了 HikariCP 连接池。


### 04、编辑 Spring Boot 项目

为了便于我们查看 HikariCP 的连接信息，我们对 SpringBootMysqlApplication 类进行编辑，增加以下内容。

```java
@SpringBootApplication
public class HikariCpDemoApplication implements CommandLineRunner {
	@Autowired
	private DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(HikariCpDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Connection conn = dataSource.getConnection();
		conn.close();
	}
}
```



HikariCpDemoApplication 实现了 CommandLineRunner 接口，该接口允许我们在项目启动的时候加载一些数据或者做一些事情，比如说我们尝试通过 DataSource 对象与数据源建立连接，这样就可以在日志信息中看到 HikariCP 的连接信息。CommandLineRunner 接口有一个方法需要实现，就是我们看到的 `run()` 方法。

![](http://www.itwanger.com/assets/images/2020/06/java-hikaricp-05.png)

通过 debug 的方式，我们可以看到，在项目运行的过程中，dataSource 这个 Bean 的类型为 HikariDataSource。


### 05、运行 Spring Boot 项目

接下来，我们直接运行 `HikariCpDemoApplication` 类，这样一个 Spring Boot 项目就启动成功了。

![](http://www.itwanger.com/assets/images/2020/06/java-hikaricp-06.png)

HikariDataSource 对象的连接信息会被打印出来。也就是说，HikariCP 连接池的配置启用了。快给自己点个赞。

![](http://www.itwanger.com/assets/images/2020/06/java-hikaricp-07.png)


### 06、为什么 Spring Boot 2.0 选择 HikariCP 作为默认数据库连接池

有几种基准测试结果可用来比较HikariCP和其他连接池框架（例如*[c3p0](http://www.mchange.com/projects/c3p0/)*，*[dbcp2](https://commons.apache.org/proper/commons-dbcp/)*，*[tomcat](https://people.apache.org/~fhanik/jdbc-pool/jdbc-pool.html)*和[*vibur）的性能*](http://www.vibur.org/)。例如，HikariCP团队发布了以下基准（可[在此处](https://github.com/brettwooldridge/HikariCP-benchmark)获得原始结果）：

HikariCP 团队为了证明自己性能最佳，特意找了几个背景对比了下。不幸充当背景的有 c3p0、dbcp2、tomcat 等传统的连接池。

![](http://www.itwanger.com/assets/images/2020/06/java-hikaricp-08.png)

从上图中，我们能感受出背景的尴尬，HikariCP 鹤立鸡群了。HikariCP 制作以如此优秀，原因大致有下面这些：


1）字节码级别上的优化：要求编译后的字节码最少，这样 CPU 缓存就可以加载更多的程序代码。

HikariCP 优化前的代码片段：

```java
public final PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException
{
    return PROXY_FACTORY.getProxyPreparedStatement(this, delegate.prepareStatement(sql, columnNames));
}
```

HikariCP 优化后的代码片段：

```java
public final PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException
{
    return ProxyFactory.getProxyPreparedStatement(this, delegate.prepareStatement(sql, columnNames));
}
```

以上两段代码的差别只有一处，就是 ProxyFactory 替代了 PROXY_FACTORY，这个改动后的字节码比优化前减少了 3 行指令。具体的分析参照 HikariCP 的 Wiki [文档](https://github.com/brettwooldridge/HikariCP/wiki/Down-the-Rabbit-Hole)。

2）使用自定义的列表（FastStatementList）代替 ArrayList，可以避免 `get()` 的时候进行范围检查，`remove()` 的时候从头到尾的扫描。

![](http://www.itwanger.com/assets/images/2020/06/java-hikaricp-09.png)


### 07、鸣谢



好了，各位读者朋友们，答应小王的文章终于写完了。**能看到这里的都是最优秀的程序员，升职加薪就是你了**👍。如果觉得不过瘾，还想看到更多，可以 star 二哥的 GitHub【[itwanger.github.io](https://github.com/qinggee/itwanger.github.io)】，本文已收录。


PS：本文配套的源码已上传至 GitHub 【[SpringBootDemo.SpringBootMysql](https://github.com/qinggee/SpringBootDemo)】。

原创不易，如果觉得有点用的话，请不要吝啬你手中**点赞**的权力；如果想要第一时间看到二哥更新的文章，请扫描下方的二维码，关注沉默王二公众号。我们下篇文章见！

![](http://www.itwanger.com/assets/images/cmower_4.png)