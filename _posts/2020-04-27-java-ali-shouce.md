---
layout: post
category: java
title: 解析一下阿里出品的泰山版Java开发手册
tagline: by 沉默王二
tags: 
  - java
---

说起华山，我就想起岳不群，不，令狐冲；说起泰山，我就想起司马迁，他的那句名言“人总有一死，或重于泰山，或轻于鸿毛”，真的发人深省啊。这就意味着，阿里出品的泰山版 Java 开发手册，是迄今为止最重量级的。

<!--more-->



![](http://www.itwanger.com/assets/images/2020/04/java-ali-shouce-01.png)

华山版是上个版本，啥时候更新的呢？2019 年 06 月 13 号，距离现在 10 个月了，时间也不短了，是时候更新了。

新版都更新了哪些内容呢？可以从官方的版本历史中看得出来。

### 01、发布错误码统一解决方案

![](http://www.itwanger.com/assets/images/2020/04/java-ali-shouce-02.png)

错误码用来干嘛呢？答案是异常日志，方便我们快速知晓错误来源，判断是谁那出的问题。上图中 A 表示错误来源于用户；还有 B 级的，表示错误来源于当前系统；C 级的，表示错误来源于第三方服务，比如 CDN 服务器。

这个解决方案还是值得借鉴的，很多成熟的系统都在使用错误码，如果你对接过微信支付的话，应该对错误码不会感到陌生。看到错误码，然后在手册中搜索一下，就能快速知晓错误的类型，还是很不错的。

### 02、新增 34 条新的规约

34 条太多了，我就挑几个重要的拉出来说一说吧。

**1）日期时间**

还记得上次技术圈的刷屏事件吧？就是那个 YYYY 和 yyyy 造成的问题。大写的 Y 表示的是当天所在的这一周是属于哪个年份的，小写的 y 表示的是当天所在的年份，差别还是挺大的。你品，你细品。

还有，大写的 M 和 小写的 m 是不同的，大写的 H 和小写的 h 也是不同的。

![](http://www.itwanger.com/assets/images/2020/04/java-ali-shouce-03.png)

另外，像获取当前毫秒数应该使用 `System.currentTimeMillis()`，而不是 `new Date().getTime()`，这些细致的规约，都应该牢记在心中，不要去犯这些低级的错误。

**2）三目运算的 NPE 问题**

说实话，这个问题我之前从来没有注意，这次看到了，就一起来学习一下。先来看下面这段代码：

```java
public class TestCondition {
    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 2;
        Integer c = null;
        Boolean flag = false;
        Integer result = flag ? a * b : c;
    }
}
```

条件 `a * b` 属于算术运算，它俩相乘后的结果是一个 int 类型，这就会导致 c 这个 Integer 类型自动拆箱，由于值为 null，就抛出了以下错误：

```
Exception in thread "main" java.lang.NullPointerException
    at com.cmower.mkyong.TestCondition.main(TestCondition.java:9)
```

那可能你会感到好奇，为什么两个 Integer 类型的变量相乘后会是一个 int 类型呢，这主要是由编译器决定的，它就是这么设计的，来看一下反编译后的字节码：

```java
public class TestCondition
{
    public static void main(String args[])
    {
        Integer a = Integer.valueOf(1);
        Integer b = Integer.valueOf(2);
        Integer c = null;
        Boolean flag = Boolean.valueOf(false);
        Integer result = Integer.valueOf(flag.booleanValue() ? a.intValue() * b.intValue() : c.intValue());
    }
}
```

`a * b` 时发生了自动拆箱，调用了 `intValue()` 方法，而三元运算的两个表达式的类型必须一致，这就导致 c 也调用了 `intValue()` 方法，由于 c 本身为 null，那就只能 NPE 了。明白了吧？

**3）Collectors 类的 `toMap()` 方法**

手册上说，在使用 `java.util.stream.Collectors` 类的 `toMap()` 方法转 Map 时，一定要使用含有参数类型为 BinaryOperator，参数名为 mergeFunction 的方法，否则当出现相同 key 值时会抛出 IllegalStateException 异常。

这段话可能理解上有点难度，那先来看一段代码吧！

```java
String[] departments = new String[] {"沉默王二", "沉默王二", "沉默王三"};
Map<Integer, String> map = Arrays.stream(departments)
        .collect(Collectors.toMap(String::hashCode, str -> str));
```

运行这段代码的时候，就会抛出异常，堆栈信息如下所示：

```
Exception in thread "main" java.lang.IllegalStateException: Duplicate key 867758096 (attempted merging values 沉默王二 and 沉默王二)
    at java.base/java.util.stream.Collectors.duplicateKeyException(Collectors.java:133)
    at java.base/java.util.stream.Collectors.lambda$uniqKeysMapAccumulator$1(Collectors.java:180)
```

key 重复了，因为两个“沉默王二”的 hashCode 相同，那这时候的解决办法呢？

```java
String[] departments = new String[] {"沉默王二", "沉默王二", "沉默王三"};
Map<Integer, String> map = Arrays.stream(departments)
        .collect(Collectors.toMap(String::hashCode, str -> str, (v1, v2) -> v2));
```

多加个参数 ` (v1, v2) -> v2`，也就是重复的时候选一个。来看看此时调用的 `toMap()` 方法吧。

```java
public static <T, K, U>
Collector<T, ?, Map<K,U>> toMap(Function<? super T, ? extends K> keyMapper,
                                Function<? super T, ? extends U> valueMapper,
                                BinaryOperator<U> mergeFunction) {
    return toMap(keyMapper, valueMapper, mergeFunction, HashMap::new);
}
```

BinaryOperator 和 mergeFunction 是不是出现了？

### 03、修改描述 90 处

手册上说，比如，阻塞等待锁、建表的小数类型等描述有修改，我特么花了半个小时也没有找出来和上一个版本之间的差别。

![泰山版](http://www.itwanger.com/assets/images/2020/04/java-ali-shouce-04.png)

![华山版](http://www.itwanger.com/assets/images/2020/04/java-ali-shouce-05.png)

不知道是不是手册的小编在瞎说，你要是能发现差别，告诉我一声。

### 04、完善若干处示例

比如说 SQL 语句栏目里的 ISNULL 的示例，确实比华山版追加了一个更详细的反例，见下图。

![](https://upload-images.jianshu.io/![](http://www.itwanger.com/assets/images/2020/04/java-ali-shouce-06.png)

但说实话，这段反例的描述我看了至少六遍才搞懂是什么意思。首先，不要在 null 前换行，影响阅读性，这倒是真的；其次呢，不要使用 `column is null` 进行判空，使用 `ISNULL(column)` 判空，效率更高，也不会出现换行的情况。

```sql
select * from cms_subject where column is null and
column1 is not null;

select * from cms_subject where ISNULL(column) and
column1 is not null;
```

### 05、最后

2016 年 12 月份，阿里首次向业界开放了这份《Java 开发手册》，到泰山版发布，已经过去了 3 年多时间了，这份手册也在全球 Java 开发者共同的努力下，成为业界普遍遵循的开发规范。这份手册包含的知识点非常全面，七大维度编程规约、异常日志、单元测试、安全规约、MySQL数据库、工程规约、设计规约都有罗列。

如果你想成为一名优秀的 Java 工程师，那么这份手册上的内容几乎是必须要掌握的。是不是已经迫不及待想要下载这份手册了？

微信搜索「**沉默王二**」回复「**手册**」就可以免费获取了，当然你也可以扫描下面的二维码后回复，赶紧赶紧。

![](http://www.itwanger.com/assets/images/itwanger.jpg)

最后，我衷心地祝福你，希望你能学有所成，to be better，奥利给！