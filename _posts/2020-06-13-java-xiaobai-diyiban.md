---
layout: post
category: java
title: 怒肝俩月，新鲜出炉史上最有趣的Java小白手册，第一版，每个 Java 初学者都应该收藏
tagline: by 沉默王二
tags: 
  - java
---

这么说吧，在我眼里，Java 就是最流行的编程语言，没有之一（PHP 往一边站）。不仅岗位多，容易找到工作，关键是薪资水平也到位，不学 Java 亏得慌，对吧？

<!--more-->

那可能零基础学编程的小伙伴就会头疼了，网上关于 Java 的大部分技术文章都不够幽默，不够风趣，不够系列，急需要一份能看得进去的学习手册，那我觉得我肝的这份手册正好符合要求，并且会一直持续更新下去。

第一版的内容暂时包含两方面，Java 基础和 Java 面向对象编程。来吧，先上目录，一睹为快。

01、Java 基本语法简介
02、Java 基本数据类型简介
03、Java `main()` 方法简介
04、Java 的流程控制语句
05、Java 包的简介
06、Java 到底是值传递还是引用传递
07、Java 的类和对象
08、Java 构造方法
09、Java 抽象类
10、Java 接口
11、Java 继承
12、this 关键字
13、super 关键字
14、重写和重载
15、static 关键字
16、Java 枚举
17、final 关键字

目录欣赏完了，接下来就是拜读精华内容的时间，搬个小板凳，认认真真好好学吧，学到就是赚到！

## 一、Java 基本语法简介

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

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-01.png)


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

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-02.png)

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

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-03.png)

### 二、Java 基本数据类型简介

### 01、布尔

布尔（boolean）仅用于存储两个值：true 和 false，也就是真和假，通常用于条件的判断。代码示例：

```java
boolean flag = true;
```


### 02、byte

byte 的取值范围在 -128 和 127 之间，包含 127。最小值为 -128，最大值为 127，默认值为 0。

在网络传输的过程中，为了节省空间，常用字节来作为数据的传输方式。代码示例：


```java
byte a = 10;
byte b = -10;
```




### 03、short

short 的取值范围在 -32,768 和 32,767 之间，包含 32,767。最小值为 -32,768，最大值为 32,767，默认值为 0。代码示例：

```java
short s = 10000;
short r = -5000;
```



### 04、int

int 的取值范围在 -2,147,483,648（-2 ^ 31）和 2,147,483,647（2 ^ 31 -1）（含）之间，默认值为 0。如果没有特殊需求，整形数据就用 int。代码示例：

```java
int a = 100000;
int b = -200000;
```

### 05、long

long 的取值范围在 -9,223,372,036,854,775,808(-2^63) 和 9,223,372,036,854,775,807(2^63 -1)（含）之间，默认值为 0。如果 int 存储不下，就用 long，整形数据就用 int。代码示例：

```java
long a = 100000L; 
long b = -200000L;
```

为了和 int 作区分，long 型变量在声明的时候，末尾要带上大写的“L”。不用小写的“l”，是因为小写的“l”容易和数字“1”混淆。

### 06、float

float 是单精度的浮点数，遵循 IEEE 754（二进制浮点数算术标准），取值范围是无限的，默认值为 0.0f。float 不适合用于精确的数值，比如说货币。代码示例：

```java
float f1 = 234.5f;
```

为了和 double 作区分，float 型变量在声明的时候，末尾要带上小写的“f”。不需要使用大写的“F”，是因为小写的“f”很容易辨别。


### 07、double

double 是双精度的浮点数，遵循 IEEE 754（二进制浮点数算术标准），取值范围也是无限的，默认值为 0.0。double 同样不适合用于精确的数值，比如说货币。代码示例：

```java
double d1 = 12.3
```

那精确的数值用什么表示呢？最好使用 BigDecimal，它可以表示一个任意大小且精度完全准确的浮点数。针对货币类型的数值，也可以先乘以 100 转成整形进行处理。

Tips：单精度是这样的格式，1 位符号，8 位指数，23 位小数，有效位数为 7 位。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-04.png)

双精度是这样的格式，1 位符号，11 位指数，52 为小数，有效位数为 16 位。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-05.png)

取值范围取决于指数位，计算精度取决于小数位（尾数）。小数位越多，则能表示的数越大，那么计算精度则越高。

>一个数由若干位数字组成，其中影响测量精度的数字称作有效数字，也称有效数位。有效数字指科学计算中用以表示一个浮点数精度的那些数字。一般地，指一个用小数形式表示的浮点数中，从第一个非零的数字算起的所有数字。如 1.24 和 0.00124 的有效数字都有 3 位。

### 08、char

char 可以表示一个 16 位的 Unicode 字符，其值范围在 '\u0000'（0）和 '\uffff'（65,535）（包含）之间。代码示例：

```java
char letterA = 'A'; // 用英文的单引号包裹住。
```

## 三、Java main() 方法简介

每个程序都需要一个入口，对于 Java 程序来说，入口就是 main 方法。

```java
public static void main(String[] args) {}
```

public、static、void 这 3 个关键字在前面的内容已经介绍过了，如果觉得回去找比较麻烦的话，这里再贴一下：

- public 关键字是另外一个访问修饰符，除了可以声明方法和变量（所有类可见），还可以声明类。`main()` 方法必须声明为 public。

- static 关键字表示该变量或方法是静态变量或静态方法，可以直接通过类访问，不需要实例化对象来访问。

-  void 关键字用于指定方法没有返回值。

另外，main 关键字为方法的名字，Java 虚拟机在执行程序时会寻找这个标识符；args 为 `main()` 方法的参数名，它的类型为一个 String 数组，也就是说，在使用 java 命令执行程序的时候，可以给 `main()` 方法传递字符串数组作为参数。

```
java HelloWorld 沉默王二 沉默王三
```

javac 命令用来编译程序，java 命令用来执行程序，HelloWorld 为这段程序的类名，沉默王二和沉默王三为字符串数组，中间通过空格隔开，然后就可以在 `main()` 方法中通过 `args[0]` 和 `args[1]` 获取传递的参数值了。

```java
public class HelloWorld {
    public static void main(String[] args) {
        if ("沉默王二".equals(args[0])) {
            
        }
        
        if ("沉默王三".equals(args[1])) {
            
        }
    }
}
```

`main()` 方法的写法并不是唯一的，还有其他几种变体，尽管它们可能并不常见，可以简单来了解一下。

第二种，把方括号 `[]` 往 args 靠近而不是 String 靠近：

```java
public static void main(String []args) { }
```

第三种，把方括号 `[]` 放在 args 的右侧：

```java
public static void main(String args[]) { }
```

第四种，还可以把数组形式换成可变参数的形式：

```java
public static void main(String...args) { }
```

第五种，在 `main()` 方法上添加另外一个修饰符 `strictfp`，用于强调在处理浮点数时的兼容性：

```java
public strictfp static void main(String[] args) { }
```

也可以在 `main()` 方法上添加 final 关键字或者 synchronized 关键字。

第六种，还可以为 args 参数添加 final 关键字：

```java
public static void main(final String[] args) { }
```

第七种，最复杂的一种，所有可以添加的关键字统统添加上：

```java
final static synchronized strictfp void main(final String[] args) { }
```

当然了，并不需要为了装逼特意把 `main()` 方法写成上面提到的这些形式，使用 IDE 提供的默认形式就可以了。

### 四、Java 的流程控制语句

在 Java 中，有三种类型的流程控制语句：

- 条件分支，用于在两个或者多个条件之间做出选择，常见的有 `if/else/else if`、三元运算符和 switch 语句。

- 循环或者遍历，常见的有 for、while 和 do-while。

- break 和 continue，用于跳出循环或者跳过进入下一轮循环。

### if 语句

if 语句的格式如下：

```java
if(布尔表达式){  
// 如果条件为 true，则执行这块代码
} 
```

画个流程图表示一下：

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-06.png)

来写个示例：

```java
public class IfExample {
    public static void main(String[] args) {
        int age = 20;
        if (age < 30) {
            System.out.println("青春年华");
        }
    }
}
```

输出：

```
青春年华
```

### if-else 语句

if-else 语句的格式如下:

```java
if(布尔表达式){  
// 条件为 true 时执行的代码块
}else{  
// 条件为 false  时执行的代码块
}  
```

画个流程图表示一下：

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-07.png)

