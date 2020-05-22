---
layout: post
category: java
title: 大清朝早亡了，还没有入门 Spring Boot？
tagline: by 沉默王二
tags: 
  - java
---

由于读者的数量越来越多，难免会被问到一些我自己都觉得不好意思的问题，比如说前几天小王就问我：“二哥，快教教我，怎么通过 Spring Boot  创建一个 Hello World 项目啊？”

<!--more-->

收到问题的时候，我都惊呆了！什么年头了，还不会用 Spring Boot，大清朝早亡了啊！


![](http://www.itwanger.com/assets/images/2020/05/java-fuwuqi-01.png)

没等我发牢骚，小王就紧接着说：“二哥，你先别生气，我投了 14 份简历才在三线小镇的一家小公司找到一份实习工作，不容易啊，领导给我安排了一个小活儿，就是搭一个 Spring Boot 的小项目，估计是测试我的实战能力吧。听说二哥热肠古道，我就抱着试一试的心态添加了你的好友。”

话说到这份上，我的气自然也就消了。随后，我花了五分钟的时间帮他解决了苦恼，没成想，他还发给我了一个小红包，表示对我的感谢。并建议我写一篇文章出来，因为他觉得像他这样的小白还有很多。期初我是有些犹豫的，毕竟网上写 Spring Boot 的文章已经很多了，况且还是“Hello World”，写出来会不会遭人喷啊。但转念一想，哪怕只有一个读者需要也是我们作者应尽的义务啊。于是就有了大家看到的这篇文章。

### 00、Spring Boot 简介

我猜，大家一定编写过基于 Spring 的应用程序，你就知道一个“Hello World”就需要大量的配置。想一想，我自己都有点怀疑，还能不能创建的出来，尤其是那些 XML 文件，完全敲不出来。

>Spring Boot 使您能轻松地创建独立的、生产级的、基于 Spring 且能直接运行的应用程序。我们对 Spring 平台和第三方库有自己的看法，所以您从一开始只会遇到极少的麻烦。

看看 Spring Boot 官网对自己的“王婆卖瓜自卖自夸”，感觉确实优秀啊。这意味着我们只需极少的配置，就可以快速创建一个可以正常运行的 Spring 应用程序。而且这些极少的配置采用的是注解的方式，没有 XML。

总之呢，Spring Boot 是一个轻量级的框架，可以完成基于 Spring 的应用程序的大部分配置工作。

### 01、使用 Spring Initlallzr 创建 Spring Boot 项目

创建一个 Spring Boot 项目非常简单，通过 Spring Initlallzr（[https://start.spring.io/](https://start.spring.io/)）就可以了。（实际上，我也只是把这个网址扔给了小王。）

![](http://www.itwanger.com/assets/images/2020/05/java-fuwuqi-02.png)

1）第一个选项一般选择 Maven（Java 后端项目首选），Gradle 在安卓项目中用的比较多。如果还不太了解 Maven，请查看我以前写的一篇文章：[Maven 入门](http://www.itwanger.com/java/2019/10/24/maven-rumen.html)。

2）第二个选项一般选择 Java。

3）第三个选项默认为 2.2.2，Spring Boot 目前最稳定的版本。

4）第四个选项填上项目的路径和名称。

5）第五个选项，我们选择 Spring Web 和 Spring Boot Actuator，表明该项目是一个 Web 项目；Actuator 是 Spring Boot 提供的对应用系统的自省和监控的集成功能，可以查看应用配置的详细信息，例如自动化配置信息、创建的 Spring beans 以及一些环境属性等。

选项选择完后，就可以点击【Generate】按钮生成一个初始化的 Spring Boot 项目了。生成的是一个压缩包，导入到 IDE 的时候需要先解压。

### 02、把 Spring Boot 项目导入 IDEA

最近迷上了 IDEA，所以暂时就不再使用 Eclipse 了。如果实在是不习惯 IDEA，可以选择 Spring 自己的 IDE——STS，基于 Eclipse 的。

![](http://www.itwanger.com/assets/images/2020/05/java-fuwuqi-03.png)

PS：导入的过程就省略了，请选择 Maven 就对了。

等待 Maven 把所有的依赖包下载完。

![](http://www.itwanger.com/assets/images/2020/05/java-fuwuqi-04.png)

项目的目录结构图如下所示。

![](http://www.itwanger.com/assets/images/2020/05/java-fuwuqi-05.png)

1）HelloSpringBootApplication 为项目的入口，带有 `main()` 方法。我们知道，传统的 Web 项目通常需要放到 Tomcat 等容器下面启动运行，而 `main()` 方法则预示着该项目可以作为一个 jar 包直接运行——因为 Spring Boot 已经内置了 Tomcat。

2）大家一定对 `@SpringBootApplication` 注解非常好奇，它的源码大致如下：

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
    excludeFilters = {@Filter(
    type = FilterType.CUSTOM,
    classes = {TypeExcludeFilter.class}
), @Filter(
    type = FilterType.CUSTOM,
    classes = {AutoConfigurationExcludeFilter.class}
)}
)
```

从这些注解中，我们大致可以窥探出，Spring Boot 项目使用了大量的注解取代了以往繁琐的 xml 配置。

### 03、编辑 Spring Boot 项目

来看下面这段代码。

```java
@SpringBootApplication
@RestController
public class HelloSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloSpringBootApplication.class, args);
    }

    @RequestMapping("hello")
    public String hello() {
    	return "Hello World";
	}
}
```

1）`@RestController` 注解相当于 `@ResponseBody` ＋ `@Controller`，意味着请求返回的对象可以直接以 JSON 的格式回显。

2）`hello()` 方法非常简单，返回一个字符串“Hello World”。`@RequestMapping` 表明这个方法是一个请求映射。

### 04、运行 Spring Boot 项目

接下来，我们直接运行 `HelloSpringBootApplication` 类，这样一个 Spring Boot 项目就启动成功了。

![](http://www.itwanger.com/assets/images/2020/05/java-fuwuqi-06.png)

默认端口为 8080。

这时候，可以直接在 IDEA 的 Terminal 面板中测试该项目是否启动成功。

![](http://www.itwanger.com/assets/images/2020/05/java-fuwuqi-07.png)

命令行为 `curl http://localhost:8080/hello`。之所以不需要项目路径 `helloSpringBoot`，是因为默认启动的时候相当于是一个 ROOT 级别的，所以不需要根目录。

curl 是一个常用的命令行工具，用来请求 Web 服务器。它的名字就是客户端（client）和 URL 的合体。curl 的功能非常强大，命令行参数多达几十种。如果运用熟练的话，完全可以取代 Postman。

![](http://www.itwanger.com/assets/images/2020/05/java-fuwuqi-08.png)

### 05、jar 形式运行 Spring Boot 项目

打开 pom.xml，我们可以看到如下内容：

```xml
<build>
	<plugins>
		<plugin>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-maven-plugin</artifactId>
		</plugin>
	</plugins>
</build>
```

这意味着我们可以通过 maven 命令 `mvn clean package` 将 Spring Boot 项目打包成 jar 文件。

![](http://www.itwanger.com/assets/images/2020/05/java-fuwuqi-09.png)

也可以加上参数用于跳过打包时候的测试：`mvn clean package -Dmaven.test.skip`

稍等片刻，就可以在 target 目录下看到对应的 jar 包。该 jar 包比起传统的 war 包更具有优势，因为不需要再单独开一个容器来跑项目了，Spring Boot 内置过了。原生的 jar 文件（.jar.original）只有不到 3 kb，非常小，因为没几行代码，但打包后 .jar 文件有 19 M，这说明 Spring Boot 帮我们做了很多肉眼看不到的工作。

![](http://www.itwanger.com/assets/images/2020/05/java-fuwuqi-10.png)

直接使用 `java -jar helloSpringBoot-0.0.1-SNAPSHOT.jar` 命令运行该 jar 包，同样可以看到 Tomcat 的启动信息。

![](http://www.itwanger.com/assets/images/2020/05/java-fuwuqi-11.png)

这次，我们使用浏览器来访问一下。

![](http://www.itwanger.com/assets/images/2020/05/java-fuwuqi-12.png)


也是 OK 的，这说明我们的第一个 Spring Boot 程序跑起来了。快给自己点个赞。

![](http://www.itwanger.com/assets/images/2020/05/java-fuwuqi-13.png)


### 06、鸣谢



好了，亲爱的读者朋友们，答应小王的文章终于写完了。**能看到这里的都是最优秀的程序员，升职加薪就是你了**👍。本文配套的源码已上传至 GitHub 【[SpringBootDemo.helloSpringBoot](https://github.com/qinggee/SpringBootDemo)】。


如果觉得文章对你有点帮助，请微信搜索「 **沉默王二** 」第一时间阅读。

>本文已收录 GitHub，[**传送门~**](https://github.com/qinggee/itwanger.github.io) ，里面更有大厂面试完整考点，欢迎 Star。

我是沉默王二，一枚有颜值却靠才华苟且的程序员。**关注即可提升学习效率，别忘了三连啊，点赞、收藏、留言，我不挑，嘻嘻**。