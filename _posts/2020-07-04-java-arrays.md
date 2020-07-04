---
layout: post
category: life
title: 老板急坏了，公司网站的 HTTPS 过期了
tagline: by 沉默王二
tags: 
  - 程序员
---

`java.util.Arrays` 类就是为数组而生的专用工具类，基本上常见的对数组的操作，Arrays 类都考虑到了，这让我由衷地觉得，是时候给该类的作者 Josh Bloch、Neal Gafter、John Rose 点个赞了。

<!--more-->

（我是怎么知道作者名的？看源码就可以，小伙伴们，装逼吧）

Arrays 都可以干嘛呢？常见的有：

- 创建数组
- 比较数组
- 数组排序
- 数组检索
- 数组转流
- 打印数组
- 数组转 List
- setAll（没想好中文名）
- parallelPrefix（没想好中文名）

那接下来，小伙伴们是不是已经迫不及待想要和二哥一起来打怪进阶了。走你。

### 01、创建数组

使用 Arrays 类创建数组可以通过以下三个方法：

- copyOf，复制指定的数组，截取或用 null 填充
- copyOfRange，复制指定范围内的数组到一个新的数组
- fill，对数组进行填充

1）copyOf，直接来看例子：

```java
String[] intro = new String[] { "沉", "默", "王", "二" };
String[] revised = Arrays.copyOf(intro, 3);
String[] expanded = Arrays.copyOf(intro, 5);
System.out.println(Arrays.toString(revised));
System.out.println(Arrays.toString(expanded));
```

revised 和 expanded 是复制后的新数组，长度分别是 3 和 5，指定的数组长度是 4。来看一下输出结果：

```
[沉, 默, 王]
[沉, 默, 王, 二, null]
```

看到没？revised 截取了最后一位，因为长度是 3 嘛；expanded 用 null 填充了一位，因为长度是 5。

2）copyOfRange，直接来看例子：

```java
String[] intro = new String[] { "沉", "默", "王", "二" };
String[] abridgement = Arrays.copyOfRange(intro, 0, 3);
System.out.println(Arrays.toString(abridgement));
```

`copyOfRange()` 方法需要三个参数，第一个是指定的数组，第二个是起始位置（包含），第三个是截止位置（不包含）。来看一下输出结果：

```java
[沉, 默, 王]
```

0 的位置是“沉”，3 的位置是“二”，也就是说截取了从 0 位（包含）到 3 位（不包含）的数组元素。那假如说下标超出了数组的长度，会发生什么呢？

```java
String[] abridgementExpanded = Arrays.copyOfRange(intro, 0, 6);
System.out.println(Arrays.toString(abridgementExpanded));
```

结束位置此时为 6，超出了指定数组的长度 4，来看一下输出结果：

```
[沉, 默, 王, 二, null, null]
```

仍然使用了 null 进行填充。为什么要这么做呢？小伙伴们思考一下，我想是作者考虑到了数组越界的问题，不然每次调用 Arrays 类就要先判断很多次长度，很麻烦。

3）fill，直接来看例子：

```java
String[] stutter = new String[4];
Arrays.fill(stutter, "沉默王二");
System.out.println(Arrays.toString(stutter));
```

使用 new 关键字创建了一个长度为 4 的数组，然后使用 `fill()` 方法将 4 个位置填充为“沉默王二”，来看一下输出结果：

```
[沉默王二, 沉默王二, 沉默王二, 沉默王二]
```

如果想要一个元素完全相同的数组时， `fill()` 方法就派上用场了。

### 02、比较数组

Arrays 类的 `equals()` 方法用来判断两个数组是否相等，来看下面这个例子：

```java
String[] intro = new String[] { "沉", "默", "王", "二" };
boolean result = Arrays.equals(new String[] { "沉", "默", "王", "二" }, intro);
System.out.println(result);
boolean result1 = Arrays.equals(new String[] { "沉", "默", "王", "三" }, intro);
System.out.println(result1);
```

输出结果如下所示：

```
true
false
```

指定的数组为沉默王二四个字，比较的数组一个是沉默王二，一个是沉默王三，所以 result 为 true，result1 为 false。

简单看一下 `equals()` 方法的源码：

```java
public static boolean equals(Object[] a, Object[] a2) {
    if (a==a2)
        return true;
    if (a==null || a2==null)
        return false;

    int length = a.length;
    if (a2.length != length)
        return false;

    for (int i=0; i<length; i++) {
        if (!Objects.equals(a[i], a2[i]))
            return false;
    }

    return true;
}
```

