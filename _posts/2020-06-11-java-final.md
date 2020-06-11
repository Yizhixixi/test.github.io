---
layout: post
category: java
title: 小公司技术总监：我去，你竟然还不会用 final 关键字
tagline: by 沉默王二
tags: 
  - java
---

写一篇文章容易吗？太不容易了，首先，需要一个安静的环境，这一点就非常不容易。很多小伙伴的办公室都是开放式的，非常吵，况且上班时间写的话，领导就不高兴了；只能抽时间写。其次，环境有了，还要有一颗安静的心，如果心里装着其他挥之不去的事，那就糟糕了，呆坐着电脑前一整天也不会有结果。

<!--more-->



我十分佩服一些同行，他们写万字长文，这在我看来，几乎不太可能完成。因为我要日更，一万字的长文，如果走原创的话，至少需要一周时间，甚至一个月的时间。

就如小伙伴们看到的，我写的文章大致都能在五分钟内阅读完，并且能够保证小伙伴们在阅读完学到或者温习到一些知识。这就是我的风格，通俗易懂，轻松幽默。

好了，又一篇我去系列的文章它来了：你竟然还不会用 final 关键字。

已经晚上 9 点半了，我还没有下班，因为要和小王一块修复一个 bug。我订了一份至尊披萨，和小王吃得津津有味的时候，他突然问了我一个问题：“老大，能给我详细地说说 final 关键字吗，总感觉对这个关键字的认知不够全面。”

一下子我的火气就来了，尽管小王问的态度很谦逊，很卑微，但我还是忍不住破口大骂：“我擦，小王，你丫的竟然不会用 final，我当初是怎么面试你进来的！”

