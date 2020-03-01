---
layout: post
category: life
title: 我去，你写的 switch 语句也太老土了吧
tagline: by 沉默王二
tags: 
  - java
---

昨天早上通过远程的方式 review 了两名新来同事的代码，大部分代码都写得很漂亮，严谨的同时注释也很到位，这令我非常满意。但当我看到他们当中有一个人写的 switch 语句时，还是忍不住破口大骂：“我擦，小王，你丫写的 switch 语句也太老土了吧！”

<!--more-->



![](http://www.itwanger.com/assets/images/2020/03/java-switch-01.png)



来看看小王写的代码吧，看完不要骂我装逼啊。

```java
private static String createPlayer(PlayerTypes playerType) {
    switch (playerType) {
        case TENNIS:
            return "网球运动员费德勒";
        case FOOTBALL:
            return "足球运动员C罗";
        case BASKETBALL:
            return "篮球运动员詹姆斯";
        case UNKNOWN:
            throw new IllegalArgumentException("未知");
        default:
            throw new IllegalArgumentException(
                    "运动员类型: " + playerType);

    }
}
```



看完上述代码后，你是不是会发出这样的感慨——“代码写得很好，没有任何问题啊！”是不是觉得我在无事生非，错怪了小王！但此时我要送上《了不起的盖茨比》中的一句话：

>我年纪还轻，阅历不深的时候，我父亲教导过我一句话，我至今还念念不忘。 “每逢你想要批评任何人的时候， ”他对我说，“你就记住，这个世界上所有的人，并不是个个都有过你拥有的那些优越条件。”

哈哈，这句话不光是让你看的，也是给我看的。是时候冷静下来谈谈上述 switch 语句的老土问题了。

![](http://www.itwanger.com/assets/images/2020/03/java-switch-02.png)

看到上图了吧，当不小心删掉 default 语句后，编译器就会报错，提示：“没有返回语句”，为了解决这个问题，我们可以新建一个 player 变量作为返回结果，就像下面这样：

```java
private static String createPlayer(PlayerTypes playerType) {
    String player = null;
    switch (playerType) {
        case TENNIS:
            player = "网球运动员费德勒";
            break;
        case FOOTBALL:
            player = "足球运动员C罗";
            break;
        case BASKETBALL:
            player = "篮球运动员詹姆斯";
            break;
        case UNKNOWN:
            throw new IllegalArgumentException("未知");
    }

    return player;
}
```

当添加了 player 变量后，case 语句中就需要添加上 break 关键字；另外在 switch 语句结束后，返回 player。这时候，编译器并不会提示任何错误，说明 default 语句在这种情况下是可以省略的。

从 JDK 12 开始（本例使用的是 JDK 13），switch 语句升级了，不仅可以像传统的 switch 语句那样作为条件的判断，还可以直接作为一个返回结果。来对小王的代码进行改造，如下所示：

```java
private static String createPlayer(PlayerTypes playerType) {
   return switch (playerType) {
        case TENNIS -> "网球运动员费德勒";
        case FOOTBALL -> "足球运动员C罗";
        case BASKETBALL -> "篮球运动员詹姆斯";
        case UNKNOWN ->  throw new IllegalArgumentException("未知");
    };
}
```

够 fashion 吧？不仅 switch 关键字之前加了 return 关键字，case 中还见到了 [Lambda 表达式](https://mp.weixin.qq.com/s/ozr0jYHIc12WSTmmd_vEjw)的影子，中划线和箭头替代了冒号，意味着箭头右侧的代码只管执行无须 break。

![](http://www.itwanger.com/assets/images/2020/03/java-switch-03.png)

并且，default 语句变成了可选项，可有可无，不信？你也动手试试。

新的 switch 语句足够的智能化，除了有上述的 3 个优势，还可以对枚举类型的条件进行校验。假如在 PlayerTypes 中增加了新的类型 PINGPANG（乒乓球）：

```java
public enum PlayerTypes {
    TENNIS,
    FOOTBALL,
    BASKETBALL,
    PINGPANG,
    UNKNOWN
}
```

此时编译器会发出以下警告：

![](http://www.itwanger.com/assets/images/2020/03/java-switch-04.png)

意思就是 switch 中的 case 条件没有完全覆盖枚举中可能存在的值。好吧，那就把 PINGPANG 的条件加上吧。来看一下完整的代码：

```java
public class OldSwitchDemo {
    public enum PlayerTypes {
        TENNIS,
        FOOTBALL,
        BASKETBALL,
        PINGPANG,
        UNKNOWN
    }

    public static void main(String[] args) {
        System.out.println(createPlayer(PlayerTypes.BASKETBALL));
    }

    private static String createPlayer(PlayerTypes playerType) {
        return switch (playerType) {
            case TENNIS -> "网球运动员费德勒";
            case FOOTBALL -> "足球运动员C罗";
            case BASKETBALL -> "篮球运动员詹姆斯";
            case PINGPANG -> "乒乓球运动员马龙";
            case UNKNOWN -> throw new IllegalArgumentException("未知");
        };
    }
}
```

switch 语句变成了强大的 switch 表达式，美滋滋啊！那假如一个运动员既会打篮球又会打乒乓球呢？

```java
private static String createPlayer(PlayerTypes playerType) {
    return switch (playerType) {
        case TENNIS -> "网球运动员费德勒";
        case FOOTBALL -> "足球运动员C罗";
        case BASKETBALL,PINGPANG -> "牛逼运动员沉默王二";
        case UNKNOWN -> throw new IllegalArgumentException("未知");
    };
}
```

就像上述代码那样，使用英文逗号“,”把条件分割开就行了，666 啊！

![](http://www.itwanger.com/assets/images/2020/03/java-switch-05.png)



不服气？switch 表达式还有更厉害的，`->` 右侧还可以是 `{}` 括起来的代码块，就像 Lambda 表达式那样。

```java
private static String createPlayer(PlayerTypes playerType) {
    return switch (playerType) {
        case TENNIS -> {
            System.out.println("网球");
            yield "网球运动员费德勒";
        }
        case FOOTBALL -> {
            System.out.println("足球");
            yield "足球运动员C罗";
        }
        case BASKETBALL -> {
            System.out.println("篮球");
            yield "篮球运动员詹姆斯";
        }
        case PINGPANG -> {
            System.out.println("乒乓球");
            yield "乒乓球运动员马龙";
        }
        case UNKNOWN -> throw new IllegalArgumentException("未知");
    };
}
```

细心的同学会发现一个之前素未谋面的关键字 `yield`，它和传统的 return、break 有什么区别呢？

先来看官方的解释：

>A yield statement transfers control by causing an enclosing switch expression to produce a specified value.

意思就是说 yield 语句通过使一个封闭的 switch 表达式产生一个指定值来转移控制权。为了进一步地了解 `yield` 关键字，我们可以反编译一下字节码：

```java
private static String createPlayer(NewSwitchDemo3.PlayerTypes playerType) {
    String var10000;
    switch(playerType) {
        case TENNIS:
            System.out.println("网球");
            var10000 = "网球运动员费德勒";
            break;
        case FOOTBALL:
            System.out.println("足球");
            var10000 = "足球运动员C罗";
            break;
        case BASKETBALL:
            System.out.println("篮球");
            var10000 = "篮球运动员詹姆斯";
            break;
        case PINGPANG:
            System.out.println("乒乓球");
            var10000 = "乒乓球运动员马龙";
            break;
        case UNKNOWN:
            throw new IllegalArgumentException("未知");
        default:
            throw new IncompatibleClassChangeError();
    }

    return var10000;
}
```

编译器在生成字节码的时候对 `yield` 关键字做了自动化转义，转成了传统的 break 语句。这下清楚了吧？

![](http://www.itwanger.com/assets/images/2020/03/java-switch-06.gif)

但是，话又说出来，那些看似 fashion 的代码也不过是把部分秀技的工作交给了编译器，还可能存在对旧版本不兼容、对队友不友好的问题——代码土点就土点呗，没准是最实用的。

>“不好意思，我为昨天早上的嚣张向你道歉。。。。。。”我向小王发送了一条信息。     

好了，我亲爱的读者朋友，以上就是本文的全部内容了，希望能够博得你的欢心。对了，我还有一个小小的请求：原创不易，如果觉得有点用的话，请不要吝啬你手中**点赞**的权力——因为这将是我写作的最强动力。


![](http://www.itwanger.com/assets/images/cmower_4.png)














