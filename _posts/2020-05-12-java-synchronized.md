---
layout: post
category: java
title: 我去，你竟然还不会用 synchronized
tagline: by 沉默王二
tags: 
  - java
---

>二哥，离你上一篇[我去](https://mp.weixin.qq.com/s/fbTzH5B7mSr5v0tQ8mV2wA)已经过去两周时间了，这个系列还不打算更新吗？着急着看呢。

<!--more-->

以上是读者 Jason 发来的一条信息，不看不知道，一看真的是吓一跳，上次我去是 4 月 3 号更新的，离现在一个多月了，可不只是两周时间啊。可能我自己天天写，没觉得时间已经过去这么久了，是时候带来新的一篇“我去”了。

![](http://www.itwanger.com/assets/images/2020/05/java-synchronized-01.png)

这次没有代码 review，是同事小王直接问我的，“青哥，能给我详细地说一说 synchronized 关键字怎么用吗？”他问的态度很谦逊，但我还是忍不住破口大骂：“我擦，小王，你丫的竟然不会用 synchronized，我当初是怎么面试你进来的！”

（我笔名是沉默王二，读者都叫二哥，但在公司不是的，同事叫我青哥，想知道我真名的，可以搜《Web全栈开发进阶之路》）

简单地说，当两个或者两个以上的线程同一时间要修改同一个可变的共享数据时，就需要一些保护措施，否则，共享数据修改后的结果大概率会超出你的预期。对于初学者来说，synchronized 关键字就是最好用的一种解决方案。

### 01、为什么需要保护

可能很多初学者不明白，为什么多线程环境下，可变共享变量修改后的结果会超出预期。为了解释清楚这一点，来看一个例子。

```java
public class SynchronizedMethod {
    private int sum;

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public void calculate() {
        setSum(getSum() + 1);
    }
}
```

SynchronizedMethod 是一个非常简单的类，有一个私有的成员变量 sum，对应的 getter/setter，以及给 sum 加 1 的 `calculate()` 方法。

然后，我们来给 `calculate()` 方法写一个简单的测试用例。

可能一些初学者还不知道怎么快速创建测试用例，我这里就手摸手地现场教学下。

第一步，把鼠标移动到类名上，会弹出一个提示框。

![](http://www.itwanger.com/assets/images/2020/05/java-synchronized-02.png)

第二步，点击「More actions」按钮，会弹出以下提示框。

![](http://www.itwanger.com/assets/images/2020/05/java-synchronized-03.png)

第三步，选择「Create Test」，弹出创建测试用例的对话框。

![](http://www.itwanger.com/assets/images/2020/05/java-synchronized-04.png)

选择最新的 JUnit5，如果项目之前没有引入 JUnit5 依赖的话，IDEA 会提醒你，点击 Fix，IDEA 会自动帮你添加，非常智能化。在对话框中勾选要创建测试用例的方法——`calculate()`。

点击 OK 按钮后，IDEA 会在 src 的同级目录 test 下创建一个名为 SynchronizedMethodTest 的测试类：

```java
class SynchronizedMethodTest {
    @Test
    void calculate() {
    }
}
```

`calculate()` 方法上会有一个 `@Test` 的注解，表示这是一个测试方法。添加具体的代码，如下所示：

```java
ExecutorService service = Executors.newFixedThreadPool(3);
SynchronizedMethod summation = new SynchronizedMethod();

IntStream.range(0, 1000)
        .forEach(count -> service.submit(summation::calculate));
service.awaitTermination(1000, TimeUnit.MILLISECONDS);

assertEquals(1000, summation.getSum());
```

1）`Executors.newFixedThreadPool()` 方法可以创建一个指定大小的线程池服务 ExecutorService。

2）通过 `IntStream.range(0, 1000).forEach()` 来执行 `calculate()` 方法 1000 次。

3）通过 `assertEquals()` 方法进行判断。

运行该测试用例，结果会是什么呢？

![](http://www.itwanger.com/assets/images/2020/05/java-synchronized-05.png)

很不幸，失败了。预期的值为 1000，但实际的值是 976。这是因为多线程环境下，可变的共享数据没有得到保护。

### 02、synchronized 的用法

这么说吧，初学者在遇到多线程问题时，只要 synchronized 关键字使用得当，问题就能够迎刃而解。记得我刚回洛阳的时候，面试官问我，项目中是怎么解决并发问题的呢？我就说用 synchronized 关键字，至于其他的一些锁机制，我那时候还不知道。

嗯，面试官好像也不知道，因为小公司嘛，并发的量级有限，性能也不用考量得太过深入（大公司的读者可以呵呵了）。接下来，就随我来，一起看看 synchronized 最常见的三种用法吧。

1）直接用在方法上，就像下面这样：

```java
public synchronized void synchronizedCalculate() {
    setSum(getSum() + 1);
}
```

修改一下测试用例：

```java
@Test
void synchronizedCalculate() throws InterruptedException {
    ExecutorService service = Executors.newFixedThreadPool(3);
    SynchronizedMethod summation = new SynchronizedMethod();

    IntStream.range(0, 1000)
            .forEach(count -> service.submit(summation::synchronizedCalculate));
    service.awaitTermination(1000, TimeUnit.MILLISECONDS);

    assertEquals(1000, summation.getSum());
}
```

这时候，再运行测试用例就通过了。因为 synchronized 关键字会对 SynchronizedMethod 对象进行加锁，同一时间内只允许一个线程对 sum 进行修改。这就好像有一间屋子，线程进入屋子里面才可以对 sum 加 1，而 synchronized 就相当于在门上加了一个锁，一个线程进去后就锁上门，修改完 sum 后，下一个线程再进去，其他线程就在门外候着。

2）用在 static 方法上，就像下面这样：

```java
public class SynchronizedStaticMethod {
    public static int sum;

    public synchronized static void synchronizedCalculate() {
        sum = sum + 1;
    }
}
```

sum 是一个静态变量，要修改静态变量的时候，就需要把方法也变成 static 的。

来新建一个测试用例：

```java
class SynchronizedStaticMethodTest {
    @Test
    void synchronizedCalculate() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);

        IntStream.range(0, 1000)
                .forEach(count -> service.submit(SynchronizedStaticMethod::synchronizedCalculate));
        service.awaitTermination(1000, TimeUnit.MILLISECONDS);

        assertEquals(1000, SynchronizedStaticMethod.sum);
    }
}
```

静态方法上添加 synchronized 的时候就不需要实例化对象了，直接使用类名就可以引用方法和使用变量了。测试用例也是可以通过的。

synchronized static 和 synchronized 不同的是，前者锁的是类，同一时间只能有一个线程访问这个类；后者锁的是对象，同一时间只能有一个线程访问方法。

3）用在方法块上，就像下面这样：

```java
public void synchronisedThis() {
    synchronized (this) {
        setSum(getSum() + 1);
    }
}
```

这时候，将 this 传递给了 synchronized 代码块，当在某个线程中执行这段代码块，该线程会获取 this 对象的锁，从而使得其他线程无法同时访问该代码块。如果方法是静态的，我们将传递类名代替对象引用，示例如下所示：

```java
public static void synchronisedThis() {
    synchronized (SynchronizedStaticMethod.class) {
        sum = sum + 1;
    }
}
```

新建一个测试用例：

```java
@Test
void synchronisedThis() throws InterruptedException {
    ExecutorService service = Executors.newFixedThreadPool(3);
    SynchronizedMethod summation = new SynchronizedMethod();

    IntStream.range(0, 1000)
            .forEach(count -> service.submit(summation::synchronisedThis));
    service.awaitTermination(1000, TimeUnit.MILLISECONDS);

    assertEquals(1000, summation.getSum());
}
```

测试用例和 synchronized 方法的大差不差，运行后也是可以通过的。两者之间有所不同，synchronized 代码块的锁粒度要比 synchronized 方法小一些，因为 synchronized 代码块所在的方法里还可以有其他代码。

![](http://www.itwanger.com/assets/images/2020/05/java-synchronized-06.png)

好了，我亲爱的读者朋友，以上就是本文的全部内容了，synchronized 的三种用法你一定掌握了吧？觉得文章有点用的话，请微信搜索「**沉默王二**」第一时间阅读。

>本文 [GitHub](https://github.com/qinggee/itwanger.github.io) 已经收录，有大厂面试完整考点，欢迎 Star。

我是沉默王二，一枚有趣的程序员，关注即可提高学习效率。最后，请无情地点赞、收藏、留言吧，谢谢。