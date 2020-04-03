---
layout: post
category: life
title: 我去，你竟然还在用 try–catch-finally
tagline: by 沉默王二
tags: 
  - java
---

>二哥，你之前那篇 [我去 switch ](https://mp.weixin.qq.com/s/1BDDLDSKDGwQAfIFMyySdg)的文章也特么太有趣了，读完后意犹未尽啊，要不要再写一篇啊？虽然用的是 Java 13 的语法，对旧版本不太友好。但谁能保证 Java 不会再来一次重大更新呢，就像 Java 8 那样，活生生地把 Java 6 拍死在了沙滩上。Java 8 是香，但早晚要升级，我挺你，二哥，别在乎那些反对的声音。


<!--more-->

这是读者 Alice 上周特意给我发来的信息，真令我动容。的确，上次的“我去”阅读量杠杠的，几个大号都转载了，包括 CSDN，次条当天都 1.5 万阅读。但比如“还以为你有什么新特技，没想到用的是 Java 13”这类批评的声音也不在少数。

不过我的心一直很大。从我写第一篇文章至今，被喷的次数就好像头顶上茂密的发量一样，数也数不清。所以我决定再接再厉，带来新的一篇“我去”。

![](http://www.itwanger.com/assets/images/2020/04/java-try-catch-finally-01.png)


这次不用远程 review 了，因为我们公司也复工了。这次 review 的代码仍然是小王的，他编写的大部分代码都很漂亮，严谨的同时注释也很到位，这令我非常满意。但当我看到他没用 try-with-resources 时，还是忍不住破口大骂：“我擦，小王，你丫的竟然还在用 try–catch-finally！”

来看看小王写的代码吧。

```java
public class Trycatchfinally {
    public static void main(String[] args) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("/牛逼.txt"));
            String str = null;
            while ((str =br.readLine()) != null) {
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
```

咦，感觉这段代码很完美无缺啊，try–catch-finally 用得中规中矩，尤其是文件名 `牛逼.txt` 很亮。不用写注释都能明白这段代码是干嘛的：在 try 块中读取文件中的内容，并一行一行地打印到控制台。如果文件找不到或者出现 IO 读写错误，就在 catch 中捕获并打印错误的堆栈信息。最后，在 finally 中关闭缓冲字符读取器对象 BufferedReader，有效杜绝了资源未被关闭的情况下造成的严重性能后果。

在 Java 7 之前，try–catch-finally 的确是确保资源会被及时关闭的最佳方法，无论程序是否会抛出异常。

但是呢，有经验的读者会从上面这段代码中发现 2 个严重的问题：

1）文件名“牛逼.txt”包含了中文，需要通过 `java.net.URLDecoder` 类的 `decode()` 方法对其转义，否则这段代码在运行时铁定要抛出文件找不到的异常。

2）如果直接通过 `new FileReader("牛逼.txt")` 创建 FileReader 对象，“牛逼.txt”需要和项目的 src 在同一级目录下，否则同样会抛出文件找不到的异常。但大多数情况下，（配置）文件会放在 resources 目录下，便于编译后文件出现在 classes 目录下，见下图。

![](http://www.itwanger.com/assets/images/2020/04/java-try-catch-finally-02.png)

为了解决以上 2 个问题，我们需要对代码进行优化：

```java
public class TrycatchfinallyDecoder {
    public static void main(String[] args) {
        BufferedReader br = null;
        try {
            String path = TrycatchfinallyDecoder.class.getResource("/牛逼.txt").getFile();
            String decodePath = URLDecoder.decode(path,"utf-8");
            br = new BufferedReader(new FileReader(decodePath));

            String str = null;
            while ((str =br.readLine()) != null) {
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
```

运行这段代码，程序就可以将文件中的内容正确输出到控制台。但如果你对“整洁”这个词心生向往的话，会感觉这段代码非常臃肿，尤其是 finally 中的代码，就好像一个灌了 12 瓶雪花啤酒的大肚腩。

网上看到一幅 Python 程序员调侃 Java 程序员的神图，直接 copy 过来（侵删），逗你一乐：

![](http://www.itwanger.com/assets/images/2020/04/java-try-catch-finally-03.png)


况且，try–catch-finally 至始至终存在一个严重的隐患：try 中的 `br.readLine()` 有可能会抛出 `IOException`，finally 中的 `br.close()` 也有可能会抛出 `IOException`。假如两处都不幸地抛出了 IOException，那程序的调试任务就变得复杂了起来，到底是哪一处出了错误，就需要花一番功夫，这是我们不愿意看到的结果。

为了模拟上述情况，我们来自定义一个类 MyfinallyReadLineThrow，它有两个方法，分别是 `readLine()` 和 `close()`，方法体都是主动抛出异常。

```java
class MyfinallyReadLineThrow {
    public void close() throws Exception {
        throw new Exception("close");
    }

    public void readLine() throws Exception {
        throw new Exception("readLine");
    }
}
```

然后我们在 `main()` 方法中使用 try-finally 的方式调用 MyfinallyReadLineThrow 的 `readLine()` 和 `close()` 方法：

```java
public class TryfinallyCustomReadLineThrow {
    public static void main(String[] args) throws Exception {
        MyfinallyReadLineThrow myThrow = null;
        try {
            myThrow = new MyfinallyReadLineThrow();
            myThrow.readLine();
        } finally {
            myThrow.close();
        }
    }
}
```

运行上述代码后，错误堆栈如下所示：

```
Exception in thread "main" java.lang.Exception: close
	at com.cmower.dzone.trycatchfinally.MyfinallyOutThrow.close(TryfinallyCustomOutThrow.java:17)
	at com.cmower.dzone.trycatchfinally.TryfinallyCustomOutThrow.main(TryfinallyCustomOutThrow.java:10)
```

`readLine()` 方法的异常信息竟然被 `close()` 方法的堆栈信息吃了，这必然会让我们误以为要调查的目标是 `close()` 方法而不是 `readLine()`——尽管它也是应该怀疑的对象。

但自从有了 try-with-resources，这些问题就迎刃而解了，只要需要释放的资源（比如 BufferedReader）实现了 AutoCloseable 接口。有了解决方案之后，我们来对之前的 finally 代码块进行瘦身。

```java
try (BufferedReader br = new BufferedReader(new FileReader(decodePath));) {
    String str = null;
    while ((str =br.readLine()) != null) {
        System.out.println(str);
    }
} catch (IOException e) {
    e.printStackTrace();
}
```

你瞧，finally 代码块消失了，取而代之的是把要释放的资源写在 try 后的 `()` 中。如果有多个资源（BufferedReader 和 PrintWriter）需要释放的话，可以直接在 `()` 中添加。

```java
try (BufferedReader br = new BufferedReader(new FileReader(decodePath));
     PrintWriter writer = new PrintWriter(new File(writePath))) {
    String str = null;
    while ((str =br.readLine()) != null) {
        writer.print(str);
    }
} catch (IOException e) {
    e.printStackTrace();
}
```

如果你想释放自定义资源的话，只要让它实现 AutoCloseable 接口，并提供 `close()` 方法即可。

```java
public class TrywithresourcesCustom {
    public static void main(String[] args) {
        try (MyResource resource = new MyResource();) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class MyResource implements AutoCloseable {
    @Override
    public void close() throws Exception {
        System.out.println("关闭自定义资源");
    }
}
```

代码运行后输出的结果如下所示：

```
关闭自定义资源
```

是不是很神奇？我们在 `try ()` 中只是 new 了一个 MyResource 的对象，其他什么也没干，但偏偏 `close()` 方法中的输出语句执行了。想要知道为什么吗？来看看反编译后的字节码吧。

```java
class MyResource implements AutoCloseable {
    MyResource() {
    }

    public void close() throws Exception {
        System.out.println("关闭自定义资源");
    }
}

public class TrywithresourcesCustom {
    public TrywithresourcesCustom() {
    }

    public static void main(String[] args) {
        try {
            MyResource resource = new MyResource();
            resource.close();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }
}
```

咦，编译器竟然主动为 try-with-resources 进行了变身，在 try 中调用了 `close()` 方法。

接下来，我们在自定义类中再添加一个 `out()` 方法，


```java
class MyResourceOut implements AutoCloseable {
    @Override
    public void close() throws Exception {
        System.out.println("关闭自定义资源");
    }

    public void out() throws Exception{
        System.out.println("沉默王二，一枚有趣的程序员");
    }
}
```

这次，我们在 try 中调用一下 `out()` 方法：

```java
public class TrywithresourcesCustomOut {
    public static void main(String[] args) {
        try (MyResourceOut resource = new MyResourceOut();) {
            resource.out();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

再来看一下反编译的字节码：

```
public class TrywithresourcesCustomOut {
    public TrywithresourcesCustomOut() {
    }

    public static void main(String[] args) {
        try {
            MyResourceOut resource = new MyResourceOut();

            try {
                resource.out();
            } catch (Throwable var5) {
                try {
                    resource.close();
                } catch (Throwable var4) {
                    var5.addSuppressed(var4);
                }

                throw var5;
            }

            resource.close();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }
}
```

这次，`catch` 块中主动调用了 `resource.close()`，并且有一段很关键的代码 ` var5.addSuppressed(var4)`。它有什么用处呢？当一个异常被抛出的时候，可能有其他异常因为该异常而被抑制住，从而无法正常抛出。这时可以通过 `addSuppressed()` 方法把这些被抑制的方法记录下来。被抑制的异常会出现在抛出的异常的堆栈信息中，也可以通过 `getSuppressed()` 方法来获取这些异常。这样做的好处是不会丢失任何异常，方便我们开发人员进行调试。

哇，有没有想到我们之前的那个例子——在 try-finally 中，`readLine()` 方法的异常信息竟然被 `close()` 方法的堆栈信息吃了。现在有了 try-with-resources，再来看看作用和 `readLine()` 方法一致的 `out()` 方法会不会被 `close()` 吃掉。

在 `close()` 和 `out()` 方法中直接抛出异常：

```java
class MyResourceOutThrow implements AutoCloseable {
    @Override
    public void close() throws Exception {
        throw  new Exception("close()");
    }

    public void out() throws Exception{
        throw new Exception("out()");
    }
}
```

调用这 2 个方法：

```java
public class TrywithresourcesCustomOutThrow {
    public static void main(String[] args) {
        try (MyResourceOutThrow resource = new MyResourceOutThrow();) {
            resource.out();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

程序输出的结果如下所示：

```
java.lang.Exception: out()
	at com.cmower.dzone.trycatchfinally.MyResourceOutThrow.out(TrywithresourcesCustomOutThrow.java:20)
	at com.cmower.dzone.trycatchfinally.TrywithresourcesCustomOutThrow.main(TrywithresourcesCustomOutThrow.java:6)
	Suppressed: java.lang.Exception: close()
		at com.cmower.dzone.trycatchfinally.MyResourceOutThrow.close(TrywithresourcesCustomOutThrow.java:16)
		at com.cmower.dzone.trycatchfinally.TrywithresourcesCustomOutThrow.main(TrywithresourcesCustomOutThrow.java:5)
```

瞧，这次不会了，`out()` 的异常堆栈信息打印出来了，并且 `close()` 方法的堆栈信息上加了一个关键字 `Suppressed`。一目了然，不错不错，我喜欢。




总结一下，在处理必须关闭的资源时，始终有限考虑使用 try-with-resources，而不是 try–catch-finally。前者产生的代码更加简洁、清晰，产生的异常信息也更靠谱。答应我好不好？别再用 try–catch-finally 了。

![](http://www.itwanger.com/assets/images/2020/04/java-try-catch-finally-04.png)

### 觉得有点用记得给我点赞哦！😎

简单介绍一下。10 年前，当我上大学的时候，专业被调剂到了计算机网络，主要学的是 Java 编程语言，但当时没怎么好好学，每年都要挂科两三门；因此工作后吃了不少亏。但是最近几年，情况发生了很大改变，你应该也能看得到我这种变化。通过坚持不懈地学习，持续不断地输出，我的编程基本功算得上是突飞猛进。

为了帮助更多的程序员，我创建了“**沉默王二**”这个 ID，专注于分享有趣的 Java 技术编程和有益的程序人生。一开始，阅读量寥寥无几，关注人数更是少得可怜。但随着影响力的逐步扩大，阅读量和关注人都在猛烈攀升。

你在看这篇文章的时候，应该也能发现，我在 CSDN 上的总排名已经来到了第 71 位，这个排名还是非常给力的。有很多读者都说，我可以冲击第一名，我不愿意藏着掖着，我是有这个野心的。如果你也喜欢我的文章，请记得微信搜索「**沉默王二**」关注我的原创公众号，回复“**1024**”更有美团技术大佬整理的 Java 面试攻略相送，还有架构师的面试视频哦。绝对不容错过，期待与你的不期而遇。

![](http://www.itwanger.com/assets/images/cmower_6.png)