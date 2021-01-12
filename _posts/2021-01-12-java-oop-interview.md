---
layout: post
category: java
title: 精心为你准备了 10 道 OOP 方面的 Java 面试题
tagline: by 沉默王二
tags: 
  - java
---


按照惯例，2 月份是一波面试找工作的高峰期，我就是 2014 年的 2 月份回的三线城市。 不过，当时傻乎乎的没有刷面试题，幸好三线城市要求的面试题不是很过分，能答得上来。

<!--more-->



如果你年后也有跳槽的打算，我还是建议你提前做好准备。今天，我为你精心准备了 13 道 OOP 方面的 Java 面试题，如果你觉得有收获的话，别忘了点赞分享啊。

**第一题，Java 是什么？**

Java 是一门计算机编程语言，高级、健壮、面向对象，并且非常安全。它由 Sun 公司在 1995 年开发，主力开发叫 James Gosling，被称为 Java 之父。

其实 Oracle 官网有一段关于 Java 的定义，是这样的：

>Java 是由 Sun Microsystems 在 1995 年首先发布的编程语言和计算平台。有许多应用程序和 Web 站点只有在安装 Java 后才能正常工作，而且这样的应用程序和 Web 站点日益增多。Java 快速、安全、可靠。从笔记本电脑到数据中心，从游戏控制台到科学超级计算机，从手机到互联网，Java 无处不在！

**第二题，什么是 OOP ？**

面向对象编程——Object Oriented Programming，简称 OOP，是一种程序设计思想。OOP 把对象作为程序的基本单元，一个对象包含了成员变量和操作成员变量的方法。

面向过程的程序设计把计算机程序视为一系列的命令集合，即一组方法的顺序执行。为了简化程序设计，面向过程把方法继续切分为子方法，即把大块方法通过切割成小块方法来降低系统的复杂度。

而面向对象的程序设计把计算机程序视为一组对象的集合，而每个对象都可以接收其他对象发过来的消息，并处理这些消息，计算机程序的执行就是一系列消息在各个对象之间传递。

OOP 有三大特征：

- 封装
- 多态
- 继承

面向对象编程的语言不只有 Java，还有 C#、C++、Python 等等。如果面试官有闲情逸致的话，还可以给他讲一个小学课本上的《女娲造人》。

女娲要造人，首先要想的是所造的人长什么样，女娲为了方便就按照自己的样子捏了一个模板，后面她只需要按照这个模板来捏就行。在 OOP 中，这个模板就称为类。

女娲按照模板（类）捏了一个人，这个人就是具体的对象；人有很多器官，比如眼睛、耳朵、鼻子、大脑、手和脚，同样还有年龄、身高，女娲为了区别每个人就会给每个对象不同的基本特征。另外，捏出的人要会走路，会吃饭，会干活等行为方式。

在 OOP 中，对象的基本特征称为成员变量，行为方式称为方法，所以类是由成员变量和方法构成的；成员变量最好隐藏起来，当需要人做什么的时候，就通过方法来完成。暴露方法的行为称为封装。

女娲想着一个人太无聊，就给它捏个伴，暂且就叫它男人和女人吧，男人和女人都是根据这个模板来的，有着一样的基本特征和行为，只是某些特征（成员变量）和行为（方法）在实现上有差异，那么这种根据模板而捏造不同实现细节的类的行为称为多态，就是一个模板有男人和女人两种形态。

女娲为了省事就让人类自己繁衍后代，就有了父子的关系，子类具有父类的基本特征和行为，子类也可以改变这些特征和行为，这种父子关系实现称为继承。

**第三题，Java 有哪些特性？**

- 面向对象。

参照第二题。

- 平台无关性。

Java 是“一次编写，到处运行（Write Once，Run any Where）”的语言，因此采用 Java 语言编写的程序具有很好的可移植性，而保证这一点的正是 Java 的虚拟机机制。在引入虚拟机之后，Java 语言在不同的平台上运行不需要重新编译。

Java 语言使用 Java 虚拟机机制屏蔽了具体平台的相关信息，使得 Java 语言编译的程序只需要生成虚拟机上的目标代码，就可以在多种平台上不加修改地运行。

- 简单性。

Java 舍弃了很多 C++ 中难以理解的特性，比如操作符的重载和多继承等，而且 Java 语言加入了垃圾回收机制，解决了程序员需要管理内存的问题，使编程变得更加简单。

- 支持多线程。

Java 支持多个线程同时执行，并提供多线程之间的同步机制。

