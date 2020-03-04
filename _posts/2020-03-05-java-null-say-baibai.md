---
layout: post
category: life
title: 终于和 null say 拜拜了，我超开心
tagline: by 沉默王二
tags: 
  - java
---

你好呀，我是沉默王二，一个和黄家驹一样身高，和刘德华一样颜值的程序员。从 10 年前我开始写第一行 Java 代码至今，一直觉得 null 在 Java 中是一个最特殊的存在，它既是好朋友，可以把不需要的变量置为 null 从而释放内存，提高性能；它又是敌人，因为它和大名鼎鼎且烦不胜烦的 `NullPointerException`（NPE）如影随形，而 NPE 的发明人 Tony Hoare 曾在 2009 年承认：“Null References 是一个荒唐的设计，就好像我赌输掉了十亿美元”。

<!--more-->

![](http://www.itwanger.com/assets/images/2020/03/null-01.png)


你看，null 竟然是一个亦敌亦友的家伙。

通常，为了表示列表中的元素不存在，我们首先想到的就是返回 null，这种想法很合理，合理到无法反驳。我们来模拟一个实际的应用场景，假设小二现在要从数据库中获取一个姓名的列表，然后将姓名打印到控制台，对应的代码如下。

```java
public class NullDemo {
    public static void main(String[] args) {
        List<String> names = getNamesFromDB();
        if (names != null) {
            for (String name : names) {
                System.out.println(name);
            }
        }
    }

    public static List<String> getNamesFromDB() {
        // 模拟此时没有从数据库获取到对应的姓名。
        return null;
    }
}
```

由于 `getNamesFromDB()` 方法返回了 null 来作为没有姓名列表的标志，那就意味着在遍历列表的时候要先对列表判空，否则将会抛出 NPE 错误，不信你把 `if (names != null)` 去掉试试，立马给你颜色看。

```
Exception in thread "main" java.lang.NullPointerException
    at com.cmower.dzone.stopdoing3things.NullDemo.main(NullDemo.java:12)
```

那假如小二在遍历的时候不想判空又不想代码抛出 NPE 错误，他该怎么做呢？闭上你的大眼睛好好想一想。

![](http://www.itwanger.com/assets/images/2020/03/null-02.png)

嗯，报告，我想出来了，建议小二从数据库中获取姓名的时候返回长度为 0 的列表，来表示未找到数据的情况。代码示例如下所示：

```java
public class Null2Length0Demo {
    public static void main(String[] args) {
        List<String> names = getNamesFromDB();
        for (String name : names) {
            System.out.println(name);
        }
    }

    public static List<String> getNamesFromDB() {
        // 模拟此时没有从数据库获取到对应的姓名。
        return Collections.emptyList();
    }
}
```

注：`Collections.emptyList()` 用于返回一个不可变的空列表，能理解吧？假如不能理解的话，我再写一个返回可变的空列表的示例，你对比着感受一下就理解了。

```java
public class Null2Length0MutableDemo {
    public static void main(String[] args) {
        List<String> names = getNamesFromDB();
        for (String name : names) {
            System.out.println(name);
        }
    }

    public static List<String> getNamesFromDB() {
        // 模拟此时没有从数据库获取到对应的姓名。
        return new ArrayList<>();
    }
}
```

`new ArrayList<>()` 返回的就是可变的，意味着你还可以改变这个列表的元素，比如说增加，删除是不可能的了，因为本身就没有元素可删。


你看，`Collections.emptyList()` 和 `new ArrayList<>()` 都可以替代 null，来减少打印列表时不必要的判空以及那个讨厌的家伙——NPE。

除了我这个想法之外，你还能想到其他的解决方案吗？来，再次闭上你的大眼睛，替小二想一想，没准你还能想到一个—— Java 8 新增的 Optional 类，一个容器类，可以存放任意类型的元素，如果值存在则
 `isPresent()` 方法会返回 true；Optional 类提供了很多专业的方法而不用显式进行空值检查，从而巧妙地消除了 NPE。

来，先读示例为快！

```java
public class Null2OptionalDemo {
    public static void main(String[] args) {
        Optional<List<String>> list = getNamesFromDB();
        list.ifPresent(names -> {
            for (String name : names) {
                System.out.println(name);
            }
        });
    }

    public static Optional<List<String>> getNamesFromDB() {
        boolean hasName = true;
        if (hasName) {
            String [] names = {"沉默王二", "一枚有趣的程序员", "微信搜索关注我"};
            return Optional.of(Arrays.asList(names));
        }
        return Optional.empty();
    }
}
```

看得不太懂？我来负责任地介绍一下，你们握个手。

假如数据库中存在姓名，则使用 `Optional.of()` 对返回值进行包装，从而返回一个 Optional 类型的对象。为什么不用构造方法呢，因为构造方法是 private 的（源码如下所示）。

```java
private Optional(T value) {
    this.value = value;
}
```

那为什么要用 `Optional.of()` 呢？嗯，good question。继续上源码。

```java
public static <T> Optional<T> of(T value) {
    return new Optional<>(Objects.requireNonNull(value));
}
```

1）如果 value 为 null，那么 `Objects.requireNonNull(value)` 就会抛出 NPE（嗯哼，总归是要碰面的，但好歹不用我们程序员主动 check 了）。

2）如果 value 不为 null，则通过 new 关键字创建正常的 Optional 对象。


假如数据库中不存在姓名呢？使用 `Optional.empty()` 作为返回值。来，继续上源码。

```java
public static<T> Optional<T> empty() {
    @SuppressWarnings("unchecked")
    Optional<T> t = (Optional<T>) EMPTY;
    return t;
}
```

嗯哼，EMPTY 是什么玩意？

```java
private static final Optional<?> EMPTY = new Optional<>(null);
```

竟然是 Optional 类的一个私有常量（static + final）。怎么此刻我的脑子里想起了安徒生先生的寓言故事——皇帝的新衣，嗯，甭管了，反正“底层终究是丑陋的”。


这样的话，就可以使用 Optional 对象的 `ifPresent()` 方法来判断值是否存在，如果只需要处理值存在的情况，就可以使用 Lambda 表达式的方式直接打印姓名。

```java
list.ifPresent(names -> {
    for (String name : names) {
        System.out.println(name);
    }
});
```

有点简单粗暴，对不对？但不管怎么说，终于可以在表象上和 null，NPE 说拜拜了，做人嘛，开心点。

![](http://www.itwanger.com/assets/images/2020/03/null-03.gif)

好了，我亲爱的读者朋友，以上就是本文的全部内容了，能看到这里的就是最优秀的程序员。**原创不易，莫要白票**，请你为本文点赞个吧，这将是我写作更多优质文章的最强动力。

>如果你觉得文章对你有点帮助，请微信搜索「 **沉默王二** 」第一时间阅读，回复【**666**】【**1024**】更有我为你精心准备的 500G 高清教学视频（已分门别类），以及大厂技术牛人整理的面经一份，本文源码已收录在**码云**，[传送门](https://gitee.com/qing_gee/JavaPoint/tree/master)~ 

![](http://www.itwanger.com/assets/images/cmower-10.png)













