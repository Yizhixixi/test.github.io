---
layout: post
category: java
title: 携程面试官竟然问我 Java 虚拟机栈
tagline: by 沉默王二
tags: 
  - java
---

大家好，我是那个永远 18 岁的老妖怪~嘘

<!--more-->







从《[JVM 内存区域划分](https://mp.weixin.qq.com/s/NaCFDOGuoHkfQZZjvY66Jg)》这篇文章中，大家应该 get 到了，Java 虚拟机内存区域可以划分为程序计数器、Java 虚拟机栈、本地方法栈和堆。今天，我们来围绕其中的一个区域——Java 虚拟机栈，深入地展开下。

先说明一下哈。这篇文章的标题里带了一个“携程面试官”，有标题党的嫌疑。但有一说一，确实有读者在上一篇文章里留言说，携程面试官问他了 Java 虚拟机内存方面的知识点，所以今天的标题我就“借题发挥”了。

![](http://www.itwanger.com/assets/images/2021/04/java-caozuoshuzhan-01.png)

从“相见恨晚”这个词中，我估摸着这名读者在这道面试题前面折戟沉沙了。这么说吧，面试官确实喜欢问 Java 虚拟机方面的知识点，因为很能考察出一名应聘者的真实功底，所以我打算多写几篇这方面的文章，希望能给大家多一点点帮助~

 Java 虚拟机以方法作为基本的执行单元，“栈帧（Stack Frame）”则是用于支持 Java 虚拟机进行方法调用和方法执行的基本数据结构。每一个栈帧中都包含了局部变量表、操作数栈、动态链接、方法返回地址和一些额外的附加信息（比如与调试、性能手机相关的信息）。之前的文章里有提到过这些概念，并做了一些简单扼要的介绍，但我觉得还不够详细，所以这篇重点要来介绍一下栈帧中的这些概念。

**1）局部变量表**

局部变量表（Local Variables Table）用来保存方法中的局部变量，以及方法参数。当 Java 源代码文件被编译成 class 文件的时候，局部变量表的最大容量就已经确定了。

我们来看这样一段代码。

```java
public class LocalVaraiablesTable {
    private void write(int age) {
        String name = "沉默王二";
    }
}
```

`write()` 方法有一个参数 age，一个局部变量 name。

然后用 Intellij IDEA 的 jclasslib 查看一下编译后的字节码文件 LocalVaraiablesTable.class。可以看到 `write()` 方法的 Code 属性中，Maximum local variables（局部变量表的最大容量）的值为 3。

![](http://www.itwanger.com/assets/images/2021/04/java-caozuoshuzhan-02.png)

按理说，局部变量表的最大容量应该为 2 才对，一个 age，一个 name，为什么是 3 呢？

当一个成员方法（非静态方法）被调用时，第 0 个变量其实是调用这个成员方法的对象引用，也就是那个大名鼎鼎的 this。调用方法 `write(18)`，实际上是调用 `write(this, 18)`。

点开 Code 属性，查看 LocalVaraiableTable 就可以看到详细的信息了。

![](http://www.itwanger.com/assets/images/2021/04/java-caozuoshuzhan-03.png)

第 0 个是 this，类型为 LocalVaraiablesTable 对象；第 1 个是方法参数 age，类型为整形 int；第 2 个是方法内部的局部变量 name，类型为字符串 String。

当然了，局部变量表的大小并不是方法中所有局部变量的数量之和，它与变量的类型和变量的作用域有关。当一个局部变量的作用域结束了，它占用的局部变量表中的位置就被接下来的局部变量取代了。

来看下面这段代码。

```java
public static void method() {
    // ①
    if (true) {
        // ②
        String name = "沉默王二";
    }
    // ③
    if(true) {
        // ④
        int age = 18;
    }
    // ⑤
}
```

- `method()` 方法的局部变量表大小为 1，因为是静态方法，所以不需要添加 this 作为局部变量表的第一个元素；
- ②的时候局部变量有一个 name，局部变量表的大小变为 1；
- ③的时候 name 变量的作用域结束；
- ④的时候局部变量有一个 age，局部变量表的大小为 1；
- ⑤的时候局 age 变量的作用域结束；

关于局部变量的作用域，《Effective Java》 中的第 57 条建议：

>将局部变量的作用域最小化，可以增强代码的可读性和可维护性，并降低出错的可能性。

在此，我还有一点要提醒大家。为了尽可能节省栈帧耗用的内存空间，局部变量表中的槽是可以重用的，就像 `method()` 方法演示的那样，这就意味着，合理的作用域有助于提高程序的性能。

局部变量表的容量以槽（slot）为最小单位，一个槽可以容纳一个 32 位的数据类型（比如说 int，当然了，《Java 虚拟机规范》中没有明确指出一个槽应该占用的内存空间大小，但我认为这样更容易理解），像 float 和 double 这种明确占用 64 位的数据类型会占用两个紧挨着的槽。

来看下面的代码。

```java
public void solt() {
    double d = 1.0;
    int i = 1;
}
```

用 jclasslib 可以查看到，`solt()` 方法的 Maximum local variables 的值为 4。

![](http://www.itwanger.com/assets/images/2021/04/java-caozuoshuzhan-04.png)

为什么等于 4 呢？带上 this 也就 3 个呀？

![](http://www.itwanger.com/assets/images/2021/04/java-caozuoshuzhan-05.png)

查看 LocalVaraiableTable 就明白了，变量 i 的下标为 3，也就意味着变量 d 占了两个槽。

![](http://www.itwanger.com/assets/images/2021/04/java-caozuoshuzhan-06.png)


2）操作数栈

同局部变量表一样，操作数栈（Operand Stack）的最大深度也在编译的时候就确定了，被写入到了 Code 属性的 maximum stack size 中。当一个方法刚开始执行的时候，操作数栈是空的，在方法执行过程中，会有各种字节码指令往操作数栈中写入和取出数据，也就是入栈和出栈操作。

来看下面这段代码。

```java
public class OperandStack {
    public void test() {
        add(1,2);
    }

    private int add(int a, int b) {
        return a + b;
    }
}
```

OperandStack 类共有 2 个方法，`test()` 方法中调用了 `add()` 方法，传递了 2 个参数。用 jclasslib 可以看到，`test()` 方法的 maximum stack size 的值为 3。

![](http://www.itwanger.com/assets/images/2021/04/java-caozuoshuzhan-07.png)

这是因为调用成员方法的时候会将 this 和所有参数压入栈中，调用完毕后 this 和参数都会一一出栈。通过 「Bytecode」 面板可以查看到对应的字节码指令。

![](http://www.itwanger.com/assets/images/2021/04/java-caozuoshuzhan-08.png)

- aload_0 用于将局部变量表中下标为 0 的引用类型的变量，也就是 this 加载到操作数栈中；
- iconst_1 用于将整数 1 加载到操作数栈中；
- iconst_2 用于将整数 2 加载到操作数栈中；
- invokevirtual 用于调用对象的成员方法；
- pop 用于将栈顶的值出栈；
- return 为 void 方法的返回指令。

再来看一下 `add()` 方法的字节码指令。

![](http://www.itwanger.com/assets/images/2021/04/java-caozuoshuzhan-09.png)

- iload_1 用于将局部变量表中下标为 1 的 int 类型变量加载到操作数栈上（下标为 0 的是 this）；
- iload_2 用于将局部变量表中下标为 2 的 int 类型变量加载到操作数栈上；
- iadd 用于 int 类型的加法运算；
- ireturn 为返回值为 int 的方法返回指令。

![](http://www.itwanger.com/assets/images/2021/04/java-caozuoshuzhan-10.png)

操作数中的数据类型必须与字节码指令匹配，以上面的 iadd 指令为例，该指令只能用于整形数据的加法运算，它在执行的时候，栈顶的两个数据必须是 int 类型的，不能出现一个 long 型和一个 double 型的数据进行 iadd 命令相加的情况。

**3）动态链接**

每个栈帧都包含了一个指向运行时常量池中该栈帧所属方法的引用，持有这个引用是为了支持方法调用过程中的动态链接（Dynamic Linking）。

来看下面这段代码。

```java
public class DynamicLinking {
    static abstract class Human {
       protected abstract void sayHello();
    }
    
    static class Man extends Human {
        @Override
        protected void sayHello() {
            System.out.println("男人哭吧哭吧不是罪");
        }
    }
    
    static class Woman extends Human {
        @Override
        protected void sayHello() {
            System.out.println("山下的女人是老虎");
        }
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        man.sayHello();
        woman.sayHello();
        man = new Woman();
        man.sayHello();
    }
}
```

大家对 Java 重写有了解的话，应该能看懂这段代码的意思。Man 类和 Woman 类继承了 Human 类，并且重写了 `sayHello()` 方法。来看一下运行结果：

```
男人哭吧哭吧不是罪
山下的女人是老虎
山下的女人是老虎
```

这个运行结果很好理解，man 的引用类型为 Human，但指向的是 Man 对象，woman 的引用类型也为 Human，但指向的是 Woman 对象；之后，man 又指向了新的 Woman 对象。

从面向对象编程的角度，从多态的角度，我们对运行结果是很好理解的，但站在 Java 虚拟机的角度，它是如何判断 man 和 woman 该调用哪个方法的呢？

用 jclasslib 看一下 main 方法的字节码指令。

![](http://www.itwanger.com/assets/images/2021/04/java-caozuoshuzhan-11.png)

- 第 1 行：new 指令创建了一个 Man 对象，并将对象的内存地址压入栈中。
- 第 2 行：dup 指令将栈顶的值复制一份并压入栈顶。因为接下来的指令 invokespecial 会消耗掉一个当前类的引用，所以需要复制一份。
- 第 3 行：invokespecial 指令用于调用构造方法进行初始化。
- 第 4 行：astore_1，Java 虚拟机从栈顶弹出 Man 对象的引用，然后将其存入下标为 1 局部变量 man 中。
- 第 5、6、7、8 行的指令和第 1、2、3、4 行类似，不同的是 Woman 对象。
- 第 9 行：aload_1 指令将第局部变量 man 压入操作数栈中。
- 第 10 行：invokevirtual 指令调用对象的成员方法 `sayHello()`，注意此时的对象类型为 `com/itwanger/jvm/DynamicLinking$Human`。
- 第 11 行：aload_2 指令将第局部变量 woman 压入操作数栈中。
- 第 12 行同第 10 行。

注意，从字节码的角度来看，`man.sayHello()`（第 10 行）和 `woman.sayHello()`（第 12 行）的字节码是完全相同的，但我们都知道，这两句指令最终执行的目标方法并不相同。

究竟发生了什么呢？

还得从 `invokevirtual` 这个指令着手，看它是如何实现多态的。根据《Java 虚拟机规范》，invokevirtual 指令在运行时的解析过程可以分为以下几步：

>①、找到操作数栈顶的元素所指向的对象的实际类型，记作 C。
②、如果在类型 C 中找到与常量池中的描述符匹配的方法，则进行访问权限校验，如果通过则返回这个方法的直接引用，查找结束；否则返回 `java.lang.IllegalAccessError` 异常。
③、否则，按照继承关系从下往上一次对 C 的各个父类进行第二步的搜索和验证。
④、如果始终没有找到合适的方法，则抛出 `java.lang.AbstractMethodError` 异常。

也就是说，invokevirtual 指令在第一步的时候就确定了运行时的实际类型，所以两次调用中的 invokevirtual 指令并不是把常量池中方法的符号引用解析到直接引用上就结束了，还会根据方法接受者的实际类型来选择方法版本，这个过程就是 Java 重写的本质。我们把这种在运行期根据实际类型确定方法执行版本的过程称为**动态链接**。




**4）方法返回地址**

当一个方法开始执行后，只有两种方式可以退出这个方法：

- 正常退出，可能会有返回值传递给上层的方法调用者，方法是否有返回值以及返回值的类型根据方法返回的指令来决定，像之前提到的 ireturn 用于返回 int 类型，return 用于 void 方法；还有其他的一些，lreturn 用于 long 型，freturn 用于 float，dreturn 用于 double，areturn 用于引用类型。

- 异常退出，方法在执行的过程中遇到了异常，并且没有得到妥善的处理，这种情况下，是不会给它的上层调用者返回任何值的。

无论是哪种方式退出，在方法退出后，都必须返回到方法最初被调用时的位置，程序才能继续执行。一般来说，方法正常退出的时候，PC 计数器的值会作为返回地址，栈帧中很可能会保存这个计数器的值，异常退出时则不会。

方法退出的过程实际上等同于把当前栈帧出栈，因此接下来可能执行的操作有：恢复上层方法的局部变量表和操作数栈，把返回值（如果有的话）压入调用者栈帧的操作数栈中，调整 PC 计数器的值，找到下一条要执行的指令等。

-----

以上部分内容参考自周志明老师的《深入理解 Java 虚拟机》，以及好朋友张亚的《深入理解 JVM 字节码》。强烈推荐一下这两本书。

初学者一开始学习 Java 虚拟机的时候可能会感到很枯燥，很难懂，但有了一定的经验积累后，再来学习这块知识就会有一种开窍了的感觉。当然了，Java 虚拟机这块的知识点是必学的，因为性能优化、找工作面试，甚至说提高编程功底都是很亟需的。

四书之一《大学》中，开篇就提到了一个概念，叫做“格物致知”，意思就是通过研究事物背后的原理来获取知识，深入 Java 虚拟机、字节码等背后深层次的结构和原理来剖析 Java，能让我们变得更自信，全身弥漫出一种“技术高手”的光芒~