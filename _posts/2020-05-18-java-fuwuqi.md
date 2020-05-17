---
layout: post
category: java
title: 服务器软件大扫盲
tagline: by 沉默王二
tags: 
  - java
---

先说一句哈，自从在 B 站开始刷视频后，我就觉得要学的内容实在是太多了。这篇“服务器软件大扫盲”就是我看了羊哥的一期视频后有感而发的，比如说 Web 服务器、HTTP 服务器、应用服务器这三个概念，我是见过很多次，但如果你非要我说出它们之间的区别的话，我只好哑口无言。

<!--more-->

还有，我自己用过的 Tomcat、Nginx、Apache、Jetty、Undertow，它们之间有什么优缺点，嗯。。。。。。只好继续哑口无言。可能有很多小伙伴和我一样，用过，但具体的差别还真的说不上来，所以我打算借这个机会来和大家一起学习下。（我就是课代表，我骄傲）

![](http://www.itwanger.com/assets/images/2020/05/java-fuwuqi-01.png)

先来说 Web 服务器，它一般指的是网站服务器，可以向浏览器（PC端或者移动端）等 Web 客户端提供服务，供请求数据或者下载数据。服务器使用 HTTP （超文本传输协议）和客户端浏览器进行通信，因此我们也把 Web 服务器称作为 HTTP 服务器。

再来说应用服务器，它是一种软件框架，提供一个应用程序运行的环境。通常用于为应用程序提供安全、数据、事务支持、负载平衡大型分布式系统管理等服务。

在我看来，Web 服务器和应用服务器之间的界限已经非常模糊，后者更高级一点，就好像公司与企业这两个名词之间的差别。

常见的 Web 服务器软件包括 Nginx、Apache、IIS，常见的应用服务器软件包括 WebLogic、JBoss，前者更轻量级，后者更重量级。

接下来，我们就来唠唠常见的一些服务器软件。

### 01、Tomcat

就我的程序生涯来看，Tomcat 用的算是最多了，没有之一。如果 Tomcat 安装成功的话，可以在本地的浏览器中访问 http://127.0.0.1:8080 来展示它的默认首页，见下图。

![](http://www.itwanger.com/assets/images/2020/05/java-fuwuqi-02.png)

Tomcat 是由 Apache 软件基金会属下 Jakarta 项目开发的 Servlet 容器，实现了对 Servlet 和 JavaServer Page（JSP）的支持，并提供了作为 Web 服务器的一些特有功能。

JSP 是由 Sun Microsystems 公司主导建立的一种动态网页技术标准。 JSP 可以响应客户端发送的请求，并根据请求内容动态地生成 HTML、XML 或其他格式文档的 Web 网页，然后返回给请求者。

JSP 以 Java 语言作为脚本语言，为用户的 HTTP 请求提供服务，并能与服务器上的其它 Java 程序共同处理复杂的业务需求。我是一名三线城市的 Java 程序员，免不了要开发一些小型网站，这也就是为什么我用 Tomcat 最多的原因。

### 02、Nginx

Nginx 是一款轻量级的 Web 服务器、也支持反向代理，由于它的内存占用少，启动极快，高并发能力强，所以在互联网项目中广泛应用。

![](http://www.itwanger.com/assets/images/2020/05/java-fuwuqi-03.png)

关于 Nginx，比较令人遗憾的一件事是，它的作者伊戈尔·赛索耶夫进了监狱。

Nginx 在官方测试的结果中，能够支持五万个并行连接，国内比较有名的公司，比如说百度、京东、新浪、网易、腾讯、淘宝等都在使用。

不知道你有没有听过虚拟主机的概念，就是在 Web 服务里有一个独立的网站站点，这个站点对应独立的域名（也可能是IP 或端口），具有独立的程序及资源，可以独立地对外提供服务供用户访问。

虚拟主机有三种类型：基于域名的虚拟主机、基于端口的虚拟主机、基于 IP 的虚拟主机。

Nginx 可以使用一个 `server{}` 标签来标识一个虚拟主机，一个 Web 服务里可以有多个虚拟主机标签对，即可以同时支持多个虚拟主机站点。这一点，非常的实用。

### 03、Apache

最开始的时候，我以为 Apache 就是 Tomcat，傻傻分不清楚。后来知道它们完全不同，logo 就不同（说什么大实话）。

![](http://www.itwanger.com/assets/images/2020/05/java-fuwuqi-04.png)

Apache 一般是指 Apache HTTP Server，是 Apache 软件基金会（和 Tomcat 同属一家基金会，因此容易混淆）下的一个网页服务器软件。由于其跨平台和安全性，被广泛使用，是最流行的 Web 服务器软件之一。它快速、可靠并且可通过简单的 API 扩展。

我是在服务器上安装 WordPress 的时候用到了 Apache，当时并不知道有 LAMP 的存在，所以安装的过程中吃了很多苦，关键是最后没有安装成功，大写的尴尬。

最后还是在青铜群里的一个群友的远程帮助下才完成安装的，他是搞 PHP 的。LAMP 就是他告诉我的，安装起来非常的傻瓜式，非常适合我这种对命令行有抗拒心理的程序员。

LAMP 是指一组运行动态网站或者服务器的自由软件名称首字母缩写：

- Linux，操作系统（一般服务器软件都安装在 Linux 上，性能极佳）
- Apache，网页服务器（就是 Apache HTTP Server）
- MariaDB 或 MySQL，数据库管理系统
- PHP、Perl 或 Python，脚本语言

这些软件配合起来使用的时候，极具活力，它的变体还有很多，另外一个比较有名的就是 LNMP，用 Nginx 代替 Apache。

### 04、Jetty

Jetty 和 Tomcat 有很多相似之处，比如说可以为 JSP 和 Servlet 提供运行时环境。Jetty 是 Java 语言编写的，它的 API 以一组 JAR 包的形式发布。

![](http://www.itwanger.com/assets/images/2020/05/java-fuwuqi-05.png)

与 Tomcat 相比，Jetty 可以同时处理大量链接并且长时间的保持这些链接，例如，一些 Web 聊天应用非常适合用 Jetty 服务器，比如说淘宝的 Web 版旺旺。

Jetty 的架构比较简单，它有一个基本数据模型，这个数据模型就是  Handler，所有可以被扩展的组件都可以作为一个 Handler，添加到 Server 中，Jetty 就是帮我们管理这些 Handler 的。

### 05、Undertow

Undertow 是一个用 Java 编写的、灵活的、高性能的 Web 服务器，提供基于 NIO 的阻塞和非阻塞 API。

Undertow 可以嵌入到应用程序中或独立运行，只需几行代码，非常容易上手。下面这段代码是官网提供的一个使用 Async IO 的简单 Hello World 服务器示例：

```java
public class HelloWorldServer {

    public static void main(final String[] args) {
        Undertow server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(new HttpHandler() {
                    @Override
                    public void handleRequest(final HttpServerExchange exchange) throws Exception {
                        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                        exchange.getResponseSender().send("Hello World");
                    }
                }).build();
        server.start();
    }
}
```

直接运行后，在浏览器中地址栏中输入 `http://localhost:8080` 就可以访问到了。是不是感觉非常轻巧？

如果有小伙伴使用过 JFinal 开发过小型网站的话，对 Undertow 应该不会陌生，因为 JFinal 的默认容器已经切换到了 Undertow。

>JFinal 是基于 Java 语言的极速 WEB + ORM 框架，其核心设计目标是开发迅速、代码量少、学习简单、功能强大、轻量级、易扩展、Restful。

### 06、企业级

至于其他的一些企业级服务器软件，我个人没有用过，就不细说了。

- JBoss，红帽子收购过，后更名为 WildFly。

- WebLogic，甲骨文出品。

- WebSphere，IBM 公司出品。

相信小伙伴们看了出品方，就知道这些服务器软件足够的重量级，都是大佬，都是大佬。

如果觉得文章对你有点帮助，请微信搜索「 **沉默王二** 」第一时间阅读。

>本文已收录 GitHub，[**传送门~**](https://github.com/qinggee/itwanger.github.io) ，里面更有大厂面试完整考点，欢迎 Star。

我是沉默王二，一枚有颜值却靠才华苟且的程序员。**关注即可提升学习效率，别忘了三连啊，点赞、收藏、留言，我不挑，嘻嘻**。