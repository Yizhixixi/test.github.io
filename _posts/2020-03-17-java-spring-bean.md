---
layout: post
category: life
title: 学妹问的Spring Bean常用配置，我用最通俗易懂的讲解让她学会了
tagline: by 沉默王二
tags: 
  - java
---

你好呀，我是沉默王二，一枚有趣的程序员，写的文章一直充满灵气，力求清新脱俗。昨天跑去王府井的小米店订购了一台小米 10，说是一周之内能到货，但我还是忍不住今天就想见到她。见我茶不思饭不想的，老婆就劝我说，与其在瞎想，还不如滚去写你的文章。于是就有了今天这篇“Spring Bean 的常用配置”，通过我和三妹对话的形式。


<!--more-->




教妹学 Java，没见过这么放肆的标题吧？“语不惊人死不休”，没错，本篇文章的标题就是这么酷炫，不然你怎么会点进来？

![](http://www.itwanger.com/assets/images/2020/03/java-spring-bean-01.png)

我有一个漂亮如花的妹妹（见上图），她叫什么呢？我想聪明的读者能猜得出：沉默王三，没错，年方三六。父母正考虑让她向我学习，做一名正儿八经的 Java 程序员。我期初是反对的，因为程序员这行业容易掉头发。但家命难为啊，与其反对，不如做点更积极的事情，比如说写点有趣的文章教教她。

![](http://www.itwanger.com/assets/images/2020/03/java-spring-bean-02.gif)


“二哥，Spring 基础篇学完后，我有一种强烈的感觉，Spring 真的好强大，就如春风佛面一般，好像学下一篇。”

“哎呀，三妹，你这个比喻虽然有些牵强，但多少有些诗意。”

“好吧，让我们开始今天的学习吧！”

### 01、Bean 的 Scope 配置

“二哥，据说 Bean 的 Scope 类型有好几种，用于定义了 Bean 的生命周期和使用环境，你能给我具体说说吗？”

“没问题啊。”

1）singleton

也就是单例模式，如果把一个 Bean 的 Scope 定义为 singleton，意味着一个 Bean 在 Spring 容器中只会创建一次实例，对该实例的任何修改都会反映到它的引用上面。这也是 Scope 的默认配置项，可省略。

来新建一个 Writer 类，内容如下：

```java
public class Writer {
    private String name;

    public Writer() {
    }

    // getter setter
}
```

再来新建一个 SingletonConfig 类，内容如下：

```java
@Configuration
public class SingletonConfig {
    @Bean
    @Scope("singleton")
    public Writer getWriterSingleton() {
        return new Writer();
    }
}
```

`@Configuration` 注解表明当前类是一个配置类，相当于 Spring 配置的一个 xml 文件。

`@Bean` 注解用在 `getWriterSingleton()` 方法上，表明当前方法返回一个 Bean 对象（Writer），然后将其交给 Spring 管理。

可以使用 Spring 定义的常量来代替字符串 singleton：

```java
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
```

当然也可以完全省略，于是 SingletonConfig 瘦身了。

```java
@Configuration
public class SingletonConfig {
    @Bean
    public Writer getWriterSingleton() {
        return new Writer();
    }
}
```

新建 SingletonMain 类，代码如下：

```java
public class SingletonMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SingletonConfig.class);
        Writer writer1 = context.getBean(Writer.class);
        Writer writer2 = context.getBean(Writer.class);

        System.out.println(writer1);
        System.out.println(writer2);

        writer1.setName("沉默王二");
        System.out.println(writer2.getName());

        context.close();
    }
}
```

程序输出的结果如下所示：

```
commonuse.singleton.Writer@19dc67c2
commonuse.singleton.Writer@19dc67c2
沉默王二
```

writer1 和 writer2 两个对象的字符串表示形式完全一样，都是 `commonuse.singleton.Writer@19dc67c2`；另外，改变了 writer1 对象的 name，writer2 也跟着变了。

从结果中我们可以得出这样的结论：Scope 为 singleton 的时候，尽管使用 `getBean()` 获取了两次 Writer 实例，但它们是同一个对象。只要更改它们其中任意一个对象的状态，另外一个也会同时改变。


2）prototype

prototype 的英文词义是复数的意思，它表示一个 Bean 会在 Spring 中创建多次实例，适合用于多线程的场景。

新建一个 PrototypeConfig 类，内容如下：

```java
@Configuration
public class PrototypeConfig {
    @Bean
    @Scope("prototype")
    public Writer getWriterPrototype() {
        return new Writer();
    }
}
```

可以使用 Spring 定义的常量来代替字符串 prototype：

```java
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
```

新建 PrototypeMain 类，代码如下：

```java
public class PrototypeMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PrototypeConfig.class);
        Writer writer1 = context.getBean(Writer.class);
        Writer writer2 = context.getBean(Writer.class);

        System.out.println(writer1);
        System.out.println(writer2);

        writer1.setName("沉默王二");
        System.out.println(writer2.getName());

        context.close();
    }
}
```

程序输出的结果如下所示：

```
commonuse.Writer@78a2da20
commonuse.Writer@dd3b207
null
```

writer1 和 writer2 两个对象的字符串表示形式完全不一样，一个是 `commonuse.Writer@78a2da20`，另一个是 `commonuse.Writer@dd3b207`；另外，虽然 writer1 对象的 name 被改变为“沉默王二”，但 writer2 的 name 仍然为 null。

从结果中我们可以得出这样的结论：Scope 为 prototype 的时候，每次调用 `getBean()` 都会返回一个新的实例，它们不是同一个对象。更改它们其中任意一个对象的状态，另外一个并不会同时改变。

3）request、session、application、websocket

这 4 个作用域仅在 Web 应用程序的上下文中可用，在实践中并不常用。request 用于为 HTTP 请求创建 Bean 实例，session 用于为 HTTP 会话创建 Bean 实例， application 用于为 ServletContext 创建 Bean 实例，而 websocket 用于为特定的 WebSocket 会话创建 Bean 实例。


### 02、Bean 的字段注入

“二哥，据说 Spring 开发中经常涉及调用各种配置文件，需要用到 `@Value` 注解，你能给我详细说说吗？”

“没问题啊。”

1）注入普通字符串

来新建一个 ValueConfig 类，内容如下：

```java
@Configuration
public class ValueConfig {
    @Value("沉默王二")
    private String name;

    public void output() {
        System.out.println(name);
    }
}
```

`@Value` 注解用在成员变量 name 上，表明当前注入 name 的值为“沉默王二”。

来新建一个 ValueMain 类，内容如下：

```java
public class ValueMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpELStringConfig.class);

        SpELStringConfig service = context.getBean(SpELStringConfig.class);
        service.output();

        context.close();
    }
}
```

程序输出结果如下：

```
沉默王二
```

结果符合我们的预期。

2）注入 Spring 表达式

使用 `@Value` 注入普通字符串的方式最为简单，我们来升级一下，注入 Spring 表达式，先来个加法运算吧。

```java
@Value("#{18 + 12}") // 30
private int add;
```

双引号中需要用到 `#{}`。再来个关系运算和逻辑运算吧。

```java
@Value("#{1 == 1}") // true
private boolean equal;

@Value("#{400 > 300 || 150 < 100}") // true
private boolean or;
```

觉得还不够刺激，再来个三元运算吧。

```java
@Value("#{2 > 1 ? '沉默是金' : '不再沉默'}") // "沉默是金"
private String ternary;
```

3）注入配置文件

假如你觉得以上这些都不够有意思，那来注入配置文件吧。

在 resources 目录下新建 value.properties 文件，内容如下：

```
name=沉默王二
age=18
```

新建一个 ValuePropertiesConfig 类，内容如下：

```java
@Configuration
@PropertySource("classpath:value.properties")
public class ValuePropertiesConfig {

    @Value("${name}")
    private String name;

    @Value("${age}")
    private int age;

    public void output() {
        System.out.println("姓名：" + name + " 年纪：" + age);
    }
}
```

`@PropertySource` 注解用于指定载入哪个配置文件（value.properties），`classpath:` 表明从 src 或者 resources 目录下找。

注意此时 `@Value("")` 的双引号中为 $ 符号而非 # 符号，`{}` 中为配置文件中的 key。


新建一个 ValuePropertiesMain 类，内容如下：

```java
public class ValuePropertiesMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ValuePropertiesConfig.class);

        ValuePropertiesConfig service = context.getBean(ValuePropertiesConfig.class);
        service.output();

        context.close();
    }
}
```

程序运行结果如下：

```
姓名：³ÁÄ¬ÍõÈý 年纪：18
```

“糟糕，二哥！中文乱码了！”

“不要怕，三妹，问题很容易解决。”

首先，查看 properties 文件的编码方式。

![](http://www.itwanger.com/assets/images/2020/03/java-spring-bean-03.png)

如果不是 UTF-8 就改为 UTF-8。同时，确保修改编码方式后的 properties 文件中没有中文乱码。

然后，在 `@PropertySource` 注解中加入编码格式。

```java
@PropertySource(value = "classpath:value.properties",  encoding = "UTF-8")
```

再次运行程序后，乱码就被风吹走了。

```
姓名：沉默王二 年纪：18
```


### 03、Bean 的初始化和销毁

“二哥，据说在实际开发中，经常需要在 Bean 初始化和销毁时加一些额外的操作，你能给我详细说说怎么实现吗？”

“没问题啊。”

1）init-method/destroy-method

新建一个 InitDestroyService 类，内容如下：

```java
public class InitDestroyService {
    public InitDestroyService() {
        System.out.println("构造方法");
    }

    public void init() {
        System.out.println("初始化");
    }

    public void destroy {
        System.out.println("销毁");
    }
}
```

`InitDestroyService()` 为构造方法，`init()` 为初始化方法，`destroy()` 为销毁方法。

新建 InitDestroyConfig 类，内容如下：

```java
@Configuration
public class InitDestroyConfig {
    @Bean(initMethod = "init",destroyMethod = "destroy")
    public InitDestroyService initDestroyService() {
        return new InitDestroyService();
    }
}
```

`@Bean` 注解的 `initMethod` 用于指定 Bean 初始化的方法，`destroyMethod` 用于指定 Bean 销毁时的方法。

新建 InitDestroyMain 类，内容如下：

```java
public class InitDestroyMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(InitDestroyConfig.class);
        InitDestroyService service = context.getBean(InitDestroyService.class);
        System.out.println("准备关闭容器");
        context.close();
    }
}
```

程序运行结果如下：

```java
构造方法
初始化
准备关闭容器
销毁
```

也就是说，初始化方法在构造方法后执行，销毁方法在容器关闭后执行。

![](http://www.itwanger.com/assets/images/2020/03/java-spring-bean-05.png)

2）@PostConstruct/@PreDestroy

新建一个 InitDestroyService 类，内容如下：

```java
public class InitDestroyService {
    public InitDestroyService() {
        System.out.println("构造方法");
    }

    @PostConstruct
    public void init() {
        System.out.println("初始化");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("销毁");
    }
}
```

`@PostConstruct` 注解的作用和 `@Bean` 注解中 init-method 作用相同，用于指定 Bean 初始化后执行的方法。

`@PreDestroy` 注解的作用和 `@Bean` 注解中 destroyMethod 作用相同，用于指定 Bean 被容器销毁后执行的方法。

新建 InitDestroyConfig 类，内容如下：

```java
@Configuration
public class InitDestroyConfig {
    @Bean
    public InitDestroyService initDestroyService() {
        return new InitDestroyService();
    }
}
```

`@Bean` 注解中不需要再指定  init-method 和 destroyMethod 参数了。

新建 InitDestroyMain 类，内容如下：

```java
public class InitDestroyMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(InitDestroyConfig.class);
        InitDestroyService service = context.getBean(InitDestroyService.class);
        System.out.println("准备关闭容器");
        context.close();
    }
}
```

程序运行结果如下：

```
构造方法
初始化
准备关闭容器
销毁
```

结果符合我们的预期。

### 04、为 Bean 配置不同的环境

“二哥，据说 Spring 开发中经常需要将 Bean 切换到不同的环境，比如说开发环境、测试环境、正式生产环境，你能给我具体说说怎么实现的吗？”

“没问题啊。”

来考虑这样一个常见的场景，我们需要为开发环境和正式生产环境配置不同的数据源。

新建 Datasource 类，内容如下：

```java
public class Datasource {
    private String dburl;

    public Datasource(String dburl) {
        this.dburl = dburl;
    }

    // getter/setter
}
```

dbname 用于指定不同环境下数据库的连接地址。

新建 Config 类，内容如下：

```java
@Configuration
public class Config {
    @Bean
    @Profile("dev")
    public Datasource devDatasource() {
        return new Datasource("开发环境");
    }

    @Bean
    @Profile("prod")
    public Datasource prodDatasource() {
        return new Datasource("正式生产环境");
    }
}
```

`@Profile` 注解用于标识不同环境下要实例化的 Bean。

新建 Main 类，内容如下：

```java
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        ConfigurableEnvironment environment = context.getEnvironment();
        environment.setActiveProfiles("prod");
        context.register(Config.class);
        context.refresh();

        Datasource datasource = context.getBean(Datasource.class);
        System.out.println(datasource.getDburl());
        context.close();
    }
}
```

新建 AnnotationConfigApplicationContext 对象的时候不要指定配置类，等到调用 `setActiveProfiles("prod")` 方法将环境设置为正式生产环境后再通过 `register(Config.class)` 方法将配置类注册到容器当中，同时记得刷新容器。

运行程序，输出以下内容：

```
正式生产环境
```

然后将 "prod" 更改为 "dev"，再次运行程序，输出以下内容：

```
开发环境
```

![](http://www.itwanger.com/assets/images/2020/03/java-spring-bean-05.gif)

“二哥，这篇文章中的示例代码你上传到码云了吗？最近 GitHub 访问起来有点卡。”

“你到挺贴心啊，三妹。[码云传送门~](https://gitee.com/qing_gee/JavaPoint/tree/master)”

“二哥，你教得真不错，我完全学会了，一点也不枯燥。”

“那必须得啊，期待下一篇吧？”

“那是当然啊，期待，非常期待，望穿秋水的感觉。”




请允许我热情地吐槽一下，这篇文章我不希望再被喷了，看在我这么辛苦搞原创（创意+干货+有趣）的份上，多鼓励鼓励好不好？**别瞅了，点赞呗，你最美你最帅**。

>如果觉得文章对你有点帮助，请微信搜索「 **沉默王二** 」第一时间阅读，回复【**666**】【**1024**】更有我为你精心准备的 500G 高清教学视频（已分门别类），以及大厂技术牛人整理的面经一份。