来写个示例：

```java
public class IfElseExample {
    public static void main(String[] args) {
        int age = 31;
        if (age < 30) {
            System.out.println("青春年华");
        } else {
            System.out.println("而立之年");
        }
    }
}
```

输出：

```
而立之年
```

除了这个例子之外，还有一个判断闰年（被 4 整除但不能被 100 整除或者被 400 整除）的例子：

```java
public class LeapYear {
    public static void main(String[] args) {
        int year = 2020;
        if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
            System.out.println("闰年");
        } else {
            System.out.println("普通年份");
        }
    }
}
```

输出：

```
闰年
```

如果执行语句比较简单的话，可以使用三元运算符来代替 if-else 语句，如果条件为 true，返回 ? 后面 : 前面的值；如果条件为 false，返回 : 后面的值。

```java
public class IfElseTernaryExample {
    public static void main(String[] args) {
        int num = 13;
        String result = (num % 2 == 0) ? "偶数" : "奇数";
        System.out.println(result);
    }
}
```

输出：

```
奇数
```

### if-else-if 语句

if-else-if 语句的格式如下：

```java
if(条件1){  
// 条件1 为 true 时执行的代码
}else if(条件2){  
// 条件2 为 true 时执行的代码
}  
else if(条件3){  
// 条件3 为 true 时执行的代码
}  
...  
else{  
// 以上条件均为 false 时执行的代码
} 
```

画个流程图表示一下：

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-08.png)

来写个示例：

```java
public class IfElseIfExample {
    public static void main(String[] args) {
        int age = 31;
        if (age < 30) {
            System.out.println("青春年华");
        } else if (age >= 30 && age < 40 ) {
            System.out.println("而立之年");
        } else if (age >= 40 && age < 50 ) {
            System.out.println("不惑之年");
        } else {
            System.out.println("知天命");
        }
    }
}
```

输出：

```
而立之年
```

### if 嵌套语句

if 嵌套语句的格式如下：

```java
if(外侧条件){    
     // 外侧条件为 true 时执行的代码 
          if(内侧条件){  
             // 内侧条件为 true 时执行的代码
    }    
}  
```

画个流程图表示一下：

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-09.png)

来写个示例：

```java
public class NestedIfExample {
    public static void main(String[] args) {
        int age = 20;
        boolean isGirl = true;
        if (age >= 20) {
            if (isGirl) {
                System.out.println("女生法定结婚年龄");
            }
        }
    }
}
```

输出：

```
女生法定结婚年龄
```

### switch 语句的格式：

```java
switch(变量) {    
case 可选值1:    
 // 可选值1匹配后执行的代码;    
 break;  // 该关键字是可选项
case 可选值2:    
 // 可选值2匹配后执行的代码;    
 break;  // 该关键字是可选项
......    
    
default: // 该关键字是可选项     
 // 所有可选值都不匹配后执行的代码 
}    
```

- 变量可以有 1 个或者 N 个值。

- 值类型必须和变量类型是一致的，并且值是确定的。

- 值必须是唯一的，不能重复，否则编译会出错。

- break 关键字是可选的，如果没有，则执行下一个 case，如果有，则跳出 switch 语句。

- default 关键字也是可选的。



画个流程图：

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-10.png)


来个示例：

```java
public class Switch1 {
    public static void main(String[] args) {
        int age = 20;
        switch (age) {
            case 20 :
                System.out.println("上学");
                break;
            case 24 :
                System.out.println("苏州工作");
                break;
            case 30 :
                System.out.println("洛阳工作");
                break;
            default:
                System.out.println("未知");
                break; // 可省略
        }
    }
}
```

输出：

```
上学
```

当两个值要执行的代码相同时，可以把要执行的代码写在下一个 case 语句中，而上一个 case 语句中什么也没有，来看一下示例：

```java
public class Switch2 {
    public static void main(String[] args) {
        String name = "沉默王二";
        switch (name) {
            case "詹姆斯":
                System.out.println("篮球运动员");
                break;
            case "穆里尼奥":
                System.out.println("足球教练");
                break;
            case "沉默王二":
            case "沉默王三":
                System.out.println("乒乓球爱好者");
                break;
            default:
                throw new IllegalArgumentException(
                        "名字没有匹配项");

        }
    }
}
```

输出：

```
乒乓球爱好者
```

枚举作为 switch 语句的变量也很常见，来看例子：

```java
public class SwitchEnumDemo {
    public enum PlayerTypes {
        TENNIS,
        FOOTBALL,
        BASKETBALL,
        UNKNOWN
    }

    public static void main(String[] args) {
        System.out.println(createPlayer(PlayerTypes.BASKETBALL));
    }

    private static String createPlayer(PlayerTypes playerType) {
        switch (playerType) {
            case TENNIS:
                return "网球运动员费德勒";
            case FOOTBALL:
                return "足球运动员C罗";
            case BASKETBALL:
                return "篮球运动员詹姆斯";
            case UNKNOWN:
                throw new IllegalArgumentException("未知");
            default:
                throw new IllegalArgumentException(
                        "运动员类型: " + playerType);

        }
    }
}
```

输出：

```
篮球运动员詹姆斯
```

### 循环语句比较

比较方式| for | while | do-while
----|----|----|----
简介|for 循环的次数是固定的|while 循环的次数是不固定的，并且需要条件为 true|do-while 循环的次数也不固定，但会至少执行一次循环，无聊条件是否为 true
何时使用|循环次数固定的|循环次数是不固定的|循环次数不固定，并且循环体至少要执行一次
语法|for(init:condition;++/--) {// 要执行的代码}|while(condition){// 要执行的代码}|do{//要执行的代码}while(condition);

### 普通的 for 循环

普通的 for 循环可以分为 4 个部分：

1）初始变量：循环开始执行时的初始条件。

2）条件：循环每次执行时要判断的条件，如果为 true，就执行循环体；如果为 false，就跳出循环。当然了，条件是可选的，如果没有条件，则会一直循环。

3）循环体：循环每次要执行的代码块，直到条件变为 false。

4）自增/自减：初识变量变化的方式。



来看一下普通 for 循环的格式：



```java
for(初识变量;条件;自增/自减){  
// 循环体
}  
```



画个流程图：

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-11.png)



来个示例：

```java
public class ForExample {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            System.out.println("沉默王二好帅啊");
        }
    }
}
```

输出：

```
沉默王二好帅啊
沉默王二好帅啊
沉默王二好帅啊
沉默王二好帅啊
沉默王二好帅啊
```

循环语句还可以嵌套呢，这样就可以打印出更好玩的呢。

```java
public class PyramidForExample {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0;j<= i;j++) {
                System.out.print("❤");
            }
            System.out.println();
        }
    }
}
```

打印出什么玩意呢？

```
❤
❤❤
❤❤❤
❤❤❤❤
❤❤❤❤❤
```


### for-each

for-each 循环通常用于遍历数组和集合，它的使用规则比普通的 for 循环还要简单，不需要初始变量，不需要条件，不需要下标来自增或者自减。来看一下语法：

```java
for(元素类型 元素 : 数组或集合){  
// 要执行的代码
}  
```


来看一下示例：

```java
public class ForEachExample {
    public static void main(String[] args) {
        String[] strs = {"沉默王二", "一枚有趣的程序员"};

        for (String str : strs) {
            System.out.println(str);
        }
    }
}
```

输出：

```
沉默王二
一枚有趣的程序员
```


### 无限 for 循环

想不想体验一下无限 for 循环的威力，也就是死循环？

```java
public class InfinitiveForExample {
    public static void main(String[] args) {
        for(;;){
            System.out.println("停不下来。。。。");
        }
    }
}
```

输出：

```
停不下来。。。。
停不下来。。。。
停不下来。。。。
停不下来。。。。
```

一旦运行起来，就停不下来了，除非强制停止。


### while 循环



```java
while(条件){  
//循环体  
}  
```



画个流程图：

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-12.png)




来个示例：

```java
public class WhileExample {
    public static void main(String[] args) {
        int i = 0;
        while (true) {
            System.out.println("沉默王二");
            i++;
            if (i == 5) {
                break;
            }
        }
    }
}
```

猜猜会输出几次？

```
沉默王二
沉默王二
沉默王二
沉默王二
沉默王二
```



### do-while 循环



```java
do{  
// 循环体
}while(提交);  
```



