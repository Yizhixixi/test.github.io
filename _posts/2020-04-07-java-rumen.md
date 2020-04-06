---
layout: post
category: life
title: 写给小白看的入门级 Java 基本语法，强烈推荐
tagline: by 沉默王二
tags: 
  - java
---

之前写的一篇[我去](https://mp.weixin.qq.com/s/fbTzH5B7mSr5v0tQ8mV2wA)阅读量非常不错，但有一句留言深深地刺痛了我：



<!--more-->

>培训班学习半年，工作半年，我现在都看不懂你这篇文章，甚至看不下去，对于我来说有点深。

从表面上看，这句话有点讽刺我的文章写得不够通俗易懂的意味，但我心胸一直如大海一般开阔，你了解的。所以，我回他，“你还不如把培训费给我，哈哈。”怎么样，你也能体会到我的幽默，以及无情吧？当然了，是时候写一篇文章（也许是一个系列哦）照顾一下顾小白群体的情绪了，帮他们入入门或者回炉再造下。

![](http://www.itwanger.com/assets/images/2020/04/java-rumen-01.gif)


众所周知，Java 是一门面向对象的编程语言。它最牛逼的地方就在于它是跨平台的，你可以在 Windows 操作系统上编写 Java 源代码，然后在 Linux 操作系统上执行编译后的字节码，而无需对源代码做任何的修改。

### 01、数据类型

Java 有 2 种数据类型，一种是基本数据类型，一种是引用类型。

基本数据类型用于存储简单类型的数据，比如说，int、long、byte、short 用于存储整数，float、double 用于存储浮点数，char 用于存储字符，boolean 用于存储布尔值。

不同的基本数据类型，有不同的默认值和大小，来个表格感受下。

数据类型|默认值|大小
---|---|---
boolean|false|1比特
char|'\u0000'|2字节
byte|0|1字节
short|0|2字节
int|0|4字节
long|0L|8字节
float|0.0f|4字节
double|0.0|8字节


引用类型用于存储对象（null 表示没有值的对象）的引用，String 是引用类型的最佳代表，比如说 `String cmower = "沉默王二"`。

### 02、声明变量

要声明一个变量，必须指定它的名字和类型，来看一个简单的示例：

```java
int age;
String name;
```

count 和 name 在声明后会得到一个默认值，按照它们的数据类型——不能是局部变量（否则 Java 编译器会在你使用变量的时候提醒要先赋值），必须是类成员变量。

```java
public class SyntaxLocalVariable {
    int age;
    String name;

    public static void main(String[] args) {
        SyntaxLocalVariable syntax = new SyntaxLocalVariable();
        System.out.println(syntax.age); // 输出 0
        System.out.println(syntax.name);  // 输出 null
    }
}
```

也可以在声明一个变量后使用“=”操作符进行赋值，就像下面这样：

```java
int age = 18;
String name = "沉默王二";
```

我们定义了 2 个变量，int 类型的 age 和 String 类型的 name，age 赋值 18，name 赋值为“沉默王二”。

每行代码后面都跟了一个“;”，表示当前语句结束了。

在 Java 中，变量最好遵守命名约定，这样能提高代码的可阅读性。

- 以字母、下划线（_）或者美元符号（$）开头
- 不能使用 Java 的保留字，比如说 int 不能作为变量名

### 03、数组

数组在 Java 中占据着重要的位置，它是很多集合类的底层实现。数组属于引用类型，它用来存储一系列指定类型的数据。

声明数组的一般语法如下所示：

```java
type[] identiier = new type[length];
```

type 可以是任意的基本数据类型或者引用类型。来看下面这个例子：

```java
public class ArraysDemo {
    public static void main(String[] args) {
        int [] nums = new int[10];
        nums[0] = 18;
        nums[1] = 19;
        System.out.println(nums[0]);
    }
}
```

数组的索引从 0 开始，第一个元素的索引为 0，第二个元素的索引为 1。为什么要这样设计？感兴趣的话，你可以去探究一下。

通过变量名[索引]的方式可以访问数组指定索引处的元素，赋值或者取值是一样的。

### 04、关键字

关键字属于保留字，在 Java 中具有特殊的含义，比如说 public、final、static、new 等等，它们不能用来作为变量名。为了便于你作为参照，我列举了 48 个常用的关键字，你可以瞅一瞅。

1.  **abstract：** abstract 关键字用于声明抽象类——可以有抽象和非抽象方法。

2.  **boolean：** boolean 关键字用于将变量声明为布尔值类型，它只有 true 和 false 两个值。

3.  **break：** break 关键字用于中断循环或 switch 语句。

4.  **byte：** byte 关键字用于声明一个可以容纳 8 个比特的变量。

5.  **case：** case 关键字用于在 switch 语句中标记条件的值。

6.  **catch：** catch 关键字用于捕获 try 语句中的异常。

7.  **char：** char 关键字用于声明一个可以容纳无符号 16 位比特的 Unicode 字符的变量。

8.  **class：** class 关键字用于声明一个类。

9.  **continue：** continue 关键字用于继续下一个循环。它可以在指定条件下跳过其余代码。

10.  **default：** default 关键字用于指定 switch 语句中除去 case 条件之外的默认代码块。

11.  **do：** do 关键字通常和 while 关键字配合使用，do 后紧跟循环体。

12.  **double：** double 关键字用于声明一个可以容纳 64 位浮点数的变量。

13.  **else：** else 关键字用于指示 if 语句中的备用分支。

14.  **enum：** enum（枚举）关键字用于定义一组固定的常量。

15.  **extends：** extends 关键字用于指示一个类是从另一个类或接口继承的。

16.  **final：** final 关键字用于指示该变量是不可更改的。

17.  **finally：** finally 关键字和 `try-catch` 配合使用，表示无论是否处理异常，总是执行 finally 块中的代码。

18.  **float：** float 关键字用于声明一个可以容纳 32 位浮点数的变量。

19.  **for：** for 关键字用于启动一个 for 循环，如果循环次数是固定的，建议使用 for 循环。

20.  **if：** if 关键字用于指定条件，如果条件为真，则执行对应代码。

21.  **implements：** implements 关键字用于实现接口。

22.  **import：** import 关键字用于导入对应的类或者接口。

23.  **instanceof：** instanceof 关键字用于判断对象是否属于某个类型（class）。

24.  **int：** int 关键字用于声明一个可以容纳 32 位带符号的整数变量。

25.  **interface：** interface 关键字用于声明接口——只能具有抽象方法。

26.  **long：** long 关键字用于声明一个可以容纳 64 位整数的变量。

27.  **native：** native 关键字用于指定一个方法是通过调用本机接口（非 Java）实现的。

28.  **new：** new 关键字用于创建一个新的对象。

29.  **null：** 如果一个变量是空的（什么引用也没有指向），就可以将它赋值为 null。

30.  **package：** package 关键字用于声明类所在的包。

31.  **private：** private 关键字是一个访问修饰符，表示方法或变量只对当前类可见。

32.  **protected：** protected 关键字也是一个访问修饰符，表示方法或变量对同一包内的类和所有子类可见。

33.  **public：** public 关键字是另外一个访问修饰符，除了可以声明方法和变量（所有类可见），还可以声明类。`main()` 方法必须声明为 public。

34.  **return：** return 关键字用于在代码执行完成后返回（一个值）。

35.  **short：** short 关键字用于声明一个可以容纳 16 位整数的变量。

36.  **static：** static 关键字表示该变量或方法是静态变量或静态方法。

37.  **strictfp：**  strictfp 关键字并不常见，通常用于修饰一个方法，确保方法体内的浮点数运算在每个平台上执行的结果相同。

38.  **super：** super 关键字可用于调用父类的方法或者变量。

39.  **switch：** switch 关键字通常用于三个（以上）的条件判断。

40.  **synchronized：** synchronized 关键字用于指定多线程代码中的同步方法、变量或者代码块。

41.  **this：** this 关键字可用于在方法或构造函数中引用当前对象。

42.  **throw：** throw 关键字主动抛出异常。

43.  **throws：** throws 关键字用于声明异常。

44.  **transient：** transient 关键字在序列化的使用用到，它修饰的字段不会被序列化。

45.  **try：** try 关键字用于包裹要捕获异常的代码块。

46.  **void：** void 关键字用于指定方法没有返回值。

47.  **volatile：** volatile 关键字保证了不同线程对它修饰的变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。

48.  **while：** 如果循环次数不固定，建议使用 while 循环。

![](http://www.itwanger.com/assets/images/2020/04/java-rumen-02.png)


### 05、操作符

除去“=”赋值操作符，Java 中还有很多其他作用的操作符，我们来大致看一下。

①、算术运算符

- +（加号）
- –（减号）
- *（乘号）
- /（除号）
- ％（取余）

来看一个例子：

```java
public class ArithmeticOperator {
    public static void main(String[] args) {
        int a = 10;
        int b = 5;
        
        System.out.println(a + b);//15  
        System.out.println(a - b);//5  
        System.out.println(a * b);//50  
        System.out.println(a / b);//2  
        System.out.println(a % b);//0  
    }
}
```

“+”号比较特殊，还可以用于字符串拼接，来看一个例子：

```java
String result = "沉默王二" + "一枚有趣的程序员";
```

②、逻辑运算符

逻辑运算符通常用于布尔表达式，常见的有：

- &&（AND）多个条件中只要有一个为 false 结果就为 false
- ||（OR）多个条件只要有一个为 true 结果就为 true
- !（NOT）条件如果为 true，加上“!”就为 false，否则，反之。

来看一个例子：

```java
public class LogicalOperator {
    public static void main(String[] args) {
        int a=10;
        int b=5;
        int c=20;
        System.out.println(a<b&&a<c);//false
        System.out.println(a>b||a<c);//true
        System.out.println(!(a<b)); // true
    }
}
```

③、比较运算符

- `<` (小于)
- `<=` (小于或者等于)
- `>` (大于)
- `>=` (大于或者等于)
- `==` (相等)
- `!=` (不等)

### 06、程序结构

Java 中最小的程序单元叫做类，一个类可以有一个或者多个字段（也叫作成员变量），还可以有一个或者多个方法，甚至还可以有一些内部类。

如果一个类想要执行，就必须有一个 main 方法——程序运行的入口，就好像人的嘴一样，嗯，可以这么牵强的理解一下。

```java
public class StructureProgram {
    public static void main(String[] args) {
        System.out.println("没有成员变量，只有一个 main 方法");
    }
}
```

- 类名叫做 StructureProgram，在它里面，只有一个 main 方法。
- `{}` 之间的代码称之为代码块。
- 以上源代码将会保存在一个后缀名为 java 的文件中。

### 07、编译然后执行代码

通常，一些教程在介绍这块内容的时候，建议你通过命令行中先执行 `javac` 命令将源代码编译成字节码文件，然后再执行 `java` 命令指定代码。

但我不希望这个糟糕的局面再继续下去了——新手安装配置 JDK 真的蛮需要勇气和耐心的，稍有不慎，没入门就先放弃了。况且，在命令行中编译源代码会遇到很多莫名其妙的错误，这对新手是极其致命的——如果你再遇到这种老式的教程，可以吐口水了。

好的方法，就是去下载 IntelliJ IDEA，简称 IDEA，它被业界公认为最好的 Java 集成开发工具，尤其在智能代码助手、代码自动提示、代码重构、代码版本管理(Git、SVN、Maven)、单元测试、代码分析等方面有着亮眼的发挥。IDEA 产于捷克（位于东欧），开发人员以严谨著称。IDEA 分为社区版和付费版两个版本，新手直接下载社区版就足够用了。

安装成功后，可以开始敲代码了，然后直接右键运行（连保存都省了），结果会在 Run 面板中显示，如下图所示。

![](http://www.itwanger.com/assets/images/2020/04/java-rumen-03.png)

想查看反编译后的字节码的话，可以在 src 的同级目录 target/classes 的包路径下找到一个 StructureProgram.class 的文件（如果找不到的话，在目录上右键选择「Reload from Disk」）。

可以双击打开它。

```java
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cmower.baeldung.basic;

public class StructureProgram {
    public StructureProgram() {
    }

    public static void main(String[] args) {
        System.out.println("没有成员变量，只有一个 main 方法");
    }
}
```

IDEA 默认会用 Fernflower 将 class 字节码反编译为我们可以看得懂的 Java 代码。实际上，class 字节码（请安装 show bytecode 插件）长下面这个样子：

```
// class version 57.65535 (-65479)
// access flags 0x21
public class com/cmower/baeldung/basic/StructureProgram {

  // compiled from: StructureProgram.java

  // access flags 0x1
  public <init>()V
   L0
    LINENUMBER 3 L0
    ALOAD 0
    INVOKESPECIAL java/lang/Object.<init> ()V
    RETURN
   L1
    LOCALVARIABLE this Lcom/cmower/baeldung/basic/StructureProgram; L0 L1 0
    MAXSTACK = 1
    MAXLOCALS = 1

  // access flags 0x9
  public static main([Ljava/lang/String;)V
   L0
    LINENUMBER 5 L0
    GETSTATIC java/lang/System.out : Ljava/io/PrintStream;
    LDC "\u6ca1\u6709\u6210\u5458\u53d8\u91cf\uff0c\u53ea\u6709\u4e00\u4e2a main \u65b9\u6cd5"
    INVOKEVIRTUAL java/io/PrintStream.println (Ljava/lang/String;)V
   L1
    LINENUMBER 6 L1
    RETURN
   L2
    LOCALVARIABLE args [Ljava/lang/String; L0 L2 0
    MAXSTACK = 2
    MAXLOCALS = 1
}
```

新手看起来还是有些懵逼的，建议过过眼瘾就行了。

![](http://www.itwanger.com/assets/images/2020/04/java-rumen-04.png)


好了，我亲爱的读者朋友，以上就是本文的全部内容了，源代码已上传到码云，点击阅读原文就可以跳转过去了。学任何一门编程语言，在我看来，方法都是一样的，那就是不停地去实践，别觉得浅显易懂就不肯动手去尝试——你的左手和右手就是你最好的老师。

### 觉得有点用记得给我点赞哦！😎

简单介绍一下。10 年前，当我上大学的时候，专业被调剂到了计算机网络，主要学的是 Java 编程语言，但当时没怎么好好学，每年都要挂科两三门；因此工作后吃了不少亏。但是最近几年，情况发生了很大改变，你应该也能看得到我这种变化。通过坚持不懈地学习，持续不断地输出，我的编程基本功算得上是突飞猛进。

为了帮助更多的程序员，我创建了“**沉默王二**”这个 ID，专注于分享有趣的 Java 技术编程和有益的程序人生。一开始，阅读量寥寥无几，关注人数更是少得可怜。但随着影响力的逐步扩大，阅读量和关注人都在猛烈攀升。

你在看这篇文章的时候，应该也能发现，我在 CSDN 上的总排名已经来到了第 71 位，这个排名还是非常给力的。有很多读者都说，我可以冲击第一名，我不愿意藏着掖着，我是有这个野心的。

如果你也喜欢我的文章，请记得微信搜索「**沉默王二**」关注我的原创公众号，回复“**1024**”更有美团技术大佬整理的 Java 面试攻略相送，还有架构师的面试视频哦。

绝对不容错过，期待与你的不期而遇。

![](http://www.itwanger.com/assets/images/cmower_6.png)