---
layout: post
category: java
title: 那个小白说他还没搞懂类和对象，我一怒之下把这篇文章扔给了他
tagline: by 沉默王二
tags: 
  - java
---

>二哥，我就是上次说你《教妹学Spring》看不懂的那个小白，没想到你还特意写了一篇[入门级的 Java 基础知识](https://mp.weixin.qq.com/s/s0vUZEGnuXqtEF62-XmkuA)，这次真的看懂了，感觉好棒。请原谅我上次的唐突，二哥能够照顾我们这些小白的学习进度，真的是良心了。

<!--more-->

以上是读者 KEL 在上一篇基础知识文章发布后特意给我发来的信息，说实话，看完后蛮感动的，良心没有被辜负啊。于是，我愉快地决定了，每隔一两周就写一篇入门级的文章给小白们看。



![](http://www.itwanger.com/assets/images/2020/04/java-class-object-01.png)


类和对象是 Java 中最基本的两个概念，可以说撑起了面向对象编程（OOP）的一片天。对象可以是现实中看得见的任何物体（一只特立独行的猪），也可以是想象中的任何虚拟物体（能七十二变的孙悟空），Java 通过类（class）来定义这些物体，有什么状态（通过字段，或者叫成员变量定义，比如说猪的颜色是纯色还是花色），有什么行为（通过方法定义，比如说猪会吃，会睡觉）。

来，让我来定义一个简单的类给你看看。

```java
public class Pig {
    private String color;

    public void eat() {
        System.out.println("吃");
    }
}
```

默认情况下，每个 Java 类都会有一个空的构造方法，尽管它在源代码中是缺省的，但却可以通过反编译字节码看到它。

```java
public class Pig {
    private String color;

    public Pig() {
    }

    public void eat() {
        System.out.println("吃");
    }
}
```

没错，就是多出来的那个 `public Pig() {}`，参数是空的，方法体是空的。我们可以通过 new 关键字利用这个构造方法来创建一个对象，代码如下所示：

```java
 Pig pig = new Pig();
```

当然了，我们也可以主动添加带参的构造方法。

```java
public class Pig {
    private String color;

    public Pig(String color) {
        this.color = color;
    }

    public void eat() {
        System.out.println("吃");
    }
}
```

这时候，再查看反编译后的字节码时，你会发现缺省的无参构造方法消失了——和源代码一模一样。

```java
public class Pig {
    private String color;

    public Pig(String color) {
        this.color = color;
    }

    public void eat() {
        System.out.println("吃");
    }
}
```

这意味着无法通过 `new Pig()` 来创建对象了——编译器会提醒你追加参数。

![](http://www.itwanger.com/assets/images/2020/04/java-class-object-02.png)

比如说你将代码修改为 `new Pig("纯白色")`，或者添加无参的构造方法。

```java
public class Pig {
    private String color;

    public Pig(String color) {
        this.color = color;
    }

    public Pig() {
    }

    public void eat() {
        System.out.println("吃");
    }
}
```

使用无参构造方法创建的对象状态默认值为 null（color 字符串为引用类型），如果是基本类型的话，默认值为对应基本类型的默认值，比如说 int 为 0，更详细的见下图。

![](http://www.itwanger.com/assets/images/2020/04/java-class-object-03.png)


接下来，我们来创建多个 Pig 对象，它的颜色各不相同。

```java
public class PigTest {
    public static void main(String[] args) {
        Pig pigNoColor = new Pig();
        Pig pigWhite = new Pig("纯白色");
        Pig pigBlack = new Pig("纯黑色");
    }
}
```

你看，我们创建了 3 个不同花色的 Pig 对象，全部来自于一个类，由此可见类的重要性，只需要定义一次，就可以多次使用。

那假如我想改变对象的状态呢？该怎么办？目前毫无办法，因为没有任何可以更改状态的方法，直接修改 color 是行不通的，因为它的访问权限修饰符是 private 的。

最好的办法就是为 Pig 类追加 getter/setter 方法，就像下面这样：

```java
public String getColor() {
    return color;
}

public void setColor(String color) {
    this.color = color;
}
```

通过 `setColor()` 方法来修改，通过 `getColor()` 方法获取状态，它们的权限修饰符是 public 的。

```java
Pig pigNoColor = new Pig();
pigNoColor.setColor("花色");
System.out.println(pigNoColor.getColor()); // 花色
```

为什么要这样设计呢？可以直接将 color 字段的访问权限修饰符换成是 public 的啊，不就和 getter/setter 一样的效果了吗？

因为有些情况，某些字段是不允许被随意修改的，它只有在对象创建的时候初始化一次，比如说猪的年龄，它只能每年长一岁（举个例子），没有月光宝盒让它变回去。

```java
private int age;

public int getAge() {
    return age;
}

public void increaseAge() {
    this.age++;
}
```

你看，age 就没有 setter 方法，只有一个每年可以调用一次的 `increaseAge()` 方法和 getter 方法。如果把 age 的访问权限修饰符更改为 public，age 就完全失去控制了，可以随意将其重置为 0 或者负数。

访问权限修饰符对于 Java 来说，非常重要，目前共有四种：public、private、protected 和 default（缺省）。

一个类只能使用 `public` 或者 `default` 修饰，public 修饰的类你之前已经见到过了，现在我来定义一个缺省权限修饰符的类给你欣赏一下。

```java
class Dog {
}
```

哈哈，其实也没啥可以欣赏的。缺省意味着这个类可以被同一个包下的其他类进行访问；而 public 意味着这个类可以被所有包下的类进行访问。

假如硬要通过 private 和 protected 来修饰类的话，编译器会生气的，它不同意。

![](http://www.itwanger.com/assets/images/2020/04/java-class-object-04.png)

private 可以用来修饰类的构造方法、字段和方法，只能被当前类进行访问。protected 也可以用来修饰类的构造方法、字段和方法，但它的权限范围更宽一些，可以被同一个包中的类进行访问，或者当前类的子类。

可以通过下面这张图来对比一下四个权限修饰符之间的差别：

![](http://www.itwanger.com/assets/images/2020/04/java-class-object-05.png)

- 同一个类中，不管是哪种权限修饰符，都可以访问；
- 同一个包下，private 修饰的无法访问；
- 子类可以访问 public 和 protected 修饰的；
- public 修饰符面向世界，哈哈，可以被所有的地方访问到。

![](http://www.itwanger.com/assets/images/2020/04/java-class-object-06.png)

好了，我亲爱的读者朋友，本文到此就打算戛然而止了，有什么不满意的，尽管留言，我保证给你上墙的机会。

我是沉默王二，一枚有趣的程序员，如果觉得文章对你有点帮助，请微信搜索「 **沉默王二** 」第一时间阅读。 **原创不易，莫要白票，请你为本文点赞个吧**，这将是我写作更多优质文章的最强动力。

>本文已同步到 GitHub，欢迎 star，[传送门~](https://github.com/qinggee/itwanger.github.io)