画个流程图：

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-13.png)





来个示例：

```java
public class DoWhileExample {
    public static void main(String[] args) {
        int i = 0;
        do {
            System.out.println("沉默王二");
            i++;
            if (i == 5) {
                break;
            }
        } while (true);
    }
}
```

程序输出结果如下所示：

```
沉默王二
沉默王二
沉默王二
沉默王二
沉默王二
```


### break 

break 关键字通常用于中断循环或 switch 语句，它在指定条件下中断程序的当前流程。如果是内部循环，则仅中断内部循环。

可以将 break 关键字用于所有类型循环语句中，比如说 [for 循环](https://blog.csdn.net/qing_gee/article/details/104833683)，[while 循环](https://blog.csdn.net/qing_gee/article/details/104861877)，以及 [do-while 循环](https://blog.csdn.net/qing_gee/article/details/104946934)。



来画个流程图感受一下：


![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-14.png)

用在 for 循环中的示例：

```java
for (int i = 1; i <= 10; i++) {
    if (i == 5) {
        break;
    }
    System.out.println(i);
}
```

用在嵌套 for 循环中的示例：

```java
for (int i = 1; i <= 3; i++) {
    for (int j = 1; j <= 3; j++) {
        if (i == 2 && j == 2) {
            break;
        }
        System.out.println(i + " " + j);
    }
}
```

用在 while 循环中的示例：

```java
int i = 1;
while (i <= 10) {
    if (i == 5) {
        i++;
        break;
    }
    System.out.println(i);
    i++;
}
```

用在 do-while 循环中的示例：

```java
int j = 1;
do {
    if (j == 5) { 
        j++;
        break;
    }
    System.out.println(j);
    j++;
} while (j <= 10);
```

### continue

当我们需要在 for 循环或者 （do）while 循环中立即跳转到下一个循环时，就可以使用 continue 关键字，通常用于跳过指定条件下的循环体，如果循环是嵌套的，仅跳过当前循环。

来个示例：

```java
public class ContinueDemo {
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            if (i == 5) {
                // 使用 continue 关键字
                continue;// 5 将会被跳过
            }
            System.out.println(i);
        }
    }
}
```

输出：

```
1
2
3
4
6
7
8
9
10
```

5 真的被跳过了。


再来个循环嵌套的例子。

```java
public class ContinueInnerDemo {
    public static void main(String[] args) {
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (i == 2 && j == 2) {
                    //  当i=2，j=2时跳过
                    continue;
                }
                System.out.println(i + " " + j);
            }
        }
    }
}
```

打印出什么玩意呢？

```
1 1
1 2
1 3
2 1
2 3
3 1
3 2
3 3
```

“2 2” 没有输出，被跳过了。

再来看一下 while 循环时 continue 的使用示例：

```java
public class ContinueWhileDemo {
    public static void main(String[] args) {
        int i = 1;
        while (i <= 10) {
            if (i == 5) {
                i++;
                continue;
            }
            System.out.println(i);
            i++;
        }
    }
}
```

输出：

```
1
2
3
4
6
7
8
9
10
```

注意：如果把 if 条件中的“i++”省略掉的话，程序就会进入死循环，一直在 continue。

最后，再来看一下 do-while 循环时 continue 的使用示例：

```java
public class ContinueDoWhileDemo {
    public static void main(String[] args) {
        int i=1;
        do{
            if(i==5){
                i++;
                continue;
            }
            System.out.println(i);
            i++;
        }while(i<=10);
    }
}

```

输出：

```
1
2
3
4
6
7
8
9
10
```

注意：同样的，如果把 if 条件中的“i++”省略掉的话，程序就会进入死循环，一直在 continue。

## 五、Java 包的简介

在 Java 中，我们使用 package（包）对相关的类、接口和子包进行分组。这样做的好处有：

- 使相关类型更容易查找
- 避免命名冲突，比如说 com.itwanger.Hello 和 com.itwangsan.Hello 不同
- 通过包和访问权限控制符来限定类的可见性

### 01、创建一个包

```java
package com.itwanger;
```

可以使用 package 关键字来定义一个包名，需要注意的是，这行代码必须处于一个类中的第一行。强烈建议在包中声明类，不要缺省，否则就失去了包结构的带来的好处。

包的命名应该遵守以下规则：

- 应该全部是小写字母
- 可以包含多个单词，单词之间使用“.”连接，比如说 `java.lang`
- 名称由公司名或者组织名确定，采用倒序的方式，比如说，我个人博客的域名是 `www.itwanger.com`，所以我创建的包名是就是 `com.itwanger.xxxx`。

每个包或者子包都在磁盘上有自己的目录结构，如果 Java 文件时在 `com.itwanger.xxxx` 包下，那么该文件所在的目录结构就应该是 `com->itwanger->xxxx`。

### 02、使用包

让我们在名为 test 的子包里新建一个 Cmower 类：

```java
package com.itwanger.test;

public class Cmower {
    private String name;
    private int age;
}
```

如果需要在另外一个包中使用 Cmower 类，就需要通过 import 关键字将其引入。有两种方式可供选择，第一种，使用 `*` 导入包下所有的类：

```java
import com.itwanger.test.*;
```

第二种，使用类名导入该类：

```java
import com.itwanger.test.Cmower;
```

Java 和第三方类库提供了很多包可供使用，可以通过上述的方式导入类库使用。

```java
package com.itwanger.test;

import java.util.ArrayList;
import java.util.List;

public class CmowerTest {
    public static void main(String[] args) {
        List<Cmower> list = new ArrayList<>();
        list.add(new Cmower());
    }
}
```

### 03、全名

有时，我们可能会使用来自不同包下的两个具有相同名称的类。例如，我们可能同时使用 `java.sql.Date` 和 `java.util.Date`。当我们遇到命名冲突时，我们需要对至少一个类使用全名（包名+类名）。

```java
List<com.itwanger.test.Cmower> list1 = new ArrayList<>();
list.add(new com.itwanger.test.Cmower());
```

### 六、Java 到底是值传递还是引用传递

将参数传递给方法有两种常见的方式，一种是“值传递”，一种是“引用传递”。C 语言本身只支持值传递，它的衍生品 C++ 既支持值传递，也支持引用传递，而 Java 只支持值传递。

### 01、值传递 VS 引用传递

首先，我们必须要搞清楚，到底什么是值传递，什么是引用传递，否则，讨论 Java 到底是值传递还是引用传递就显得毫无意义。

当一个参数按照值的方式在两个方法之间传递时，调用者和被调用者其实是用的两个不同的变量——被调用者中的变量（原始值）是调用者中变量的一份拷贝，对它们当中的任何一个变量修改都不会影响到另外一个变量。

而当一个参数按照引用传递的方式在两个方法之间传递时，调用者和被调用者其实用的是同一个变量，当该变量被修改时，双方都是可见的。

Java 程序员之所以容易搞混值传递和引用传递，主要是因为 Java 有两种数据类型，一种是基本类型，比如说 int，另外一种是引用类型，比如说 String。

基本类型的变量存储的都是实际的值，而引用类型的变量存储的是对象的引用——指向了对象在内存中的地址。值和引用存储在 stack（栈）中，而对象存储在 heap（堆）中。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-15.png)

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

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-16.png)


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

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-17.png)

2）在调用 `modify()` 方法时，实参 a 和 b 都在栈中创建了一个新的副本，分别是 a1 和 b1，但指向的对象是一致的（a 和 a1 指向对象 a，b 和 b1 指向对象 b）。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-18.png)

3）在 `modify()` 方法中，修改了形参 a1 的 age 为 30，意味着对象 a 的 age 从 18 变成了 30，而实参 a 指向的也是对象 a，所以 a 的 age 也变成了 30；形参 b1 指向了一个新的对象，随后 b1 的 age 被修改为 30。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-19.png)

修改 a1 的 age，意味着同时修改了 a 的 age，因为它们指向的对象是一个；修改 b1 的 age，对 b 却没有影响，因为它们指向的对象是两个。

程序输出的结果如下所示：

```
30
18
```

果然和我们的分析是吻合的。

## 七、Java 的类和对象

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

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-20.png)

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

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-21.png)

（图片中有一处错误，boolean 的默认值为 false）

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

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-22.png)

private 可以用来修饰类的构造方法、字段和方法，只能被当前类进行访问。protected 也可以用来修饰类的构造方法、字段和方法，但它的权限范围更宽一些，可以被同一个包中的类进行访问，或者当前类的子类。

