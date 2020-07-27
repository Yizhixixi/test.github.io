---
layout: post
category: java
title: 给我半首歌的时间，给你说明白Immutable List
tagline: by 沉默王二
tags: 
  - java
---

>先看再点赞，给自己一点思考的时间，微信搜索【**沉默王二**】关注这个靠才华苟且的程序员。
>本文 **GitHub** [github.com/itwanger](https://github.com/itwanger/itwanger.github.io) 已收录，里面还有一线大厂整理的面试题，以及我的系列文章。

<!--more-->



Immutable List，顾名思义，就是，啥，不明白 Immutable 是什么意思？一成不变的意思，所以 Immutable List 就是一个不可变的 List 类，这意味着该 List 声明后，它的内容就是固定的，不可增删改的。

如果对不可变类比较陌生的话，可以先点击下面的链接查看我之前写的另外一篇文章。

[这次要说不明白immutable类，我就怎么地](https://mp.weixin.qq.com/s/wbdV9rV60AwWiiTEBYPP7g)

如果尝试对 List 中的元素进行增加、删除或者更新，就会抛出 UnsupportedOperationException 异常。

另外，Immutable List 中的元素是非 null 的，如果使用 null 来创建 Immutable List，则会抛出 NullPointerException；如果尝试在 Immutable List 中添加 null 元素，则会抛出 UnsupportedOperationException。

那 Immutable List 有什么好处呢？

- 它是线程安全的；
- 它是高效的；
- 因为它是不可变的，就可以像 String 一样传递给第三方类库，不会发生任何安全问题。

那接下来，我们来看一下，如何创建 Immutable List。注意，源码是基于 JDK14 的。

### 01、借助原生 JDK

Collections 类的 `unmodifiableList()` 方法可以创建一个类似于 Immutable List 的 UnmodifiableList 或者 UnmodifiableRandomAccessList，都是不可修改的。

```java
public static <T> List<T> unmodifiableList(List<? extends T> list) {
    return (list instanceof RandomAccess ?
            new Collections.UnmodifiableRandomAccessList<>(list) :
            new Collections.UnmodifiableList<>(list));
}
```



来看一下使用方法：

```java
List<String> list = new ArrayList<>(Arrays.asList("沉默王二", "沉默王三", "沉默王四"));
List<String> unmodifiableList = Collections.unmodifiableList(list);
```

我们尝试往 unmodifiableList 中添加元素“沉默王五”：

```java
unmodifiableList.add("沉默王五");
```

运行后会抛出 UnsupportedOperationException 异常：

```
Exception in thread "main" java.lang.UnsupportedOperationException
    at java.base/java.util.Collections$UnmodifiableCollection.add(Collections.java:1062)
    at com.cmower.mkyong.immutablelist.ImmutableListDemo.main(ImmutableListDemo.java:16)
```

### 02、借助 Java 9

Java 9 的时候，List 类新增了一个 `of()` 静态工厂方法，可以用来创建不可变的 List。先来看一下源码：

```java
static <E> List<E> of(E e1, E e2, E e3) {
    return new ImmutableCollections.ListN<>(e1, e2, e3);
}
```

`of()` 方法有很多变体，比如说：

```java
static <E> List<E> of(E e1) {
        return new ImmutableCollections.List12<>(e1);
    }
static <E> List<E> of(E e1, E e2) {
        return new ImmutableCollections.List12<>(e1, e2);
    }
static <E> List<E> of(E e1, E e2, E e3, E e4) {
    return new ImmutableCollections.ListN<>(e1, e2, e3, e4);
}
static <E> List<E> of(E e1, E e2, E e3, E e4, E e5) {
    return new ImmutableCollections.ListN<>(e1, e2, e3, e4, e5);
}
static <E> List<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
    return new ImmutableCollections.ListN<>(e1, e2, e3, e4, e5,
            e6, e7, e8, e9, e10);
}
```

该方法的设计者也挺有意思的，`of()` 方法的参数，从 0 到 10 都有一个相同签名的重载方法。

甚至当参数是可变的时候，使用 switch 语句对参数的个数进行了判断，然后调用不同的重载方法：

```java
static <E> List<E> of(E... elements) {
    switch (elements.length) { // implicit null check of elements
        case 0:
            @SuppressWarnings("unchecked")
            var list = (List<E>) ImmutableCollections.ListN.EMPTY_LIST;
            return list;
        case 1:
            return new ImmutableCollections.List12<>(elements[0]);
        case 2:
            return new ImmutableCollections.List12<>(elements[0], elements[1]);
        default:
            return new ImmutableCollections.ListN<>(elements);
    }
}
```

不管是 ImmutableCollections.List12 还是 ImmutableCollections.ListN，它们都是 final 的，并且继承了 AbstractImmutableList，里面的元素也是 final 的。

```java
static final class List12<E> extends ImmutableCollections.AbstractImmutableList<E>
        implements Serializable {

    @Stable
    private final E e0;

    @Stable
    private final E e1;
}

static final class ListN<E> extends ImmutableCollections.AbstractImmutableList<E>
        implements Serializable {

    // EMPTY_LIST may be initialized from the CDS archive.
    static @Stable List<?> EMPTY_LIST;

    static {
        VM.initializeFromArchive(ImmutableCollections.ListN.class);
        if (EMPTY_LIST == null) {
            EMPTY_LIST = new ImmutableCollections.ListN<>();
        }
    }

    @Stable
    private final E[] elements;
}
```

好了，来看一下使用方法吧：

```java
final List<String> unmodifiableList = List.of("沉默王二", "沉默王三", "沉默王四");
unmodifiableList.add("沉默王五");
```

ImmutableCollections 的内部类 ListN 或者 List12 同样不可修改，使用 `add()` 方法添加元素同样会在运行时抛出异常：

```
Exception in thread "main" java.lang.UnsupportedOperationException
    at java.base/java.util.ImmutableCollections.uoe(ImmutableCollections.java:73)
    at java.base/java.util.ImmutableCollections$AbstractImmutableCollection.add(ImmutableCollections.java:77)
    at com.cmower.mkyong.immutablelist.ImmutableListDemo.main(ImmutableListDemo.java:20)
```

### 03、借助 Guava

>Guava 工程包含了若干被 Google 的 Java 项目广泛依赖的核心库，例如：集合 [collections] 、缓存 [caching] 、原生类型支持 [primitives support] 、并发库 [concurrency libraries] 、通用注解 [common annotations] 、字符串处理 [string processing] 、I/O 等等。 所有这些工具每天都在被 Google 的工程师应用在产品服务中。

在实际的项目实战当中，Guava 类库的使用频率真的蛮高的，因此我们需要在项目中先引入 Guava 的 Maven 依赖。

```
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>28.1-jre</version>
</dependency>
```

Guava 定了一个 ImmutableList 类，它的声明方式如下所示：

```java
@GwtCompatible(serializable = true, emulated = true)
@SuppressWarnings("serial") // we're overriding default serialization
public abstract class ImmutableList<E> extends ImmutableCollection<E>
    implements List<E>, RandomAccess {
}
```

它的类结构关系如下所示：

```java
java.lang.Object
  ↳ java.util.AbstractCollection
      ↳ com.google.common.collect.ImmutableCollection
          ↳ com.google.common.collect.ImmutableList
```

ImmutableList 类的 `copyOf()` 方法可用于创建一个不可变的 List 对象：

```java
List<String> list = new ArrayList<>(Arrays.asList("沉默王二", "沉默王三", "沉默王四"));
List<String> unmodifiableList = ImmutableList.copyOf(list);
unmodifiableList.add("沉默王五");
```

ImmutableList 同样不允许添加元素，`add()` 方法在执行的时候会抛出 UnsupportedOperationException 异常：

```
Exception in thread "main" java.lang.UnsupportedOperationException
    at com.google.common.collect.ImmutableCollection.add(ImmutableCollection.java:244)
    at com.cmower.mkyong.immutablelist.ImmutableListDemo.main(ImmutableListDemo.java:25)
```

ImmutableList 类的 `of()` 方法和 Java 9 的 `of()` 方法类似，同样有很多相同签名的重载方法，使用方法也完全类似：

```java
List<String> unmodifiableList = ImmutableList.of("沉默王二", "沉默王三", "沉默王四");
```

ImmutableList 类还提供了 builder 模式，既可以在创建的时候添加元素，也可以基于已有的 List 创建，还可以将两者混合在一起。

```java
ImmutableList<String> iList = ImmutableList.<String>builder()
        .add("沉默王二", "沉默王三", "沉默王四")
        .build();

List<String> list = List.of("沉默王二", "沉默王三", "沉默王四");
ImmutableList<String> iList = ImmutableList.<String>builder()
        .addAll(list)
        .build();

List<String> list = List.of("沉默王二", "沉默王三", "沉默王四");
ImmutableList<String> iList = ImmutableList.<String>builder()
        .addAll(list)
        .add("沉默王五")
        .build();
```

### 04、Collections.unmodifiableList() 和 ImmutableList 有什么区别？

`Collections.unmodifiableList()` 基于原有的 List 创建了一个不可变的包装器，该包装器是不可修改的，但是，我们可以通过对原有的 List 进行修改，从而影响到包装器，来看下面的示例：

```java
List<String> list = new ArrayList<>();
list.add("沉默王二");

List<String> iList = Collections.unmodifiableList(list);

list.add("沉默王三");
list.add("沉默王四");

System.out.println(iList);
```

程序输出的结果如下所示：

```
[沉默王二, 沉默王三, 沉默王四]
```

但如果我们通过 ImmutableList 类创建一个不可变 List，原有 List 的改变并不会影响到 ImmutableList。

```java
List<String> list = new ArrayList<>();
list.add("沉默王二");

ImmutableList<String> iList = ImmutableList.copyOf(list);

list.add("沉默王三");
list.add("沉默王四");

System.out.println(iList);
```

程序输出的结果如下所示：

```
[沉默王二]
```

这是因为 ImmutableList 是在原有的 List 上进行了拷贝。

-----

我是沉默王二，一枚有颜值却靠才华苟且的程序员。**关注即可提升学习效率，别忘了三连啊，点赞、收藏、留言，我不挑，奥利给**。

注：如果文章有任何问题，欢迎毫不留情地指正。

如果你觉得文章对你有些帮助欢迎微信搜索「**沉默王二**」第一时间阅读，回复「**小白**」更有我肝了 4 万+字的 Java 小白手册 2.0 版，本文 **GitHub** [github.com/itwanger](https://github.com/itwanger/itwanger.github.io) 已收录，欢迎 star。
