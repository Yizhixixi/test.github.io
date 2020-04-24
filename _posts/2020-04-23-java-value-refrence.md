---
layout: post
category: java
title: 面试官：兄弟，说说Java到底是值传递还是引用传递
tagline: by 沉默王二
tags: 
  - java
---

>二哥，好久没更新面试官系列的文章了啊，真的是把我等着急了，所以特意过来催催。我最近一段时间在找工作，能从二哥的文章中学到一点就多一点信心啊！

<!--more-->
说句实在话，离读者 trust you 发给我这段信息已经过去 1 周时间了。不是我怠慢，确实是可更新的内容实在是太多了。这不，又有两个读者不约而同地要求我更新一下 Java 到底是值传递还是引用传递方面的文章——其实这个[问题](https://mp.weixin.qq.com/s/nEYCb_42C4JhyatI-wFFAA)我之前是写过的，但现在看起来答案似乎不够尽善尽美，所以打算以面试的角度重写一篇。

七年前，我从温和湿润的苏州回到古色古香的洛阳，抱着一幅“天下我有”的心态“约谈”了几位面试官。其中有一位叫老马，让我印象深刻。因为他当时扔了一个面试题把我砸懵了：说说 Java 到底是值传递还是引用传递吧。

我当时年轻气盛，自认为所有的面试题都能对答如流，没想到被老马“刁难”了——原来洛阳这块互联网的荒漠也有技术专家啊。现在回想起来，脸上不自觉地就泛起了羞愧的红晕：当时真菜！不管怎么说，是时候写篇文章剖析一下值传递还是引用传递的区别了。

将参数传递给方法有两种常见的方式，一种是“值传递”，一种是“引用传递”。C 语言本身只支持值传递，它的衍生品 C++ 既支持值传递，也支持引用传递，而 Java 只支持值传递。

### 01、值传递 VS 引用传递

首先，我们必须要搞清楚，到底什么是值传递，什么是引用传递，否则，讨论 Java 到底是值传递还是引用传递就显得毫无意义。

当一个参数按照值的方式在两个方法之间传递时，调用者和被调用者其实是用的两个不同的变量——被调用者中的变量（原始值）是调用者中变量的一份拷贝，对它们当中的任何一个变量修改都不会影响到另外一个变量。

而当一个参数按照引用传递的方式在两个方法之间传递时，调用者和被调用者其实用的是同一个变量，当该变量被修改时，双方都是可见的。

Java 程序员之所以容易搞混值传递和引用传递，主要是因为 Java 有两种数据类型，一种是基本类型，比如说 int，另外一种是引用类型，比如说 String。

基本类型的变量存储的都是实际的值，而引用类型的变量存储的是对象的引用——指向了对象在内存中的地址。值和引用存储在 stack（栈）中，而对象存储在 heap（堆）中。


![](http://www.itwanger.com/assets/images/2020/04/java-value-refrence-01.png)


之所以有这个区别，是因为：

- 栈的优势是，存取速度比堆要快，仅次于直接位于 CPU 中的寄存器。但缺点是，栈中的数据大小与生存周期必须是确定的。
- 堆的优势是可以动态地分配内存大小，生存周期也不必事先告诉编译器，Java 的垃圾回收器会自动收走那些不再使用的数据。但由于要在运行时动态分配内存，存取速度较慢。

### 02、基本类型的参数传递

众所周知，Java 有 8 种基本数据类型，分别是 int、long、byte、short、float、double 、char 和 boolean。它们的值直接存储在栈中，每当作为参数传递时，都会将原始值（实参）复制一份新的出来，给形参用。形参将会在被调用方法结束时从栈中清除。

来看下面这段代码：

```java
public class PrimitiveTypeDemo {
    public static void main(String[] args) {
        int age = 18;
        modify(age);
        System.out.println(age);
    }

    private static void modify(int age1) {
        age1 = 30;
    }
}
```

1）main 方法中的 age 是基本类型，所以它的值 18 直接存储在栈中。

2）调用 `modify()` 方法的时候，将为实参 age 创建一个副本（形参 age1），它的值也为 18，不过是在栈中的其他位置。

3）对形参 age 的任何修改都只会影响它自身而不会影响实参。

![](http://www.itwanger.com/assets/images/2020/04/java-value-refrence-02.png)


### 03、引用类型的参数传递

来看一段创建引用类型变量的代码：

```java
Writer writer = new Writer(18, "沉默王二");
```

writer 是对象吗？还是对象的引用？为了搞清楚这个问题，我们可以把上面的代码拆分为两行代码：

```java
Writer writer;
writer = new Writer(18, "沉默王二");
```

假如 writer 是对象的话，就不需要通过 new 关键字创建对象了，对吧？那也就是说，writer 并不是对象，在“=”操作符执行之前，它仅仅是一个变量。那谁是对象呢？`new Writer(18, "沉默王二")`，它是对象，存储于堆中；然后，“=”操作符将对象的引用赋值给了 writer 变量，于是 writer 此时应该叫对象引用，它存储在栈中，保存了对象在堆中的地址。

每当引用类型作为参数传递时，都会创建一个对象引用（实参）的副本（形参），该形参保存的地址和实参一样。

来看下面这段代码：

```java
public class ReferenceTypeDemo {
    public static void main(String[] args) {
        Writer a = new Writer(18);
        Writer b = new Writer(18);
        modify(a, b);

        System.out.println(a.getAge());
        System.out.println(b.getAge());
    }

    private static void modify(Writer a1, Writer b1) {
        a1.setAge(30);

        b1 = new Writer(18);
        b1.setAge(30);
    }
}
```

1）在调用 `modify()` 方法之前，实参 a 和 b 指向的对象是不一样的，尽管 age 都为 18。

![](http://www.itwanger.com/assets/images/2020/04/java-value-refrence-03.png)

2）在调用 `modify()` 方法时，实参 a 和 b 都在栈中创建了一个新的副本，分别是 a1 和 b1，但指向的对象是一致的（a 和 a1 指向对象 a，b 和 b1 指向对象 b）。

![](http://www.itwanger.com/assets/images/2020/04/java-value-refrence-04.png)

3）在 `modify()` 方法中，修改了形参 a1 的 age 为 30，意味着对象 a 的 age 从 18 变成了 30，而实参 a 指向的也是对象 a，所以 a 的 age 也变成了 30；形参 b1 指向了一个新的对象，随后 b1 的 age 被修改为 30。

![](http://www.itwanger.com/assets/images/2020/04/java-value-refrence-05.png)

修改 a1 的 age，意味着同时修改了 a 的 age，因为它们指向的对象是一个；修改 b1 的 age，对 b 却没有影响，因为它们指向的对象是两个。

程序输出的结果如下所示：

```
30
18
```

果然和我们的分析是吻合的。



### 04、最后

好了，我亲爱的读者朋友，以上就是本文的全部内容了。看完之后，再遇到面试官问 Java 到底是值传递还是引用传递时，就不用担心被刁难了。我是沉默王二，一枚有趣的程序员。**原创不易，莫要白票，请你为本文点赞个吧**，这将是我写作更多优质文章的最强动力。



>如果觉得文章对你有点帮助，请微信搜索「 **沉默王二** 」第一时间阅读，回复【**666**】更有我为你精心准备的 500G 高清教学视频（已分门别类）。本文 [GitHub](https://github.com/qinggee/itwanger.github.io) 已经收录，有大厂面试完整考点，欢迎 Star。

