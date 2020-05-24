---
layout: post
category: java
title: 技术大佬：我去，你竟然还不会用 this 关键字
tagline: by 沉默王二
tags: 
  - java
---

[上一篇](https://mp.weixin.qq.com/s/Ra9zotaUTsm3z-UMimB5pg)文章写的是 Spring Boot 的入门，结果有读者留言说，Java 都还没搞完，搞什么 Spring Boot，唬得我一愣一愣的。那这篇就继续来搞 Java，推出广受好评的我去系列第四集：你竟然还不会用 this 关键字。

<!--more-->

“老大，能给说详细地说说 this 关键字吗，总感觉对这个关键字的认知不够全面。”小王又过来找我了，他问的态度很谦逊，很卑微，但我还是忍不住破口大骂：“我擦，小王，你丫的竟然不会用 this，我当初是怎么面试你进来的！”


![](http://www.itwanger.com/assets/images/2020/05/java-this-super-01.png)

小王被我这句话吓坏了，赶紧躲到自己岗位上改 bug 去了。我呢，加班加点开始写这篇文章，真良心用苦啊。在 Java 中，this 关键字指的是当前对象（它的方法正在被调用）的引用，能理解吧，各位亲？不理解的话，我们继续往下看。

看完再不明白，你过来捶爆我，我保证不还手，只要不打脸。

### 01、消除字段歧义

我敢赌一毛钱，所有的读者，不管男女老少，应该都知道这种用法，毕竟写构造方法的时候经常用啊。谁要不知道，过来，我给你发一毛钱红包，只要你脸皮够厚。

```java
public class Writer {
    private int age;
    private String name;

    public Writer(int age, String name) {
        this.age = age;
        this.name = name;
    }
}
```

Writer 类有两个成员变量，分别是 age 和 name，在使用有参构造函数的时候，如果参数名和成员变量的名字相同，就需要使用 this 关键字消除歧义：this.age 是指成员变量，age 是指构造方法的参数。

### 02、引用类的其他构造方法

当一个类的构造方法有多个，并且它们之间有交集的话，就可以使用 this 关键字来调用不同的构造方法，从而减少代码量。

比如说，在无参构造方法中调用有参构造方法：

```java
public class Writer {
    private int age;
    private String name;

    public Writer(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public Writer() {
        this(18, "沉默王二");
    }
}
```

也可以在有参构造方法中调用无参构造方法：

```java
public class Writer {
    private int age;
    private String name;

    public Writer(int age, String name) {
        this();
        this.age = age;
        this.name = name;
    }

    public Writer() {
    }
}
```

需要注意的是，`this()` 必须是构造方法中的第一条语句，否则就会报错。

![](http://www.itwanger.com/assets/images/2020/05/java-this-super-02.png)

### 03、作为参数传递

在下例中，有一个无参的构造方法，里面调用了 `print()` 方法，参数只有一个 this 关键字。

```java
public class ThisTest {
    public ThisTest() {
        print(this);
    }

    private void print(ThisTest thisTest) {
        System.out.println("print " +thisTest);
    }

    public static void main(String[] args) {
        ThisTest test = new ThisTest();
        System.out.println("main " + test);
    }
}
```

来打印看一下结果：

```java
print com.cmower.baeldung.this1.ThisTest@573fd745
main com.cmower.baeldung.this1.ThisTest@573fd745
```

从结果中可以看得出来，this 就是我们在 `main()` 方法中使用 new 关键字创建的 ThisTest 对象。

### 04、链式调用

学过 JavaScript，或者 jQuery 的读者可能对链式调用比较熟悉，类似于 `a.b().c().d()`，仿佛能无穷无尽调用下去。

在 Java 中，对应的专有名词叫 Builder 模式，来看一个示例。

```java
public class Writer {
    private int age;
    private String name;
    private String bookName;
    
    public Writer(WriterBuilder builder) {
        this.age = builder.age;
        this.name = builder.name;
        this.bookName = builder.bookName;
    }

    public static class WriterBuilder {
        public String bookName;
        private int age;
        private String name;

        public WriterBuilder(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public WriterBuilder writeBook(String bookName) {
            this.bookName = bookName;
            return this;
        }

        public Writer build() {
            return new Writer(this);
        }
    }
}
```

Writer 类有三个成员变量，分别是 age、name 和 bookName，还有它们仨对应的一个构造方法，参数是一个内部静态类 WriterBuilder。

内部类 WriterBuilder 也有三个成员变量，和 Writer 类一致，不同的是，WriterBuilder 类的构造方法里面只有 age 和 name 赋值了，另外一个成员变量 bookName 通过单独的方法 `writeBook()` 来赋值，注意，该方法的返回类型是 WriterBuilder，最后使用 return 返回了 this 关键字。

最后的 `build()` 方法用来创建一个 Writer 对象，参数为 this 关键字，也就是当前的 WriterBuilder 对象。

这时候，创建 Writer 对象就可以通过链式调用的方式。

```java
Writer writer = new Writer.WriterBuilder(18,"沉默王二")
                .writeBook("《Web全栈开发进阶之路》")
                .build();
```

### 05、在内部类中访问外部类对象

说实话，自从 Java 8 的函数式编程出现后，就很少用到 this 在内部类中访问外部类对象了。来看一个示例：

```java
public class ThisInnerTest {
    private String name;
    
    class InnerClass {
        public InnerClass() {
            ThisInnerTest thisInnerTest = ThisInnerTest.this;
            String outerName = thisInnerTest.name;
        }
    }
}
```

在内部类 InnerClass 的构造方法中，通过外部类.this 可以获取到外部类对象，然后就可以使用外部类的成员变量了，比如说 name。

### 06、关于 super

本来想单独写一篇 super 关键字的，但可写的内容不多。本质上，this 关键字和 super 关键字有蛮多相似之处的，所以，就放在 this 这篇文章的末尾说一说吧。

简而言之，super 关键字就是用来访问父类的。

先来看父类：

```java
public class SuperBase {
    String message = "父类";

    public SuperBase(String message) {
        this.message = message;
    }

    public SuperBase() {
    }

    public void printMessage() {
        System.out.println(message);
    }
}
```

再来看子类：

```java
public class SuperSub extends SuperBase {
    String message = "子类";

    public SuperSub(String message) {
        super(message);
    }

    public SuperSub() {
        super.printMessage();
        printMessage();
    }

    public void getParentMessage() {
        System.out.println(super.message);
    }

    public void printMessage() {
        System.out.println(message);
    }
}
```

1）super 关键字可用于访问父类的构造方法

你看，子类可以通过 `super(message)` 来调用父类的构造方法。现在来新建一个 SuperSub 对象，看看输出结果是什么：

```java
SuperSub superSub = new SuperSub("子类的message");
```

new 关键字在调用构造方法创建子类对象的时候，会通过 super 关键字初始化父类的 message，所以此此时父类的 message 会输出“子类的message”。

2）super 关键字可以访问父类的变量

上述例子中的 SuperSub 类中就有，`getParentMessage()` 通过 `super.message` 方法父类的同名成员变量 message。

3）当方法发生重写时，super 关键字可以访问父类的同名方法

上述例子中的 SuperSub 类中就有，无参的构造方法 `SuperSub()` 中就使用 `super.printMessage()` 调用了父类的同名方法。


### 07、总结

亲爱的读者朋友，我应该说得很全面了吧？我想小王看到了这篇文章后一定会感谢我的良苦用心的，他毕竟是个积极好学的好同事啊。

如果觉得文章对你有点帮助，请微信搜索「 **沉默王二** 」第一时间阅读，回复「**并发**」更有一份阿里大牛重写的 Java 并发编程实战，从此再也不用担心面试官在这方面的刁难了。

>本文已收录 GitHub，[**传送门~**](https://github.com/qinggee/itwanger.github.io) ，里面更有大厂面试完整考点，欢迎 Star。

我是沉默王二，一枚有颜值却靠才华苟且的程序员。**关注即可提升学习效率，别忘了三连啊，点赞、收藏、留言，我不挑，嘻嘻**。