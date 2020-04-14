---
layout: post
category: life
title: 恕我直言，我怀疑你并不会生成随机数
tagline: by 沉默王二
tags: 
  - java
---

有一次，我在逛 Stack Overflow 的时候，发现有这样一个问题：“Java 中如何产生一个指定范围内的随机数”，我心想，“就这破问题，竟然有 398 万的阅读量，统计确定没搞错？不就一个 `Math.random()` 的事儿嘛。”



<!--more-->


![](http://www.itwanger.com/assets/images/2020/04/java-random-01.png)


于是我直接动用自己的权力投了一票反对。结果，没等到权力执行后的喜悦，却收到了一条提醒：“声望值低于 125 的人有投票权，但不会公开显示。”我呀，我去，扎心了。就冲我这急脾气，不用代码证明一下自己的实力，我还有脸说自己有十年的开发经验吗？于是我兴冲冲地就打开 IDEA，敲下了下面这段代码：

```java
public class GenerateMathRandomInteger {
    public static void main(String[] args) {
        int leftLimit = 2;
        int rightLimit = 11;

        Runnable r = () -> {
            int generatedInteger = leftLimit + (int) (Math.random() * rightLimit);
            System.out.println(generatedInteger);
        };
       for (int i = 1; i < 10; i++) {
           new Thread(r).start();
       }
    }
}
```

这段代码我写得没毛病吧？乍看上去，参数和类的命名都很合理，就连 Lambda 表达式也用上了。但程序输出的结果却出乎我的意料：

```
8
10
10
4
3
4
6
12
3
```

12 是从哪里蹦出来的？当然是从程序的 bug 里蹦出来。`leftLimit + (int) (Math.random() * rightLimit)` 生成的随机数可能超出指定的范围。不行，`Math.random()` 信不过，必须要换一种方法。灵机一动，我想到了 `Random` 类，于是我写下了新的代码：

```java
public class GenerateRandomInteger {
    public static void main(String[] args) {
        int leftLimit = 2;
        int rightLimit = 11;

        Random random = new Random();
        int range = rightLimit - leftLimit + 1;

        Runnable r = () -> {
            int generatedInteger = leftLimit + random.nextInt() % range;
            System.out.println(generatedInteger);
        };
       for (int i = 1; i < 10; i++) {
           new Thread(r).start();
       }
    }
}
```

这一次，我满怀信心，`Math.random()` 解决不了的问题，`random.nextInt()` 就一定能够解决。结果，输出结果再次啪啪啪打了我这张帅脸。

```
0
-3
10
2
2
-4
-4
-6
6
```

竟然还有负数，这真的是残酷的现实，我被教育了，似乎找回了刚入职那会被领导蹂躏的感觉。幸好，我的心态已经不像年轻时候那样易怒，稳得一匹：出问题不要紧，找解决方案就对了。

于是 5 分钟后我写出了下面这段代码：

```java
public class GenerateRandomInteger {
    public static void main(String[] args) {
        int leftLimit = 2;
        int rightLimit = 11;

        Random random = new Random();
        int range = rightLimit - leftLimit;

        Runnable r = () -> {
            int generatedInteger = leftLimit + (int)(random.nextFloat() * range);
            System.out.println(generatedInteger);
        };
       for (int i = 1; i < 10; i++) {
           new Thread(r).start();
       }
    }
}
```

无论是调整线程的数量，还是多次重新运行，结果都符合预期，在 2 - 11 之间。

```
7
2
5
8
6
2
9
9
7
```

`nextFloat()` 方法返回一个均匀分布在 0 - 1 之间的随机浮点数（包含 0.0f，但不包含 1.0f），乘以最大值和最小值的差，再强转为 int 类型就可以保证这个随机数在 0 到（最大值-最小值）之间，最后再加上最小值，就恰好可以得到指定范围内的数字。

如果你肯读源码的话，会发现 Random 类有一个 `nextInt(int bound)` 的方法，该方法会返回一个随机整数，均匀分布在 0 - bound 之间（包含 0，但不包含指定的 bound）。那么利用该方法也可以得到一个有效的随机数，来看示例代码。

```java
public class GenerateRandomNextInt {
    public static void main(String[] args) {
        int leftLimit = 2;
        int rightLimit = 11;

        Random random = new Random();
        Runnable r = () -> {
            int generatedInteger = leftLimit + random.nextInt(rightLimit - leftLimit + 1);
            System.out.println(generatedInteger);
        };
       for (int i = 1; i < 10; i++) {
           new Thread(r).start();
       }
    }
}
```

由于 `nextInt()` 不包含 bound，因此需要 + 1。程序运行的结果也符合预期：

```
8
2
9
8
4
6
4
5
7
```

你看，我之前两次尝试都以失败告终，但我仍然没有放弃希望，经过自己的深思熟虑，我又找到了两种可行的解决办法。这让我想起了普希金的一首诗歌：

>假如生活欺骗了你，不要悲伤，不要心急，忧郁的日子里需要镇静，一切都会过去，一切都是瞬息，一切都会过去。希望之火需要再燃，需要呵护，不致让暴风雨将其熄灭，不致让自己在黑暗、阴冷、无助中绝望。

一首好诗吟完之后，我们再来想想还有没有其他的方案。反正我是想到了，Java 7 以后可以使用 `ThreadLocalRandom` 类，代码示例如下：

```java
public class GenerateRandomThreadLocal {
    public static void main(String[] args) {
        int leftLimit = 2;
        int rightLimit = 11;

        Runnable r = () -> {
            int generatedInteger = ThreadLocalRandom.current().nextInt(leftLimit, rightLimit +1);
            System.out.println(generatedInteger);
        };
       for (int i = 1; i < 10; i++) {
           new Thread(r).start();
       }
    }
}
```

程序输出的结果如下：

```
11
9
6
10
6
6
10
7
3
```

ThreadLocalRandom 类继承自 Random 类，它使用了内部生成的种子来初始化（外部无法设置，所以不能再现测试场景），并且不需要显式地使用 new 关键字来创建对象（Random 可以通过构造方法设置种子），可以直接通过静态方法 `current()` 获取针对本地线程级别的对象：

```java
static final ThreadLocalRandom instance = new ThreadLocalRandom();

static final void localInit() {
    int p = probeGenerator.addAndGet(PROBE_INCREMENT);
    int probe = (p == 0) ? 1 : p; // skip 0
    long seed = mix64(seeder.getAndAdd(SEEDER_INCREMENT));
    Thread t = Thread.currentThread();
    U.putLong(t, SEED, seed);
    U.putInt(t, PROBE, probe);
}

public static ThreadLocalRandom current() {
    if (U.getInt(Thread.currentThread(), PROBE) == 0)
        localInit();
    return instance;
}
```

这样做的好处就是，在多线程或者线程池的环境下，可以节省不必要的内存开销。

最后，我再提供一个解决方案，使用 Apache Commons Math 类库的 RandomDataGenerator 类。在使用该类库之前，需要在 pom.xml 文件中引入该类库的依赖。

```java
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-math3</artifactId>
    <version>3.6.1</version>
</dependency>
```

在需要生成指定范围的随机数时，使用 `new RandomDataGenerator()` 获取随机生成器实例，然后使用 `nextInt()` 方法直接获取最大值与最小值之间的随机数。来看示例。

```java
public class RandomDataGeneratorDemo {
    public static void main(String[] args) {
        int leftLimit = 2;
        int rightLimit = 11;

        Runnable r = () -> {
            int generatedInteger = new RandomDataGenerator().nextInt( leftLimit,rightLimit);
            System.out.println(generatedInteger);
        };
       for (int i = 1; i < 10; i++) {
           new Thread(r).start();
       }
    }
}
```

输出结果如下所示：

```java
8
4
4
4
10
3
10
3
6
```

结果完全符合我们的预期——这也是我的最后一招，没想到就这么愉快地全交给你了。


![](http://www.itwanger.com/assets/images/2020/04/java-random-02.gif)


好了，我亲爱的读者朋友，以上就是本文的全部内容了。如果觉得文章对你有点帮助，请微信搜索「 **沉默王二** 」第一时间阅读。示例代码已经上传到 GitHub，[传送门~](https://github.com/qinggee/SpringDemo)

我是沉默王二，一枚有趣的程序员。**原创不易，莫要白票**，请你为本文点个赞吧，这将是我写作更多优质文章的最强动力。