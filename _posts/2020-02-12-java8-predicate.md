---
layout: post
category: java
title: java 8 Predicate
tagline: by 沉默王二
tag: java
---

Predicate 是 Java 8 新增的一个函数式接口（通过 `@FunctionalInterface` 注解定义），因此可以将一个 [Lambda 表达式](http://www.itwanger.com/java/2020/02/09/java-Lambda.html)赋值于它。

<!--more-->


来看这样一个例子：

```java
Predicate<Integer> noGreaterThan5 = x -> x > 5;

List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

List<Integer> collect = list.stream()
        .filter(noGreaterThan5)
        .collect(Collectors.toList());

System.out.println(collect); // [6, 7, 8, 9, 10]
```

还可以使用 `and()` 方法对条件进行拼接：

```java
Predicate<Integer> noGreaterThan5 = x -> x > 5;
Predicate<Integer> noLessThan8 = x -> x < 8;

List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

List<Integer> collect = list.stream()
        .filter(noGreaterThan5.and(noLessThan8))
        .collect(Collectors.toList());

System.out.println(collect); // [6, 7]
```

除了 `and()`，还有 `or`：

```java
Predicate<String> lengthIs3 = x -> x.length() == 3;
Predicate<String> startWithA = x -> x.startsWith("A");

List<String> list = Arrays.asList("A", "AA", "AAA", "B", "BB", "BBB");

List<String> collect = list.stream()
        .filter(lengthIs3.or(startWithA))
        .collect(Collectors.toList());

System.out.println(collect); // [A, AA, AAA, BBB]
```

还有 `negate()`（相反）：

```java
Predicate<String> startWithA = x -> x.startsWith("A");

List<String> list = Arrays.asList("A", "AA", "AAA", "B", "BB", "BBB");

List<String> collect = list.stream()
        .filter(startWithA.negate())
        .collect(Collectors.toList());

System.out.println(collect); //[B, BB, BBB]
```

再来看一下 `test()`：

```java
Predicate<String> startWithA = x -> x.startsWith("王");

// 以王或者沉开头
boolean result = startWithA.or(x -> x.startsWith("沉")).test("沉默王二");
System.out.println(result);     // true
```