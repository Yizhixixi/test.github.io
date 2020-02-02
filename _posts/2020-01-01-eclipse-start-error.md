---
layout: post
category: java
title: Eclipse启动报错：org.eclipse.e4.core.di.InjectionException
tagline: by 沉默王二
tag: java
---

启动 Eclipse 的时候，出现了下面这个错误 `org.eclipse.e4.core.di.InjectionException: java.lang.NoClassDefFoundError: javax/annotation/PostConstruct`

<!--more-->


![](http://www.itwanger.com/assets/images/2020/01/eclipse-start-error-01.png)

看了一下日志，错误信息大致是

```
!ENTRY org.eclipse.core.resources 2 10035 2020-01-01 10:06:57.045
!MESSAGE The workspace will exit with unsaved changes in this session.
!SESSION 2020-01-01 10:06:58.748 -----------------------------------------------
eclipse.buildId=4.7.3.M20180330-0640
java.version=13.0.1
java.vendor=Oracle Corporation
BootLoader constants: OS=macosx, ARCH=x86_64, WS=cocoa, NL=zh_CN_#Hans
Framework arguments:  -product org.eclipse.epp.package.jee.product -keyring /Users/maweiqing/.eclipse_keyring
Command-line arguments:  -os macosx -ws cocoa -arch x86_64 -product org.eclipse.epp.package.jee.product -keyring /Users/maweiqing/.eclipse_keyring

!ENTRY org.eclipse.core.resources 2 10035 2020-01-01 10:07:06.932
!MESSAGE The workspace exited with unsaved changes in the previous session; refreshing workspace to recover changes.

!ENTRY org.eclipse.osgi 4 0 2020-01-01 10:07:07.087
!MESSAGE Application error
!STACK 1
org.eclipse.e4.core.di.InjectionException: java.lang.NoClassDefFoundError: javax/annotation/PostConstruct
	at org.eclipse.e4.core.internal.di.InjectorImpl.internalMake(InjectorImpl.java:410)
	at org.eclipse.e4.core.internal.di.InjectorImpl.make(InjectorImpl.java:318)
	at org.eclipse.e4.core.contexts.ContextInjectionFactory.make(ContextInjectionFactory.java:162)
	at org.eclipse.e4.ui.internal.workbench.swt.E4Application.createDefaultHeadlessContext(E4Application.java:491)
	at org.eclipse.e4.ui.internal.workbench.swt.E4Application.createDefaultContext(E4Application.java:505)
	at org.eclipse.e4.ui.internal.workbench.swt.E4Application.createE4Workbench(E4Application.java:204)
```

注意到里面的关键信息 `java.version=13.0.1`，也就是说本地环境的 JDK 升级了，因为这个导致了一些错误。

再打开 Eclipse 的 .ini 文件看了一下（找到 Eclipse 的启动图标，在访达中显示，右键显示包内容）。

![](http://www.itwanger.com/assets/images/2020/01/eclipse-start-error-02.png)

看到了 JDK 1.8 的信息。

![](http://www.itwanger.com/assets/images/2020/01/eclipse-start-error-03.png)

再打开终端，输入 `java -version`

![](http://www.itwanger.com/assets/images/2020/01/eclipse-start-error-04.png)

原来真的是因为 JDK 的版本冲突造成的，怎么办呢？

查看下电脑上有安装了哪些 JDK

```
/usr/libexec/java_home -V
```

![](http://www.itwanger.com/assets/images/2020/01/eclipse-start-error-05.png)

好多版本哦，那就为 Eclipse 指定 1.8 的版本吧。

```
-vm
/Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/bin
```

位置呢？

![](http://www.itwanger.com/assets/images/2020/01/eclipse-start-error-06.png)

保存 eclipse.ini 文件后，再启动 Eclipse，就大功告成了。