可以通过下面这张图来对比一下四个权限修饰符之间的差别：

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-23.png)

- 同一个类中，不管是哪种权限修饰符，都可以访问；
- 同一个包下，private 修饰的无法访问；
- 子类可以访问 public 和 protected 修饰的；
- public 修饰符面向世界，哈哈，可以被所有的地方访问到。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-24.png)

## 八、Java 构造方法

假设现在有一个 Writer 类，它有两个字段，姓名和年纪：

```java
public class Writer {
    private String name;
    private int age;

    @Override
    public String toString() {
        return "Writer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

重写了 `toString()` 方法，用于打印 Writer 类的详情。由于没有构造方法，意味着当我们创建 Writer 对象时，它的字段值并没有初始化：

```java
Writer writer = new Writer();
System.out.println(writer.toString());
```

输出结果如下所示：

```
Writer{name='null', age=0}
```

name 是字符串类型，所以默认值为 null，age 为 int 类型，所以默认值为 0。

让我们为 Writer 类主动加一个无参的构造方法：

```java
public Writer() {
    this.name = "";
    this.age = 0;
}
```

构造方法也是一个方法，只不过它没有返回值，默认返回创建对象的类型。需要注意的是，当前构造方法没有参数，它被称为无参构造方法。如果我们没有主动创建无参构造方法的话，编译器会隐式地自动添加一个无参的构造方法。这就是为什么，一开始虽然没有构造方法，却可以使用 `new Writer()` 创建对象的原因，只不过，所有的字段都被初始化成了默认值。

接下来，让我们添加一个有参的构造方法：

```java
public Writer(String name, int age) {
    this.name = name;
    this.age = age;
}
```

现在，我们创建 Writer 对象的时候就可以通过对字段值初始化值了。

```java
Writer writer1 = new Writer("沉默王二",18);
System.out.println(writer1.toString());
```

来看一下打印结果：

```
Writer{name='沉默王二', age=18}
```

可以根据字段的数量添加不同参数数量的构造方法，比如说，我们可以单独为 name 字段添加一个构造方法：

```java
public Writer(String name) {
    this.name = name;
}
```

为了能够兼顾 age 字段，我们可以通过 this 关键字调用其他的构造方法：

```java
public Writer(String name) {
    this(name,18);
}
```

把作者的年龄都默认初始化为 18。如果需要使用父类的构造方法，还可以使用 super 关键字，手册后面有详细的介绍。

## 九、Java 抽象类

当我们要完成的任务是确定的，但具体的方式需要随后开个会投票的话，Java 的抽象类就派上用场了。这句话怎么理解呢？搬个小板凳坐好，听我来给你讲讲。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-25.png)


### 01、抽象类的 5 个关键点

1）定义抽象类的时候需要用到关键字 `abstract`，放在 `class` 关键字前。

```java
public abstract class AbstractPlayer {
}
```

关于抽象类的命名，阿里出品的 Java 开发手册上有强调，“抽象类命名要使用 Abstract 或 Base 开头”，记住了哦。

2）抽象类不能被实例化，但可以有子类。

尝试通过 `new` 关键字实例化的话，编译器会报错，提示“类是抽象的，不能实例化”。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-26.png)

通过 `extends` 关键字可以继承抽象类，继承后，BasketballPlayer 类就是 AbstractPlayer 的子类。

```java
public class BasketballPlayer extends AbstractPlayer {
}
```

3）如果一个类定义了一个或多个抽象方法，那么这个类必须是抽象类。

当在一个普通类（没有使用 `abstract` 关键字修饰）中定义了抽象方法，编译器就会有两处错误提示。

第一处在类级别上，提醒你“这个类必须通过 `abstract` 关键字定义”，or 的那个信息没必要，见下图。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-27.png)

第二处在方法级别上，提醒你“抽象方法所在的类不是抽象的”，见下图。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-28.png)

4）抽象类可以同时声明抽象方法和具体方法，也可以什么方法都没有，但没必要。就像下面这样：

```java
public abstract class AbstractPlayer {
    abstract void play();
    
    public void sleep() {
        System.out.println("运动员也要休息而不是挑战极限");
    }
}
```

5）抽象类派生的子类必须实现父类中定义的抽象方法。比如说，抽象类中定义了 `play()` 方法，子类中就必须实现。

```java
public class BasketballPlayer extends AbstractPlayer {
    @Override
    void play() {
        System.out.println("我是张伯伦，篮球场上得过 100 分");
    }
}
```

如果没有实现的话，编译器会提醒你“子类必须实现抽象方法”，见下图。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-29.png)

### 02、什么时候用抽象类

与抽象类息息相关的还有一个概念，就是接口，我们留到下一篇文章中详细说，因为要说的知识点还是蛮多的。你现在只需要有这样一个概念就好，接口是对行为的抽象，抽象类是对整个类（包含成员变量和行为）进行抽象。

（是不是有点明白又有点不明白，别着急，翘首以盼地等下一篇文章出炉吧）

除了接口之外，还有一个概念就是具体的类，就是不通过 `abstract` 修饰的普通类，见下面这段代码中的定义。

```java
public class BasketballPlayer {
   public void play() {
        System.out.println("我是詹姆斯，现役第一人");
    }
}
```

有接口，有具体类，那什么时候该使用抽象类呢？

1）我们希望一些通用的功能被多个子类复用。比如说，AbstractPlayer 抽象类中有一个普通的方法 `sleep()`，表明所有运动员都需要休息，那么这个方法就可以被子类复用。

```java
public abstract class AbstractPlayer {
    public void sleep() {
        System.out.println("运动员也要休息而不是挑战极限");
    }
}
```

虽然 AbstractPlayer 类可以不是抽象类——把 `abstract` 修饰符去掉也能满足这种场景。但 AbstractPlayer 类可能还会有一个或者多个抽象方法。

BasketballPlayer 继承了 AbstractPlayer 类，也就拥有了 `sleep()` 方法。

```java
public class BasketballPlayer extends AbstractPlayer {
}
```

BasketballPlayer 对象可以直接调用 `sleep()` 方法：

```java
BasketballPlayer basketballPlayer = new BasketballPlayer();
basketballPlayer.sleep();
```

FootballPlayer 继承了 AbstractPlayer 类，也就拥有了 `sleep()` 方法。

```java
public class FootballPlayer extends AbstractPlayer {
}
```

FootballPlayer 对象也可以直接调用 `sleep()` 方法：

```java
FootballPlayer footballPlayer = new FootballPlayer();
footballPlayer.sleep();
```

2）我们需要在抽象类中定义好 API，然后在子类中扩展实现。比如说，AbstractPlayer  抽象类中有一个抽象方法 `play()`，定义所有运动员都可以从事某项运动，但需要对应子类去扩展实现。

```java
public abstract class AbstractPlayer {
    abstract void play();
}
```

BasketballPlayer 继承了 AbstractPlayer 类，扩展实现了自己的 `play()` 方法。

```java
public class BasketballPlayer extends AbstractPlayer {
    @Override
    void play() {
        System.out.println("我是张伯伦，我篮球场上得过 100 分，");
    }
}
```

FootballPlayer 继承了 AbstractPlayer 类，扩展实现了自己的 `play()` 方法。

```java
public class FootballPlayer extends AbstractPlayer {
    @Override
    void play() {
        System.out.println("我是C罗，我能接住任意高度的头球");
    }
}
```

3）如果父类与子类之间的关系符合 `is-a` 的层次关系，就可以使用抽象类，比如说篮球运动员是运动员，足球运动员是运动员。

### 03、具体示例

为了进一步展示抽象类的特性，我们再来看一个具体的示例。假设现在有一个文件，里面的内容非常简单——“Hello World”，现在需要有一个读取器将内容读取出来，最好能按照大写的方式，或者小写的方式。

这时候，最好定义一个抽象类，比如说 BaseFileReader：

```java
public abstract class BaseFileReader {
    protected Path filePath;

    protected BaseFileReader(Path filePath) {
        this.filePath = filePath;
    }

    public List<String> readFile() throws IOException {
        return Files.lines(filePath)
                .map(this::mapFileLine).collect(Collectors.toList());
    }

