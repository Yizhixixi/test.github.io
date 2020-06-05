---
layout: post
category: java
title: 面试官：兄弟，说说Java的static关键字吧
tagline: by 沉默王二
tags: 
  - java
---

读者乙在上一篇[我去](https://mp.weixin.qq.com/s/MIX4srqeg7STzphhR6Gp5g)系列文章里留言说，“我盲猜下一篇标题是，‘我去，你竟然不知道 static 关键字’”。我只能说乙猜对了一半，像我这么有才华的博主，怎么可能被读者猜中了心思呢，必须搞点不一样的啊，所以本篇文章的标题你看到了。

<!--more-->

![](http://www.itwanger.com/assets/images/2020/06/java-static-01.gif)

七年前，我从美女很多的苏州回到美女也不少的洛阳，抱着一幅“从二线城市退居三线城市”的心态，投了不少简历，也“约谈”了不少面试官，但仅有两三个令我感到满意。其中有一位叫老马，至今还活在我的微信通讯录里。他当时扔了一个面试题把我砸懵了：“**兄弟，说说 Java 的 static 关键字吧。**”

我那时候二十三岁，正值青春年华，自认为所有的面试题都能对答如流，结果没想到啊，被“刁难”了——原来洛阳这块互联网的荒漠也有技术专家啊。现在回想起来，脸上不自觉地泛起了羞愧的红晕：主要是自己当时太菜了。

不管怎么说，经过多年的努力，我现在的技术功底已经非常扎实了，有能力写篇文章剖析一下 Java 的 static 关键字了——只要能给初学者一些参考，我就觉得非常满足。

先来个提纲挈领（唉呀妈呀，成语区博主上线了）吧：

>static 关键字可用于变量、方法、代码块和内部类，表示某个特定的成员只属于某个类本身，而不是该类的某个对象。

### 01、静态变量

静态变量也叫类变量，它属于一个类，而不是这个类的对象。

```java
public class Writer {
    private String name;
    private int age;
    public static int countOfWriters;

    public Writer(String name, int age) {
        this.name = name;
        this.age = age;
        countOfWriters++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```

其中，countOfWriters 被称为静态变量，它有别于 name 和 age 这两个成员变量，因为它前面多了一个修饰符 `static`。

这意味着无论这个类被初始化多少次，静态变量的值都会在所有类的对象中共享。

```java
Writer w1 = new Writer("沉默王二",18);
Writer w2 = new Writer("沉默王三",16);

System.out.println(Writer.countOfWriters);
```

按照上面的逻辑，你应该能推理得出，countOfWriters 的值此时应该为 2 而不是 1。从内存的角度来看，静态变量将会存储在 Java 虚拟机中一个名叫“Metaspace”（元空间，Java 8 之后）的特定池中。

静态变量和成员变量有着很大的不同，成员变量的值属于某个对象，不同的对象之间，值是不共享的；但静态变量不是的，它可以用来统计对象的数量，因为它是共享的。就像上面例子中的 countOfWriters，创建一个对象的时候，它的值为 1，创建两个对象的时候，它的值就为 2。

简单小结一下：

1）由于静态变量属于一个类，所以不要通过对象引用来访问，而应该直接通过类名来访问；

![](http://www.itwanger.com/assets/images/2020/06/java-static-02.png)

2）不需要初始化类就可以访问静态变量。

```java
public class WriterDemo {
    public static void main(String[] args) {
        System.out.println(Writer.countOfWriters); // 输出 0
    }
}
```

### 02、静态方法

静态方法也叫类方法，它和静态变量类似，属于一个类，而不是这个类的对象。

```java
public static void setCountOfWriters(int countOfWriters) {
    Writer.countOfWriters = countOfWriters;
}
```

`setCountOfWriters()` 就是一个静态方法，它由 static 关键字修饰。

如果你用过 java.lang.Math 类或者 Apache 的一些工具类（比如说 StringUtils）的话，对静态方法一定不会感动陌生。

![](http://www.itwanger.com/assets/images/2020/06/java-static-03.png)

Math 类的几乎所有方法都是静态的，可以直接通过类名来调用，不需要创建类的对象。

![](http://www.itwanger.com/assets/images/2020/06/java-static-04.png)


简单小结一下：

1）Java 中的静态方法在编译时解析，因为静态方法不能被重写（方法重写发生在运行时阶段，为了多态）。

2）抽象方法不能是静态的。

![](http://www.itwanger.com/assets/images/2020/06/java-static-05.png)

3）静态方法不能使用 this 和 super 关键字。

4）成员方法可以直接访问其他成员方法和成员变量。

5）成员方法也可以直接方法静态方法和静态变量。

6）静态方法可以访问所有其他静态方法和静态变量。

7）静态方法无法直接访问成员方法和成员变量。

![](http://www.itwanger.com/assets/images/2020/06/java-static-06.png)


### 03、静态代码块

静态代码块可以用来初始化静态变量，尽管静态方法也可以在声明的时候直接初始化，但有些时候，我们需要多行代码来完成初始化。

```java
public class StaticBlockDemo {
    public static List<String> writes = new ArrayList<>();

    static {
        writes.add("沉默王二");
        writes.add("沉默王三");
        writes.add("沉默王四");

        System.out.println("第一块");
    }

    static {
        writes.add("沉默王五");
        writes.add("沉默王六");

        System.out.println("第二块");
    }
}
```

writes 是一个静态的 ArrayList，所以不太可能在声明的时候完成初始化，因此需要在静态代码块中完成初始化。

简单小结一下：

1）一个类可以有多个静态代码块。

2）静态代码块的解析和执行顺序和它在类中的位置保持一致。为了验证这个结论，可以在 StaticBlockDemo 类中加入空的 main 方法，执行完的结果如下所示：

```
第一块
第二块
```

### 04、静态内部类

Java 允许我们在一个类中声明一个内部类，它提供了一种令人信服的方式，允许我们只在一个地方使用一些变量，使代码更具有条理性和可读性。

常见的内部类有四种，成员内部类、局部内部类、匿名内部类和静态内部类，限于篇幅原因，前三种不在我们本次文章的讨论范围，以后有机会再细说。

```java
public class Singleton {
    private Singleton() {}

    private static class SingletonHolder {
        public static final Singleton instance = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonHolder.instance;
    }
}
```

以上这段代码是不是特别熟悉，对，这就是创建单例的一种方式，第一次加载 Singleton 类时并不会初始化 instance，只有第一次调用 `getInstance()` 方法时 Java 虚拟机才开始加载 SingletonHolder 并初始化 instance，这样不仅能确保线程安全也能保证 Singleton 类的唯一性。不过，创建单例更优雅的一种方式是使用枚举。

简单小结一下：

1）静态内部类不能访问外部类的所有成员变量。

2）静态内部类可以访问外部类的所有静态变量，包括私有静态变量。

3）外部类不能声明为 static。

![](http://www.itwanger.com/assets/images/2020/06/java-static-07.png)


学到了吧？学到就是赚到。

我是沉默王二，一枚有趣的程序员。如果觉得文章对你有点帮助，请微信搜索「 **沉默王二** 」第一时间阅读，回复【**666**】更有我为你精心准备的 500G 高清教学视频（已分门别类）。

>本文 [GitHub](https://github.com/qinggee/itwanger.github.io) 已经收录，有大厂面试完整考点，欢迎 Star。

**原创不易，莫要白票，请你为本文点个赞吧**，这将是我写作更多优质文章的最强动力。