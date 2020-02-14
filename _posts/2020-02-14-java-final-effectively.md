---
layout: post
category: java
title: 老哥，Java 中 final 和 effectively final 到底有什么区别？
tagline: by 沉默王二
tag: java
---

之前写了一篇 Lambda 的入门文章，有小伙伴很认真地参考着学习了一下，并且在学习的过程中提出了新的问题：“老哥，当我在使用 Lambda 表达式中使用变量的时候，编译器提醒我‘Local variable limit defined in an enclosing scope must be final or effectively final’，意思就是‘从 Lambda 表达式引用的本地变量必须是最终变量或实际上的最终变量’，这其中的 final 和 effectively final 到底有什么区别呢？”

<!--more-->




看到这个问题，我忍不住调皮了，不假思索地回答说：“当然是 effectively 啦！”

那老弟竟然没被我气晕过去，紧接着说：“老哥，我知道你幽默，你风趣，这节骨眼你能不能细致给我说说这两者之间的区别啊？在线等。”

然后我细细致致地又给他聊了半个多小时，总算是解释清楚了。临了，他还特意叮嘱我说：“老哥，别忘了写篇文章啊！让更多的小伙伴知道这个区别。”

![](http://www.itwanger.com/assets/images/2020/02/java-final-01.png)

现在的年轻人啊，真特么有心，未来是你的！我承认我是个负责任的男人，既然如此，下面我就来详细地谈一谈 final 和 effectively final 之间的区别。

先来看看官方（Java 8 的规格说明书）是怎么说的，如下：

>a final variable can be defined as An entity once that cannot be changed nor derived from later and an effectively final defined as variable or parameter whose value is never changed after it is initialized is effectively final.

就像你看到的，上面这段解释并没有针对关键词“effectively”进行详细的说明——看得云里雾里的，等于没看。

来看下面这段代码：

```java
int limit = 10;
Runnable r = () -> {
    limit = 5;
    for (int i = 0; i < limit; i++) {
        System.out.println(i);
    }
};
new Thread(r).start();
```

编译器会提醒我们“Lambda 表达式中的变量必须是 final 或者 effectively final”，按照编译器的提示，我们把 limit 变量修饰为 final，但这时候，编译器提示了新的错误。

![](http://www.itwanger.com/assets/images/2020/02/java-final-02.png)

这次的错误就很明确了，final 变量是不能被重新赋值的——众所周知，这正是 final 关键字的作用——于是我们把 `limit = 5` 这行代码去掉。

```java
final int limit = 10;
Runnable r = () -> {
    for (int i = 0; i < limit; i++) {
        System.out.println(i);
    }
};
new Thread(r).start();
```

考虑到 limit 在接下来的代码中并未被重新赋值，我们可以将 final 关键字去掉。

```java
int limit = 10;
Runnable r = () -> {
    for (int i = 0; i < limit; i++) {
        System.out.println(i);
    }
};
new Thread(r).start();
```

代码仍然可以正常编译，正常运行，那么此时的 limit 变量就是“effectively final”的。由于 limit 在接下来的代码中没有被重新赋值，编译器就被欺骗了，想当然地认为 limit 就是一个 final 变量（实际上的最终变量）。

假如 limit 在声明为普通的变量（没有 final 修饰）后又被重新赋值了，那也就不可能成为“effectively final”了。

因此得出的结论是，“effectively final”是一个行为类似于“final”的变量，但没有将其声明为“final”变量，关键就在于编译器是怎么看待的。

![](http://www.itwanger.com/assets/images/2020/02/java-final-03.gif)

好了，我亲爱的读者朋友，以上就是本文的全部内容了。喜欢的话，就点个在看。