    protected abstract String mapFileLine(String line);
}
```

filePath 为文件路径，使用 protected 修饰，表明该成员变量可以在需要时被子类访问。

`readFile()` 方法用来读取文件，方法体里面调用了抽象方法 `mapFileLine()`——需要子类扩展实现大小写的方式。

你看，BaseFileReader 设计的就非常合理，并且易于扩展，子类只需要专注于具体的大小写实现方式就可以了。

小写的方式：

```java
public class LowercaseFileReader extends BaseFileReader {
    protected LowercaseFileReader(Path filePath) {
        super(filePath);
    }

    @Override
    protected String mapFileLine(String line) {
        return line.toLowerCase();
    }
}
```

大写的方式：

```java
public class UppercaseFileReader extends BaseFileReader {
    protected UppercaseFileReader(Path filePath) {
        super(filePath);
    }

    @Override
    protected String mapFileLine(String line) {
        return line.toUpperCase();
    }
}
```

你看，从文件里面一行一行读取内容的代码被子类复用了——抽象类 BaseFileReader 类中定义的普通方法 `readFile()`。与此同时，子类只需要专注于自己该做的工作，LowercaseFileReader 以小写的方式读取文件内容，UppercaseFileReader 以大写的方式读取文件内容。

接下来，我们来新建一个测试类 FileReaderTest：

```java
public class FileReaderTest {
    public static void main(String[] args) throws URISyntaxException, IOException {
        URL location = FileReaderTest.class.getClassLoader().getResource("helloworld.txt");
        Path path = Paths.get(location.toURI());
        BaseFileReader lowercaseFileReader = new LowercaseFileReader(path);
        BaseFileReader uppercaseFileReader = new UppercaseFileReader(path);
        System.out.println(lowercaseFileReader.readFile());
        System.out.println(uppercaseFileReader.readFile());
    }
}
```

项目的 resource 目录下有一个文本文件，名字叫 helloworld.txt。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-30.png)


可以通过 `ClassLoader.getResource()` 的方式获取到该文件的 URI 路径，然后就可以使用 LowercaseFileReader 和 UppercaseFileReader 两种方式读取到文本内容了。

输出结果如下所示：

```
[hello world]
[HELLO WORLD]
```

## 十、Java 接口

对于面向对象编程来说，抽象是一个极具魅力的特征。如果一个程序员的抽象思维很差，那他在编程中就会遇到很多困难，无法把业务变成具体的代码。在 Java 中，可以通过两种形式来达到抽象的目的，一种是抽象类，另外一种就是接口。

如果你现在就想知道抽象类与接口之间的区别，我可以提前给你说一个：

- 一个类只能继承一个抽象类，但却可以实现多个接口。

当然了，在没有搞清楚接口到底是什么，它可以做什么之前，这个区别理解起来会有点难度。

### 01、接口是什么

接口是通过 interface 关键字定义的，它可以包含一些常量和方法，来看下面这个示例。

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

1）接口中定义的变量会在编译的时候自动加上 `public static final` 修饰符，也就是说 LED 变量其实是一个常量。

Java 官方文档上有这样的声明：

>Every field declaration in the body of an interface is implicitly public, static, and final.

换句话说，接口可以用来作为常量类使用，还能省略掉 `public static final`，看似不错的一种选择，对吧？

不过，这种选择并不可取。因为接口的本意是对方法进行抽象，而常量接口会对子类中的变量造成命名空间上的“污染”。

2）没有使用 `private`、`default` 或者 `static` 关键字修饰的方法是隐式抽象的，在编译的时候会自动加上 `public abstract` 修饰符。也就是说 `getElectricityUse()` 其实是一个抽象方法，没有方法体——这是定义接口的本意。

3）从 Java 8 开始，接口中允许有静态方法，比如说 `isEnergyEfficient()` 方法。

静态方法无法由（实现了该接口的）类的对象调用，它只能通过接口的名字来调用，比如说 `Electronic.isEnergyEfficient("LED")`。

接口中定义静态方法的目的是为了提供一种简单的机制，使我们不必创建对象就能调用方法，从而提高接口的竞争力。

4）接口中允许定义 `default` 方法也是从 Java 8 开始的，比如说 `printDescription()`，它始终由一个代码块组成，为实现该接口而不覆盖该方法的类提供默认实现，也就是说，无法直接使用一个“;”号来结束默认方法——编译器会报错的。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-31.png)

允许在接口中定义默认方法的理由是很充分的，因为一个接口可能有多个实现类，这些类就必须实现接口中定义的抽象类，否则编译器就会报错。假如我们需要在所有的实现类中追加某个具体的方法，在没有 `default` 方法的帮助下，我们就必须挨个对实现类进行修改。

来看一下 Electronic 接口反编译后的字节码吧，你会发现，接口中定义的所有变量或者方法，都会自动添加上 `public` 关键字——假如你想知道编译器在背后都默默做了哪些辅助，记住反编译字节码就对了。
```java
public interface Electronic
{

    public abstract int getElectricityUse();

    public static boolean isEnergyEfficient(String electtronicType)
    {
        return electtronicType.equals("LED");
    }

    public void printDescription()
    {
        System.out.println("\u7535\u5B50");
    }

    public static final String LED = "LED";
}
```

有些读者可能会问，“二哥，为什么我反编译后的字节码和你的不一样，你用了什么反编译工具？”其实没有什么秘密，微信搜「沉默王二」回复关键字「**JAD**」就可以免费获取了，超级好用。

### 02、定义接口的注意事项

由之前的例子我们就可以得出下面这些结论：

- 接口中允许定义变量
- 接口中允许定义抽象方法
- 接口中允许定义静态方法（Java 8 之后）
- 接口中允许定义默认方法（Java 8 之后）

除此之外，我们还应该知道：

1）接口不允许直接实例化。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-32.png)

需要定义一个类去实现接口，然后再实例化。

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

2）接口可以是空的，既不定义变量，也不定义方法。

```java
public interface Serializable {
}
```

Serializable 是最典型的一个空的接口，我之前分享过一篇文章《Java Serializable：明明就一个空的接口嘛》，感兴趣的读者可以去我的个人博客看一看，你就明白了空接口的意义。

>[http://www.itwanger.com/java/2019/11/14/java-serializable.html](http://www.itwanger.com/java/2019/11/14/java-serializable.html)

3）不要在定义接口的时候使用 final 关键字，否则会报编译错误，因为接口就是为了让子类实现的，而 final 阻止了这种行为。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-33.png)

4）接口的抽象方法不能是 private、protected 或者 final。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-34.png)

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-35.png)

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-36.png)

5）接口的变量是隐式 `public static final`，所以其值无法改变。

### 03、接口可以做什么

1）使某些实现类具有我们想要的功能，比如说，实现了 Cloneable 接口的类具有拷贝的功能，实现了 Comparable 或者 Comparator 的类具有比较功能。

Cloneable 和 Serializable 一样，都属于标记型接口，它们内部都是空的。实现了 Cloneable 接口的类可以使用 `Object.clone()` 方法，否则会抛出 CloneNotSupportedException。

```java
public class CloneableTest implements Cloneable {
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        CloneableTest c1 = new CloneableTest();
        CloneableTest c2 = (CloneableTest) c1.clone();
    }
}
```

运行后没有报错。现在把 `implements Cloneable` 去掉。

```java
public class CloneableTest {
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        CloneableTest c1 = new CloneableTest();
        CloneableTest c2 = (CloneableTest) c1.clone();

    }
}
```

运行后抛出 CloneNotSupportedException：

```
Exception in thread "main" java.lang.CloneNotSupportedException: com.cmower.baeldung.interface1.CloneableTest
    at java.base/java.lang.Object.clone(Native Method)
    at com.cmower.baeldung.interface1.CloneableTest.clone(CloneableTest.java:6)
    at com.cmower.baeldung.interface1.CloneableTest.main(CloneableTest.java:11)
