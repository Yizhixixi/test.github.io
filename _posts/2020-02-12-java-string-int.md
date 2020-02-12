---
layout: post
category: java
title: Java：把String转成int
tagline: by 沉默王二
tag: java
---

在 Java 中，我们可以通过 `Integer.parseInt()` 将一个字符串转成 int。来看下面这个例子。


<!--more-->

```java
String number = "10";
int result = Integer.parseInt(number);
System.out.println(result);
```

输出：

```
10
```

除此之外，还可以使用 `Integer.valueOf()`，返回一个 int 的包装器 Integer。

```java
String number = "10";
Integer result = Integer.valueOf(number);
System.out.println(result);
```

输出：

```
10
```

总结一下，`parseInt(String)` 返回的是原始类型 int，而 `valueOf(String)` 返回的是一个包装器类型 Integer 对象。

如果一个字符串中包含了不可转的字符时，会抛出 `NumberFormatException`。



```java
String number = "10A";
int result = Integer.parseInt(number);
System.out.println(result); 
```

输出：

```
Exception in thread "main" java.lang.NumberFormatException: For input string: "10A"
    at java.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:68)
    at java.base/java.lang.Integer.parseInt(Integer.java:658)
    at java.base/java.lang.Integer.parseInt(Integer.java:776)
    at com.cmower.java_demo.mkyoung.String2int.main(String2int.java:14)
```