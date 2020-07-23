---
layout: post
category: java
title: 毫不留情地揭开 ArrayList 和 LinkedList 之间的神秘面纱
tagline: by 沉默王二
tags: 
  - java
---

>先看再点赞，给自己一点思考的时间，思考过后请毫不犹豫微信搜索【**沉默王二**】，关注这个靠才华苟且的程序员。
本文 **GitHub** [github.com/itwanger](https://github.com/qinggee/itwanger.github.io) 已收录，里面还有技术大佬整理的面试题，以及二哥的系列文章。

<!--more-->





[ArrayList](https://mp.weixin.qq.com/s/9of-ZgM3FGMkg3g4t7Mq4w) 和 [LinkedList](https://mp.weixin.qq.com/s/Gr1kXieJoQol2WuFuHWQaA) 是 List 接口的两种不同实现，并且两者都不是线程安全的。但初学者往往搞不清楚它们两者之间的区别，不知道什么时候该用 ArrayList，什么时候该用 LinkedList，那这篇文章就来传道受业解惑一下。

![](http://www.itwanger.com/assets/images/2020/07/java-linkedlist-arraylist-01.gif)

ArrayList 内部使用的动态数组来存储元素，LinkedList 内部使用的双向链表来存储元素，这也是 ArrayList 和 LinkedList 最本质的区别。

注：本文使用的 JDK 源码版本为 14，小伙伴如果发现文章中的源码和自己本地的不同时，不要担心，不是我源码贴错了，也不是你本地的源码错了，只是版本不同而已。

由于 ArrayList 和 LinkedList 内部使用的存储方式不同，导致它们的各种方法具有不同的时间复杂度。先来通过维基百科理解一下时间复杂度这个概念。

>在计算机科学中，算法的时间复杂度（Time complexity）是一个函数，它定性描述该算法的运行时间。这是一个代表算法输入值的字符串的长度的函数。时间复杂度常用大 O 符号表述，不包括这个函数的低阶项和首项系数。使用这种方式时，时间复杂度可被称为是渐近的，亦即考察输入值大小趋近无穷时的情况。例如，如果一个算法对于任何大小为 n （必须比 $n_0$ 大）的输入，它至多需要 $5n^3 + 3n$ 的时间运行完毕，那么它的渐近时间复杂度是 $O(n3^)$。

对于 ArrayList 来说：

 1）`get(int index)` 方法的时间复杂度为 $O(1)$，因为是直接从底层数组根据下标获取的，和数组长度无关。

```java
public E get(int index) {
    Objects.checkIndex(index, size);
    return elementData(index);
}
```

这也是 ArrayList 的最大优点。

2）`add(E e)` 方法会默认将元素添加到数组末尾，但需要考虑到数组扩容的情况，如果不需要扩容，时间复杂度为 $O(1)$。

```java
public boolean add(E e) {
    modCount++;
    add(e, elementData, size);
    return true;
}

private void add(E e, Object[] elementData, int s) {
    if (s == elementData.length)
        elementData = grow();
    elementData[s] = e;
    size = s + 1;
}
```

如果需要扩容的话，并且不是第一次（`oldCapacity > 0`）扩容的时候，内部执行的 `Arrays.copyOf()` 方法是耗时的关键，需要把原有数组中的元素复制到扩容后的新数组当中。

```java
private Object[] grow(int minCapacity) {
    int oldCapacity = elementData.length;
    if (oldCapacity > 0 || elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
        int newCapacity = ArraysSupport.newLength(oldCapacity,
                minCapacity - oldCapacity, /* minimum growth */
                oldCapacity >> 1           /* preferred growth */);
        return elementData = Arrays.copyOf(elementData, newCapacity);
    } else {
        return elementData = new Object[Math.max(DEFAULT_CAPACITY, minCapacity)];
    }
}
```

3）`add(int index, E element)` 方法将新的元素插入到指定的位置，考虑到需要复制底层数组（根据之前的判断，扩容的话，数组可能要复制一次），根据最坏的打算（不管需要不需要扩容，`System.arraycopy()` 肯定要执行），所以时间复杂度为 $O(n)$。

```java
public void add(int index, E element) {
    rangeCheckForAdd(index);
    modCount++;
    final int s;
    Object[] elementData;
    if ((s = size) == (elementData = this.elementData).length)
        elementData = grow();
    System.arraycopy(elementData, index,
            elementData, index + 1,
            s - index);
    elementData[index] = element;
    size = s + 1;
}
```

来执行以下代码，把沉默王八插入到下标为 2 的位置上。

```java
ArrayList<String> list = new ArrayList<>();
list.add("沉默王二");
list.add("沉默王三");
list.add("沉默王四");
list.add("沉默王五");
list.add("沉默王六");
list.add("沉默王七");
list.add(2, "沉默王八");
```
`System.arraycopy()` 执行完成后，下标为 2 的元素为沉默王四，这一点需要注意。也就是说，在数组中插入元素的时候，会把插入位置以后的元素依次往后复制，所以下标为 2 和下标为 3 的元素都为沉默王四。

![](http://www.itwanger.com/assets/images/2020/07/java-linkedlist-arraylist-02.png)

之后再通过 `elementData[index] = element` 将下标为 2 的元素赋值为沉默王八；随后执行 `size = s + 1`，数组的长度变为 7。

![](http://www.itwanger.com/assets/images/2020/07/java-linkedlist-arraylist-03.png)

4）` remove(int index)` 方法将指定位置上的元素删除，考虑到需要复制底层数组，所以时间复杂度为 $O(n)$。

```java
public E remove(int index) {
    Objects.checkIndex(index, size);
    final Object[] es = elementData;

    @SuppressWarnings("unchecked") E oldValue = (E) es[index];
    fastRemove(es, index);

    return oldValue;
}
private void fastRemove(Object[] es, int i) {
    modCount++;
    final int newSize;
    if ((newSize = size - 1) > i)
        System.arraycopy(es, i + 1, es, i, newSize - i);
    es[size = newSize] = null;
}
```

对于 LinkedList 来说：

1）`get(int index)` 方法的时间复杂度为 $O(n)$，因为需要循环遍历整个链表。

```java
public E get(int index) {
    checkElementIndex(index);
    return node(index).item;
}

LinkedList.Node<E> node(int index) {
    // assert isElementIndex(index);

    if (index < (size >> 1)) {
        LinkedList.Node<E> x = first;
        for (int i = 0; i < index; i++)
            x = x.next;
        return x;
    } else {
        LinkedList.Node<E> x = last;
        for (int i = size - 1; i > index; i--)
            x = x.prev;
        return x;
    }
}
```

下标小于链表长度的一半时，从前往后遍历；否则从后往前遍历，这样从理论上说，就节省了一半的时间。

如果下标为 0 或者 `list.size() - 1` 的话，时间复杂度为 $O(1)$。这种情况下，可以使用 `getFirst()` 和 `getLast()` 方法。

```java
public E getFirst() {
    final LinkedList.Node<E> f = first;
    if (f == null)
        throw new NoSuchElementException();
    return f.item;
}

public E getLast() {
    final LinkedList.Node<E> l = last;
    if (l == null)
        throw new NoSuchElementException();
    return l.item;
}
```

first 和 last 在链表中是直接存储的，所以时间复杂度为 $O(1)$。

2）`add(E e)` 方法默认将元素添加到链表末尾，所以时间复杂度为 $O(1)$。

```java
public boolean add(E e) {
    linkLast(e);
    return true;
}
void linkLast(E e) {
    final LinkedList.Node<E> l = last;
    final LinkedList.Node<E> newNode = new LinkedList.Node<>(l, e, null);
    last = newNode;
    if (l == null)
        first = newNode;
    else
        l.next = newNode;
    size++;
    modCount++;
}
```

3）`add(int index, E element)` 方法将新的元素插入到指定的位置，需要先通过遍历查找这个元素，然后再进行插入，所以时间复杂度为 $O(n)$。

```java
public void add(int index, E element) {
    checkPositionIndex(index);

    if (index == size)
        linkLast(element);
    else
        linkBefore(element, node(index));
}
```

如果下标为 0 或者 `list.size() - 1` 的话，时间复杂度为 $O(1)$。这种情况下，可以使用 `addFirst()` 和 `addLast()` 方法。

```java
public void addFirst(E e) {
    linkFirst(e);
}
private void linkFirst(E e) {
    final LinkedList.Node<E> f = first;
    final LinkedList.Node<E> newNode = new LinkedList.Node<>(null, e, f);
    first = newNode;
    if (f == null)
        last = newNode;
    else
        f.prev = newNode;
    size++;
    modCount++;
}
```

`linkFirst()` 只需要对 first 进行更新即可。

```java
public void addLast(E e) {
    linkLast(e);
}

void linkLast(E e) {
    final LinkedList.Node<E> l = last;
    final LinkedList.Node<E> newNode = new LinkedList.Node<>(l, e, null);
    last = newNode;
    if (l == null)
        first = newNode;
    else
        l.next = newNode;
    size++;
    modCount++;
}
```

`linkLast()` 只需要对 last 进行更新即可。

需要注意的是，有些文章里面说，LinkedList 插入元素的时间复杂度近似 $O(1)$，其实是有问题的，因为 `add(int index, E element)` 方法在插入元素的时候会调用 `node(index)` 查找元素，该方法之前我们之间已经确认过了，时间复杂度为 $O(n)$，即便随后调用 `linkBefore()` 方法进行插入的时间复杂度为 $O(1)$，总体上的时间复杂度仍然为 $O(n)$ 才对。


```java
void linkBefore(E e, LinkedList.Node<E> succ) {
    // assert succ != null;
    final LinkedList.Node<E> pred = succ.prev;
    final LinkedList.Node<E> newNode = new LinkedList.Node<>(pred, e, succ);
    succ.prev = newNode;
    if (pred == null)
        first = newNode;
    else
        pred.next = newNode;
    size++;
    modCount++;
}
```

4）` remove(int index)` 方法将指定位置上的元素删除，考虑到需要调用 `node(index)` 方法查找元素，所以时间复杂度为 $O(n)$。

```java
public E remove(int index) {
    checkElementIndex(index);
    return unlink(node(index));
}

E unlink(LinkedList.Node<E> x) {
    // assert x != null;
    final E element = x.item;
    final LinkedList.Node<E> next = x.next;
    final LinkedList.Node<E> prev = x.prev;

    if (prev == null) {
        first = next;
    } else {
        prev.next = next;
        x.prev = null;
    }

    if (next == null) {
        last = prev;
    } else {
        next.prev = prev;
        x.next = null;
    }

    x.item = null;
    size--;
    modCount++;
    return element;
}
```

通过时间复杂度的比较，以及源码的分析，我相信小伙伴们在选择的时候就有了主意，对吧？

需要注意的是，如果列表很大很大，ArrayList 和 LinkedList 在内存的使用上也有所不同。LinkedList 的每个元素都有更多开销，因为要存储上一个和下一个元素的地址。ArrayList 没有这样的开销。

但是，ArrayList 占用的内存在声明的时候就已经确定了（默认大小为 10），不管实际上是否添加了元素，因为复杂对象的数组会通过 null 来填充。LinkedList 在声明的时候不需要指定大小，元素增加或者删除时大小随之改变。

另外，ArrayList 只能用作列表；LinkedList 可以用作列表或者队列，因为它还实现了 Deque 接口。

我在写这篇文章的时候，遇到了一些问题，所以请教了一些大厂的技术大佬，结果有个朋友说，“如果真的不知道该用 ArrayList 还是 LinkedList，就选择 ArrayList 吧！”

我当时以为他在和我开玩笑呢，结果通过时间复杂度的分析，好像他说得有道理啊。查询的时候，ArrayList 比 LinkedList 快，这是毋庸置疑的；插入和删除的时候，之前有很多资料说 LinkedList 更快，时间复杂度为 $O(1)$，但其实不是的，因为要遍历列表，对吧？

反而 ArrayList 更轻量级，不需要在每个元素上维护上一个和下一个元素的地址。

![](http://www.itwanger.com/assets/images/2020/07/java-linkedlist-arraylist-04.png)


我这样的结论可能和大多数文章得出的结论不符，那么我想，选择权交给小伙伴们，你们在使用的过程中认真地思考一下，并且我希望你们把自己的思考在留言区放出来。


-----

我是沉默王二，一枚有颜值却靠才华苟且的程序员。**关注即可提升学习效率，别忘了三连啊，点赞、收藏、留言，我不挑，奥利给**。

注：如果文章有任何问题，欢迎毫不留情地指正。

如果你觉得文章对你有些帮助欢迎微信搜索「**沉默王二**」第一时间阅读，回复「**小白**」更有我肝了 4 万+字的 Java 小白手册 2.0 版，本文 **GitHub** [github.com/itwanger](https://github.com/itwanger/itwanger.github.io) 已收录，欢迎 star。
