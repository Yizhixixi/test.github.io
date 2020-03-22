---
layout: post
category: life
title: Java中的斐波那契数列
tagline: by 沉默王二
tags: 
  - java
---

在斐波那契数列中，下一个数字是前两个数的和，例如：0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55。前两个数分别是 0 和 1。


<!--more-->


在 Java 中，有两种方法可以实现斐波那契数列：

- 循环的方式
- 递归的方式

### 循环的方式

```java
public class FibonacciExampleFor {
    public static void main(String[] args) {
        int n1 = 0, n2 = 1, n3;
        System.out.print(n1 + " " + n2);

        for (int i = 2; i < 10; ++i) {
            n3 = n1 + n2;
            System.out.print(" " + n3);
            n1 = n2;
            n2 = n3;
        }
    }
}
```

输出：

```
0 1 1 2 3 5 8 13 21 34
```

### 递归的方式

```java
public class FibonacciExampleRecursion {
    static int n1 = 0, n2 = 1, n3 = 0;

    static void printFibonacci(int count) {
        if (count > 0) {
            n3 = n1 + n2;
            n1 = n2;
            n2 = n3;
            System.out.print(" " + n3);
            
            // 之前打印了一个
            printFibonacci(count - 1);
        }
    }

    public static void main(String[] args) {
        int count = 10;

        // 打印 0 1
        System.out.print(n1 + " " + n2);
        // 已经打印了 2 个
        printFibonacci(count - 2);
    }
}
```

输出：

```
0 1 1 2 3 5 8 13 21 34
```

>如果觉得文章对你有点帮助，请微信搜索「 **沉默王二** 」第一时间阅读，回复【**666**】【**1024**】更有我为你精心准备的 500G 高清教学视频（已分门别类），以及大厂技术牛人整理的面经一份，本文源码已收录在码云，**[传送门](https://gitee.com/qing_gee/JavaPoint/tree/master)~** 

![](http://www.itwanger.com/assets/images/cmower_11.png)