---
layout: post
category: java
title: 面试官刁难：Java字符串可以引用传递吗？
tagline: by 沉默王二
tag: java
---

老读者都知道了，六年前，我从苏州回到洛阳，抱着一幅“海归”的心态，投了不少简历，也“约谈”了不少面试官，但仅有两三个令我感到满意。其中有一位叫老马，至今还活在我的手机通讯录里。他当时扔了一个面试题把我砸懵了：“王二，Java 字符串可以引用传递吗？”

<!--more-->




[我](https://mp.weixin.qq.com/s/feoOINGSyivBO8Z1gaQVOA)当时二十三岁，正值青春年华，从事 Java 编程已有 N 年经验（N < 4），自认为所有的面试题都能对答如流，结果没想到啊，被“刁难”了——原来洛阳这块互联网的荒漠也有技术专家啊。现在回想起来，脸上不自觉地泛起了羞愧的红晕：主要是自己当时太菜了。不管怎么说，是时候写篇文章剖析一下字符串是否可以引用传递了。

对于绝大多数的初级程序员或者说不重视“内功”的老鸟来说，往往停留在“知其然不知其所以然”的层面上——会用，略知一二，但要求他把问题说清楚的时候，就只能挠挠头双手一摊一张问号脸了。

![](http://www.itwanger.com/assets/images/2020/01/java-string-reference-pass-01.png)

好了，让我们来步入正题。先来看一段有趣但令人困惑的代码片段吧。

```java
public static void main(String[] args) {
    String x = new String("沉默王二");
    change(x);
    System.out.println(x);
}

public static void change(String x) {
    x = "沉默王三";
}
```

从代码的字面逻辑来看，程序应该输出“沉默王三”，但事与愿违，程序输出的结果却是“沉默王二”。`change()` 方法做的是无用功，因为 String 是值传递而不是引用传递。引用传递可以在被调用的方法中对实参进行修改，但值传递却不可以。为什么呢？

x 存储的是一个引用，该引用指向内存中的“沉默王二”字符串对象。当我们把 x 作为参数传递给 `change()` 方法时，x 仍然指向的是内存中“沉默王二”字符串，就像下面这幅图表达的意思一样。

![](http://www.itwanger.com/assets/images/2020/01/java-string-reference-pass-02.png)

那么问题来了。正因为 Java 是值传递，x 的值是“沉默王二”的引用。那么当 `change()` 方法被调用的时候，x 不是刚好指向了内存中新创建的字符串对象“沉默王三”了吗？就像下面这幅图表达的意思那样。

![](http://www.itwanger.com/assets/images/2020/01/java-string-reference-pass-03.png)

哦，看起来是一个很完美的解释，对吧？但这样的解释存在一些问题。

当字符串“沉默王二”被创建的时候，Java 会在内存中申请一小段空间，用来存储这个字符串对象。然后呢，把对象的引用指向了变量 x，也就是说，变量 x 实际上存储的是对象的引用（对象在内存中存储的地址）。

我相信大家对上面这一点（对象和对象引用）已经完全理解了。

关键的点来了。当变量 x 作为参数（实参）传递给 `change()` 方法时，实际上传递的是 x 的一个拷贝（形参）。在 `change()` 方法中，形参 x 起先引用的也是“沉默王二”这个对象，当执行 `x = "沉默王三"` 的时候，会在内存中创建新的字符串“沉默王三”，然后形参 x 不再引用“沉默王二”这个对象了，改为引用“沉默王三”这个对象了。但实参 x 呢？并没有发生任何的改变！就像下面这幅图一样。

![](http://www.itwanger.com/assets/images/2020/01/java-string-reference-pass-04.png)

假如我们真的需要改变字符串呢？那就不能使用 String 类了，最好使用 StringBuilder，来撸一串代码吧。

```java
public static void main(String[] args) {
    StringBuilder x = new StringBuilder("沉默王二");
    change(x);
    System.out.println(x);
}

public static void change(StringBuilder x) {
    x.delete(3,4).append("三");
}
```

上述代码会输出“沉默王三”，但假如我们使用 new 关键字重新对形参 x 进行赋值，就无济于事。

```java
public static void main(String[] args) {
    StringBuilder x = new StringBuilder("沉默王二");
    change(x);
    System.out.println(x);
}

public static void change(StringBuilder x) {
    x = new StringBuilder("沉默王三");
}
```

程序输出的结果仍然是“沉默王二”，原因其实和 String 一样，`change()` 方法在内存中创建了新的字符串“沉默王三”，然后形参 x 不再引用“沉默王二”这个对象，改为引用“沉默王三”这个对象了。但实参 x 并没有任何改变。


看到这，有些读者可能更疑惑了。`x = new StringBuilder("沉默王三")` 不可以改变实参，而 `x.delete(3,4).append("三")` 却可以，为什么？为什么？为什么？为什么呢？

不要着急，我们来分析一下 `delete()` 方法的源码。

```java
public AbstractStringBuilder delete(int start, int end) {
    int len = end - start;
    if (len > 0) {
        System.arraycopy(value, start+len, value, start, count-end);
        count -= len;
    }
    return this;
}
```

其中 value 是一个字符数组，用来存储字符序列；count 用来表示字符序列中实际有效的字符数量。

当 ` count -= len` 执行之前，value 的字符内容为“沉默王二”，count 为 4。我是怎么知道的呢？通过 [IDEA](http://www.itwanger.com/java/2019/11/25/java-idea-community.html) 的 debug 视图，截图为证。

![](http://www.itwanger.com/assets/images/2020/01/java-string-reference-pass-05.png)

当 ` count -= len` 执行之后，value 的字符内容仍然为“沉默王二”，但 count 变成了 3。

![](http://www.itwanger.com/assets/images/2020/01/java-string-reference-pass-06.png)

当鼠标停留在 this 上时，此时的字符内容为“沉默王”，也就意味着 x 当前的字符内容为“沉默王”。同样的，当我们在 `append()` 方法上进行 debug 的时候，也可以观察到字符串发生变化的细节。

![](http://www.itwanger.com/assets/images/2020/01/java-string-reference-pass-07.png)

当 `append()` 方法执行结束后，此时形参 x 的字符内容为“沉默王三”。

![](http://www.itwanger.com/assets/images/2020/01/java-string-reference-pass-08.png)

当 `change()` 方法执行完后，此时实参 x 的字符内容为“沉默王三”。


![](http://www.itwanger.com/assets/images/2020/01/java-string-reference-pass-09.png)

通过上面的源码分析，大家应该会发现另外一个事实：x 对象始终是“StringBuilder@512”，这意味着什么呢？一图胜千言，画个图大家一看就明白了。

![](http://www.itwanger.com/assets/images/2020/01/java-string-reference-pass-10.png)

由于形参 x 和实参 x 引用的都是同一个对象，那么 `change()` 方法执行结束后，实参 x 的字符内容自然也就发生了变化。

综上所述：Java 字符串不是引用传递而是值传递；更进一步的说，Java 只有值传递，没有引用传递。


![](http://www.itwanger.com/assets/images/2020/01/java-string-reference-pass-11.png)

>遥想公瑾当年，小乔初嫁了，雄姿英发。<br>
羽扇纶巾，谈笑间，樯橹灰飞烟灭。 <br>
故国神游，多情应笑我，早生华发。

哎，后悔啊，早年我要是能把这道面试题吃透的话，也不用被老马刁难了。另外，我想要告诉大家的是，作为程序员，我们千万不要轻视这些基础的知识点。因为基础的知识点是各种上层技术共同的基础，只有彻底地掌握了这些基础知识点，才能更好地理解程序的运行原理，做出更优化的产品。

------

好了，各位读者朋友们，以上就是本文的全部内容了。**能看到这里的都是最优秀的程序员，升职加薪就是你了**👍。如果觉得不过瘾，还想看到更多，可以 star 二哥的 GitHub【[itwanger.github.io](https://github.com/qinggee/itwanger.github.io)】，本文已收录。

原创不易，如果觉得有点用的话，请不要吝啬你手中**点赞**的权力；如果想要第一时间看到二哥更新的文章，请扫描下方的二维码，关注沉默王二公众号。我们下篇文章见！


