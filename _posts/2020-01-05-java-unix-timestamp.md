---
layout: post
category: java
title: 【小知识大用处】Java与Unix时间戳互转
tagline: by 沉默王二
tag: java
---

随着读者的数量越来越多，总不免被问到一些“面向搜索引擎”的问题，比如说：“Java 怎么与 Unix 时间戳互转啊？”期初我很受不了，问得多了我就习惯了，于是就打算把这些小知识点统一写成文章，到时候直接扔给读者。

<!--more-->



Unix 时间戳是指从1970 年 1 月 1 日（UTC/GMT 的午夜）开始所经过的秒数，不考虑闰秒。比如说 `1578179845`。

Java 中获取时间戳的大多数 API 返回的并不是 Unix 时间戳，而是从1970 年 1 月 1 日（UTC/GMT 的午夜）开始所经过的毫秒数。比如说 `1578179845000`。

```java
System.currentTimeMillis();
Calendar.getInstance().getTimeInMillis();
new Date().getTime();
```



将毫秒级转成秒级很简单，除以 1000 就搞定。

```java
long timeStamp = System.currentTimeMillis();
int timeStampUnix = (int) (timeStamp / 1000);
```

但是时间戳这样的数据对用户来说就好像是天文数字，因此需要一些加工处理，使其变成用户习惯的格式，Java 是怎么格式化这些时间戳呢？

```java
int timeStampUnix = 1578179845;
SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String result = simpleDateFormat.format(new Date(timeStampUnix * 1000L)); 
// 2020-01-05 07:17:25
```

使用 SimpleDateFormat 类，指定对应的格式，然后再将时间戳转成 Date，最后进行 format 格式化。

那怎么再转成时间戳呢？

```java
String str = "2020-01-05 07:17:25";
SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
int timeStampUnix = (int) (simpleDateFormat.parse(str).getTime() / 1000);
// 1578179845
```

不过，在实战项目中，我们通常借用第三方类库来实现转换，比如说格式化间戳可以使用 `org.apache.commons.lang3.time.DateFormatUtils`。

```java
DateFormatUtils.format(1578179845 * 1000L,"yyyy-MM-dd HH:mm:ss")
```

再转成时间戳可以使用 `org.apache.commons.lang3.time.DateUtils`。

```java
DateFormatUtils.format(1578179845 * 1000L,"yyyy-MM-dd HH:mm:ss")
```

![](http://www.itwanger.com/assets/images/2020/01/java-timestamp-unix-01.png)


好了，读者朋友们，以上就是本文的全部内容了，如果你还有其他的问题需要协助，可以微信搜索【沉默王二】关注我的公众号，回复“微信”即可拉取到我的个人微信，目前好友位已经不多了，早就是优势。

如果你觉得文章有点帮助，请点个赞再走。有句印度古谚是这样说的：“赠人玫瑰，手有余香。”