```

至于 Comparable 和 Comparator 的用法，感兴趣的读者可以参照我之前写的另外一篇文章《来吧，一文彻底搞懂Java中的Comparable和Comparator》。

>[http://www.itwanger.com/java/2020/01/04/java-comparable-comparator.html](http://www.itwanger.com/java/2020/01/04/java-comparable-comparator.html)


2）Java 原则上只支持单一继承，但通过接口可以实现多重继承的目的。

可能有些读者会问，“二哥，为什么 Java 只支持单一继承？”简单来解释一下。

如果有两个类共同继承（extends）一个有特定方法的父类，那么该方法会被两个子类重写。然后，如果你决定同时继承这两个子类，那么在你调用该重写方法时，编译器不能识别你要调用哪个子类的方法。这也正是著名的菱形问题，见下图。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-37.png)

ClassC 同时继承了 ClassA 和 ClassB，ClassC 的对象在调用 ClassA 和 ClassB 中重载的方法时，就不知道该调用 ClassA 的方法，还是 ClassB 的方法。

接口没有这方面的困扰。来定义两个接口，Fly 会飞，Run 会跑。

```java
public interface Fly {
    void fly();
}
public interface Run {
    void run();
}
```

然后让一个类同时实现这两个接口。

```java
public class Pig implements Fly,Run{
    @Override
    public void fly() {
        System.out.println("会飞的猪");
    }

    @Override
    public void run() {
        System.out.println("会跑的猪");
    }
}
```

这就在某种形式上达到了多重继承的目的：现实世界里，猪的确只会跑，但在雷军的眼里，站在风口的猪就会飞，这就需要赋予这只猪更多的能力，通过抽象类是无法实现的，只能通过接口。

3）实现多态。

什么是多态呢？通俗的理解，就是同一个事件发生在不同的对象上会产生不同的结果，鼠标左键点击窗口上的 X 号可以关闭窗口，点击超链接却可以打开新的网页。

多态可以通过继承（`extends`）的关系实现，也可以通过接口的形式实现。来看这样一个例子。

Shape 是表示一个形状。

```java
public interface Shape {
    String name();
}
```

圆是一个形状。

```java
public class Circle implements Shape {
    @Override
    public String name() {
        return "圆";
    }
}
```

正方形也是一个形状。

```java
public class Square implements Shape {
    @Override
    public String name() {
        return "正方形";
    }
}
```

然后来看测试类。

```java
List<Shape> shapes = new ArrayList<>();
Shape circleShape = new Circle();
Shape squareShape = new Square();

shapes.add(circleShape);
shapes.add(squareShape);

for (Shape shape : shapes) {
    System.out.println(shape.name());
}
```

多态的存在 3 个前提：

1、要有继承关系，Circle 和 Square 都实现了 Shape 接口
2、子类要重写父类的方法，Circle 和 Square 都重写了 `name()` 方法
3、父类引用指向子类对象，circleShape 和 squareShape 的类型都为 Shape，但前者指向的是 Circle 对象，后者指向的是 Square 对象。

然后，我们来看一下测试结果：

```
圆
正方形
```

也就意味着，尽管在 for 循环中，shape 的类型都为 Shape，但在调用 `name()` 方法的时候，它知道 Circle 对象应该调用 Circle 类的 `name()` 方法，Square 对象应该调用 Square 类的 `name()` 方法。

### 04、接口与抽象类的区别

好了，关于接口的一切，你应该都搞清楚了。现在回到读者春夏秋冬的那条留言，“兄弟，说说抽象类和接口之间的区别？”

1）语法层面上

- 接口中不能有 public 和 protected 修饰的方法，抽象类中可以有。
- 接口中的变量只能是隐式的常量，抽象类中可以有任意类型的变量。
- 一个类只能继承一个抽象类，但却可以实现多个接口。

2）设计层面上

抽象类是对类的一种抽象，继承抽象类的类和抽象类本身是一种 `is-a` 的关系。

接口是对类的某种行为的一种抽象，接口和类之间并没有很强的关联关系，所有的类都可以实现 `Serializable` 接口，从而具有序列化的功能。

就这么多吧，能说道这份上，我相信面试官就不会为难你了。

## 十一、Java 继承

在 Java 中，一个类可以继承另外一个类或者实现多个接口，我想这一点，大部分的读者应该都知道了。还有一点，我不确定大家是否知道，就是一个接口也可以继承另外一个接口，就像下面这样：

```java
public interface OneInterface extends Cloneable {
}
```

这样做有什么好处呢？我想有一部分读者应该已经猜出来了，就是实现了 OneInterface 接口的类，也可以使用 `Object.clone()` 方法了。

```java
public class TestInterface implements OneInterface {
    public static void main(String[] args) throws CloneNotSupportedException {
        TestInterface c1 = new TestInterface();
        TestInterface c2 = (TestInterface) c1.clone();
    }
}
```

除此之外，我们还可以在 OneInterface 接口中定义其他一些抽象方法（比如说深拷贝），使该接口拥有 Cloneable 所不具有的功能。

```java
public interface OneInterface extends Cloneable {
    void deepClone();
}
```

看到了吧？这就是继承的好处：**子接口拥有了父接口的方法，使得子接口具有了父接口相同的行为；同时，子接口还可以在此基础上自由发挥，添加属于自己的行为**。

以上，把“接口”换成“类”，结论同样成立。让我们来定义一个普通的父类 Wanger：

```java
public class Wanger {
    int age;
    String name;
    void write() {
        System.out.println("我写了本《基督山伯爵》");
    }
}
```

然后，我们再来定义一个子类 Wangxiaoer，使用关键字 `extends` 来继承父类 Wanger：

```java
public class Wangxiaoer extends Wanger{
    @Override
    void write() {
        System.out.println("我写了本《茶花女》");
    }
}
```

我们可以将通用的方法和成员变量放在父类中，达到代码复用的目的；然后将特殊的方法和成员变量放在子类中，除此之外，子类还可以覆盖父类的方法（比如`write()` 方法）。这样，子类也就焕发出了新的生命力。

Java 只支持单一继承，这一点，我在上一篇[接口](https://mp.weixin.qq.com/s/06d5Fk_ho4yafR83mfbWag)的文章中已经提到过了。如果一个类在定义的时候没有使用 `extends` 关键字，那么它隐式地继承了 `java.lang.Object` 类——在我看来，这恐怕就是 Java 号称万物皆对象的真正原因了。

那究竟子类继承了父类的什么呢？

子类可以继承父类的非 private 成员变量，为了验证这一点，我们来看下面这个示例。

```java
public class Wanger {
    String defaultName;
    private String privateName;
    public String publicName;
    protected String protectedName;
}
```

父类 Wanger 定义了四种类型的成员变量，缺省的 defaultName、私有的 privateName、共有的 publicName、受保护的 protectedName。

在子类 Wangxiaoer 中定义一个测试方法 `testVariable()`：

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-38.png)

可以确认，除了私有的 privateName，其他三种类型的成员变量都可以继承到。

同理，子类可以继承父类的非 private 方法，为了验证这一点，我们来看下面这个示例。

```java
public class Wanger {
    void write() {
    }

    private void privateWrite() {
    }

    public void publicWrite() {
    }

    protected void protectedWrite() {
    }
}
```

父类 Wanger 定义了四种类型的方法，缺省的 write、私有的 privateWrite()、共有的 publicWrite()、受保护的 protectedWrite()。

在子类 Wangxiaoer 中定义一个 main 方法，并使用 new 关键字新建一个子类对象：

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-39.png)

可以确认，除了私有的 privateWrite()，其他三种类型的方法都可以继承到。

不过，子类无法继承父类的构造方法。如果父类的构造方法是带有参数的，代码如下所示：

```java
public class Wanger {
    int age;
    String name;

    public Wanger(int age, String name) {
        this.age = age;
        this.name = name;
    }
}
```

则必须在子类的构造器中显式地通过 super 关键字进行调用，否则编译器将提示以下错误：

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-40.png)

修复后的代码如下所示：

```java
public class Wangxiaoer extends Wanger{
    public Wangxiaoer(int age, String name) {
        super(age, name);
    }
}
```

is-a 是继承的一个明显特征，就是说子类的对象引用类型可以是一个父类类型。

```java
public class Wangxiaoer extends Wanger{
    public static void main(String[] args) {
        Wanger wangxiaoer = new Wangxiaoer();
    }
}
```

同理，子接口的实现类的对象引用类型也可以是一个父接口类型。

```java
public interface OneInterface extends Cloneable {
}
public class TestInterface implements OneInterface {
    public static void main(String[] args) {
        Cloneable c1 = new TestInterface();
    }
}
```

尽管一个类只能继承一个类，但一个类却可以实现多个接口，这一点，我在[上一篇文章](https://mp.weixin.qq.com/s/06d5Fk_ho4yafR83mfbWag)也提到过了。另外，还有一点我也提到了，就是 Java 8 之后，接口中可以定义 default 方法，这很方便，但也带来了新的问题：

>如果一个类实现了多个接口，而这些接口中定义了相同签名的 default 方法，那么这个类就要重写该方法，否则编译无法通过。

FlyInterface 是一个会飞的接口，里面有一个签名为 `sleep()` 的默认方法：

```java
public interface FlyInterface {
    void fly();
    default void sleep() {
        System.out.println("睡着飞");
    }
}
```

RunInterface 是一个会跑的接口，里面也有一个签名为 `sleep()` 的默认方法：

```java
public interface RunInterface {
    void run();
    default void sleep() {
        System.out.println("睡着跑");
    }
}
```

Pig 类实现了 FlyInterface 和 RunInterface 两个接口，但这时候编译出错了。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-41.png)

原本，default 方法就是为实现该接口而不覆盖该方法的类提供默认实现的，现在，相同方法签名的 `sleep()` 方法把编译器搞懵逼了，只能重写了。

```java
public class Pig implements FlyInterface, RunInterface {

