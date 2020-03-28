---
layout: post
category: life
title: 学妹让我给她解释几个Spring的专业名词，我不太会...
tagline: by 沉默王二
tags: 
  - java
---

你好呀，我是沉默王二，一个和黄家驹一样身高，刘德华一样颜值的程序员（不信围观朋友圈呗）。从 2 位偶像的年纪上，你就可以断定我的码龄至少在 10 年以上，但实话实说，我一直坚信自己只有 18 岁，因为好学使我年轻。本篇文章就打算通过我和三妹对话的形式来聊一聊“Spring 的 Aware、异步编程、计划任务”。


<!--more-->

教妹学 Spring，没见过这么放肆的标题吧？“语不惊人死不休”，没错，本篇文章的标题就是这么酷炫，不然你怎么会点进来？


![](http://www.itwanger.com/assets/images/2020/03/java-spring03-01.png)


我有一个漂亮如花的妹妹（见上图，怎么又变了？还不能一天做个梦），她叫什么呢？我想聪明的读者能猜得出：沉默王三，没错，年方三六。父母正考虑让她向我学习，做一名正儿八经的 Java 程序员。我一开始是反对的，因为程序员这行业容易掉头发，女生可不适合掉头发。但家命难为啊，与其反对，不如做点更积极的事情，比如说写点有趣的文章教教她。


![](http://www.itwanger.com/assets/images/2020/03/java-spring03-02.png)

“二哥，听说今天要学习 Spring 的 Aware、异步编程、计划任务，真的是翘首以盼啊。”

“哎呀，三妹，瞧你那迫不及待的大眼神，就好像昨晚上月亮一样圆，一样大。”


### 01、Spring Aware

“二哥，据说 Aware 的目的是让 Bean 获取 Spring 容器的服务，你能给我具体说说吗？”

“没问题啊。”

Bean 一般不需要了解容器的状态和直接使用容器，但是在某些情况下，需要在 Bean 中直接对容器进行操作，这时候，就可以通过特定的 Aware 接口来完成。常见的 Spring Aware 接口有下面这些：

| Aware 子接口 | 描述 |
| --- | --- |
| BeanNameAware | 获取容器中 Bean 的名称 |
| BeanFactoryAware | Bean 被容器创建以后，会有一个相应的 BeanFactory，可以直接通过它来访问容器 |
| ApplicationContextAware | Bean 被初始化后，会被注入到 ApplicationContext，可以直接通过它来访问容器|
| MessageSourceAware | 获取 Message Source 的相关文本信息 |
| ResourceLoaderAware | 获取资源加载器，以获取外部资源文件 |


1）BeanNameAware

新建一个 MyBeanName 类，内容如下：

```java
public class MyBeanName implements BeanNameAware {
    @Override
    public void setBeanName(String beanName) {
        System.out.println(beanName);
    }
}
```

MyBeanName 实现了 BeanNameAware 接口，并重写了 `setBeanName()` 方法。beanName 参数表示 Bean 在 Spring 容器中注册的 name。

新建一个 Config 类，内容如下：

```java
@Configuration
public class Config {
    @Bean(name = "myCustomBeanName")
    public MyBeanName getMyBeanName() {
        return new MyBeanName();
    }
}
```

`@Bean` 注解用在 `getMyBeanName()` 方法上，表明当前方法返回一个 Bean 对象（MyBeanName），并通过 name 属性指定 Bean 的名字为“myCustomBeanName”。

新建 BeanNameMain 类，代码如下：

```java
public class BeanNameMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanNameConfig.class);
        MyBeanName myBeanName = context.getBean(MyBeanName.class);
        context.close();
    }
}
```

程序输出的结果如下所示：

```
myCustomBeanName
```

如果把 `@Bean()` 注解中的 `(name = "myCustomBeanName)"` 去掉的话，程序输出的内容将会是 BeanNameConfig 类的 `getMyBeanName()` 的方法名“getMyBeanName”。

2）BeanFactoryAware

新建一个 MyBeanFactory 类，内容如下：

```java
public class MyBeanFactory implements BeanFactoryAware {
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void getMyBeanName() {
        MyBeanName myBeanName = beanFactory.getBean(MyBeanName.class);
        System.out.println(beanFactory.isSingleton("myCustomBeanName"));
        System.out.println(beanFactory.isSingleton("getMyBeanFactory"));
    }
}
```

借助 `setBeanFactory()` 方法，可以将容器中的 BeanFactory 赋值给 MyBeanFactory 类的成员变量 beanFactory，这样就可以在 `getMyBeanName()` 方法中使用 BeanFactory 了。

通过 `getBean()` 方法可以获取 Bean 的实例；通过 `isSingleton()` 方法判断 Bean 是否为一个单例。

在 Config 类中追加 MyBeanFactory 的 Bean：

```java
@Configuration
public class Config {
    @Bean(name = "myCustomBeanName")
    public MyBeanName getMyBeanName() {
        return new MyBeanName();
    }

    @Bean
    public MyBeanFactory getMyBeanFactory() {
        return new MyBeanFactory();
    }
}
```

新建 BeanFactoryMain 类，代码如下：

```java
public class BeanFactoryMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        MyBeanFactory myBeanFactory = context.getBean(MyBeanFactory.class);
        myBeanFactory.getMyBeanName();
        context.close();
    }
}
```

初始化 MyBeanFactory 后就可以调用 `getMyBeanName()` 方法了，程序输出的结果如下所示：

```
myCustomBeanName
true
true
```

结果符合我们的预期：MyBeanName 的名字为“myCustomBeanName”，MyBeanName 和 MyBeanFactory 的 scope 都是 singleton。

3）其他几个 Aware 接口就不再举例说明了。通常情况下，不要实现 Aware 接口，因为它会使 Bean 和 Spring 框架耦合。


### 02、异步编程

“二哥，据说 Spring 可以通过 @Async 来实现异步编程，你能给我详细说说吗？”

“没问题啊。”

新建一个 AsyncService 类，内容如下：

```java
public class AsyncService {
    @Async
    public void execute() {
        System.out.println(Thread.currentThread().getName());
    }
}
```

`@Async` 注解用在 public 方法上，表明 `execute()` 方法是一个异步方法。

新建一个 AsyncConfig 类，内容如下：

```java
@Configuration
@EnableAsync
public class AsyncConfig {
    @Bean
    public AsyncService getAsyncService() {
        return new AsyncService();
    }
}
```

在配置类上使用 `@EnableAsync` 注解用以开启异步编程，否则 `@Async` 注解不会起作用。

新建一个 AsyncMain 类，内容如下：

```java
public class AsyncMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AsyncConfig.class);
        AsyncService service = context.getBean(AsyncService.class);
        for (int i = 0; i < 10; i++) {
            service.execute();
        }
    }
```

程序输出结果如下：

```
SimpleAsyncTaskExecutor-1
SimpleAsyncTaskExecutor-9
SimpleAsyncTaskExecutor-7
SimpleAsyncTaskExecutor-8
SimpleAsyncTaskExecutor-10
SimpleAsyncTaskExecutor-3
SimpleAsyncTaskExecutor-2
SimpleAsyncTaskExecutor-4
SimpleAsyncTaskExecutor-6
SimpleAsyncTaskExecutor-5
```

OK，结果符合我们的预期，异步编程实现了。就像你看到的那样，Spring 提供了一个默认的 SimpleAsyncTaskExecutor 用来执行线程，我们也可以在方法级别和应用级别上对执行器进行配置。

1）方法级别

新建 AsyncConfig 类，内容如下：

```java
@Configuration
@EnableAsync
public class AsyncConfig {
    @Bean
    public AsyncService getAsyncService() {
        return new AsyncService();
    }

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        return executor;
    }
}
```

在配置类中创建了一个返回类型为 Executor 的 Bean，其名称定义为“threadPoolTaskExecutor”，并且重新设置了 ThreadPoolTaskExecutor 的核心线程池大小，默认为 1，现在修改为 5。

新进 AsyncService 类，内容如下：

```java
public class AsyncService {
    @Async("threadPoolTaskExecutor")
    public void execute() {
        System.out.println(Thread.currentThread().getName());
    }
}
```

`@Async` 注解上需要指定我们之前配置的线程池执行器“threadPoolTaskExecutor”。

新建 AsyncMain 类，内容如下：

```java
public class AsyncMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AsyncConfig.class);
        AsyncService service = context.getBean(AsyncService.class);
        for (int i = 0; i < 10; i++) {
            service.execute();
        }
    }
}
```

程序运行结果如下：

```
threadPoolTaskExecutor-1
threadPoolTaskExecutor-2
threadPoolTaskExecutor-4
threadPoolTaskExecutor-3
threadPoolTaskExecutor-5
threadPoolTaskExecutor-3
threadPoolTaskExecutor-2
threadPoolTaskExecutor-4
threadPoolTaskExecutor-1
threadPoolTaskExecutor-5
```

从结果中可以看得出，线程池执行器变成了“threadPoolTaskExecutor”，并且大小为 5。

2）应用级别

新建 AsyncConfig 类，内容如下：

```java
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
    @Bean
    public AsyncService getAsyncService() {
        return new AsyncService();
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.initialize();
        return executor;
    }
}
```

需要实现 AsyncConfigurer 接口，并重写 `getAsyncExecutor()` 方法，这次设置线程池的大小为 3。注意执行器要执行一次 `initialize()` 方法。

新进 AsyncService 类，内容如下：

```java
public class AsyncService {
    @Async
    public void execute() {
        System.out.println(Thread.currentThread().getName());
    }
}
```

新建 AsyncMain 类，内容如下：

```java
public class AsyncMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AsyncConfig.class);
        AsyncService service = context.getBean(AsyncService.class);
        for (int i = 0; i < 10; i++) {
            service.execute();
        }
    }
}
```

程序运行结果如下：

```
ThreadPoolTaskExecutor-2
ThreadPoolTaskExecutor-2
ThreadPoolTaskExecutor-2
ThreadPoolTaskExecutor-2
ThreadPoolTaskExecutor-2
ThreadPoolTaskExecutor-2
ThreadPoolTaskExecutor-2
ThreadPoolTaskExecutor-2
ThreadPoolTaskExecutor-1
ThreadPoolTaskExecutor-3
```

从结果中可以看得出，线程池执行器变成了“ThreadPoolTaskExecutor”，并且大小为 3。



### 03、计划任务

“二哥，据说 Spring 可以通过 @Scheduled 来实现计划任务，你能给我详细说说怎么实现吗？”

“没问题啊。”

新建一个 ScheduledService 类，内容如下：

```java
@Service
public class ScheduledService {
    @Scheduled(fixedDelay = 1000)
    public void scheduleFixedDelayTask() {
        System.out.println(
                "固定时间段后执行任务 - " + System.currentTimeMillis() / 1000);
    }

    @Scheduled(fixedRate = 1000)
    public void scheduleFixedRateTask() {
        System.out.println(
                "固定的频率执行任务 - " + System.currentTimeMillis() / 1000);
    }

    @Scheduled(cron = "0/2 * * * * ?")
    public void scheduleTaskUsingCronExpression() {
        long now = System.currentTimeMillis() / 1000;
        System.out.println(
                "Cron 表达式执行任务 - " + now);
    }
}
```

`@Service` 注解用于指定 ScheduledService 类为一个业务层的 Bean。`@Scheduled` 注解用于指定当前方法（返回类型为 void，无参）为一个任务执行方法，常见的用法有以下 3 种：

1）fixedDelay 用于确保任务执行的完成时间与任务下一次执行的开始时间之间存在 n 毫秒的延迟，下一次任务执行前，上一次任务必须执行完。

2）fixedRate 用于确保每 n 毫秒执行一次计划任务，即使最后一次调用可能仍在运行。

3）Cron 表达式比 fixedDelay 和 fixedRate 都要灵活，由 7 个部分组成，各部分之间用空格隔开，其完整的格式如下所示：

```
Seconds Minutes Hours Day-of-Month Month Day-of-Week Year
```

单词都很简单，就不用我翻译了。其中 Year 是可选项。常见的范例如下所示：

```
*/5 * * * * ?  每隔 5 秒执行一次
0 */1 * * * ?  每隔 1 分钟执行一次
0 0 23 * * ?  每天 23 点执行一次
0 0 1 * * ?  每天凌晨 1 点执行一次：
0 0 1 1 * ?  每月 1 号凌晨 1 点执行一次
0 0 23 L * ?  每月最后一天 23 点执行一次
0 0 1 ? * L  每周星期天凌晨 1 点执行一次
0 26,29,33 * * * ?  在 26 分、29 分、33 分执行一次
0 0 0,13,18,21 * * ? 每天的 0 点、13 点、18 点、21 点各执行一次
```

新建 ScheduledConfig 类，内容如下：

```java
@Configuration
@EnableScheduling
@ComponentScan("high.scheduled")
public class ScheduledConfig {
}
```

`@EnableScheduling` 注解用于开启计划任务。`@ComponentScan` 注解用于扫描当前包下的类，如果它使用了注解（比如 `@Service`），就将其注册成为一个 Bean。

新建 ScheduledMain 类，内容如下：

```java
public class ScheduledMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScheduledConfig.class);
    }
}
```

程序运行结果如下：

```java
固定的频率执行任务 - 1584666273
固定时间段后执行任务 - 1584666273
Cron 表达式执行任务 - 1584666274
固定的频率执行任务 - 1584666274
固定时间段后执行任务 - 1584666274
固定的频率执行任务 - 1584666275
固定时间段后执行任务 - 1584666275
Cron 表达式执行任务 - 1584666276
```

从结果中可以看得出，如果任务之间没有冲突的话，fixedDelay 任务之间的间隔和 fixedRate 任务之间的间隔是相同的，都是 1 秒；Cron 表达式任务与上一次任务之间的间隔为 2 秒。



“二哥，这篇文章中的示例代码你上传到 GitHub 了吗？”

“你到挺贴心啊，三妹。[传送门~](https://github.com/qinggee/SpringDemo)”

“二哥，你教得真不错，我完全学会了，一点也不枯燥。”

“那必须得啊，期待下一篇吧？”

“那是当然啊，期待，非常期待，望眼欲穿的感觉。”

![](http://www.itwanger.com/assets/images/2020/03/java-spring03-03.gif)


请允许我热情地吐槽一下，这篇文章我不希望再被喷了，看在我这么辛苦搞原创（创意+干货+有趣）的份上，多鼓励鼓励好不好？**别瞅了，点赞呗，你最美你最帅**。

>如果觉得文章对你有点帮助，请微信搜索「 **沉默王二** 」第一时间阅读，回复【**666**】【**1024**】更有我为你精心准备的 500G 高清教学视频（已分门别类），以及大厂技术牛人整理的面经一份。


