---
layout: post
category: java
title: 怒肝一夜，关于Java字符串的全部，都在这份手册里了
tagline: by 沉默王二
tags: 
  - java
---

String 可以说是 Java 中最常见的数据类型，用来表示一串文本，它的使用频率非常高，为了小伙伴们着想，我怒肝了一周，把字符串能写的全都写了出来。

<!--more-->

来看一下脑图吧，感受一下这份手册涉及到的知识点，不是我吹，有了这份手册，字符串的相关知识可以说全部掌握了。

![](http://www.itwanger.com/assets/images/2020/06/java-string-01.png)

## 一、多行字符串

每个操作系统对换行符的定义都不尽相同，所以在拼接多行字符串之前，需要先获取到操作系统的换行符，Java 可以通过下面的方式获取：

```java
String newLine = System.getProperty("line.separator");
```

通过 System 类的 `getProperty()` 方法，带上“line.separator”关键字就可以获取到了。

有了换行符，就可以使用 String 类的 `concat()` 方法或者直接使用“+”号操作符拼接多行字符串了。

```java
String mutiLine = "亲爱的"
        .concat(newLine)
        .concat("我想你了")
        .concat(newLine)
        .concat("你呢？")
        .concat(newLine)
        .concat("有没有在想我呢？");
```

```java
String mutiLine1 = "亲爱的"
        + newLine
        + "你好幼稚啊"
        + newLine
        + "技术文章里"
        + newLine
        + "你写这些合适吗";
```

Java 8 的 String 类加入了一个新的方法 `join()`，可以将换行符与字符串拼接起来，非常方便：

```java
String mutiLine2 = String.join(newLine, "亲爱的", "合适啊", "这叫趣味", "哈哈");
```

StringBuilder 当然也是合适的：

```java
String mutiLine3 = new StringBuilder()
        .append("亲爱的")
        .append(newLine)
        .append("看不下去了")
        .append(newLine)
        .append("肉麻")
        .toString();
```

StringBuffer 类似，就不再举例了。

另外，Java 还可以通过 `Files.readAllBytes()` 方法从源文件中直接读取多行文本，格式和源文件保持一致：

```java
String mutiLine4 = new String(Files.readAllBytes(Paths.get("src/main/resource/cmower.txt")));
```

## 二、检查字符串是否为空

说到“空”这个概念，它在编程中有两种定义，英文单词分别是 empty 和 blank，来做一下区分。如果字符串为 null，或者长度为 0，则为 empty；如果字符串仅包含空格，则为 blank。

### 01、empty

Java 1.6 之后，String 类新添加了一个 `empty()` 方法，用于判断字符串是否为 empty。

```java
boolean isEmpty(String str) {
    return str.isEmpty();
}
```

为了确保不抛出 NPE，最好在判断之前先判空，因为 `empty()` 方法只判断了字符串的长度是否为 0：

![](http://www.itwanger.com/assets/images/2020/06/java-string-02.png)

所以我们来优化一下 `isEmpty()` 方法：

```java
boolean isEmpty(String str) {
    return str != null || str.isEmpty();
}
```

### 02、blank

如果想检查字符串是否为 blank，有一种变通的做法，就是先通过 String 类的 `trim()` 方法去掉字符串两侧的空白字符，然后再判断是否为 empty：

```java
boolean isBlank(String str) {
    return str != null || str.trim().isEmpty();
}
```

### 03、第三方类库

在实际的项目开发当中，检查字符串是否为空最常用的还是 Apache 的 commons-lang3 包，有各式各样判空的方法。

![](http://www.itwanger.com/assets/images/2020/06/java-string-03.png)

更重要的是，可以省却判 null 的操作，因为 StringUtils 的所有方法都是 null 安全的。

## 三、生成随机字符串

有时候，我们需要生成一些随机的字符串，比如说密码。

```java
int leftLimit = 97; // 'a'
int rightLimit = 122; // 'z'
int targetStringLength = 6;
Random random = new Random();
StringBuilder buffer = new StringBuilder(targetStringLength);
for (int i = 0; i < targetStringLength; i++) {
    int randomLimitedInt = leftLimit + (int)
            (random.nextFloat() * (rightLimit - leftLimit + 1));
    buffer.append((char) randomLimitedInt);
}
String generatedString = buffer.toString();

System.out.println(generatedString);
```

这段代码就会生成一串 6 位的随机字符串，范围是小写字母 a - z 之间。

除了使用 JDK 原生的类库之外，还可以使用 Apache 的 Commons Lang 包，`RandomStringUtils.random()` 方法刚好满足需求：

```java
int length = 6;
boolean useLetters = true;
// 不使用数字
boolean useNumbers = false;
String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

System.out.println(generatedString);
```

## 四、删除字符串最后一个字符

删除字符串最后一个字符，最简单的方法就是使用 `substring()` 方法进行截取，0 作为起始下标，`length() - 1` 作为结束下标。

不管怎么样，`substring()` 方法不是 null 安全的，需要先判空：

```java
    public static String removeLastChar(String s) {
        return (s == null || s.length() == 0)
                ? null
                : (s.substring(0, s.length() - 1));
    }
```

如果不想在操作之前判空，那么就直接上 Apache 的 Commons Lang 包：

```java
String s = "沉默王二";
StringUtils.substring(s, 0, s.length() - 1);
```

当然了，如果目的非常明确——就是只删除字符串的最后一个字符，还可以使用 StringUtils 类的 `chop()` 方法：

```java
StringUtils.chop(s);
```

如果你看过源码的话，你就会发现，它内部其实也是调用了 `substring()` 方法。

```java
public static String chop(final String str) {
    if (str == null) {
        return null;
    }
    final int strLen = str.length();
    if (strLen < 2) {
        return EMPTY;
    }
    final int lastIdx = strLen - 1;
    final String ret = str.substring(0, lastIdx);
    final char last = str.charAt(lastIdx);
    if (last == CharUtils.LF && ret.charAt(lastIdx - 1) == CharUtils.CR) {
        return ret.substring(0, lastIdx - 1);
    }
    return ret;
}
```

如果你对正则表达式了解的话，也可以使用 `replaceAll()` 方法进行替换，把最后一个字符 `.$` 替换成空字符串就可以了。

```java
s.replaceAll(".$", "")
```

当然了，`replaceAll()` 方法也不是 null 安全的，所以要提前判空：

```java
String result= (s == null) ? null : s.replaceAll(".$", "");
```

如果对 Java 8 的 Lambda 表达式和 Optional 比较熟的话，还可以这样写：

```java
String result1 = Optional.ofNullable(s)
       .map(str -> str.replaceAll(".$", ""))
       .orElse(s);
```

看起来就显得高大上多了，一看就是有经验的 Java 程序员。

## 五、统计字符在字符串中出现的次数

要统计字符在字符串中出现的次数，有很多方法，直接使用 JDK 的 API 就是最直接的一种：

```java
String someString = "chenmowanger";
char someChar = 'e';
int count = 0;

for (int i = 0; i < someString.length(); i++) {
    if (someString.charAt(i) == someChar) {
        count++;
    }
}
System.out.println(count);
```

这种方式很直白，但有没有更优雅的呢？有，Java 8 就优雅多了：

```java
long count = someString.chars().filter(ch -> ch == 'e').count();
```

如果想使用第三方类库的话，可以继续选择 Apache 的 Commons Lang 包：

```java
int count2 = StringUtils.countMatches("chenmowanger", "e");
```

也非常优雅，很容易看得懂。

## 六、拆分字符串

大多数情况下，String 类的 `split()` 方法就能够满足拆分字符串的需求：

```java
String[] splitted = "沉默王二，一枚有趣的程序员".split("，");
```

当然了，该方法也不是 null 安全的，那想要 null 安全，小伙伴们应该能想到谁了吧？

之前反复提到的 StringUtils 类，来自 Apache 的 Commons Lang 包：

```java
String[] splitted = StringUtils.split("沉默王二，一枚有趣的程序员", "，");
```

如果对拆分字符串还有更多兴趣的话，可以参考我之前写的另外一篇文章[咦，拆分个字符串都这么讲究](https://mp.weixin.qq.com/s/P0HOlgREXqUWIDKgKxr-MA)。

## 七、字符串比较

对于初学者来说，最容易犯的错误就是使用“==”操作符来判断两个字符串的值是否相等，这也是一道很常见的面试题。

```java
String string1 = "沉默王二";
String string2 = "沉默王二";
String string3 = new String("沉默王二");

System.out.println(string1 == string2);
System.out.println(string1 == string3);
```

这段程序的第一个结果是 true，第二个结果为 false，这是因为使用 new 关键字创建的对象和使用双引号声明的字符串不是同一个对象，而“==” 操作符是用来判断对象是否相等的。

如果单纯的比较两个字符串的值是否相等，应该使用 `equals()` 方法：

```java
String string1 = "沉默王二";
String string2 = "沉默王二";
String string3 = new String("沉默王二");

System.out.println(string1.equals(string2));
System.out.println(string1.equals(string3));
```

这段程序输出的结果就是两个 true，因为 `equals()` 方法就是用来单纯的判断字符串的值是否相等。

关于 Java 字符串的比较，可以参照我之前写的另外一篇文章[如何比较 Java 的字符串](https://mp.weixin.qq.com/s/WyrRCUlelzOxyfVBrxAGUg)。

## 八、字符串拼接

### 01、“+”号操作符

要说姿势，“+”号操作符必须是字符串拼接最常用的一种了，没有之一。

```java
String chenmo = "沉默";
String wanger = "王二";

System.out.println(chenmo + wanger);
```

我们把这段代码使用 JAD 反编译一下。

```java
String chenmo = "\u6C89\u9ED8"; // 沉默
String wanger = "\u738B\u4E8C"; // 王二
System.out.println((new StringBuilder(String.valueOf(chenmo))).append(wanger).toString());
```

我去，原来编译的时候把“+”号操作符替换成了 StringBuilder 的 append 方法。也就是说，“+”号操作符在拼接字符串的时候只是一种形式主义，让开发者使用起来比较简便，代码看起来比较简洁，读起来比较顺畅。算是 Java 的一种语法糖吧。

### 02、StringBuilder

除去“+”号操作符，StringBuilder 的 append 方法就是第二个常用的字符串拼接姿势了。

先来看一下 StringBuilder 类的 append 方法的源码：

```java
public StringBuilder append(String str) {
    super.append(str);
    return this;
}
```

这 3 行代码没啥可看的，可看的是父类 AbstractStringBuilder 的 append 方法：

```java
public AbstractStringBuilder append(String str) {
    if (str == null)
        return appendNull();
    int len = str.length();
    ensureCapacityInternal(count + len);
    str.getChars(0, len, value, count);
    count += len;
    return this;
}
```

1）判断拼接的字符串是不是 null，如果是，当做字符串“null”来处理。`appendNull` 方法的源码如下：

```java
private AbstractStringBuilder appendNull() {
    int c = count;
    ensureCapacityInternal(c + 4);
    final char[] value = this.value;
    value[c++] = 'n';
    value[c++] = 'u';
    value[c++] = 'l';
    value[c++] = 'l';
    count = c;
    return this;
}
```

2）拼接后的字符数组长度是否超过当前值，如果超过，进行扩容并复制。`ensureCapacityInternal` 方法的源码如下：

```java
private void ensureCapacityInternal(int minimumCapacity) {
    // overflow-conscious code
    if (minimumCapacity - value.length > 0) {
        value = Arrays.copyOf(value,
                newCapacity(minimumCapacity));
    }
}
```

3）将拼接的字符串 str 复制到目标数组 value 中。

```java
str.getChars(0, len, value, count)
```

### 03、StringBuffer

先有 StringBuffer 后有 StringBuilder，两者就像是孪生双胞胎，该有的都有，只不过大哥 StringBuffer 因为多呼吸两口新鲜空气，所以是线程安全的。

```java
public synchronized StringBuffer append(String str) {
    toStringCache = null;
    super.append(str);
    return this;
}
```

StringBuffer 类的 append 方法比 StringBuilder 多了一个关键字 synchronized，可暂时忽略 `toStringCache = null`。

synchronized 是 Java 中的一个非常容易脸熟的关键字，是一种同步锁。它修饰的方法被称为同步方法，是线程安全的。

### 04、String 类的 concat 方法

单就姿势上来看，String 类的 concat 方法就好像 StringBuilder 类的 append。

```java
String chenmo = "沉默";
String wanger = "王二";

System.out.println(chenmo.concat(wanger));
```

文章写到这的时候，我突然产生了一个奇妙的想法。假如有这样两行代码：

```java
chenmo += wanger
chenmo = chenmo.concat(wanger)
```

它们之间究竟有多大的差别呢？

之前我们已经了解到，`chenmo += wanger` 实际上相当于 `(new StringBuilder(String.valueOf(chenmo))).append(wanger).toString()`。

要探究“+”号操作符和 `concat` 之间的差别，实际上要看 append 方法和 concat 方法之间的差别。

append 方法的源码之前分析过了。我们就来看一下 concat 方法的源码吧。

```java
public String concat(String str) {
    int otherLen = str.length();
    if (otherLen == 0) {
        return this;
    }
    int len = value.length;
    char buf[] = Arrays.copyOf(value, len + otherLen);
    str.getChars(buf, len);
    return new String(buf, true);
}
```

1）如果拼接的字符串的长度为 0，那么返回拼接前的字符串。

```java
if (otherLen == 0) {
    return this;
}
```

2）将原字符串的字符数组 value 复制到变量 buf 数组中。

```java
char buf[] = Arrays.copyOf(value, len + otherLen);
```

3）把拼接的字符串 str 复制到字符数组 buf 中，并返回新的字符串对象。

```java
str.getChars(buf, len);
return new String(buf, true);
```

通过源码分析我们大致可以得出以下结论：

1）如果拼接的字符串是 null，concat 时候就会抛出 NullPointerException，“+”号操作符会当做是“null”字符串来处理。

2）如果拼接的字符串是一个空字符串（""），那么 concat 的效率要更高一点。毕竟不需要 `new  StringBuilder` 对象。

3）如果拼接的字符串非常多，concat 的效率就会下降，因为创建的字符串对象越多，开销就越大。

**注意了！！！**

弱弱地问一下啊，还有在用 JSP 的同学吗？EL 表达式中是不允许使用“+”操作符来拼接字符串的，这时候就只能用 `concat` 了。

```jsp
${chenmo.concat('-').concat(wanger)}
```

### 05、String 类的 join 方法

JDK 1.8 提供了一种新的字符串拼接姿势：String 类增加了一个静态方法 join。

```java
String chenmo = "沉默";
String wanger = "王二";
String cmower = String.join("", chenmo, wanger);
System.out.println(cmower);
```

第一个参数为字符串连接符，比如说：

```java
String message = String.join("-", "王二", "太特么", "有趣了");
```

输出结果为：王二-太特么-有趣了

我们来看一下 join 方法的源码：

```java
public static String join(CharSequence delimiter, CharSequence... elements) {
    Objects.requireNonNull(delimiter);
    Objects.requireNonNull(elements);
    // Number of elements not likely worth Arrays.stream overhead.
    StringJoiner joiner = new StringJoiner(delimiter);
    for (CharSequence cs: elements) {
        joiner.add(cs);
    }
    return joiner.toString();
}
```

发现了一个新类 StringJoiner，类名看起来很 6，读起来也很顺口。StringJoiner 是 `java.util` 包中的一个类，用于构造一个由分隔符重新连接的字符序列。限于篇幅，本文就不再做过多介绍了，感兴趣的同学可以去了解一下。

### 06、StringUtils.join

实战项目当中，我们处理字符串的时候，经常会用到这个类——`org.apache.commons.lang3.StringUtils`，该类的 join 方法是字符串拼接的一种新姿势。

```java
String chenmo = "沉默";
String wanger = "王二";

StringUtils.join(chenmo, wanger);
```

该方法更善于拼接数组中的字符串，并且不用担心 NullPointerException。

```java
StringUtils.join(null)            = null
StringUtils.join([])              = ""
StringUtils.join([null])          = ""
StringUtils.join(["a", "b", "c"]) = "abc"
StringUtils.join([null, "", "a"]) = "a"
```

通过查看源码我们可以发现，其内部使用的仍然是 StringBuilder。

```java
public static String join(final Object[] array, String separator, final int startIndex, final int endIndex) {
    if (array == null) {
        return null;
    }
    if (separator == null) {
        separator = EMPTY;
    }

    final StringBuilder buf = new StringBuilder(noOfItems * 16);

    for (int i = startIndex; i < endIndex; i++) {
        if (i > startIndex) {
            buf.append(separator);
        }
        if (array[i] != null) {
            buf.append(array[i]);
        }
    }
    return buf.toString();
}
```

大家读到这，不约而同会有这样一种感觉：我靠（音要拖长），没想到啊没想到，字符串拼接足足有 6 种姿势啊，晚上回到家一定要一一尝试下。

### 07、为什么阿里开发手册不建议在 for 循环中使用”+”号操作符进行字符串拼接


来看两段代码。

第一段，for 循环中使用”+”号操作符。

```java
String result = "";
for (int i = 0; i < 100000; i++) {
    result += "六六六";
}
```

第二段，for 循环中使用 append。

```java
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 100000; i++) {
    sb.append("六六六");
}
```

这两段代码分别会耗时多长时间呢？在我的 iMac 上测试出的结果是：

1）第一段代码执行完的时间为 6212 毫秒

2）第二段代码执行完的时间为 1 毫秒

差距也太特么大了吧！为什么呢？

我相信有不少同学已经有了自己的答案：第一段的 for 循环中创建了大量的 StringBuilder 对象，而第二段代码至始至终只有一个  StringBuilder 对象。

PS：第一版暂时先更新这么多，后面遇到一些新的知识点的话，我再更新下一个版本。小伙伴们有建议的话，也可以提出来。

那可能有些小伙伴可能就忍不住了，这份字符串手册有没有 PDF 版可以白嫖啊，那必须得有啊，直接微信搜「**沉默王二**」回复「**字符串**」就可以了，不要手软，觉得不错的，请多多分享——赠人玫瑰，手有余香哦。

>本文已收录 GitHub，[**传送门~**](https://github.com/qinggee/itwanger.github.io) ，里面更有大厂面试完整考点，欢迎 Star。

我是沉默王二，一枚有颜值却靠才华苟且的程序员。**关注即可提升学习效率，别忘了三连啊，点赞、收藏、留言，我不挑，嘻嘻**。