    @Override
    public void fly() {
        System.out.println("会飞的猪");
    }

    @Override
    public void sleep() {
        System.out.println("只能重写了");
    }

    @Override
    public void run() {
        System.out.println("会跑的猪");
    }
}
```

类虽然不能继承多个类，但接口却可以继承多个接口，这一点，我不知道有没有触及到一些读者的知识盲区。

```java
public interface WalkInterface extends FlyInterface,RunInterface{
    void walk();
}
```

## 十二、this 关键字

在 Java 中，this 关键字指的是当前对象（它的方法正在被调用）的引用，能理解吧，各位亲？不理解的话，我们继续往下看。

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

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-42.png)

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

## 十三、super 关键字

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

## 十四、重写和重载

先来看一段重写的代码吧。

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


![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-43.png)

哎，后悔啊，早年我要是能把这道面试题吃透的话，也不用被老马刁难了。吟一首诗感慨一下人生吧。

>青青园中葵，朝露待日晞。
阳春布德泽，万物生光辉。
常恐秋节至，焜黄华叶衰。
百川东到海，何时复西归?
少壮不努力，老大徒伤悲


另外，我想要告诉大家的是，重写（Override）和重载（Overload）是 Java 中两个非常重要的概念，新手经常会被它们俩迷惑，因为它们俩的英文名字太像了，中文翻译也只差一个字。难，太难了。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-44.png)

## 十五、static 关键字

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

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-45.png)

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

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-46.png)

Math 类的几乎所有方法都是静态的，可以直接通过类名来调用，不需要创建类的对象。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-47.png)


简单小结一下：

1）Java 中的静态方法在编译时解析，因为静态方法不能被重写（方法重写发生在运行时阶段，为了多态）。

2）抽象方法不能是静态的。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-48.png)

3）静态方法不能使用 this 和 super 关键字。

4）成员方法可以直接访问其他成员方法和成员变量。

5）成员方法也可以直接方法静态方法和静态变量。

6）静态方法可以访问所有其他静态方法和静态变量。

7）静态方法无法直接访问成员方法和成员变量。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-49.png)


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

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-50.png)

## 十六、Java 枚举

开门见山地说吧，enum（枚举）是 Java 1.5 时引入的关键字，它表示一种特殊类型的类，默认[继承](https://mp.weixin.qq.com/s/q-dMxOXxT8N3W6ftmNWkWQ)自 java.lang.Enum。

为了证明这一点，我们来新建一个枚举 PlayerType：

```java
public enum PlayerType {
    TENNIS,
    FOOTBALL,
    BASKETBALL
}
```

两个关键字带一个类名，还有大括号，以及三个大写的单词，但没看到继承 Enum 类啊？别着急，心急吃不了热豆腐啊。使用 JAD 查看一下反编译后的字节码，就一清二楚了。

```java
public final class PlayerType extends Enum
{

    public static PlayerType[] values()
    {
        return (PlayerType[])$VALUES.clone();
    }

    public static PlayerType valueOf(String name)
    {
        return (PlayerType)Enum.valueOf(com/cmower/baeldung/enum1/PlayerType, name);
    }

    private PlayerType(String s, int i)
    {
        super(s, i);
    }

    public static final PlayerType TENNIS;
    public static final PlayerType FOOTBALL;
    public static final PlayerType BASKETBALL;
    private static final PlayerType $VALUES[];

    static 
    {
        TENNIS = new PlayerType("TENNIS", 0);
        FOOTBALL = new PlayerType("FOOTBALL", 1);
        BASKETBALL = new PlayerType("BASKETBALL", 2);
        $VALUES = (new PlayerType[] {
            TENNIS, FOOTBALL, BASKETBALL
        });
    }
}
```

看到没？PlayerType 类是 final 的，并且继承自 Enum 类。这些工作我们程序员没做，编译器帮我们悄悄地做了。此外，它还附带几个有用静态方法，比如说 `values()` 和  `valueOf(String name)`。

### 01、内部枚举

好的，小伙伴们应该已经清楚枚举长什么样子了吧？既然枚举是一种特殊的类，那它其实是可以定义在一个类的内部的，这样它的作用域就可以限定于这个外部类中使用。

```java
public class Player {
    private PlayerType type;
    public enum PlayerType {
        TENNIS,
        FOOTBALL,
        BASKETBALL
    }
    
    public boolean isBasketballPlayer() {
      return getType() == PlayerType.BASKETBALL;
    }

    public PlayerType getType() {
        return type;
    }

    public void setType(PlayerType type) {
        this.type = type;
    }
}
```

PlayerType 就相当于 Player 的内部类，`isBasketballPlayer()` 方法用来判断运动员是否是一个篮球运动员。

由于枚举是 final 的，可以确保在 Java 虚拟机中仅有一个常量对象（可以参照反编译后的[静态代码块](https://mp.weixin.qq.com/s/24EdEt3UyP7aKZStvvuDgQ)「static 关键字带大括号的那部分代码」），所以我们可以很安全地使用“==”运算符来比较两个枚举是否相等，参照 `isBasketballPlayer()` 方法。

那为什么不使用 `equals()` 方法判断呢？

```java
if(player.getType().equals(Player.PlayerType.BASKETBALL)){};
if(player.getType() == Player.PlayerType.BASKETBALL){};
```

“==”运算符比较的时候，如果两个对象都为 null，并不会发生 `NullPointerException`，而 `equals()` 方法则会。

另外， “==”运算符会在编译时进行检查，如果两侧的类型不匹配，会提示错误，而 `equals()` 方法则不会。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-51.png)

### 02、枚举可用于 switch 语句

这个我在之前的一篇[我去](https://mp.weixin.qq.com/s/1BDDLDSKDGwQAfIFMyySdg)的文章中详细地说明过了，感兴趣的小伙伴可以点击链接跳转过去看一下。

```java
switch (playerType) {
        case TENNIS:
            return "网球运动员费德勒";
        case FOOTBALL:
            return "足球运动员C罗";
        case BASKETBALL:
            return "篮球运动员詹姆斯";
        case UNKNOWN:
            throw new IllegalArgumentException("未知");
        default:
            throw new IllegalArgumentException(
                    "运动员类型: " + playerType);

    }
```

### 03、枚举可以有构造方法

如果枚举中需要包含更多信息的话，可以为其添加一些字段，比如下面示例中的 name，此时需要为枚举添加一个带参的构造方法，这样就可以在定义枚举时添加对应的名称了。

```java
public enum PlayerType {
    TENNIS("网球"),
    FOOTBALL("足球"),
    BASKETBALL("篮球");

    private String name;

    PlayerType(String name) {
        this.name = name;
    }
}
```

### 04、EnumSet

EnumSet 是一个专门针对枚举类型的 Set 接口的实现类，它是处理枚举类型数据的一把利器，非常高效（内部实现是位向量，我也搞不懂）。

因为 EnumSet 是一个抽象类，所以创建 EnumSet 时不能使用 new 关键字。不过，EnumSet 提供了很多有用的静态工厂方法：

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-52.png)

下面的示例中使用 `noneOf()` 创建了一个空的 PlayerType 的 EnumSet；使用 `allOf()` 创建了一个包含所有 PlayerType 的 EnumSet。

```java
public class EnumSetTest {
    public enum PlayerType {
        TENNIS,
        FOOTBALL,
        BASKETBALL
    }