因为数组是一个对象，所以先使用“==”操作符进行判断，如果不相等，再判断是否为 null，两个都为 null，返回 false；紧接着判断 length，不等的话，返回 false；否则的话，依次调用 `Objects.equals()` 比较相同位置上的元素是否相等。

感觉非常严谨，这也就是学习源码的意义，鉴赏的同时，学习。

除了 `equals()` 方法，还有另外一个诀窍可以判断两个数组是否相等，尽管可能会出现误差（概率非常小）。那就是 `Arrays.hashCode()` 方法，先来看一下该方法的源码：

```java
public static int hashCode(Object a[]) {
    if (a == null)
        return 0;

    int result = 1;

    for (Object element : a)
        result = 31 * result + (element == null ? 0 : element.hashCode());

    return result;
}
``` 

哈希算法本身是非常严谨的，所以如果两个数组的哈希值相等，那几乎可以判断两个数组是相等的。

```java
String[] intro = new String[] { "沉", "默", "王", "二" };

System.out.println(Arrays.hashCode(intro));
System.out.println(Arrays.hashCode(new String[] { "沉", "默", "王", "二" }));
```

来看一下输出结果：

```
868681617
868681617
```

两个数组的哈希值相等，毕竟元素是一样的。但这样确实不够严谨，优先使用 `Objects.equals()` 方法。

### 03、数组排序

Arrays 类的 `sort()` 方法用来判断两个数组是否相等，来看下面这个例子：

```java
String[] intro1 = new String[] { "chen", "mo", "wang", "er" };
String[] sorted = Arrays.copyOf(intro1, 4);
Arrays.sort(sorted);
System.out.println(Arrays.toString(sorted));
```

由于排序会改变原有的数组，所以我们使用了 `copyOf()` 方法重新复制了一份。来看一下输出结果：

```
[chen, er, mo, wang]
```

可以看得出，按照的是首字母的升序进行排列的。基本数据类型是按照双轴快速排序的，引用数据类型是按照 TimSort 排序的，使用了 Peter McIlroy 的“乐观排序和信息理论复杂性”中的技术。

### 04、数组检索

数组排序后就可以使用 Arrays 类的 `binarySearch()` 方法进行二分查找了。否则的话，只能线性检索，效率就会低很多。

```java
String[] intro1 = new String[] { "chen", "mo", "wang", "er" };
String[] sorted = Arrays.copyOf(intro1, 4);
Arrays.sort(sorted);
int exact = Arrays.binarySearch(sorted, "wang");
System.out.println(exact);
int caseInsensitive = Arrays.binarySearch(sorted, "Wang", String::compareToIgnoreCase);
System.out.println(caseInsensitive);
```

 `binarySearch()` 方法既可以精确检索，也可以模糊检索，比如说忽略大小写。来看一下输出结果：

```
3
3
```

排序后的结果是 `[chen, er, mo, wang]`，所以检索出来的下标是 3。

### 05、数组转流

Stream 流非常强大，需要入门的小伙伴可以查看我之前写的一篇文章：

