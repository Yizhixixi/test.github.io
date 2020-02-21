---
layout: post
category: java
title: 面试官：Java的重写和重载有什么区别？
tagline: by 沉默王二
tag: java
---

老读者都知道了，七年前，我从美女很多的苏州回到美女更多的洛阳（美化了），抱着一幅“从二线城市退居三线城市”的心态，投了不少简历，也“约谈”了不少面试官，但仅有两三个令我感到满意。其中有一位叫老马，至今还活在我的微信通讯录里。他当时扔了一个面试题把我砸懵了：“**王二，Java 的重写（Override）和重载（Overload）有什么区别？**”

<!--more-->





那年我二十三岁，正值青春年华，大约就是周杰伦发布《八度空间》的年纪，自认为所有的面试题都能对答如流，结果没想到啊，被“刁难”了——原来洛阳这块互联网的荒漠也有技术专家啊。现在回想起来，脸上不自觉地泛起了羞愧的红晕：主要是自己当时太菜了。不管怎么说，七年时间过去了，我的技术功底已经非常扎实，有能力写篇文章剖析一下 Java 的重写和重载了，只要能给后来者一些参考，我就觉得做了天大的善事。

![](http://www.itwanger.com/assets/images/2020/02/java-override-overload-01.png)

好了，让我们来步入正题。先来看一段重写的代码吧。

```java
class LaoWang{
    public void write() {
        System.out.println("老王写了一本《基督山伯爵》");
    }
}
public class XiaoWang extends LaoWang {
    @Override
    public void write() {
        System.out.println("小王写了一本《茶花女》");
    }
}
```

重写的两个方法名相同，方法参数的个数也相同；不过一个方法在父类中，另外一个在子类中。就好像父类 LaoWang 有一个 `write()` 方法（无参），方法体是写一本《基督山伯爵》；子类 XiaoWang 重写了父类的 `write()` 方法（无参），但方法体是写一本《茶花女》。

来写一段测试代码。

```java
public class OverridingTest {
    public static void main(String[] args) {
        LaoWang wang = new XiaoWang();
        wang.write();
    }
}
```

大家猜结果是什么？

```
小王写了一本《茶花女》
```

在上面的代码中，们声明了一个类型为 LaoWang 的变量 wang。在编译期间，编译器会检查 LaoWang 类是否包含了 `write()` 方法，发现 LaoWang 类有，于是编译通过。在运行期间，new 了一个 XiaoWang 对象，并将其赋值给 wang，此时 Java 虚拟机知道 wang 引用的是 XiaoWang 对象，所以调用的是子类 XiaoWang 中的 `write()` 方法而不是父类 LaoWang  中的 `write()` 方法，因此输出结果为“小王写了一本《茶花女》”。


再来看一段重载的代码吧。

```java
class LaoWang{
    public void read() {
        System.out.println("老王读了一本《Web全栈开发进阶之路》");
    }
    
    public void read(String bookname) {
        System.out.println("老王读了一本《" + bookname + "》");
    }
}
```

重载的两个方法名相同，但方法参数的个数不同，另外也不涉及到继承，两个方法在同一个类中。就好像类 LaoWang 有两个方法，名字都是 `read()`，但一个有参数（书名），另外一个没有（只能读写死的一本书）。

来写一段测试代码。

```java
public class OverloadingTest {
    public static void main(String[] args) {
        LaoWang wang = new LaoWang();
        wang.read();
        wang.read("金瓶梅");
    }
}
```

这结果就不用猜了。变量 wang 的类型为 LaoWang，`wang.read()` 调用的是无参的 `read()` 方法，因此先输出“老王读了一本《Web全栈开发进阶之路》”；`wang.read("金瓶梅")` 调用的是有参的 `read(bookname)` 方法，因此后输出“老王读了一本《金瓶梅》”。在编译期间，编译器就知道这两个 `read()` 方法时不同的，因为它们的方法签名（=方法名称+方法参数）不同。

简单的来总结一下：

1）编译器无法决定调用哪个重写的方法，因为只从变量的类型上是无法做出判断的，要在运行时才能决定；但编译器可以明确地知道该调用哪个重载的方法，因为引用类型是确定的，参数个数决定了该调用哪个方法。

2）多态针对的是重写，而不是重载。


![](http://www.itwanger.com/assets/images/2020/02/java-override-overload-02.png)

哎，后悔啊，早年我要是能把这道面试题吃透的话，也不用被老马刁难了。吟一首诗感慨一下人生吧。

>青青园中葵，朝露待日晞。
阳春布德泽，万物生光辉。
常恐秋节至，焜黄华叶衰。
百川东到海，何时复西归?
少壮不努力，老大徒伤悲


另外，我想要告诉大家的是，重写（Override）和重载（Overload）是 Java 中两个非常重要的概念，新手经常会被它们俩迷惑，因为它们俩的英文名字太像了，中文翻译也只差一个字。难，太难了。

![](http://www.itwanger.com/assets/images/2020/02/java-override-overload-03.png)



好了，我亲爱的读者朋友，以上就是本文的全部内容了。能看到这里的都是最优秀的程序员，二哥必须要伸出大拇指为你点个赞👍。如果觉得不过瘾，还想看到更多，我再推荐 3 篇：

[如何快速打好Java基础？](https://mp.weixin.qq.com/s/2_JvGgcyVwPpJ_eoAsnHVQ)
[强烈推荐10本程序员在家读的书](https://mp.weixin.qq.com/s/8H_0d63lPKed22et0yv_3w)
[在三线城市工作爽吗？](https://mp.weixin.qq.com/s/fSK1jBndow9zKvVDsq4qRA)

原创不易，如果觉得有点用的话，请不要吝啬你手中点赞的权力，因为这将是我最强的写作动力。我们下篇文章见！

![](http://www.itwanger.com/assets/images/cmower_5.png)