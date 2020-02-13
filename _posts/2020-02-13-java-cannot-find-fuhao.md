---
layout: post
category: java
title: StackOverflow上87万访问量的问题：什么是“找不到符号”？
tagline: by 沉默王二
tag: java
---

你好呀，我是沉默王二，一个和黄家驹一样身高，和刘德华一样颜值的程序员。为了输出更好的内容，我就必须先输入更多的内容，于是我选择 Stack Overflow 作为学习的第一战线，毕竟很多大牛都在强烈推荐。本篇文章，我们来探讨一下访问量足足有 87+ 万次的问题——什么是“找不到符号”，它是什么意思，它是如何发生的，以及如何修复它。

<!--more-->


额外多 BB 几句。坚持写作这些年来，真的成长特别快，我建议你也行动起来——我坚信，学习不在入，而在出！

![](http://www.itwanger.com/assets/images/2020/02/java-cannot-find-fuhao-01.png)

上图是之前的一个领导给我发的微信，看来他也看到了我的成长。（一不小心，暴露了自己的真名）

如果你还有啥想看的、想了解的，欢迎在评论区留言！我会的、我能写的，我都非常乐意分享出来，和你共同成长！接下来，我们来看正文。

### 01、“找不到符号”错误是什么意思

先来看一段代码：

```java
String s = String();
```

有点经验的 Java 程序员应该能够发现上面这段代码中的错误，它缺少了一个 `new` 关键字。因此，这段代码在编译阶段是不会通过的。

![](http://www.itwanger.com/assets/images/2020/02/java-cannot-find-fuhao-02.png)

当我们对编译错误置之不理，尝试运行它的时候，程序会抛出以下错误。

![](http://www.itwanger.com/assets/images/2020/02/java-cannot-find-fuhao-03.png)

“找不到符号”，意味着要么源代码有着明显的错误，要么编译方式有问题。总之呢，是我们程序员搞的鬼，把编译器搞懵逼了，它有点力不从心，很无辜。

![](http://www.itwanger.com/assets/images/2020/02/java-cannot-find-fuhao-04.png)

### 02、“找不到符号”是如何发生的

1）拼写错误

程序员毕竟也是人，是人就会犯错。

- 单词拼错了，比如说把 StringBuilder 拼写成了 StringBiulder。

```java
StringBuilder sb = new StringBiulder(); // 找不到符号，类 StringBiulder
```

- 大小写错了，比如说把 StringBuilder 拼写成了 Stringbuilder。

```java
StringBuilder sb = new Stringbuilder(); // 找不到符号，类 Stringbuilder
```

2）未声明变量

有时候，我们会在没有声明变量的情况下使用一个变量。

```java
System.out.println(sss); // 找不到符号，变量 sss
```

或者变量超出了作用域。

```java
for (int i = 0; i < 100; i++);
{
    System.out.println("i is " + i);
}
```

上面这段代码很不容易发现错误，因为仅仅是在“{”前面多了一个“;”。“;”使得 for 循环的主体被切割成了两个部分，“{}”中的 i 超出了“()”中定义的 i 范围。

3）方法用错了，或者不存在

比如说，Java 如何获取数组和字符串的长度？length 还是 length()？

```java
String[] strs = {"沉默王二"};
System.out.println(strs.length()); // 找不到符号，方法 length()

String str = "沉默王二";
System.out.println(str.length); // 找不到符号，变量 length
```

4）忘记导入类了

在使用第三方类库的时候，切记要先导入类。

```java
StringUtils.upperCase("abcd");// 找不到符号，类 StringUtils
```

不过，IDEA 中可以设置类自动导入，来避免这个错误。

![](http://www.itwanger.com/assets/images/2020/02/java-cannot-find-fuhao-05.png)


导致出现“找不到符号”的错误原因千奇百怪，上面也只是列举出了其中的一小部分。问题的根源在于程序员本身，随着编程经验的积累，以及集成开发工具的帮助，这些错误很容易在代码编写阶段被发现。

### 03、如何修复“找不到符号”错误

一般来说，修复“找不到符号”的错误很简单，要么根据 IDE 的提示在编写代码的时候直接修复；要么根据运行后输出的堆栈日志顺藤摸瓜。

![](http://www.itwanger.com/assets/images/2020/02/java-cannot-find-fuhao-06.png)

日志会给出具体的行号，以及错误的类型。根据提示，想一下自己的代码要表达什么意思，然后做出修复的具体动作。比如上图中提醒我们 35 行代码出错了，找不到变量 j，那么就意味着我们需要给变量 j 一个类型声明即可。

### 04、更复杂的原因

在实际的项目当中，出现“找不到符号”的错误原因往往很复杂，但大多数情况下，可以归结为以下几点：

- 编码格式不对。比如说应该是 UTF-8，但有些遗留的项目会设置为 GBK、GB2312 等等。

- JDK 的版本不匹配。比如说某些团队成员的电脑上安装的是 JDK 1.6，有的是 JDK 8，版本升级后的一些新语法自然就会和老版本发生冲突。

- 第三方类库的升级。一些开源的共同类库往往会不兼容旧的版本，比如说最新版的 StringUtils 类的包为 `org.apache.commons.lang3`，但之前是 `org.apache.commons.lang`。

- 类名和方法名都相同，但包名不同，方法的参数不同，在使用的时候就容易造成“找不到符号”。

在我初学 Java 的时候，老师要求我们用记事本来编写代码，然后在命令行中编译和运行代码，那时候真的叫一个痛苦啊。

![](http://www.itwanger.com/assets/images/2020/02/java-cannot-find-fuhao-07.png)

经常出现“找不到符号”的错误，差点入门到放弃。因为初学阶段，哪能记住那么多编程语言的规则啊，经常忘东忘西，再者记事本是没有行号的，找起问题来，简直要了老命。

吃过这样的苦后，我就强烈建议初学者不要再使用记事本编程了（莫装逼），直接上 IDE，有啥问题，工具帮你悠着点。

### 05、鸣谢

好了，我亲爱的读者朋友，以上就是本文的全部内容了。毫无疑问，能看到这里你在我心目中就是最棒的求知者，我必须要伸出大拇指为你点个赞👍。如果还想看到更多，我再推荐你 2 篇，希望你能够喜欢。

[如何快速打好Java基础？](https://mp.weixin.qq.com/s/2_JvGgcyVwPpJ_eoAsnHVQ)
[如何优雅地打印一个Java对象？](https://mp.weixin.qq.com/s/d7j-EbhYqCnp5F63WI1lIA)

最后，我有一个小小的请求，原创不易，如果觉得有点用的话，请不要吝啬你手中**点赞**的权力——因为这将是我写作的最强动力。