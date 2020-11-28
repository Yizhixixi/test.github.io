---
layout: post
category: java
title: GitHub 上 1.3k Star 的 strman-java 项目有值得学习的地方吗？源码视角来分析一波
tagline: by 沉默王二
tags: 
  - java
---


大家好，我是沉默王二。


<!--more-->




很多初学编程的同学，经常给我吐槽，说：“二哥，你在敲代码的时候会不会有这样一种感觉，写着写着看不下去了，觉得自己写出来的代码就好像屎一样？”

这里我必须得说一句，初入“江湖”的时候，确实会觉得自己的代码写得很烂，但这么多年下来，这种感觉已经荡然无存了。

（吹嘛，我也会，哈哈）

那，怎么才能让写出来的代码不那么烂呢？

我的一个经验就是，“拿来主义”，尽量不去重复造轮子。使用那些已经被验证过，足够优质的开源库不仅能够让我们的代码变得优雅，还能够让我们在不断的使用过程当中，学习到编程的精髓。

洋务运动的时候，有一句很响亮的口号叫做，“师夷长技以制夷”。先去用，再去学，自然而然就会变得牛逼。同学们，你们说，是不是这个理？

我今天推荐的这款开源库，名字叫做 [strman-java](https://github.com/shekhargulati/strman-java)，GitHub 上标星 1.3k，一款超赞的字符串处理工具库，基于 Java 8，语法非常简洁。

接下来，我们来看看怎么用。

Maven 项目只需要在 pom.xml 文件中添加以下依赖即可。

```
<dependency>
    <groupId>com.shekhargulati</groupId>
    <artifactId>strman</artifactId>
    <version>0.4.0</version>
</dependency>
```

好了，可以肆无忌惮地调用 strman-java 的 API 了。我会在介绍的时候插入一些源码的介绍，方便同学们更深一步的学习，尽量做到“知其然知其所以然”。

![](http://www.itwanger.com/assets/images/2020/11/java-strman-01.png)


### 01、append

把可变字符串参数添加到指定的字符串尾部。

```java
Strman.append("沉","默","王","二");
```

结果如下所示：

```
沉默王二
```

append 对应的方法是 prepend，把可变字符串参数前置到指定的字符串前面，使用方法如下。

```java
Strman.prepend("沉","默","王","二");
```

结果如下所示：

```
默王二沉
```


### 02、appendArray

把字符串数组添加到指定的字符串尾部。

```java
String [] strs = {"默","王","二"};
Strman.appendArray("沉",strs);
```

结果如下所示：

```
沉默王二
```

append 内部其实调用的 appendArray，来看一下源码：

```java
public static String append(final String value, final String... appends) {
    return appendArray(value, appends);
}
```

**当使用可变参数的时候，实际上是先创建了一个数组，该数组的大小就是可变参数的个数，然后将参数放入数组当中，再将数组传递给被调用的方法**。

通过观察反编译后的字节码，就能看得到。

```java
Strman.append("沉","默","王","二");
```

实际等同于：

```
Strman.append("沉", new String[]{"默", "王", "二"});
```

再来看一下 appendArray 方法的源码：

```java
public static String appendArray(final String value, final String[] appends) {
    StringJoiner joiner = new StringJoiner("");
    for (String append : appends) {
        joiner.add(append);
    }
    return value + joiner.toString();
}
```

内部用的 StringJoiner，Java 8 时新增的一个类。构造方法有两种。

第一种，指定分隔符：

```java
public StringJoiner(CharSequence delimiter) {
    this(delimiter, "", "");
}
```

第二种，指定分隔符、前缀、后缀：

```java
public StringJoiner(CharSequence delimiter,
                    CharSequence prefix,
                    CharSequence suffix) {
    this.prefix = prefix.toString();
    this.delimiter = delimiter.toString();
    this.suffix = suffix.toString();
}
```

虽然也可以在 StringBuilder 类的帮助下在每个字符串之后附加分隔符，但 StringJoiner 提供了更简单的方法来实现，无需编写大量的代码。

### 03、at

获取指定索引处上的字符。

```java
Strman.at("沉默王二", 0);
Strman.at("沉默王二", -1);
Strman.at("沉默王二", 4);
```

结果如下所示：

```
Optional[沉]
Optional[二]
Optional.empty
```

也就是说，at 可以处理 `-(length-1)` 到 `(length-1)` 之内的索引（当索引为负数的时候将从末尾开始查找），如果超出这个范围，将会返回 `Optional.empty`，避免发生空指针。

来看一下源码：

```java
public static Optional<String> at(final String value, int index) {
    if (isNullOrEmpty(value)) {
        return Optional.empty();
    }
    int length = value.length();
    if (index < 0) {
        index = length + index;
    }
    return (index < length && index >= 0) ? Optional.of(String.valueOf(value.charAt(index))) : Optional.empty();
}
```

本质上，是通过 String 类的 `charAt()` 方法查找的，但包裹了一层 Optional，就巧妙地躲开了烦人的空指针。

Optional 是 Java 8 时新增的一个类，该类提供了一种用于表示可选值而非空引用的类级别解决方案。

![](http://www.itwanger.com/assets/images/2020/11/java-strman-02.png)

### 04、between 

按照指定起始字符和截止字符来返回一个字符串数组。

```java
String [] results = Strman.between("[沉默王二][一枚有趣的程序员]","[", "]");
System.out.println(Arrays.toString(results));
```

结果如下所示：

```
[沉默王二, 一枚有趣的程序员]
```

来看一下源码：

```java
public static String[] between(final String value, final String start, final String end) {
    String[] parts = value.split(end);
    return Arrays.stream(parts).map(subPart -> subPart.substring(subPart.indexOf(start) + start.length()))
            .toArray(String[]::new);
}
```

`java.util.Arrays` 类是为数组而生的专用工具类，基本上常见的对数组的操作，Arrays 类都考虑到了，`stream()` 方法可以将数组转换成流：

```java
String[] intro = new String[] { "沉", "默", "王", "二" };
Arrays.stream(intro);
```

Java 8 新增的 Stream 流在很大程度上提高了开发人员在操作集合（Collection）时的生产力。要想操作流，首先需要有一个数据源，可以是数组或者集合。每次操作都会返回一个新的流对象，方便进行链式操作，但原有的流对象会保持不变。

`map()` 方法可以把一个流中的元素转化成一个新流中的元素，它可以接收一个 Lambda 表达式作为参数。Lambda 表达式描述了一个代码块（或者叫匿名方法），可以将其作为参数传递给构造方法或者普通方法以便后续执行。

考虑下面这段代码：

```java
() -> System.out.println("沉默王二")
```

来从左到右解释一下，`()` 为 Lambda 表达式的参数列表（本例中没有参数），`->` 标识这串代码为 Lambda 表达式（也就是说，看到 `->` 就知道这是 Lambda），`System.out.println("沉默王二")` 为要执行的代码，即将“沉默王二”打印到标准输出流。

`toArray()` 方法可以将流转换成数组，你可能比较好奇的是 `String[]::new`，它是什么东东呢？来看一下 `toArray()` 方法的源码。

```java
<A> A[] toArray(IntFunction<A[]> generator);
```

也就是说 `String[]::new` 是一个 IntFunction，一个可以产生所需的新数组的函数，可以通过反编译字节码看看它到底是什么：

```
String[] strArray = (String[])list.stream().toArray((x$0) -> {
    return new String[x$0];
});
```

也就是相当于返回了一个指定长度的字符串数组。


### 05、chars 

返回组成字符串的单个字符的数组。

```java
String [] results = Strman.chars("沉默王二");
System.out.println(Arrays.toString(results));
```

结果如下所示：

```
[沉, 默, 王, 二]
```

来看一下源码：

```java
public static String[] chars(final String value) {
    return value.split("");
}
```

内部是通过 String 类的 `split()` 方法实现的。

### 06、charsCount

统计字符串中每个字符出现的次数。

```java
Map<Character, Long> map = Strman.charsCount("沉默王二的妹妹叫沉默王三");
System.out.println(map);
```

结果如下所示：

```
{的=1, 默=2, 三=1, 妹=2, 沉=2, 叫=1, 王=2, 二=1}
```

是不是瞬间觉得这个方法有意思多了，一步到位，统计出字符串中各个字符出现的次数，来看一下源码吧。

```java
public static Map<Character, Long> charsCount(String input) {
    return input.chars().mapToObj(c -> (char) c).collect(groupingBy(identity(), counting()));
}
```

String 类的 `chars()` 方法是 Java 9 新增的，它返回一个针对基本类型 int 的流：IntStream。

`mapToObj()` 方法主要是将 Stream 中的元素进行装箱操作， 转换成一个引用类型的值， 它接收一个 IntFunction 接口， 它是一个 `int -> R` 的函数接口。

`collect()` 方法可以把流转成集合 Map。

### 07、collapseWhitespace

用单个空格替换掉多个连续的空格。

```java
Strman.collapseWhitespace("沉默王二       一枚有趣的程序员");
```

结果如下所示：

```
Strman.collapseWhitespace("沉默王二       一枚有趣的程序员")
```

来看一下源码：

```java
public static String collapseWhitespace(final String value) {
    return value.trim().replaceAll("\\s\\s+", " ");
}
```

内部先用 `trim()` 方法去掉两侧的空格，然后再用正则表达式将多个连续的空格替换成单个空格。

### 08、contains

验证指定的字符串是否包含某个字符串。

```java
System.out.println(Strman.contains("沉默王二", "沉"));
System.out.println(Strman.contains("Abbc", "a", false));
```

结果如下所示：

```
true
true
```

第三个参数 caseSensitive 是可选项，如果为 false 则表明不区分大小写。

来看一下源码：

```java
public static boolean contains(final String value, final String needle, final boolean caseSensitive) {
    if (caseSensitive) {
        return value.contains(needle);
    }
    return value.toLowerCase().contains(needle.toLowerCase());
}
```

内部通过 String 类的 `contains()` 方法实现，如果不区分大小写，则先调用 `toLowerCase()` 方法转成小写。

### 09、containsAny

验证指定的字符串是否包含字符串数组中任意一个字符串，或更多。

```java
System.out.println(Strman.containsAny("沉默王二", new String [] {"沉","三"}));
System.out.println(Strman.containsAny("沉默王二", new String [] {"沉默","三"}));
System.out.println(Strman.containsAny("沉默王二", new String [] {"不","三"}));
```

结果如下所示：

```
true
true
false
```

来看一下源码：

```java
public static boolean containsAny(final String value, final String[] needles, final boolean caseSensitive) {
    return Arrays.stream(needles).anyMatch(needle -> contains(value, needle, caseSensitive));
}
```

Stream 类提供了三个方法可供进行元素匹配，它们分别是：

- `anyMatch()`，只要有一个元素匹配传入的条件，就返回 true。

- `allMatch()`，只有有一个元素不匹配传入的条件，就返回 false；如果全部匹配，则返回 true。

- `noneMatch()`，只要有一个元素匹配传入的条件，就返回 false；如果全部匹配，则返回 true。

### 10、endsWith

验证字符串是否以某个字符串结尾。

```java
System.out.println(Strman.endsWith("沉默王二","二"));
System.out.println(Strman.endsWith("Abbc", "A", false));
```

结果如下所示：

```
true
false
```

来看一下源码：

```java
public static boolean endsWith(final String value, final String search, final int position,
                               final boolean caseSensitive) {
    int remainingLength = position - search.length();
    if (caseSensitive) {
        return value.indexOf(search, remainingLength) > -1;
    }
    return value.toLowerCase().indexOf(search.toLowerCase(), remainingLength) > -1;
}
```

内部通过 String 类的 `indexOf()` 方法实现。

### 11、ensureLeft

确保字符串以某个字符串开头，如果该字符串没有以指定的字符串开头，则追加上去。

```java
System.out.println(Strman.ensureLeft("沉默王二", "沉"));
System.out.println(Strman.ensureLeft("默王二", "沉"));
```

结果如下所示：

```
沉默王二
沉默王二
```

来看一下源码：

```java
public static String ensureLeft(final String value, final String prefix, final boolean caseSensitive) {
    if (caseSensitive) {
        return value.startsWith(prefix) ? value : prefix + value;
    }
    String _value = value.toLowerCase();
    String _prefix = prefix.toLowerCase();
    return _value.startsWith(_prefix) ? value : prefix + value;
}
```

内部通过 String 类的 `startsWith()` 方法先进行判断，如果结果为 false，则通过“+”操作符进行连接。

ensureLeft 对应的还有 ensureRight，同理，这里不再赘述。

### 12、base64Encode

把字符串进行 base64 编码。

```java
Strman.base64Encode("沉默王二");
```

结果如下所示：

```
5rKJ6buY546L5LqM
```

Base64 是一种基于 64 个可打印字符来表示二进制数据的表示方法。来看一下源码：

```java
public static String base64Encode(final String value) {
    return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
}
```

内部是通过 Base64 类实现的，Java 8 新增的一个类。

base64Encode 对应的解码方法是 base64Decode，使用方法如下所示：

```java
Strman.base64Decode("5rKJ6buY546L5LqM")
```

如果不可解码的会，会抛出 IllegalArgumentException 异常。

```
Exception in thread "main" java.lang.IllegalArgumentException: Last unit does not have enough valid bits
  at java.base/java.util.Base64$Decoder.decode0(Base64.java:763)
  at java.base/java.util.Base64$Decoder.decode(Base64.java:535)
  at java.base/java.util.Base64$Decoder.decode(Base64.java:558)
  at strman.Strman.base64Decode(Strman.java:328)
  at com.itwanger.strman.Demo.main(Demo.java:58)
```

### 13、binEncode

把字符串转成二进制的 [Unicode](https://mp.weixin.qq.com/s/pNQjlXOivIgO3pbYc0GnpA)（16 位）。

```java
Strman.binEncode("沉默王二");
```

结果如下所示：

```
0110110010001001100111101101100001110011100010110100111010001100
```

binEncode 对应的方法是 binDecode，把二进制的 Unicode 转成字符串，使用方法如下所示：

```java
Strman.binDecode("0110110010001001100111101101100001110011100010110100111010001100");
```

### 14、first

返回字符串的前 N 个字符。

```java
System.out.println(Strman.first("沉默王二", 0));
System.out.println(Strman.first("沉默王二", 1));
System.out.println(Strman.first("沉默王二", 2));
```

结果如下所示：

```
Optional[]
Optional[沉]
Optional[沉默]
```

如果 N 为负数的话，将会抛出 StringIndexOutOfBoundsException 异常：

```
Exception in thread "main" java.lang.StringIndexOutOfBoundsException: begin 0, end -1, length 4
  at java.base/java.lang.String.checkBoundsBeginEnd(String.java:3319)
  at java.base/java.lang.String.substring(String.java:1874)
  at strman.Strman.lambda$first$9(Strman.java:414)
  at java.base/java.util.Optional.map(Optional.java:265)
  at strman.Strman.first(Strman.java:414)
  at com.itwanger.strman.Demo.main(Demo.java:68)
```

针对 N 为负数的情况，我觉得没有之前的 at 方法处理的巧妙。

来看一下源码：

```java
public static Optional<String> first(final String value, final int n) {
    return Optional.ofNullable(value).filter(v -> !v.isEmpty()).map(v -> v.substring(0, n));
}
```

内部是通过 String 类的 `substring()` 方法实现的，不过没有针对 n 小于 0 的情况做处理。

`ofNullable()` 方法可以创建一个即可空又可非空的 Optional 对象。

`filter()` 方法的参数类型为 Predicate（Java 8 新增的一个函数式接口），也就是说可以将一个 Lambda 表达式传递给该方法作为条件，如果表达式的结果为 false，则返回一个 EMPTY 的 Optional 对象，否则返回过滤后的 Optional 对象。

`map()` 方法可以按照一定的规则将原有 Optional 对象转换为一个新的 Optional 对象，原有的 Optional 对象不会更改。

first 对应的的是 last 方法，返回字符串的后 N 个字符。

### 15、head

返回字符串的第一个字符。

```java
Strman.head("沉默王二");
```

结果如下所示：

```
Optional[沉]
```

来看一下源码：

```java
public static Optional<String> head(final String value) {
    return first(value, 1);
}
```

内部是通过调用 `first()` 方法实现的，只不过 N 为 1。

### 16、unequal

检查两个字符串是否不等。

```java
Strman.unequal("沉默王二","沉默王三");
```

结果如下所示：

```
true
```

来看一下源码：

```java
public static boolean unequal(final String first, final String second) {
    return !Objects.equals(first, second);
}
```

内部是通过 `Objects.equals()` 方法进行判断的，由于 String 类重写了 `equals()` 方法，也就是说，实际上还是通过 String 类的 `equals()` 方法进行判断的。

### 17、insert

把字符串插入到指定索引处。

```java
Strman.insert("沉默二","王",2);
```

结果如下所示：

```
沉默王二
```

来看一下源码：

```java
public static String insert(final String value, final String substr, final int index) {
    if (index > value.length()) {
        return value;
    }
    return append(value.substring(0, index), substr, value.substring(index));
}
```

如果索引超出字符串长度，直接返回原字符串；否则调用 `append()` 方法将指定字符串插入到对应索引处。

### 18、repeat

对字符串重复指定次数。

```java
Strman.repeat("沉默王二", 3);
```

结果如下所示：

```
沉默王二沉默王二沉默王二
```

来看一下源码：

```java
public static String repeat(final String value, final int multiplier) {
    return Stream.generate(() -> value).limit(multiplier).collect(joining());
}
```

`Stream.generate()` 生成的 Stream，默认是串行（相对 parallel 而言）但无序的（相对 ordered 而言）。由于它是无限的，在管道中，必须利用 limit 之类的操作限制 Stream 大小。

`collect(joining())` 可以将流转成字符串。


### 19、leftPad

返回给定长度的新字符串，以便填充字符串的开头。

```java
Strman.leftPad("王二","沉默",6);
```

结果如下所示：

```
沉默沉默沉默沉默王二
```

来看一下源码：

```java
public static String leftPad(final String value, final String pad, final int length) {
    if (value.length() > length) {
        return value;
    }
    return append(repeat(pad, length - value.length()), value);
}
```

内部会先调用 `repeat()` 方法进行补位，然后再调用 `append()` 方法拼接。

leftPad 方法对应的是 rightPad，填充字符串的末尾。

**19）removeEmptyStrings**，从字符串数组中移除空字符串。

```java
String [] results = Strman.removeEmptyStrings(new String[]{"沉", " ", "   ", "默王二"});
System.out.println(Arrays.toString(results));
```

结果如下所示：

```
[沉, 默王二]
```

来看一下源码：

```java
public static String[] removeEmptyStrings(String[] strings) {
    if (Objects.isNull(strings)) {
        throw new IllegalArgumentException("Input array should not be null");
    }
    return Arrays.stream(strings).filter(str -> str != null && !str.trim().isEmpty()).toArray(String[]::new);
}
```

通过 Stream 的 `filter()` 方法过滤掉了空格。

### 20、reverse

反转字符串。

```java
Strman.reverse("沉默王二");
```

结果如下所示：

```
二王默沉
```

来看一下源码：

```java
public static String reverse(final String value) {
    return new StringBuilder(value).reverse().toString();
}
```

内部是通过 `StringBuilder` 类的 `reverse()` 方法进行反转的。

### 21、safeTruncate

对字符串进行截断，但不会破坏单词的完整性。

```java
Strman.safeTruncate("Java is the best",13,"...");
```

结果如下所示：

```
Java is...
```

来看一下源码：

```java
public static String safeTruncate(final String value, final int length, final String filler) {
    if (length == 0) {
        return "";
    }
    if (length >= value.length()) {
        return value;
    }

    String[] words = words(value);
    StringJoiner result = new StringJoiner(" ");
    int spaceCount = 0;
    for (String word : words) {
        if (result.length() + word.length() + filler.length() + spaceCount > length) {
            break;
        } else {
            result.add(word);
            spaceCount++;
        }
    }
    return append(result.toString(), filler);
}
```

先调用 `words()` 方法对字符串进行单词分割，然后按照长度进行截断，最后调用 `append()` 方法填充上补位符。

safeTruncate 对应的是 truncate，可能会破坏单词的完整性，使用方法如下所示：

```java
Strman.truncate("Java is the best",13,"...")
```

结果如下所示：

```
Java is th...
```

来看一下源码：

```java
public static String truncate(final String value, final int length, final String filler) {
    if (length == 0) {
        return "";
    }
    if (length >= value.length()) {
        return value;
    }
    return append(value.substring(0, length - filler.length()), filler);
}
```

就是单纯的切割和补位，没有对单词进行保护。

### 22、shuffle

对字符串重新洗牌。

```java
Strman.shuffle("沉默王二");
```

结果如下所示：

```
王默二沉
```

来看一下源码：

```java
public static String shuffle(final String value) {
    String[] chars = chars(value);
    Random random = new Random();
    for (int i = 0; i < chars.length; i++) {
        int r = random.nextInt(chars.length);
        String tmp = chars[i];
        chars[i] = chars[r];
        chars[r] = tmp;
    }
    return Arrays.stream(chars).collect(joining());
}
```

调用 `chars()` 方法把字符串拆分为字符串数组，然后遍历对其重排，最后通过 Stream 转成新的字符串。


### 23、其他方法

Strman 中还有很多其他巧妙的字符串处理方法，比如说把字符串按照指定的前后缀进行包裹 surround 等等，同学们可以参考 Strman 的官方文档进行学习：

>https://github.com/shekhargulati/strman-java/wiki


PS：最近有小伙伴私信我要一份优质的 Java 教程，我在 GitHub 花了很长时间才找到了一份，115k star，真的非常不错，来看一下目录：

![](http://www.itwanger.com/assets/images/2020/11/java-strman-03.png)


![](http://www.itwanger.com/assets/images/2020/11/java-strman-04.png)


![](http://www.itwanger.com/assets/images/2020/11/java-strman-05.png)

花了三个半小时把这份教程整理成 PDF 后，我发给了小伙伴，他“啪”的一下就发过来了私信，很快啊，“二哥，你也太用心了，这份教程的质量真的高，不服不行！”

如果你也对这份 PDF 感兴趣的话，可以通过下面的方式获取。

>链接:[https://pan.baidu.com/s/1rT0l5ynzAQLF--efyRHzQw](https://pan.baidu.com/s/1rT0l5ynzAQLF--efyRHzQw)  密码:dz95


多说一句，遇到好的资源，在让它吃灰的同时，能学一点就赚一点，对吧？知识是无穷无尽的，但只要我们比其他人多学到了那么一点点，那是不是就超越了呢？

**点个赞吧**，希望更多的人看得到！