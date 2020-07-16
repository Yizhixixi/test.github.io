---
layout: post
category: life
title: 我去，脸皮厚啊，你竟然使用==比较浮点数？
tagline: by 沉默王二
tags: 
  - 程序员
---

>先看再点赞，给自己一点思考的时间，思考过后请毫不犹豫微信搜索【**沉默王二**】，关注这个长发飘飘却靠才华苟且的程序员。
本文 **GitHub** [github.com/itwanger](https://github.com/qinggee/itwanger.github.io) 已收录，里面还有技术大佬整理的面试题，以及二哥的系列文章。

<!--more-->




老读者都知道了，我在九朝古都洛阳的一家小作坊式的公司工作，身兼数职，谈业务、敲代码的同时带两个新人，其中一个就是大家熟知的小王，经常犯错，被我写到文章里。

不过，小王的心态一直很不错，他不觉得被我批评有什么丢人的，反而每次读完我的文章后觉得自己又升级了。因此，我觉得小王大有前途，再这么干个一两年，老板要是觉得我的性价比低了，没准就把我辞退留下小王了。一想到这，我竟然枯燥一笑了。

![](http://www.itwanger.com/assets/images/2020/07/java-float-01.gif)

那天，我闲来无聊，就准备偷偷 review 一下小王的代码，看能不能鸡蛋里挑点骨头，没想到，还真的被我挑到了。

```java
double d1 = .0;
for (int i = 1; i <= 11; i++) {
    d1 += .1;
}

double d2 = .1 * 11;

System.out.println(d1 == d2);
```

小王这段代码蛮炫技的，其实，尤其是 `.0`、`.1` 的写法，我平常都老实巴交的写成 `0.0`、`0.1`，从来没想着要把小数点前面的 0 省略。

按照正常的逻辑来看，d1 在经过 11 次循环加 `.1` 后的结果应该是 `1.1`，d2 通过 `.1` 乘以 11 后的结果也应该是 `1.1`，最后打印出来的结果应该是 true，对吧？小王应该也是这么期待的，我觉得。

但我当时硬是没忍住我的暴脾气，破口大骂：“我擦，小王，你竟然敢用 == 比较浮点数，这不是找刺激吗？”

![](http://www.itwanger.com/assets/images/2020/07/java-float-02.png)


如果有读者也觉得输出结果是 true 的话，可以把上面这段代码在本地运行一下，输出的结果一定会出乎你的意料。

```
false
```

对，false，我没骗你。如何正确地比较浮点数（单精度的 float 和双精度的 double），不单单是 Java 特定的问题，很多编程语言的初学者也会遇到同样的问题。在计算机的内存中，存储浮点数时使用的是 IEEE 754 标准，就会有精度的问题，至于实际上的存储转换过程，这篇文章不做过多的探讨。

（主要是我太菜了，探讨的过程很枯燥，一点都不有趣，严谨地理论推导就交给那些真正的技术大佬们吧，我就不献丑了。）

![](http://www.itwanger.com/assets/images/2020/07/java-float-03.png)

同学们只需要知道，存储和转换的过程中浮点数容易引起一些较小的舍入误差，正是这个原因，导致在比较浮点数的时候，不能使用“==”操作符——要求严格意义上的完全相等。

再来看一下小王的代码，我们把 d1 和 d2 打印出来，看看它们的值到底是什么。

```java
d1：1.0999999999999999
d2：1.1
```

怪不得“==”的时候输出 false，原来 d1 的值有一些误差，并不是我们预期的 1.1。既然“==”不能用来比较浮点数，那么小王就得挨骂，这逻辑讲得通吧？

那这个问题该怎么解决呢？

对于浮点数的存储和转化问题，我表示无能为力，这是实在话，计算机的底层问题，驾驭不了。但是，可以通过一些折中的办法，比如说允许两个值之间有点误差（指定一个阈值），小到 0.000000.....1，具体多少个  0 懒得数了，反正特别小，那么我们就认为两个浮点数是相等的。

第一种方案就是使用 `Math.abs()` 方法来计算两个浮点数之间的差异，如果这个差异在阈值范围之内，我们就认为两个浮点数是相等。

```java
final double THRESHOLD = .0001;

double d1 = .0;
for (int i = 1; i <= 11; i++) {
    d1 += .1;
}

double d2 = .1 * 11;

if(Math.abs(d1-d2) < THRESHOLD) {
    System.out.println("d1 和 d2 相等");
} else {
    System.out.println("d1 和 d2 不等");
}
```

`Math.abs()` 方法用来返回 double 的绝对值，如果 double 小于 0，则返回 double 的正值，否则返回 double。也就是说，`abs()` 后的结果绝对大于 0，如果结果小于阈值（THRESHOLD），我们就认为 d1 和 d2 相等。

第二种解决方案就是使用 BigDecimal 类，可以指定要舍入的模式和精度，这样就可以解决舍入的误差。

可以使用 BigDecimal 类的 `compareTo()` 方法对两个数进行比较，该方法将会忽略小数点后的位数，怎么理解这句话呢？比如说 2.0 和 2.00 的位数不同，但它俩的值是相等的。

如果 a 小于 b，则该方法返回 -1，如果相等，则返回 0，否则返回 -1。

注意，千万不要使用 `equals()` 方法对两个 BigDecimal 对象进行比较，这是因为 `equals()` 方法会考虑位数，如果位数不同，则会返回 false，尽管数学值是相等的。

```java
BigDecimal a = new BigDecimal("2.00");
BigDecimal b = new BigDecimal("2.0");

System.out.println(a.equals(b));
System.out.println(a.compareTo(b) == 0);
```

`a.equals(b)` 的结果就为 false，因为 2.00 和 2.0 小数点后的位数不同，但 `a.compareTo(b) == 0` 的结果就为 true，因为 2.00 和 2.0 在数学层面的值的确是相等的。

`compareTo()` 方法比较的过程非常严谨，感兴趣的同学可以查看一下源码，其中位数不同的时候，会执行以下方法进行比较。

```java
private int compareMagnitude(BigDecimal val) {
    // Match scales, avoid unnecessary inflation
    long ys = val.intCompact;
    long xs = this.intCompact;
    if (xs == 0)
        return (ys == 0) ? 0 : -1;
    if (ys == 0)
        return 1;

    long sdiff = (long)this.scale - val.scale;
    if (sdiff != 0) {
        // Avoid matching scales if the (adjusted) exponents differ
        long xae = (long)this.precision() - this.scale;   // [-1]
        long yae = (long)val.precision() - val.scale;     // [-1]
        if (xae < yae)
            return -1;
        if (xae > yae)
            return 1;
        if (sdiff < 0) {
            // The cases sdiff <= Integer.MIN_VALUE intentionally fall through.
            if ( sdiff > Integer.MIN_VALUE &&
                    (xs == INFLATED ||
                            (xs = longMultiplyPowerTen(xs, (int)-sdiff)) == INFLATED) &&
                    ys == INFLATED) {
                BigInteger rb = bigMultiplyPowerTen((int)-sdiff);
                return rb.compareMagnitude(val.intVal);
            }
        } else { // sdiff > 0
            // The cases sdiff > Integer.MAX_VALUE intentionally fall through.
            if ( sdiff <= Integer.MAX_VALUE &&
                    (ys == INFLATED ||
                            (ys = longMultiplyPowerTen(ys, (int)sdiff)) == INFLATED) &&
                    xs == INFLATED) {
                BigInteger rb = val.bigMultiplyPowerTen((int)sdiff);
                return this.intVal.compareMagnitude(rb);
            }
        }
    }
    if (xs != INFLATED)
        return (ys != INFLATED) ? longCompareMagnitude(xs, ys) : -1;
    else if (ys != INFLATED)
        return 1;
    else
        return this.intVal.compareMagnitude(val.intVal);
}
```

好了，现在让我们使用 BigDecimal 来解决精度问题吧。

```java
BigDecimal d1 = new BigDecimal("0.0");
BigDecimal pointOne = new BigDecimal("0.1");
for (int i = 1; i <= 11; i++) {
    d1 = d1.add(pointOne);
}

BigDecimal d2 = new BigDecimal("0.1");
BigDecimal eleven = new BigDecimal("11");
d2 = d2.multiply(eleven);

System.out.println("d1 = " + d1);
System.out.println("d2 = " + d2);

System.out.println(d1.compareTo(d2));
```

程序输出的结果如下：

```
d1 = 1.1
d2 = 1.1
0
```

d1 和 d2 都为 1.1，所以 `compareTo()` 的结果就为 0，表示两个值是相等的。

总结一下，在遇到浮点数的时候，千万不要使用“==”操作符来进行比较，因为有精度问题。要么使用阈值来忽略舍入的问题，要么使用 BigDecimal 来替代 double 或者 float。

等会我就把这篇文章发给小王看看，同学们顺手点个赞👍，让小王不再感到那么孤单寂寞和冷。

-----

我是沉默王二，一枚有颜值却靠才华苟且的程序员。**关注即可提升学习效率，别忘了三连啊，点赞、收藏、留言，我不挑，奥利给**。

注：如果文章有任何问题，欢迎毫不留情地指正。

很多读者都同情我说，“二哥，你像母猪似的日更原创累不累？”我只能说写作不易，且行且珍惜啊，关键是我真的喜欢写作。最后，欢迎微信搜索「**沉默王二**」第一时间阅读，回复「**简历**」更有阿里大佬的简历模板，本文 **GitHub** [github.com/itwanger](https://github.com/qinggee/itwanger.github.io) 已收录，欢迎 star。