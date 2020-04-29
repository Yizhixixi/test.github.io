---
layout: post
category: java
title: 咦，Java拆分个字符串都这么讲究
tagline: by 沉默王二
tags: 
  - java
---

提到 Java 拆分字符串，我猜你十有八九会撂下一句狠话，“这有什么难的，直接上 String 类的 `split()` 方法不就拉到了！”假如你真的这么觉得，那可要注意了，事情远没这么简单。

<!--more-->



![](http://www.itwanger.com/assets/images/2020/04/java-split-01.gif)

来来来，搬个小板凳坐下。

假如现在有这样一串字符“沉默王二，一枚有趣的程序员”，需要按照中文的逗号“，”进行拆分，这意味着第一串字符为逗号前面的“沉默王二”，第二串字符为逗号后面的“一枚有趣的程序员”（这不废话）。另外，在拆分之前，要先进行检查，判断一下这串字符是否包含逗号，否则应该抛出异常。

```java
public class Test {
    public static void main(String[] args) {
        String cmower = "沉默王二，一枚有趣的程序员";
        if (cmower.contains("，")) {
            String [] parts = cmower.split("，");
            System.out.println("第一部分：" + parts[0] +" 第二部分：" + parts[1]);
        } else {
            throw new IllegalArgumentException("当前字符串没有包含逗号");
        }
    }
}
```

这段代码看起来挺严谨的，对吧？程序输出的结果完全符合预期：

```
第一部分：沉默王二 第二部分：一枚有趣的程序员
```

这是建立在字符串是确定的情况下，最重要的是分隔符是确定的。否则，麻烦就来了。

大约有 12 种英文特殊符号，如果直接拿这些特殊符号替换上面代码中的分隔符（中文逗号），这段程序在运行的时候就会出现以下提到的错误。

- 反斜杠 `\`（ArrayIndexOutOfBoundsException）
- 插入符号 `^`（同上）
- 美元符号 `$`（同上）
- 逗点 `.`（同上）
- 竖线 `|`（正常，没有出错）
- 问号 `?`（PatternSyntaxException）
- 星号 `*`（同上）
- 加号 `+`（同上）
- 左小括号或者右小括号 `()`（同上）
- 左方括号或者右方括号 `[]`（同上）
- 左大括号或者右大括号 `{}`（同上）

看到这，可能有小伙伴会说，“这不是钻牛角尖嘛”，不不不，做技术就应该秉持严谨的态度，否则，老大会给你的绩效打低分的——奖金拿得少，可不是好滋味。

那遇到特殊符号该怎么办呢？上正则表达式呗。

>正则表达式是一组由字母和符号组成的特殊文本，它可以用来从文本中找出满足你想要的格式的句子。

那可能又有小伙伴说，“正则表达式那么多，我记不住啊！”别担心，我已经替你想好对策了。

下面这个链接是 GitHub 上学习正则表达式的一个在线文档，非常详细。遇到正则表达式的时候，掏出这份手册就完事了。记不住那么多正则表达式没关系啊，活学活用呗。

[https://github.com/cdoco/learn-regex-zh](https://github.com/cdoco/learn-regex-zh)

除了这份文档，还有一份：

[https://github.com/cdoco/common-regex](https://github.com/cdoco/common-regex)

作者收集了一些在平时项目开发中经常用到的正则表达式，可以直接拿来用，妙啊。

解决了心病之后，我们来用英文逗点“.”来替换一下分隔符：

```java
String cmower = "沉默王二.一枚有趣的程序员";
if (cmower.contains(".")) {
    String [] parts = cmower.split("\\.");
    System.out.println("第一部分：" + parts[0] +" 第二部分：" + parts[1]);
}
```

在使用 `split()` 方法的时候，就需要使用正则表达式 `\\.` 来替代特殊字符英文逗点“.”了。为什么用两个反斜杠呢？因为它本身就是一个特殊字符，需要先转义。

也可以使用字符类 `[]` 来包含英文逗点“.”，它也是一个正则表达式，用来匹配方括号中包含的任意字符。

```java
cmower.split("[.]");
```

除此之外， 还可以使用 Pattern 类的 `quote()` 方法来包裹英文逗点“.”，该方法会返回一个使用 `\Q\E` 包裹的字符串。


![](http://www.itwanger.com/assets/images/2020/04/java-split-02.png)

此时，`String.split()` 方法的使用示例如下所示：

```java
String [] parts = cmower.split(Pattern.quote("."));
```

当通过调试模式进入 `String.split()` 方法源码的话，会发现以下细节：

```java
return Pattern.compile(regex).split(this, limit);
```

String 类的 `split()` 方法调用了 Pattern 类的 `split()` 方法。也就意味着，我们拆分字符串有了新的选择，可以不使用 String 类的 `split()` 方法了。

```java
public class TestPatternSplit {
    /**
     * 使用预编译功能，提高效率
     */
    private static Pattern twopart = Pattern.compile("\\.");

    public static void main(String[] args) {
        String [] parts = twopart.split("沉默王二.一枚有趣的程序员");
        System.out.println("第一部分：" + parts[0] +" 第二部分：" + parts[1]);
    }
}
```

除此之外，还可以使用 Pattern 配合 Matcher 类进行字符串拆分，这样做的好处是可以对要拆分的字符串进行一些严格的限制，来看一段示例代码：

```java
public class TestPatternMatch {
    /**
     * 使用预编译功能，提高效率
     */
    private static Pattern twopart = Pattern.compile("(.+)\\.(.+)");

    public static void main(String[] args) {
        checkString("沉默王二.一枚有趣的程序员");
        checkString("沉默王二.");
        checkString(".一枚有趣的程序员");
    }

    private static void checkString(String str) {
        Matcher m = twopart.matcher(str);
        if (m.matches()) {
            System.out.println("第一部分：" + m.group(1) + " 第二部分：" + m.group(2));
        } else {
            System.out.println("不匹配");
        }
    }
}
```

这时候，正则表达式为 `(.+)\\.(.+)`，意味着可以把字符串按照英文逗点拆分成一个字符组，英文小括号 `()` 的作用就在于此（可以查看我之前提供的正则表达式手册）。

由于模式是确定的，所以可以把 Pattern 表达式放在 `main()` 方法外面，通过 static 的预编译功能提高程序的效率。


来看一下程序的输出结果：

```java
第一部分：沉默王二 第二部分：一枚有趣的程序员
不匹配
不匹配
```

不过，使用 Matcher 来匹配一些简单的字符串时相对比较沉重一些，使用 String 类的 `split()` 仍然是首选，因为该方法还有其他一些牛逼的功能。

比如说，你想把分隔符包裹在拆分后的字符串的第一部分，可以这样做：

```java
String cmower = "沉默王二，一枚有趣的程序员";
if (cmower.contains("，")) {
    String [] parts = cmower.split("(?<=，)");
    System.out.println("第一部分：" + parts[0] +" 第二部分：" + parts[1]);
}
```

程序输出的结果如下所示：

```
第一部分：沉默王二， 第二部分：一枚有趣的程序员
```

可以看到分隔符“，”包裹在了第一部分，如果希望包裹在第二部分，可以这样做：

```java
String [] parts = cmower.split("(?=，)");
```

可能有些小伙伴很好奇，`?<=` 和 `?=` 是什么东东啊？它其实是正则表达式中的断言模式。


![](http://www.itwanger.com/assets/images/2020/04/java-split-03.png)

温馨提醒：如果对断言模式比较生疏的话，可以查看我之前提供的正则表达式手册。

另外，假如说字符串中包含了多个分隔符，而我们只需要 2 个的话，还可以这样做：

```java
String cmower = "沉默王二，一枚有趣的程序员，宠爱他";
if (cmower.contains("，")) {
    String [] parts = cmower.split("，", 2);
    System.out.println("第一部分：" + parts[0] +" 第二部分：" + parts[1]);
}
```

`split()` 方法可以传递 2 个参数，第一个为分隔符，第二个为拆分的字符串个数。查看该方法源码的话，你就可以看到以下内容：


![](http://www.itwanger.com/assets/images/2020/04/java-split-04.png)

直接 `substring()` 到原字符串的末尾，也就是说，第二个分隔符处不再拆分。然后就 break 出循环了。来看一下程序输出的结果：

```
第一部分：沉默王二 第二部分：一枚有趣的程序员，宠爱他
```


![](http://www.itwanger.com/assets/images/2020/04/java-split-05.gif)


好了，我亲爱的读者朋友，以上就是本文的全部内容了。是不是突然感觉拆分个字符串真的挺讲究的？我是沉默王二，一枚有趣的程序员。**原创不易，莫要白票，请你为本文点赞个吧**，这将是我写作更多优质文章的最强动力。



如果觉得文章对你有点帮助，请微信搜索「 **沉默王二** 」第一时间阅读，回复【**666**】更有我为你精心准备的 500G 高清教学视频（已分门别类）。本文 [GitHub](https://github.com/qinggee/itwanger.github.io) 已经收录，有大厂面试完整考点，欢迎 Star。