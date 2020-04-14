---
layout: post
category: life
title: 一文教会你如何在 Spring 中进行集成测试，太赞了
tagline: by 沉默王二
tags: 
  - java
---

不得不说，测试真的是太重要了！但并不是所有的开发者都这样认为，这种感觉在我回到洛阳后尤其强烈。竟然有团队成员不经测试就把代码提交到代码库，并且是会报错的那种，我天呐，遇到这种队友我也是醉了。


<!--more-->

我之前是在一家日企工作，他们非常注重测试，占用的时间比代码编写的时间多多了。从单元测试到集成测试，所有的测试结果都要整理成文档保存下来，哪怕你觉得完全没有必要。但正是这种一丝不苟的态度，成就了日企软件高质量的美誉。

单元测试通常比较简单，对运行环境的依赖性不强。但集成测试就完全不同了，需要整个项目是能够跑起来的，比如说需要数据库是连接的，网络是通畅的等等。

>集成测试最简单的形式是：把两个已经测试过的单元组合成一个组件，测试它们之间的接口。从这一层意义上讲，组件是指多个单元的集成聚合。在现实方案中，许多单元组合成组件，而这些组件又聚合为程序的更大部分。方法是测试片段的组合，并最终扩展成进程，将模块与其他组的模块一起测试。最后，将构成进程的所有模块一起测试。

Spring 提供了 Spring TestContex Framework 来支持集成测试，它不依赖于特定的测试框架，因此你可以选择 Junit，也可以选择 TestNG。本文选择 Junit，因此需要先将 Junit 和 Spring TestContex Framework 的依赖添加到 pom.xml 文件中。

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-test</artifactId>
    <version>5.2.2.RELEASE</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>
```

准备工作完成后，来考虑这样一个场景，我们需要同时为开发环境和生产环境维护数据源配置，因此我们先创建一个公共的接口 Datasource，内容如下所示：

```java
public interface Datasource {
    public void setup();
}
```

新建 DevDatasource 类，实现 Datasource 接口，该类用于为开发环境配置数据源，内容如下：

```java
@Component
@Profile("dev")
public class DevDatasource implements Datasource {
    @Override
    public void setup() {
        System.out.println("开发环境");
    }
}
```

`@Component` 注解用于为 Spring 容器注入一个通用的 Bean 组件。`@Profile` 注解用于标识不同环境下要实例化的 Bean，字符串“dev” 表示该组件用于开发环境。

新建 ProdDatasource 类，实现 Datasource 接口，该类用于为正式运行环境配置数据源，内容如下：

```java
@Component
@Profile("prod")
public class ProdDatasource implements Datasource {
    @Override
    public void setup() {
        System.out.println("正式环境");
    }
}
```

新建配置类 SpringTestConfig，内容如下：

```java
@Configuration
@ComponentScan("test")
public class SpringTestConfig {
}
```

`@Configuration` 注解表示当前类为一个配置类，`@ComponentScan` 注解用于指定扫描路径，该路径下的 Bean 将会自动装配到 Spring 容器中。

基于 Maven 构建的项目默认有两个测试目录，src/test/java 和 src/test/resources，分别对应于 src/main/java 和 src/main/resources。前者用于测试项目源码，后者用于测试项目资源。

我们在 `src/test/java` 目录下新建测试类 DevTest，内容如下：

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration(classes = { SpringTestConfig.class })
public class DevTest {
    @Autowired
    Datasource datasource;

    @Test
    public void testSpringProfiles() {
        Assert.assertTrue(datasource instanceof DevDatasource);
    }
}
```

1）`@RunWith` 注解用于指定 Junit 运行环境，是 Junit 提供给其他框架测试环境的接口扩展，Spring 提供了 `org.springframework.test.context.junit4.SpringJUnit4ClassRunner` 作为 Junit 测试环境。

2）`@ActiveProfile` 注解用于指定哪个配置文件处于活动状态，本例为开发环境“dev”。

3）`@ContextConfiguration` 注解用于指定配置类，本例为 SpringTestConfig 类。

4）`@Autowired` 注解用于指定 Spring 要自动装配的 Bean。

5）`@Test` 注解用于表示当前方法为 Junit 测试方法。

程序运行的结果如下图所示：

![](http://www.itwanger.com/assets/images/2020/04/java-spring-test-01.png)


再新建一个测试类 ProdTest，内容如下：

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("prod")
@ContextConfiguration(classes = { SpringTestConfig.class })
public class ProdTest {

    @Autowired
    Datasource datasource;

    @Autowired
    Environment environment;

    @Test
    public void testSpringProfiles() {
        for (String profileName : environment.getActiveProfiles()) {
            System.out.println("当前激活的 profile - " + profileName);
        }
        Assert.assertTrue(datasource instanceof ProdDatasource);
    }
}
```

这次使用 `@ActiveProfiles("prod")` 注解指定当前环境为正式运行下的环境。本例中我们装配了一个新的 Bean environment，使用它的 `getActiveProfiles()` 方法可以获取当前激活的 Profile。程序运行的结果如下图所示：

![](http://www.itwanger.com/assets/images/2020/04/java-spring-test-02.png)

好了，我亲爱的读者朋友，以上就是本文的全部内容了，是不是感觉在 Spring 中进行集成测试还是挺简单的？如果觉得文章对你有点帮助，请微信搜索「 **沉默王二** 」第一时间阅读。示例代码已经上传到 GitHub，[传送门~](https://github.com/qinggee/SpringDemo)

我是沉默王二，一枚有趣的程序员。**原创不易，莫要白票**，请你为本文点个赞吧，这将是我写作更多优质文章的最强动力。