- 健壮性。

Java 的强类型机制、异常处理、垃圾回收机制等都是 Java 健壮性的重要保证。

- 高性能。

随着 JIT（Just in Time，即时编译）的发展，Java 的运行速度也越来越快。

**第四题，Java 是 100% 面向对象吗？**

不是的。Java 有 8 中基本数据类型，包括 boolean、char、byte、short、int、long、float 和 double，它们都不是对象。

![](http://www.itwanger.com/assets/images/2021/01/oop-interview-01.png)

**第五题，什么是抽象？**

抽象是指为了某种目的，对一个概念或一种现象包含的信息进行过滤，移除不相关的信息，只保留与某种最终目的相关的信息。例如，一个*皮质的足球*，我们可以过滤掉它的质料，得到更一般性的概念，也就是*球*。从另外一个角度看，抽象就是简化事物，抓住事物本质的过程。

在 Java 中，可以通过两种形式来体现抽象：抽象类和接口。

定义抽象类的时候需要用到关键字 abstract，放在 class 关键字前。

```java
public abstract class AbstractPlayer {
    abstract void play();
}
```

通过 extends 关键字可以继承抽象类。

```java
public class BasketballPlayer extends AbstractPlayer {
    @Override
    void play() {
        System.out.println("我是张伯伦，篮球场上得过 100 分");
    }
}
```

接口是通过 interface 关键字定义的，它可以包含一些常量和方法。

```java
public interface Electronic {
    // 常量
    String LED = "LED";

    // 抽象方法
    int getElectricityUse();

    // 静态方法
    static boolean isEnergyEfficient(String electtronicType) {
        return electtronicType.equals(LED);
    }

    // 默认方法
    default void printDescription() {
        System.out.println("电子");
    }
}
```

通过 implements 关键字可以实现一个接口。

```java
public class Computer implements Electronic {

    public static void main(String[] args) {
        new Computer();
    }

    @Override
    public int getElectricityUse() {
        return 0;
    }
}
```

**第六题，什么是封装？**

封装将类的某些信息隐藏在类的内部，不允许外部程序直接访问，只能通过该类提供的方法来实现对隐藏信息的操作和访问。

例如，一台计算机内部极其复杂，有主板、CPU、硬盘和内存， 而一般用户不需要了解它的内部细节，不需要知道主板的型号、CPU 主频、硬盘和内存的大小，于是计算机制造商用机箱把计算机封装起来，对外提供了一些接口，如鼠标、键盘和显示器等，这样做了以后，用户使用计算机就非常方便了。

封装最主要的功能在于我们能修改自己的实现代码，而不用修改那些调用我们代码的程序片段。

实现封装的具体步骤如下：

- 修改成员变量的可见性来限制对属性的访问，一般设为 private。
- 为每个成员变量创建一对赋值（setter）方法和取值（getter）方法，一般设为 public，用于属性的读写。
- 在赋值和取值方法中，加入成员变量控制语句（对成员变量值的合法性进行判断）。

```java
public class Student {
    private int id;
    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public static void main(String args[]) {
        Student student = new Student();
        student.setId(1034);
        student.setName("沉默王二");

        System.out.println("学生 id " + student.getId());
        System.out.println("学生姓名 " + student.getName());
    }
}
```

**第七题，抽象和封装有什么区别？**

抽象只关注对象的相关数据，并隐藏所有可能无关行为的细节。它隐藏了背景细节，但强调了要点，以减少复杂性并提高效率。抽象关注的是思想而非事件。

封装将成员变量和方法绑定到单个类中，同时限制了对某些成员变量的访问。封装意味着隐藏成员变量以保护成员变量免受外部访问，从而使应用程序某一部分的更改不会影响其他部分。

- 抽象和封装在“**定义**”上的差异 —— 抽象通过隐藏不相关的细节来强调对象的所有基本方面，以提高效率并消除复杂性。封装是一种数据隐藏机制，它将数据包装在一个胶囊中以限制来自外界的访问。

- 抽象和封装在“**功能**”上的差异 —— 抽象仅强调使复杂程序更简单的基本功能，而封装则是将成员变量和操作成员变量的方法绑定到单个类中。

- 抽象和封装在“**实现**”上的区别 —— 抽象由抽象类和接口实现，而封装则通过访问权限修饰符来实现。

**第八题，什么是继承？**

继承是面向对象编程中非常强大的一种机制，在 Java 中使用 extends 关键字来表示继承关系。当创建一个类时，总是在继承，如果没有明确指出要继承的类，总是隐式地从根类 Object 进行继承。

```java
class Person {
    public Person() {
    }
}

class Man extends Person {
    public Man() {
    }
}
```

Person 类隐式地继承了 Object 类，因此 Person 类也自动地拥有了 `hashCode()`、`equals()`、`toString()` 等方法。

Man 类继承了 Person 类，这样一来，Person 类就称为父类，Man类就称为子类。如果两个类存在继承关系，子类会自动继承父类的方法和成员变量，在子类中可以调用父类的方法和成员变量。

Java 只允许单继承，也就是说一个类最多只能显式地继承一个父类。但一个类却可以被多个类继承，也就是说一个类可以拥有多个子类。

**第九题，什么是多态？**

多态是同一个行为具有多个不同表现形式的能力，可以使程序具有良好的扩展性，并可以对所有类的对象进行通用处理。

在继承关系中，子类如果定义了一个与父类方法签名完全相同的方法，被称为方法重写（Override）。

```java
public class Shape {
    public void getArea() {
        System.out.println("形状");
    }
}

public class Rectangle extends Shape {
    @Override
    public void getArea() {
        System.out.println("矩形");

    }

    public static void main(String args[]) {
        Shape shape = new Rectangle();
        shape.getArea();
    }
}
```

方法重写和方法重载（Overload）不同，如果一个类有多个名字相同但参数个数不同的方法，我们通常称这些方法为方法重载。

```java
public class Calculator {
    public int subtract(int a, int b) {
        return a - b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public static void main(String args[]) {
        Calculator calculator = new Calculator();
        System.out.println("150 和 12 相差 " + calculator.subtract(150, 12));
        System.out.println("15.5 和 15.4 相差 " + calculator.subtract(15.50, 15.40));
    }
}
```

通过下面这幅图可以了解到，子类 Circle、Triangle、Square 继承了父类 Shape，并且重写了 `draw()` 方法用于绘制不同的形状。

![](http://www.itwanger.com/assets/images/2021/01/oop-interview-02.png)



方法重写用于提供父类已经声明的方法的特殊实现，是实现多态的基础条件。



**第十题，怎么区分关联、聚合、组合？**

能体现关联（Association）关系的例子有客户和订单之间的关系、公司和员工之间的关系、用户与电脑之间的关系。关联关系所涉及的两个类是处在同一层次上的。

```java
class Computer {
    public void develop() {
    }
}

class Person {
    private Computer computer;

    public Person(Computer computer) {
        this.computer = computer;
    }

    public void work() {
        computer.develop();
    }
}
```

聚合（Aggregation）是整体和个体之间的关系。聚合关系所涉及的两个类是处在不平等层次上的，一个代表整体，另一个代表部分，体现的是 has-a 的关系，比如班级和学生，班级如果不存在了学生还可以存在。

再比如说任何地址之间的关系。

```java
public class Person {
    private Address address;
}

class Address {
    private String city;
    private String state;
    private String country;
}
```

组合（Composition）关系是比聚合关系更强的关系，体现的是 is-a 的关系，比如说房子与房间，房子如果不存在了房间就没有存在的必要了。

再比如说车与轮胎、车门、车窗之间的关系。

```java
public class Car {
    private Tire[] tires;
    private Door[] doors;
    private Window[] windows;
}

class Tire {
}

class Door {
}

class Steering {
}

class Window {
}
```

好了，关于面向对象编程方面的面试题就先了解到这吧，如果能够对你在年后的面试中助一臂之力那就太好了，如果不能，温故一下封装、继承、多态方面的知识也是很有必要的。

PS：好朋友 Guide 哥的**面试突击第四版**可以下载了，GitHub 标星 95.3k，真的非常牛逼。第四版增加/修改的内容如下图所示。

![](http://www.itwanger.com/assets/images/2021/01/oop-interview-03.png)

如果你不知道获取方式的话，我提供给你一个：

链接:[https://pan.baidu.com/s/19voBuI_zR4_E9qrk0iAUYA](https://pan.baidu.com/s/19voBuI_zR4_E9qrk0iAUYA)  密码:413c

**❤看完两件事**：

如果你觉得这篇内容对你挺有帮助，我想邀请你帮我两个忙：

1. **点赞**，让更多的人也能看到这篇内容（收藏不点赞，等于耍流氓😁）
2. **关注我**，让我们成为长期关系

**谢谢你的支持！**