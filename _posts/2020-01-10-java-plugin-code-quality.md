---
layout: post
category: java
title: 讲真，这两个IDE插件，可以让你写出质量杠杠的代码
tagline: by 沉默王二
tag: java
---

昨晚躺在床上看《拯救大兵瑞恩》的时候，不由得感叹道：“斯皮尔伯格的电影质量真高，片头真实地还原了二战的残酷性。”看完后，我的精神异常的亢奋，就想写篇文章来帮助大家提高一下代码的质量，毕竟二哥也是一个有态度的作者啊，向斯皮尔伯格学习。

<!--more-->



![](http://www.itwanger.com/assets/images/2020/01/java-plugin-code-quality-01.png)





代码质量的重要性就不用我来赘述了，大家都懂。没有人喜欢糟糕的代码，就像没有人喜欢烂片一样。这里推荐两个优秀的 IDE 插件给大家，保管能提高你的代码质量，我亲身实操过的，确实很不错。

### 01、Alibaba Java 代码规范插件

阿里巴巴的《Java 开发手册》，相信大家都不会感到陌生，其配套的代码规范插件的下载次数据说达到了 80 万次。好吧，我今天又贡献了一次。😁。

插件的 GitHub 地址如下所示：

[https://github.com/alibaba/p3c](https://github.com/alibaba/p3c)

该项目此刻的 star 数达到了 19.9K，很不错的成绩哦。

插件（有 IDEA 版本和 Eclipse 版）的安装教程地址如下所示：

[https://github.com/alibaba/p3c/wiki](https://github.com/alibaba/p3c/wiki)

IDEA 的安装方法更便捷一点，直接在【Plugins】面板中搜索关键字“alibaba”，第一个选项就是该插件，直接点击【Install】就可以了。见下图。

![](http://www.itwanger.com/assets/images/2020/01/java-plugin-code-quality-02.png)

安装成功后，我扫描了一下当前项目【右键菜单→编码规约扫描】，发现了下图中这些建议。

![](http://www.itwanger.com/assets/images/2020/01/java-plugin-code-quality-03.png)

此时此刻，我只能套用周杰伦老师那句：“哎呀，不错哦”。

PS：项目代码已经同步到 GitHub，地址为 [itwanger.JavaPoint](https://github.com/qinggee/JavaPoint)，欢迎大家 star 和 issue。


### 02、SonarLint 插件

SonarLint 插件的官方地址如下所示：

[https://www.sonarlint.org/](https://www.sonarlint.org/)

来看一下该插件的自我介绍：

![](http://www.itwanger.com/assets/images/2020/01/java-plugin-code-quality-04.png)

用我蹩脚的英语翻译一下：

>SonarLint 是一个 IDE 插件，可以帮助你在编写代码时检测到问题，并进行修复。就像拼写检查器一样实时，SonarLint 可以方便你在提交代码之前就对其进行修复。

SonarLint 插件也有各种版本，不仅支持 Eclipse 和 IDEA，还支持 Visual Studio 和 VS Code。IDEA 的安装方法也很简单，直接在【Plugins】面板中搜索关键字“SonarLint”，直接点击【Install】就可以了。见下图。

![](http://www.itwanger.com/assets/images/2020/01/java-plugin-code-quality-05.png)

安装成功后，我扫描了一下当前项目【右键菜单→SonarLint → Analysis With SonarLint 】，发现了下面这些建议。

![](http://www.itwanger.com/assets/images/2020/01/java-plugin-code-quality-06.png)

PPS：项目代码已经同步到 GitHub，地址为 [itwanger.JavaPoint](https://github.com/qinggee/JavaPoint)，欢迎大家 star 和 issue。

### 03、对比 Alibaba 代码规范插件和 SonarLint 插件

这两款插件各有不同，首先最大的不同就是 Alibaba 代码规范插件是中文版的，SonarLint 是英文版的，😄。

![](http://www.itwanger.com/assets/images/2020/01/java-plugin-code-quality-07.png)

其次呢，对比两款插件扫描的截图就可以发现，Alibaba 代码规范插件是按照问题的等级归类的；而 SonarLint 插件是按照不同的类归类的。

能不能说重点？好，好，好，客官别着急嘛。

- Alibaba 代码规范插件比较关心的是编码风格上的规范，比如说 long 变量的赋值、条件语句后的大括号、重写的时候有没有使用 `@Override` 注解等。

- SonarLint 插件比较关心的是代码的正确性，比如说尽量不要重写 clone 方法、使用日志系统代替 `System.out`、重写 clone 方法的时候不要返回 null 等。

总之呢，两者之间没有冲突，建议配合使用，Alibaba 代码规范插件用来规范代码，SonarLint 插件用来发现代码隐藏的问题。这样的话，就能够在代码编写阶段规避风险，消灭隐患，提高程序的健壮性。

### 04、鸣谢

好了，各位读者朋友们，以上就是本文的全部内容了。**能看到这里的都是最优秀的程序员，升职加薪就是你了**👍。如果觉得不过瘾，还想看到更多，我再推荐几篇给大家。

[惊呆了！Java程序员最常犯的错竟然是这10个](https://mp.weixin.qq.com/s/i1RZRO4mNCJ32Y_EX6QM2g)

[2019年，我在全网最受欢迎的10篇文章，阅读量超60万](https://mp.weixin.qq.com/s/J8rnSdji3Mkrml98soFBFw)

[面试官刁难：Java字符串可以引用传递吗？](https://mp.weixin.qq.com/s/Iq6C56_H6CThR5w6v_sQ7Q)

**原创不易，如果觉得有点用的话，请不要吝啬你手中点赞的权力**；如果想要第一时间看到二哥更新的文章，请扫描下方的二维码，关注沉默王二公众号。我们下篇文章见！

![](http://www.itwanger.com/assets/images/cmower_04.png)