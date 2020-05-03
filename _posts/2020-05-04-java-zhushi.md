---
layout: post
category: java
title: 老师，你确定Java注释不会被执行吗？
tagline: by 沉默王二
tags: 
  - java
---

之前在 CSDN 上分享过一篇文章，涉及到 Java 中的注释，就信誓旦旦地写了一句话：“注释是不会被执行的！”结果，有小伙伴留言说，“老师，你确定吗？”

<!--more-->



![](http://www.itwanger.com/assets/images/2020/05/java-zhushi-01.gif)

我这个人一直有个优点，就是能听得进去别人的声音，管你是赞美的还是批评的，从来都是虚心接受。因为我相信，大多数小伙伴都是出于善的目的。

况且，我在技术上从来没想过要成为多牛逼的大佬，就是喜欢分享的感觉，而已。很多文章中出现的错误，我都原封不动的保留，因为如果把修正了，那么留言中那些指出错误的人，在后来的读者眼里，就会觉得不合时宜。

那些 diss 我的小伙伴们，放心，我是不会介意的。

尽管如此，但对于注释这件事，真的是不能忍啊！注释肯定不会被执行啊，我想这位小伙伴一定是在讽刺我。于是我就私信问他为什么，然后他就甩给了我下面这段代码：

```java
public class Test {
    public static void main(String[] args) {
        String name = "沉默王二";
        // \u000dname="沉默王三";
        System.out.println(name);
    }
}
```

我拷贝到 IDEA 中跑了一下，结果程序输出的结果出乎我的意料：

```
沉默王三
```

竟然是王三，不是王二。看到这个结果，我算是彻底懵逼了。

![](https://upload-images.jianshu.io/upload_images/1179389-0f6e6585fd2dbbab.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

那一刹那，我感觉这十来年的 Java 算是白学了。大学那会，老师说注释是不会执行的；就连《编程思想》里也说注释是不会执行的。那现在谁能告诉我这到底为什么？

不是说程序的世界很单纯吗？不是 0 就是 1？事情搞到这个地步，只能花心思好好研究一下了。

单纯从代码上来看，问题应该出在那串特殊的字符上——`\u000d`，如果不是它在作怪，把 name 的值由“沉默王二”修改为了“沉默王三”，就没有别的原因了——没别的，凭借多年的工作经验，找问题的根源我还是很得心应手的。

`\u000d` 虽然看上去比较陌生，但我知道它是一个 Unicode 字符。问了一下搜索引擎后，知道它代表一个换行符——一种恍然大悟的感觉啊。我知道，Java 编译器不仅会编译代码，还会解析 Unicode 字符。

我大致看了一眼上面这段代码编译后的字节码，它长下面这个样子：

```
// class version 58.65535 (-65478)
// access flags 0x21
public class com/cmower/dzone/secret/Test {

  // compiled from: Test.java

  // access flags 0x1
  public <init>()V
   L0
    LINENUMBER 3 L0
    ALOAD 0
    INVOKESPECIAL java/lang/Object.<init> ()V
    RETURN
   L1
    LOCALVARIABLE this Lcom/cmower/dzone/secret/Test; L0 L1 0
    MAXSTACK = 1
    MAXLOCALS = 1

  // access flags 0x9
  public static main([Ljava/lang/String;)V
   L0
    LINENUMBER 5 L0
    LDC "\u6c89\u9ed8\u738b\u4e8c"
    ASTORE 1
   L1
    LINENUMBER 6 L1
    LDC "\u6c89\u9ed8\u738b\u4e09"
    ASTORE 1
   L2
    LINENUMBER 7 L2
    GETSTATIC java/lang/System.out : Ljava/io/PrintStream;
    ALOAD 1
    INVOKEVIRTUAL java/io/PrintStream.println (Ljava/lang/String;)V
   L3
    LINENUMBER 8 L3
    RETURN
   L4
    LOCALVARIABLE args [Ljava/lang/String; L0 L4 0
    LOCALVARIABLE name Ljava/lang/String; L1 L4 1
    MAXSTACK = 2
    MAXLOCALS = 2
}
```

嗯，表示看不懂。不过没关系，把它反编译一下就行了，于是我看到下面这段代码：

```java
public class Test {
    public Test() {
    }

    public static void main(String[] args) {
        String name = "沉默王二";
        name = "沉默王三";
        System.out.println(name);
    }
}
```

咦，两个反斜杠  `//` 真的不见了，这可以确定一点——注释确实是不会执行的。只不过 `\u000d` 把 `name="沉默王三";` 挤到了 `//` 注释的下一行，就好像下面这段代码的样子：

```java
public class Test {
    public static void main(String[] args) {
        String name = "沉默王二";
        //
        name="沉默王三";
        System.out.println(name);
    }
}
```

那这算不算是 Java 的 bug 呢？说算也不算。

![](https://upload-images.jianshu.io/upload_images/1179389-d201d87d8289123e.gif?imageMogr2/auto-orient/strip)

>因为通过允许 Java 源代码包含 Unicode 字符，可以确保在世界上任何一个区域编写的代码在其他地方执行。

老实说，这段话是我从网上找到，好像明白点啥，又好像不明白。那再来看一段代码：

```java
double π = Math.PI;
System.out.println(\u03C0);
```

假如说程序员小王在创建周期率这个变量的时候，不知道 `π` 这个字符怎么敲出来，那么他就可以选择使用 `\u03C0` 来替代——编译器知道 `\u03C0` 就是 `π` 这个变量（编译器会在编译其他代码之前先解析 Unicode 字符）。

只能说 `\u000d` 是一种例外吧。

当然了，除非特殊情况，不要在源代码中包含 Unicode 字符，以免更改源代码的本意。

这篇文章没有别的意思，我也不想探究过于深奥的东西，纯粹是提高一下小伙伴们的认知：注释有可能被编译器执行。就好像，鲁迅如果不知道茴香豆的“茴”字有 4 种写法，那他就没办法让孔乙己在鲁镇的那家茶馆里装逼。

当然了，如果有小伙伴想体验一下装逼的感觉的话，可以把下面这段代码保存在一个名叫 Ugly.java 的文件中：

```java
\u0070\u0075\u0062\u006c\u0069\u0063\u0020\u0020\u0020\u0020

\u0063\u006c\u0061\u0073\u0073\u0020\u0055\u0067\u006c\u0079

\u007b\u0070\u0075\u0062\u006c\u0069\u0063\u0020\u0020\u0020

\u0020\u0020\u0020\u0020\u0073\u0074\u0061\u0074\u0069\u0063

\u0076\u006f\u0069\u0064\u0020\u006d\u0061\u0069\u006e\u0028

\u0053\u0074\u0072\u0069\u006e\u0067\u005b\u005d\u0020\u0020

\u0020\u0020\u0020\u0020\u0061\u0072\u0067\u0073\u0029\u007b

\u0053\u0079\u0073\u0074\u0065\u006d\u002e\u006f\u0075\u0074

\u002e\u0070\u0072\u0069\u006e\u0074\u006c\u006e\u0028\u0020

\u0022\u0048\u0065\u006c\u006c\u006f\u0020\u0077\u0022\u002b

\u0022\u006f\u0072\u006c\u0064\u0022\u0029\u003b\u007d\u007d
```

在命令行中先执行 `javac Ugly.java`，再执行 `java Ugly` 命令就可以看到程序结果了：

```
Hello world
```

体验过后，就拉到吧。反正写这样的代码谁也看不懂，除了机器。

好了，我亲爱的读者朋友，以上就是本文的全部内容了。是不是感觉认知边界又拓宽了？**原创不易，莫要白票，请你为本文点个赞吧**，这将是我写作更多优质文章的最强动力。

我是沉默王二，一枚有趣的程序员。如果觉得文章对你有点帮助，请微信搜索「 **沉默王二** 」第一时间阅读。

>回复【**666**】更有我为你精心准备的 500G 高清教学视频（已分门别类）。本文 [GitHub](https://github.com/qinggee/itwanger.github.io) 已经收录，有大厂面试完整考点，欢迎 Star。

