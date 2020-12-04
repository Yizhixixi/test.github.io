---
layout: post
category: java
title: 讲真，我欠GitHub的 contributor 一个瑞思拜
tagline: by 沉默王二
tags: 
  - java
---


之前分享了一篇《[GitHub上最励志的计算机自学教程](https://mp.weixin.qq.com/s/l-w5Zh37Q6Zb9Yl7WU54Mw)》到 CSDN 上，就有小伙伴留言说，“我是这个项目的贡献者”，简简单单的留言中，你就可以感受到那种强烈的自豪感。尤其是这个仓库已经收获了 140k+ 的 star，说到这，我这胃里面突然泛出了一股柠檬的味道。


<!--more-->


![](http://www.itwanger.com/assets/images/2020/12/github-contributor-01.png)


直白地说吧，一旦成为某个优质开源项目的贡献者，无论是吹牛逼还是写进简历，都是很划算的。

如果你是第一次接触 GitHub，不要担心，我已经帮你整理了一份攻略，点击下面的链接就可以入门了。

>[文科妹子都会用 GitHub，你这个工科生还等什么](https://mp.weixin.qq.com/s/IcbWYOZ_HXc9O8h0o62Wmg)

入门之后，就可以牵着我柔嫩的小手，一起成为优质开源项目的贡献者吧！我答应你，这将会很好玩 :)

### 01、fork 项目

我个人最喜欢 2 个开源项目，一个是好朋友江南一点雨的微人事，一个是好朋友 macrozheng 的电商项目。你也可以挑选你自己喜欢的，我这里就拿微人事来举例吧。

>[https://github.com/lenve/vhr](https://github.com/lenve/vhr)

点击上面的链接跳转到项目的主页，然后点击右上侧的 Fork 按钮。

![](http://www.itwanger.com/assets/images/2020/12/github-contributor-02.png)

该动作将会复制这个项目到你的个人账户下。

![](http://www.itwanger.com/assets/images/2020/12/github-contributor-03.png)

### 02、clone 项目

现在，按照下面的方式把你 fork 后的项目 clone 到本机。

PS：必须要 clone fork 后的项目而不是原项目，否则你没有改动的权限。

点击绿色的「Code」按钮，然后选择「Open with GitHub Desktop」。

![](http://www.itwanger.com/assets/images/2020/12/github-contributor-04.png)

在弹出窗口上选择「打开 GitHub Desktop.app」.

![](http://www.itwanger.com/assets/images/2020/12/github-contributor-05.png)

然后就会跳转到 GitHub 桌面版，点击「Clone」。

![](http://www.itwanger.com/assets/images/2020/12/github-contributor-06.png)

等待片刻，可能会比较慢，稍安勿躁，毕竟你懂。当出现下面这个提示（问你用这个项目干嘛，当然是成为项目的贡献者了，嘿嘿）的时候，点击「Continue」

![](http://www.itwanger.com/assets/images/2020/12/github-contributor-07.png)

然后，项目就顺顺利利地克隆到你的电脑上了。

### 03、创建一个分支

现在，来创建一个分支，点击「Current Branch」，然后在弹出式菜单上点击「New Branch」。

![](http://www.itwanger.com/assets/images/2020/12/github-contributor-08.png)

填写一个你喜欢的分支名后，点击「Create Branch」。

![](http://www.itwanger.com/assets/images/2020/12/github-contributor-09.png)

### 04、做一些必要的修改并提交

这里先说明一点，我提前已经和江南一点雨沟通过了，所以可以直接编辑他的  README.md。

![](http://www.itwanger.com/assets/images/2020/12/github-contributor-10.png)

小伙伴们如果想成为微人事的贡献者，可以尝试完成项目中一些未完成的功能，然后提交 pr，成为贡献者。

![](http://www.itwanger.com/assets/images/2020/12/github-contributor-11.png)

保存修改后的 README.md 之后，就可以在 GitHub 桌面版上看到修改后的内容了。

![](http://www.itwanger.com/assets/images/2020/12/github-contributor-12.png)

在摘要栏里填写信息后，点击「Commit to itwanger-add」。

![](http://www.itwanger.com/assets/images/2020/12/github-contributor-13.png)

可以 GitHub 桌面版的底部看到修改内容已经提交了。

![](http://www.itwanger.com/assets/images/2020/12/github-contributor-14.png)


### 05、发布分支并创建 PR

点击「Publish branch」发布分支。

![](http://www.itwanger.com/assets/images/2020/12/github-contributor-15.png)

发布完成后，可以看到按钮变成了「Create Pull Request」。

![](http://www.itwanger.com/assets/images/2020/12/github-contributor-16.png)

这里解释一下“Pull Request”（简称 PR），这里借用网友 beepony 的一段解释，我觉得非常清晰明了。

>想想我们中学时期的考试吧。你做的试卷就像是一个仓库，你的试卷肯定会有一些错误，就相当于程序中的 bug。老师把你的试卷拿过来，相当于先 fork。在你的卷子上做一些修改批注，相当于 commit。最后把改好的试卷给你，相当于 pull request，你拿到试卷重新改正错误，相当于 merge。

换个直白的说法就是：

>我改了江南一点雨（人称松哥）的代码，松哥你拉回去看看吧 ！！！

解释清楚 PR 的含义后，我们来点击「Create Pull Request」，它会打开一个网页，提示我进行登录。

![](http://www.itwanger.com/assets/images/2020/12/github-contributor-17.png)

登录完成后，就可以在网页端上看到我们要提交的 PR。

![](http://www.itwanger.com/assets/images/2020/12/github-contributor-18.png)

到了这一步，还等什么，抓紧时间提交吧，点击「Create Pull Request」按钮，会跳转到下一个页面。

![](http://www.itwanger.com/assets/images/2020/12/github-contributor-19.png)

为了省去等待的时间，我直接找了松哥。

![](http://www.itwanger.com/assets/images/2020/12/github-contributor-20.png)

刷新一下当前的 PR，就可以看到 PR 已经成功 merge 了。

![](http://www.itwanger.com/assets/images/2020/12/github-contributor-21.png)

回答项目主页，就可以看到我已经成为 contributor 了，好开心啊！

![](http://www.itwanger.com/assets/images/2020/12/github-contributor-22.png)

从此以后，我是不是也可以出去吹牛逼了，沉默王二，GitHub 上标星 19.2k 项目的贡献者。额，这种感觉真不错，有种黄袍加身的感觉，不不不，有种荣誉加身的感觉！

赶紧行动起来吧！

PS：最后贴一下 JavaBooks 的 GitHub 链接，希望能够对你有所帮助。

>[https://github.com/itwanger/JavaBooks](https://github.com/itwanger/JavaBooks)