![](http://www.itwanger.com/assets/images/2020/06/java-final-01.png)

发火归发火，我这个人还是有原则的，等十点半回到家后，我决定为小王专门写一篇文章，好好地讲一讲 final 关键字，也希望给更多的小伙伴一些帮助。

尽管[继承](https://mp.weixin.qq.com/s/q-dMxOXxT8N3W6ftmNWkWQ)可以让我们重用现有代码，但有时处于某些原因，我们确实需要对可扩展性进行限制，final 关键字可以帮助我们做到这一点。

### 01、final 类

如果一个类使用了 final 关键字修饰，那么它就无法被继承。如果小伙伴们细心观察的话，Java 就有不少 final 类，比如说最常见的 String 类。

```java
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence,
               Constable, ConstantDesc {}
```

为什么 String 类要设计成 final 的呢？原因大致有以下三个：

- 为了实现字符串常量池
- 为了线程安全
- 为了 HashCode 的不可变性

更详细的原因，可以查看我之前写的一篇[文章](https://mp.weixin.qq.com/s/CRQrm5zGpqWxYL_ztk-b2Q)。

任何尝试从 final 类继承的行为将会引发编译错误，为了验证这一点，我们来看下面这个例子，Writer 类是 final 的。

```java
public final class Writer {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

尝试去继承它，编译器会提示以下错误，Writer 类是 final 的，无法继承。

![](http://www.itwanger.com/assets/images/2020/06/java-final-02.png)

不过，类是 final 的，并不意味着该类的对象是不可变的。

```java
Writer writer = new Writer();
writer.setName("沉默王二");
System.out.println(writer.getName()); // 沉默王二
```

Writer 的 name 字段的默认值是 null，但可以通过 settter 方法将其更改为“沉默王二”。也就是说，如果一个类只是 final 的，那么它并不是不可变的全部条件。

如果，你想了解不可变类的全部真相，请查看我之前写的文章[这次要说不明白immutable类，我就怎么地](https://mp.weixin.qq.com/s/wbdV9rV60AwWiiTEBYPP7g)。突然发现，写系列文章真的妙啊，很多相关性的概念全部涉及到了。我真服了自己了。

把一个类设计成 final 的，有其安全方面的考虑，但不应该故意为之，因为把一个类定义成 final 的，意味着它没办法继承，假如这个类的一些方法存在一些问题的话，我们就无法通过重写的方式去修复它。

![](http://www.itwanger.com/assets/images/2020/06/java-final-03.png)


### 02、final 方法

被 final 修饰的方法不能被重写。如果我们在设计一个类的时候，认为某些方法不应该被重写，就应该把它设计成 final 的。

Thread 类就是一个例子，它本身不是 final 的，这意味着我们可以扩展它，但它的 `isAlive()` 方法是 final 的：

```java
public class Thread implements Runnable {
    public final native boolean isAlive();
}
```

需要注意的是，该方法是一个本地（native）方法，用于确认线程是否处于活跃状态。而本地方法是由操作系统决定的，因此重写该方法并不容易实现。

Actor 类有一个 final 方法 `show()`：

```java
public class Actor {
    public final void show() {
        
    }
}
```

当我们想要重写该方法的话，就会出现编译错误：

![](http://www.itwanger.com/assets/images/2020/06/java-final-04.png)

如果一个类中的某些方法要被其他方法调用，则应考虑事被调用的方法称为 final 方法，否则，重写该方法会影响到调用方法的使用。

一个类是 final 的，和一个类不是 final，但它所有的方法都是 final 的，考虑一下，它们之间有什么区别？

我能想到的一点，就是前者不能被继承，也就是说方法无法被重写；后者呢，可以被继承，然后追加一些非 final 的方法。没毛病吧？看把我聪明的。

![](http://www.itwanger.com/assets/images/2020/06/java-final-05.png)


### 03、final 变量

被 final 修饰的变量无法重新赋值。换句话说，final 变量一旦初始化，就无法更改。之前被一个小伙伴问过，什么是 effective final，什么是 final，这一点，我在之前的文章也有阐述过，所以这里再贴一下地址：

>[http://www.itwanger.com/java/2020/02/14/java-final-effectively.html](http://www.itwanger.com/java/2020/02/14/java-final-effectively.html)

1）final 修饰的基本数据类型

来声明一个 final 修饰的 int 类型的变量：

```java
final int age = 18;
```

尝试将它修改为 30，结果编译器生气了：

![](http://www.itwanger.com/assets/images/2020/06/java-final-06.png)

2）final 修饰的引用类型

现在有一个普通的类 Pig，它有一个字段 name：

```java
public class Pig {
   private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

在测试类中声明一个 final 修饰的 Pig 对象：

```java
 final Pig pig = new Pig();
```

如果尝试将 pig 重新赋值的话，编译器同样会生气：

![](http://www.itwanger.com/assets/images/2020/06/java-final-07.png)

但我们仍然可以去修改 Pig 的字段值：

```java
final Pig pig = new Pig();
pig.setName("特立独行");
System.out.println(pig.getName()); // 特立独行
```

3）final 修饰的字段

final 修饰的字段可以分为两种，一种是 static 的，另外一种是没有 static 的，就像下面这样：

```java
public class Pig {
   private final int age = 1;
   public static final double PRICE = 36.5;
}
```

非 static 的 final 字段必须有一个默认值，否则编译器将会提醒没有初始化：

![](http://www.itwanger.com/assets/images/2020/06/java-final-08.png)

static 的 final 字段也叫常量，它的名字应该为大写，可以在声明的时候初始化，也可以通过 static [代码块初始化]()。

4) final 修饰的参数

final 关键字还可以修饰参数，它意味着参数在方法体内不能被再修改：

```java
public class ArgFinalTest {
    public void arg(final int age) {
    }

    public void arg1(final String name) {
    }
}
```

如果尝试去修改它的话，编译器会提示以下错误：

![](http://www.itwanger.com/assets/images/2020/06/java-final-09.png)


### 04、总结

亲爱的读者朋友，我应该说得很全面了吧？我想小王看到了这篇文章后一定会感谢我的良苦用心的，他毕竟是个积极好学的好同事啊。

如果觉得文章对你有点帮助，请微信搜索「 **沉默王二** 」第一时间阅读，回复「**并发**」更有一份阿里大牛重写的 Java 并发编程实战，从此再也不用担心面试官在这方面的刁难了。

>本文已收录 GitHub，[**传送门~**](https://github.com/qinggee/itwanger.github.io) ，里面更有大厂面试完整考点，欢迎 Star。

我是沉默王二，一枚有颜值却靠才华苟且的程序员。**关注即可提升学习效率，别忘了三连啊，点赞、收藏、留言，我不挑，嘻嘻**。

