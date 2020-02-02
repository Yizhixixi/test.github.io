---
layout: post
category: java
title: 惊呆了！Java程序员最常犯的错竟然是这10个
tagline: by 沉默王二
tag: java
---

和绝大多数的程序员一样，我也非常的宅。周末最奢侈的享受就是逛一逛技术型网站，比如说 programcreek，这个小网站上有一些非常有意思的主题。比如说：Java 程序员最常犯的错竟然是这 10 个，像这类令人好奇心想害死猫的主题，非常值得扒出来给大家分享一下。

<!--more-->



![](http://www.itwanger.com/assets/images/2020/01/java-top10-mistake-02.png)


PS：别问[我](https://mp.weixin.qq.com/s/feoOINGSyivBO8Z1gaQVOA)“为什么标题要加上‘惊呆了’？”问了答案就只有一个——吓唬人——总得勾起大家的阅读兴趣嘛（我容易吗我）。

### 01、把 Array 转成 ArrayList

说实在的，很多 Java 程序员喜欢把 Array 转成 ArrayList：

```java
List<String> list = Arrays.asList(arr);
```

但实际上，`Arrays.asList()` 返回的 ArrayList 并不是 `java.util.ArrayList`，而是 Arrays 的内部私有类 `java.util.Arrays.ArrayList`。虽然名字完全相同，都是 `ArrayList`，但两个类有着很大的不同。`Arrays.ArrayList` 虽然有 `set()`、`get()` 和 `contains()` 等方法，但却没有一个方法用来添加元素，因此它的大小是固定的。

如果想创建一个真正的 `ArrayList`，需要这样做：

```java
List<String> list = new ArrayList<String>(Arrays.asList(arr));
```

`ArrayList` 的构造方法可以接收一个 Collection 类型的参数，而 `Arrays.ArrayList` 是其子类，所以可以这样转化。

### 02、通过 Set 检查数组中是否包含某个值

之前我在写一篇文章《[如何检查Java数组中是否包含某个值 ](https://mp.weixin.qq.com/s/DBvgghP5cN6KlPnILaqjmQ)》中曾提到一种方法：

```java
Set<String> set = new HashSet<String>(Arrays.asList(arr));
return set.contains(targetValue);
```

这种方法确实可行，但却忽视了性能问题；为了能够尽快完成检查，可以这样做：

```java
Arrays.asList(arr).contains(targetValue);
```

或者使用普通的 for 循环或者 for-each。

### 03、通过 for 循环删除列表中的元素

新手特列喜欢使用 for 循环删除列表中的元素，就像这样：

```java
List<String> list = new ArrayList<String>(Arrays.asList("沉", "默", "王", "二"));
for (int i = 0; i < list.size(); i++) {
    list.remove(i);
}
System.out.println(list);
```

上面这段代码的目的是把列表中的元素全部删除，但结果呢：

```java
[默, 二]
```

竟然还有两个元素没删除，why？

当 List 的元素被删除时，其 `size()` 会减小，元素的下标也会改变，所以想通过 for 循环删除元素是行不通的。

那 for-each 呢？

```java
for(String s : list) {
    if ("沉".equals(s)) {
       list.remove(s);
    }
}

System.out.println(list);
```

竟然还抛出异常了：

```java
Exception in thread "main" java.util.ConcurrentModificationException
	at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:909)
	at java.util.ArrayList$Itr.next(ArrayList.java:859)
	at com.cmower.java_demo.programcreek.Top10Mistake.main(Top10Mistake.java:15)
```

抛出异常的原因，可以查看我之前写的文章《[Java，你告诉我 fail-fast 是什么鬼？](http://www.itwanger.com/java/2019/11/22/java-fail-fast.html)》。

有经验的程序员应该已经知道答案了，使用 Iterator：

```java
Iterator<String> iter = list.iterator();
while (iter.hasNext()) {
    String s = iter.next();

    if (s.equals("沉")) {
        iter.remove();
    }
}

System.out.println(list);
```

程序输出的结果如下：

```java
[默, 王, 二]
```

### 04、使用 Hashtable 而不是 HashMap

通常来说，哈希表应该是 Hashtable，但在 Java 中，哈希表通常指的是 HashMap。两者之间的区别之一是 Hashtable 是线程安全的。如果没有特殊要求的话，哈希表应该使用 HashMap 而不是 Hashtable。

### 05、使用原始类型

在 Java 中，新手很容易混淆无限通配符和原始类型之间的差别。举例来说，`List<?> list` 为无限通配符，`List list` 为原始类型。

来看下面这段代码：


```java
public static void add(List list, Object o){
    list.add(o);
}
public static void main(String[] args){
    List<String> list = new ArrayList<String>();
    add(list, 18);
    add(list, "沉默王二");
    String s = list.get(0);
}
```

这段代码在运行时会抛出异常：

```java
Exception in thread "main" java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.String
	at com.cmower.java_demo.programcreek.Top10Mistake.main(Top10Mistake.java:38)
```

使用原始类型非常的危险，因为跳过了泛型的检查。至于 `List<Object>` 和 `List` 之间的区别，查看我写的另外一篇文章：《[为什么不应该使用Java的原始类型](http://www.itwanger.com/java/2019/12/19/java-raw-type.html)》。

### 06、使用 public 修饰字段


有些新手喜欢使用 public 修饰字段，因为不需要 `getter/setter` 方法就可以访问字段。但实际上，这是一个非常糟糕的设计；有经验的程序员更习惯于提供尽可能低的访问级别。

### 07、使用 ArrayList 而不是 LinkedList

新手往往搞不清楚 ArrayList 和 LinkedList 之间的区别，因此更倾向于使用 ArrayList，因为比较面熟。但是呢，它们之间存在巨大的性能差异。简单的说吧，如果“添加/删除”的操作比较多，而“获取”的操作比较少，则应该首选 LinkedList。

### 08、使用过多的不可变对象

不可变对象有着不少的优点，比如说简单性和安全性。但是呢，如你所料，它也有一些难以抗拒的弊端：对于每一个不同的值，它都需要一个单独的对象来表示，这样的对象太多的话，很可能会导致大量的垃圾，回收的成本就变得特别高。

为了在可变与不可变之间保持平衡，通常会使用可变对象来避免产生太多中间对象。一个经典的例子就是使用 StringBuilder（可变对象） 来连接大量的字符串，否则的话，String（不可变对象）会产生很多要回收的垃圾。

反例：

```java
String result="";
for(String s: arr){
	result = result + s;
}
```

正例：

```java
StringBuilder result = new StringBuilder();
for (String s: strs) {
	result.append(s);
}
```

参考文章：[为什么 Java 字符串是不可变的？](https://mp.weixin.qq.com/s/CRQrm5zGpqWxYL_ztk-b2Q)

### 09、父类没有默认的无参构造方法

在 Java 中，如果父类没有定义构造方法，则编译器会默认插入一个无参的构造方法；但如果在父类中定义了构造方法，则编译器不会再插入无参构造方法。所以下面的代码会在编译时出错。

![](http://www.itwanger.com/assets/images/2020/01/java-top10-mistake-03.png)

子类中的无参构造方法试图调用父类的无参构造方法，但父类中并未定义，因此编译出错了。解决方案就是在父类中定义无参构造方法。


![](http://www.itwanger.com/assets/images/2020/01/java-top10-mistake-04.png)

### 10、使用构造方法创建字符串

创建字符串有两种方法：

1）使用双引号

```java
String er = "沉默王二";
```

2）使用构造方法

```java
String san = new String("沉默王三");
```

但是它们之间有着很大的不同（可以参照[创建 Java 字符串，用""还是构造函数](http://www.itwanger.com/java/2019/11/28/java-string-shuangyinhao-gouzaohanshu.html)），双引号被称为字符串常量，可以避免重复内容的字符串在内存中创建。

![](http://www.itwanger.com/assets/images/2020/01/java-top10-mistake-05.png)

好了，读者朋友们，以上就是本文的全部内容了。可以掏心窝子地说，没有任何客观的数据来证明它们就是前十名，但绝对非常普遍。如果不认可其中的内容，请在留言区轻喷，好人有好报。如果觉得不过瘾，还想看到更多，可以 star 二哥的 GitHub【[itwanger.github.io](https://github.com/qinggee/itwanger.github.io)】，本文已收录。

**原创不易，如果觉得有点用的话，请不要吝啬你手中点赞的权力**——这将是我最强的写作动力。