[一文带你入门Java Stream流，太强了](https://mp.weixin.qq.com/s/7hNUjjmqKcHDtymsfG_Gtw)

Arrays 类的 `stream()` 方法可以将数组转换成流：

```java
String[] intro = new String[] { "沉", "默", "王", "二" };
System.out.println(Arrays.stream(intro).count());
```

还可以为 `stream()` 方法指定起始下标和结束下标：

```java
System.out.println(Arrays.stream(intro, 1, 2).count());
```

如果下标的范围有误的时候，比如说从 2 到 1 结束，则程序会抛出 ArrayIndexOutOfBoundsException 异常：

```
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: origin(2) > fence(1)
	at java.base/java.util.Spliterators.checkFromToBounds(Spliterators.java:387)
```

### 06、打印数组

关于数组的打印方式，我之前单独写过一篇文章：

[打印Java数组最优雅的方式是什么？](https://mp.weixin.qq.com/s/udUi8ee8lA412aqpqFBcmw)

里面谈了很多种数组打印的方式，因为数组是一个对象，直接 `System.out.println` 的话，结果是这样的：

```
[Ljava.lang.String;@3d075dc0
```

那最优雅的方式，其实文章里面已经出现过很多次了，就是 `Arrays.toString()`：

```java
public static String toString(Object[] a) {
    if (a == null)
        return "null";

    int iMax = a.length - 1;
    if (iMax == -1)
        return "[]";

    StringBuilder b = new StringBuilder();
    b.append('[');
    for (int i = 0; ; i++) {
        b.append(String.valueOf(a[i]));
        if (i == iMax)
            return b.append(']').toString();
        b.append(", ");
    }
}
```

小伙伴可以好好欣赏一下这段源码，感觉考虑得非常周到。

### 07、数组转 List

尽管数组非常强大，但它自身可以操作的工具方法很少，比如说判断数组中是否包含某个值。转成 List 的话，就简便多了。

```java
String[] intro = new String[] { "沉", "默", "王", "二" };
List<String> rets = Arrays.asList(intro);
System.out.println(rets.contains("二"));
```

不过需要注意的是，`Arrays.asList()` 返回的是 `java.util.Arrays.ArrayList`，并不是  `java.util.ArrayList`，它的长度是固定的，无法进行元素的删除或者添加。

```java
rets.add("三");
rets.remove("二");
```

执行这两个方法的时候，会抛出异常：

```
Exception in thread "main" java.lang.UnsupportedOperationException
	at java.base/java.util.AbstractList.add(AbstractList.java:153)
	at java.base/java.util.AbstractList.add(AbstractList.java:111)
```

要想操作元素的话，需要多一步转化：

```java
List<String> rets1 = new ArrayList<>(Arrays.asList(intro));
rets1.add("三");
rets1.remove("二");
```

### 08、setAll

Java 8 新增了 `setAll()` 方法，它提供了一个函数式编程的入口，可以对数组的元素进行填充：

```java
int[] array = new int[10];
Arrays.setAll(array, i -> i * 10);
System.out.println(Arrays.toString(array));
```

这段代码什么意思呢？i 就相当于是数组的下标，值从 0 开始，到 9 结束，那么 `i * 10` 就意味着 0 * 10 开始，到 9 * 10 结束，来看一下输出结果：

```
[0, 10, 20, 30, 40, 50, 60, 70, 80, 90]
``` 

比之前的 `fill()` 方法强大多了，对吧？不再填充的是相同的元素。

### 09、parallelPrefix

`parallelPrefix()` 方法和 `setAll()` 方法一样，也是 Java 8 之后提供的，提供了一个函数式编程的入口，通过遍历数组中的元素，将当前下标位置上的元素与它之前下标的元素进行操作，然后将操作后的结果覆盖当前下标位置上的元素。

```java
int[] arr = new int[] { 1, 2, 3, 4};
Arrays.parallelPrefix(arr, (left, right) -> left + right);
System.out.println(Arrays.toString(arr));
```

上面代码中有一个 Lambda 表达式（`(left, right) -> left + right`），需要入门的小伙伴可以查看我之前写的一篇文章：

[Lambda 表达式入门，看这篇就够了](https://mp.weixin.qq.com/s/ozr0jYHIc12WSTmmd_vEjw)

那为了让小伙伴看得更明白一些，我们把上面这段代码稍微改造一下：

```java
int[] arr = new int[]{1, 2, 3, 4};
Arrays.parallelPrefix(arr, (left, right) -> {
    System.out.println(left + "，" + right);
    return left + right;
});
System.out.println(Arrays.toString(arr));
```

先来看一下输出结果：

```
1，2
3，3
6，4
[1, 3, 6, 10]
```

也就是说， Lambda 表达式执行了三次：

- 第一次是 1 和 2 相加，结果是 3，替换下标为 1 的位置
- 第二次是 3 和 3 相加，结果是 6，也就是第一次的结果和下标为 2 的元素相加的结果
- 第三次是 6 和 4 相加，结果是 10，也就是第二次的结果和下标为 3 的元素相加的结果

有点强大，对吧？

### 10、总结

好了，我亲爱的小伙伴们，以上就是本文的全部内容了，能看到这里的都是最优秀的程序员，二哥必须要为你点个赞。

我是沉默王二，一枚有趣的程序员。如果觉得文章对你有点帮助，请微信搜索「 **沉默王二** 」第一时间阅读，回复【**666**】更有我为你精心准备的 500G 高清教学视频（已分门别类）。

>本文 [GitHub](https://github.com/qinggee/itwanger.github.io) 已经收录，有大厂面试完整考点，欢迎 Star。

**原创不易，莫要白票，请你为本文点个赞吧**，这将是我写作更多优质文章的最强动力。