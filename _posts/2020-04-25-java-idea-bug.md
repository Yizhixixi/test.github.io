---
layout: post
category: java
title: IDEA惊天bug，进程已结束,退出代码-1073741819 (0xC0000005)
tagline: by 沉默王二
tags: 
  - java
---

由于昨天要写的文章没有写完，于是今天早上我四点半就“自然醒”了，心里面有事，睡觉也不安稳。洗漱完毕后，我打开电脑，正襟危坐，摆出一副要干架的态势，不能再拖了。

<!--more-->





要写的文章中涉及到一串代码，关于 Undertow 的一个入门示例，贴出来大家看一下。

```java
public class UndertowTest {
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

Undertow 是大名鼎鼎的 Red Hat（红帽子）公司开发的一款基于 NIO 的高性能 Web 服务器软件，不需要单独安装软件，只需要几行代码就可以在 Java 应用程序中启动一个 Web 服务，就像上面那段代码。

前提条件是你已经在 pom.xml 文件中引入了 Undertow 的依赖。

```
<dependency>
    <groupId>io.undertow</groupId>
    <artifactId>undertow-core</artifactId>
    <version>2.0.28.Final</version>
</dependency>
```

没想到，代码在 IDEA 中运行后，竟然非正常退出了！

![](http://www.itwanger.com/assets/images/2020/04/java-idea-bug-01.png)

没有任何错误提示，真的是郁闷。主机名 localhost 没有问题，端口 8080 也没有被占用，但 main 方法在没有任何外力的因素下直接结束了。在浏览器地址栏中输入 `http://localhost:8080` 自然也无法显示“Hello World”。

回看 Run 面板中的输出信息，唯一让我感到疑惑的就是下面这行：

>进程已结束,退出代码-1073741819 (0xC0000005)

正常来说，程序执行的输出结果如下所示：

>进程已结束,退出代码0

退出代码是 0，表示程序正常结束；退出代码是 1073741819，它代表什么意思呢？

肯定是非正常呗。我第一时间想到的原因是，会不会是我代码写错了？于是查看了 Undertow 的官网，一个单词一个单词的检查，甚至上了 beyond compare 进行比较，也没有找到任何可疑之处。

折腾得够呛，于是不得不上了谷歌大法：

![](http://www.itwanger.com/assets/images/2020/04/java-idea-bug-02.png)

换了各种关键词，查阅了各种文章，没有找到可行的解决办法。谷歌无果，我想那就试试某度吧，结果搜到了游戏，天地良心啊。

![](http://www.itwanger.com/assets/images/2020/04/java-idea-bug-03.png)

搜索引擎靠不住，那就只能靠自己了。于是我写下了这段代码：

```java
public class TestClose {
    public static void main(String[] args) {
        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

运行结果大吃一惊。我揉了好几次眼睛，甚至上了倍清亮眼药水，结果也是非正常退出，错误代码和之前的 Undertow 一致。

![](http://www.itwanger.com/assets/images/2020/04/java-idea-bug-04.png)

搞什么嘛，这可是 `Thread.sleep(10000000)` 休眠大法啊，也能在一秒内结束，那一定不是 Undertow 的代码示例写错了，而是 IDEA 在作妖。

事到如今，我想起了被打入冷宫的 Eclipse。同样的代码，进程没有立即结束掉。

![](http://www.itwanger.com/assets/images/2020/04/java-idea-bug-05.png)

Undertow 的代码示例也没有问题，程序没有立即结束。

![](http://www.itwanger.com/assets/images/2020/04/java-idea-bug-06.png)

在浏览器中输入 `http://localhost:8080` 也能正常访问。

![](http://www.itwanger.com/assets/images/2020/04/java-idea-bug-07.png)

对比之后，问题就很确定了，出在 IDEA 身上，一定是它哪根神经错乱了。于是换了一下谷歌搜索的关键词，结果如下所示。

![](http://www.itwanger.com/assets/images/2020/04/java-idea-bug-08.png)

果然也有同行遇到了类似的问题，但文章中提到的原因竟然是金山词霸的划词翻译，这有点太扯了吧？

![](http://www.itwanger.com/assets/images/2020/04/java-idea-bug-09.png)


虽然有点扯，但的确值得试一试，毕竟山穷水尽了啊，况且我的电脑上真的安装了金山词霸，并且是打开状态。

![](http://www.itwanger.com/assets/images/2020/04/java-idea-bug-10.png)

那就退出试试呗，结果。。。。。。。。。。真的有用啊。

![](http://www.itwanger.com/assets/images/2020/04/java-idea-bug-11.png)

这次，进程没有主动结束，这是什么神奇的操作啊？人生第一次开始怀疑科学了！

既然和金山词霸有关系，那么我就脑洞打开了，是不是因为我装了汉化插件的关系？于是我把 IDEA 的汉化插件禁用，并且在重启之前打开了金山词霸。

![](http://www.itwanger.com/assets/images/2020/04/java-idea-bug-12.png)

结果证明没关系，进程主动结束了。

![](http://www.itwanger.com/assets/images/2020/04/java-idea-bug-13.png)

于是只好再次退出金山词霸。没想到，当我点击 Run 的小图标再次运行程序时，IDEA 竟然闪退了。看来它们之间的确有着不可告人的秘密，具体原因未知。

不管怎么说，这个莫名其妙的 bug 是解决了，有必要总结一波经验心得了，希望能够给小伙伴们在开发中一些启发。因为作为程序员，面对不会说话的计算机，有时候，真的会遇到一些难以名状的错误，把我们折腾得够呛。

经验一：保持冷静，切勿暴躁，心态失衡时容易捶鼠标，捶键盘，捶坏了，还得买新的。

经验二：先从自身代码找原因，复制粘贴有时候也会出现偏差，这时候，最好就和源头对比一下。如果肉眼发现不了，上比较工具，靠谱。

经验三：问谷歌，不要问某度。这年头，经常听到一些小伙伴们抱怨说，公司不允许上网，遇到问题时真的无从下手，我只能说这样的公司真的是闭关锁国啊。

经验四：换个环境试一试。同样的代码，环境不同，运行后的解决真有可能不同。IDEA 中出错，放 Eclipse 中试试；Windows 下出错，放 Linux 下试试；自己的环境有问题，放同事的环境下试试。

经验五：搜索的时候换一下关键词，真的是“柳暗花明又一村”啊。

经验六：重复以上。




### 鸣谢

好了，我亲爱的读者朋友，以上就是本文的全部内容了。看完之后，再遇到面试官问 Java 到底是值传递还是引用传递时，就不用担心被刁难了。我是沉默王二，一枚有趣的程序员。**原创不易，莫要白票，请你为本文点赞个吧**，这将是我写作更多优质文章的最强动力。



>如果觉得文章对你有点帮助，请微信搜索「 **沉默王二** 」第一时间阅读，回复【**666**】更有我为你精心准备的 500G 高清教学视频（已分门别类）。本文 [GitHub](https://github.com/qinggee/itwanger.github.io) 已经收录，有大厂面试完整考点，欢迎 Star。