    public static void main(String[] args) {
        EnumSet<PlayerType> enumSetNone = EnumSet.noneOf(PlayerType.class);
        System.out.println(enumSetNone);

        EnumSet<PlayerType> enumSetAll = EnumSet.allOf(PlayerType.class);
        System.out.println(enumSetAll);
    }
}
```

程序输出结果如下所示：

```java
[]
[TENNIS, FOOTBALL, BASKETBALL]
```

有了 EnumSet 后，就可以使用 Set 的一些方法了：

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-53.png)

### 05、EnumMap

EnumMap 是一个专门针对枚举类型的 Map 接口的实现类，它可以将枚举常量作为键来使用。EnumMap 的效率比 HashMap 还要高，可以直接通过数组下标（枚举的 ordinal 值）访问到元素。

和 EnumSet 不同，EnumMap 不是一个抽象类，所以创建 EnumMap 时可以使用 new 关键字：

```java
EnumMap<PlayerType, String> enumMap = new EnumMap<>(PlayerType.class);
```

有了 EnumMap 对象后就可以使用 Map 的一些方法了：

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-54.png)

和 HashMap 的使用方法大致相同，来看下面的例子：

```java
EnumMap<PlayerType, String> enumMap = new EnumMap<>(PlayerType.class);
enumMap.put(PlayerType.BASKETBALL,"篮球运动员");
enumMap.put(PlayerType.FOOTBALL,"足球运动员");
enumMap.put(PlayerType.TENNIS,"网球运动员");
System.out.println(enumMap);

System.out.println(enumMap.get(PlayerType.BASKETBALL));
System.out.println(enumMap.containsKey(PlayerType.BASKETBALL));
System.out.println(enumMap.remove(PlayerType.BASKETBALL));
```

程序输出结果如下所示：

```
{TENNIS=网球运动员, FOOTBALL=足球运动员, BASKETBALL=篮球运动员}
篮球运动员
true
篮球运动员
```

### 06、单例

通常情况下，实现一个单例并非易事，不信，来看下面这段代码

```java
public class Singleton {  
    private volatile static Singleton singleton; 
    private Singleton (){}  
    public static Singleton getSingleton() {  
    if (singleton == null) {
        synchronized (Singleton.class) { 
        if (singleton == null) {  
            singleton = new Singleton(); 
        }  
        }  
    }  
    return singleton;  
    }  
}
```

但枚举的出现，让代码量减少到极致：

```java
public enum EasySingleton{
    INSTANCE;
}
```

完事了，真的超级短，有没有？枚举默认实现了 Serializable 接口，因此 Java 虚拟机可以保证该类为单例，这与传统的实现方式不大相同。传统方式中，我们必须确保单例在反序列化期间不能创建任何新实例。

### 07、枚举可与数据库交互

我们可以配合 Mybatis 将数据库字段转换为枚举类型。现在假设有一个数据库字段 check_type 的类型如下：

```sql
`check_type` int(1) DEFAULT NULL COMMENT '检查类型（1：未通过、2：通过）',
```

它对应的枚举类型为 CheckType，代码如下：

```java
public enum CheckType {
    NO_PASS(0, "未通过"), PASS(1, "通过");
    private int key;

    private String text;

    private CheckType(int key, String text) {
        this.key = key;
        this.text = text;
    }

    public int getKey() {
        return key;
    }

    public String getText() {
        return text;
    }

    private static HashMap<Integer,CheckType> map = new HashMap<Integer,CheckType>();
    static {
        for(CheckType d : CheckType.values()){
            map.put(d.key, d);
        }
    }
    
    public static CheckType parse(Integer index) {
        if(map.containsKey(index)){
            return map.get(index);
        }
        return null;
    }
}
```

1）CheckType 添加了构造方法，还有两个字段，key 为 int 型，text 为 String 型。

2）CheckType 中有一个`public static CheckType parse(Integer index)`方法，可将一个 Integer 通过 key 的匹配转化为枚举类型。

那么现在，我们可以在 Mybatis 的配置文件中使用 `typeHandler` 将数据库字段转化为枚举类型。

```xml
<resultMap id="CheckLog" type="com.entity.CheckLog">
  <id property="id" column="id"/>
  <result property="checkType" column="check_type" typeHandler="com.CheckTypeHandler"></result>
</resultMap>
```

其中 checkType 字段对应的类如下：

```java
public class CheckLog implements Serializable {

    private String id;
    private CheckType checkType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CheckType getCheckType() {
        return checkType;
    }

    public void setCheckType(CheckType checkType) {
        this.checkType = checkType;
    }
}
```

CheckTypeHandler 转换器的类源码如下：

```java
public class CheckTypeHandler extends BaseTypeHandler<CheckType> {

    @Override
    public CheckType getNullableResult(ResultSet rs, String index) throws SQLException {
        return CheckType.parse(rs.getInt(index));
    }

    @Override
    public CheckType getNullableResult(ResultSet rs, int index) throws SQLException {
        return CheckType.parse(rs.getInt(index));
    }

    @Override
    public CheckType getNullableResult(CallableStatement cs, int index) throws SQLException {
        return CheckType.parse(cs.getInt(index));
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int index, CheckType val, JdbcType arg3) throws SQLException {
        ps.setInt(index, val.getKey());
    }
}
```

CheckTypeHandler 的核心功能就是调用 CheckType 枚举类的 `parse()` 方法对数据库字段进行转换。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-55.gif)

恕我直言，我觉得小伙伴们肯定会用 Java 枚举了，如果还不会，就过来砍我！

## 十七、final 关键字


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

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-56.png)

不过，类是 final 的，并不意味着该类的对象是不可变的。

```java
Writer writer = new Writer();
writer.setName("沉默王二");
System.out.println(writer.getName()); // 沉默王二
```

Writer 的 name 字段的默认值是 null，但可以通过 settter 方法将其更改为“沉默王二”。也就是说，如果一个类只是 final 的，那么它并不是不可变的全部条件。

如果，你想了解不可变类的全部真相，请查看我之前写的文章[这次要说不明白immutable类，我就怎么地](https://mp.weixin.qq.com/s/wbdV9rV60AwWiiTEBYPP7g)。突然发现，写系列文章真的妙啊，很多相关性的概念全部涉及到了。我真服了自己了。

把一个类设计成 final 的，有其安全方面的考虑，但不应该故意为之，因为把一个类定义成 final 的，意味着它没办法继承，假如这个类的一些方法存在一些问题的话，我们就无法通过重写的方式去修复它。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-57.png)


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

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-58.png)

如果一个类中的某些方法要被其他方法调用，则应考虑事被调用的方法称为 final 方法，否则，重写该方法会影响到调用方法的使用。

一个类是 final 的，和一个类不是 final，但它所有的方法都是 final 的，考虑一下，它们之间有什么区别？

我能想到的一点，就是前者不能被继承，也就是说方法无法被重写；后者呢，可以被继承，然后追加一些非 final 的方法。没毛病吧？看把我聪明的。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-59.png)


### 03、final 变量

被 final 修饰的变量无法重新赋值。换句话说，final 变量一旦初始化，就无法更改。之前被一个小伙伴问过，什么是 effective final，什么是 final，这一点，我在之前的文章也有阐述过，所以这里再贴一下地址：

>[http://www.itwanger.com/java/2020/02/14/java-final-effectively.html](http://www.itwanger.com/java/2020/02/14/java-final-effectively.html)

1）final 修饰的基本数据类型

来声明一个 final 修饰的 int 类型的变量：

```java
final int age = 18;
```

尝试将它修改为 30，结果编译器生气了：

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-60.png)

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

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-61.png)

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

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-62.png)

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

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-63.png)

。。。。。。

后续还会继续更新，但有些小伙伴可能就忍不住了，这份小白手册有没有 PDF 版可以白嫖啊，那必须得有啊，直接「**沉默王二**」公众号后台回复「**小白**」就可以了，不要手软，觉得不错的，请多多分享——赠人玫瑰，手有余香哦。

![](http://www.itwanger.com/assets/images/2020/06/java-xiaobai-diyiban-64.png)

没关注的话，扫描上面的二维码就可以了，然后回复「**小白**」。

我是沉默王二，一枚有颜值却靠才华苟且的程序员。**关注即可提升学习效率，别忘了三连啊，点赞、收藏、留言，我不挑，嘻嘻**。