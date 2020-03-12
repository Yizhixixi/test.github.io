---
layout: post
category: life
title: 教妹学Java：Spring 入门篇
tagline: by 沉默王二
tags: 
  - java
---

你好呀，我是沉默王二，一个和黄家驹一样身高，刘德华一样颜值的程序员（管你信不信呢）。从两位偶像的年纪上，你就可以断定我的码龄至少在 10 年以上，但实话实说，我一直坚信自己只有 18 岁，因为我有一颗好学的心。本篇文章就打算通过我和三妹对话的形式来学一学“Spring 的基础”。

<!--more-->




教妹学 Java，没见过这么放肆的标题吧？“语不惊人死不休”，没错，本篇文章的标题就是这么酷炫，不然你怎么会点进来？

![](http://www.itwanger.com/assets/images/2020/03/java-spring-rumen-01.png)

我有一个漂亮如花的妹妹（见上图），她叫什么呢？我想聪明的读者能猜得出：沉默王三，没错，年方三六。父母正考虑让她向我学习，做一名正儿八经的 Java 程序员。我期初是反对的，因为程序员这行业容易掉头发。但家命难为啊，与其反对，不如做点更积极的事情，比如说写点有趣的文章教教她。

![](http://www.itwanger.com/assets/images/2020/03/java-spring-rumen-02.gif)


“二哥，你怎么现在才想起来要教我学 Spring 啊？”

“没办法啊，三妹，之前教妹学 Java 系列被喷的体无完肤，没办法就停滞了。今天决定走自己的路，让别人也走自己的路去。”

“我就说嘛，Spring 在 Java 中占据那么重要的位置，你竟然没舍得教我，还以为你怕被我拍死在沙滩上呢？”

“嗯，你天真了。”

### 01、Spring 简史

“二哥，据说 Spring 前后经历了 3 个时代，你能给我说说吗？”

“没问题啊。”

1）石器时代

也就是 Spring 1.x 时代，一个项目看上去全都是 xml 文件，里面配置着各种各样的 bean，项目越大，xml 文件就越多，到最后人都感觉不好了，像回到了原始社会。

2）黑铁时代

也就是 Spring 2.x 时代，终于可以使用注解配置 bean 了，这主要得益于 JDK 1.5 新增的注解功能。想一想，一个小小的注解，比如说 `@Component` 、`@Service` 就可以替代一大段的 xml 配置代码，简直爽歪歪啊。

使用 xml 文件配置数据源，使用注解配置业务类，两种方式相得益彰。

3）黄金时代

也就是 Spring 3.x 至今，可以使用 Java 配置的方式了。那什么是 Java 配置呢？

其实从形式上看，Java 配置和注解配置没什么区别，因为 Java 配置也用的是注解，只不过，以前用 application-context.xml 配置数据源等信息，现在用 `@Configuration` 注解的类配置。

### 02、Spring 特性

“二哥，据说 Spring 的特性非常丰富，你能给我说说吗？”

“没问题啊。”

1）核心技术：依赖注入（DI）、面向切面编程（AOP）、国际化、数据绑定、类型转换

2）测试：TestContext 框架、Spring MVC 测试

3）数据访问：事务、JDBC、对象关系映射（ORM）

4）Spring MVC（一个模型 - 视图 - 控制器「MVC」的 Web 框架）、Spring WebFlux（一个典型非阻塞异步的框架）

5）Spring Integration（面向企业应用集成）

### 03、Spring 生态

“二哥，据说 Spring 的生态圈非常火热，你能给我说说吗？”

“没问题啊。”

1）Spring Boot：当下最火的一个 Spring 项目了，使用它我们可以快速搭建好一个项目

2）Spring Cloud：为分布式开发提供了强大的工具集

3）Spring Security：通过认证和授权保护应用

4）Spring Web Flow：基于 Spring MVC 的流程式 Web 应用

5）等等等等

### 04、快速搭建 Spring 项目

“二哥，说了那么多，能不能来个上手项目，让我瞧瞧 Spring 项目到底长什么样子？”

“马上就来，三妹。”

我推荐使用 Intellij IDEA 作为集成开发工具，因为它插件丰富、功能全面。就我们目前的开发任务来说，社区版足够用了。

1）打开 IDEA，依次点击 File→New→Project→Maven。

![](http://www.itwanger.com/assets/images/2020/03/java-spring-rumen-03.png)

2）点击 Next 后填写项目的名称，然后创建项目。打开 pom.xml 文件，添加以下内容。

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>5.2.2.RELEASE</version>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.6.1</version>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
            </configuration>
        </plugin>
    </plugins>
</build>
```

dependencies 节点下添加了 Spring 的核心依赖 spring-context，它为 Spring 提供了一个运行时的环境，用以保存各个对象的状态。它依赖于 Spring 的四个核心组件：spring-aop、spring-beans、spring-core、spring-expression，也就是说只要引入 spring-context 的依赖，Maven 会自动引入这四个核心。

build 节点下添加了 Maven 的编译插件，并且指定了 Java 的编译版本为 1.8。

3）IDEA 下编辑文件会自动保存，所以与此同时，Maven 会将 Spring 的依赖包全部下载到本地仓库中。

4）稍稍眯一会眼睛，就可以看到 Spring 的依赖包了。

![](http://www.itwanger.com/assets/images/2020/03/java-spring-rumen-04.png)

5）Maven 是一个软件项目管理工具，基于项目对象模型（Project Object Model，明白 pom.xml 的由来了吧？）的概念，可以管理项目依赖的 jar 包，对项目进行编译打包等。

6）新建 HelloService 类，代码如下：

```java
@Service
public class HelloService {
    public void hello (String what) {
        System.out.println("hello " + what);
    }
}
```

`@Service` 注解一般在业务逻辑层使用。该类非常简单，只有一个方法 `hello()`，参数是字符串，然后在控制台打印 `hello xxx`。

7）新建 HelloConfig 类，代码如下：

```java
@Configuration
public class HelloConfig {
    @Bean
    public HelloService helloService() {
        return new HelloService();
    }
}
```

`@Configuration` 注解表明当前类是一个配置类，相当于 Spring 配置的一个 xml 文件。

`@Bean` 注解用在 `helloService()` 方法上，表明当前方法返回一个 Bean 对象（HelloService），然后将其交给 Spring 管理。产生这个 Bean 对象的方法只会被调用一次，随后 Spring  将其放在自己的 IOC 容器中。

8）新建 HelloMain 类，代码如下：

```java
public class HelloMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(HelloConfig.class);
        HelloService helloService = context.getBean(HelloService.class);
        helloService.hello("66666");
        context.close();
    }
}
```

HelloMain 类中有一个 `main()` 方法，它负责将当前项目跑起来。

`AnnotationConfigApplicationContext` 是一个用来管理注解 Bean 的容器，可以将 `@Configuration` 注解的类 Class 作为参数获取容器对象。再通过 `getBean()` 方法获取注册的 Bean 对象。获取到 HelloService 对象后，就可以让它说一声“66666”了。

![](http://www.itwanger.com/assets/images/2020/03/java-spring-rumen-05.png)






“二哥，这篇文章中的示例代码你上传到码云了吗？最近 GitHub 访问起来有点卡。”

“你到挺贴心啊，三妹。[码云传送门~](https://gitee.com/qing_gee/JavaPoint/tree/master)”

“二哥，你教得真不错，我完全学会了，一点也不枯燥。”

“那必须滴啊，期待下一篇吧？”

“那是当然啊，期待，非常期待，望眼欲穿的感觉。”

![](http://www.itwanger.com/assets/images/2020/03/java-spring-rumen-06.gif)





### 05、鸣谢

觉得好的记得回来给我点赞哦！😎

简单介绍一下，我大学的时候选择了 Java 这门编程语言，但没怎么好好学，还挂科了好几门；工作后吃了不少亏。这 2 年奋起直追，基础知识扎实了不少，更是创作了大量优质的技术文章，帮助了很多同行。

欢迎你关注我的公号：**沉默王二**，里面有不少市面上搜不到的珍贵资源，比如说回复“666”，就可以获取高清教学视频，已分门别类，带有目录，想学什么就学什么！

任何学习上的问题，都可以加我的微信：qing_geee，另外，我有几个高质量的交流群，我会**不定期在群里分享学习资源，福利等等**，感兴趣的可以说下我邀请你！

对了，如果你是一枚 Java 小白的话，也可以加我微信，我相信你在学习的过程中一定遇到不少问题，或许我可以帮助你，毕竟我也是过来人了！

![](http://www.itwanger.com/assets/images/cmower_11.png)













