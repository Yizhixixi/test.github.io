---
layout: post
category: java
title: 除了闹过腥风血雨的fastjson，你还知道哪些Java解析JSON的利器？
tagline: by 沉默王二
tag: java
---

昨天下午 5 点 10 分左右，我解决掉了最后一个 bug，轻舒一口气，准备关机下班。可这个时候，老板朝我走来，脸上挂着神秘的微笑，我就知道他不怀好意。果不其然，他扔给了我一个新的需求，要我在 Java 中解析 JSON，并且要在半个小时候给出最佳的解决方案。

<!--more-->



![](http://www.itwanger.com/assets/images/2020/01/java-json-parse-01.png)


无奈，提前下班的希望破灭了。不过，按时下班的希望还是有的。于是我撸起袖子开始了研究，结果出乎我的意料，竟然不到 10 分钟就找出了最佳方案。但我假装还没有搞出来，趁着下班前的这段时间把方案整理成了现在你们看到的这篇文章。

### 01、JSON 是什么

**JSON**（JavaScript Object Notation）是一种轻量级的数据交换格式，易于阅读和编写，机器解析和生成起来更是轻而易举。JSON 采用了完全独立于编程语言的文本格式，但它的格式非常符合 C 语言家族的习惯（比如 C、C++、C#、Java、JavaScript、Python 等）。 这种特质使得 JSON 成为了最理想的数据交换格式。

JSON 建构于两种常见的数据结构：

*   “键/值”对。
*   数组。

这使得 JSON 在同样基于这些结构的编程语言之间的交换成为可能。在 Java 中，解析 JSON 的第三方类库有很多，比如说下面这些。

![](http://www.itwanger.com/assets/images/2020/01/java-json-parse-02.png)

很多，对不对？但日常开发中，最常用的只有四个：Gson、Jackson、org.json 和阿里巴巴的 fastjson。下面我们来简单地对比下。



### 02、Gson

Gson 是谷歌提供的一个开源库，可以将 Java 对象序列化为 JSON 字符串，同样可以将 JSON 字符串反序列化（解析）为匹配的 Java 对象。

使用 Gson 之前，需要先在项目中引入 Gson 的依赖。

```
<dependency>
	<groupId>com.google.code.gson</groupId>
	<artifactId>gson</artifactId>
	<version>2.8.6</version>
	<scope>compile</scope>
</dependency>
```

**1）简单示例**

```java
Gson gson = new Gson();
gson.toJson(18);            // ==> 18
gson.toJson("沉默王二");       // ==> "沉默王二"
```

上面这段代码通过 `new` 关键字创建了一个 Gson 对象，然后调用其 `toJson()` 方法将整形和字符串转成了 JSON 字符串。

同样，可以调用 `fromJson()` 方法将简单的 JSON 字符串解析为整形和字符串。

```java
int one = gson.fromJson("18", int.class);
Integer one1 = gson.fromJson("18", Integer.class);
String str = gson.fromJson("\"沉默王二\"", String.class);
```

**2）复杂点的示例**

Cmower 类有两个字段：整形 age 和 字符串 name。

```java
class Cmower {
    private int age = 18;
    private String name = "沉默王二";
}
```

将其转成 JSON 字符串。

```java
Gson gson = new Gson();
String json = gson.toJson(new Cmower());
System.out.println(json);
```

输出结果为：

```
{"age":18,"name":"沉默王二"}
```

可以再通过 `fromJson()` 方法将字符串 json 解析为 Java 对象。

```java
gson.fromJson(json, Cmower.class);
```

**3）数组示例**

```java
Gson gson = new Gson();
int[] ints = {1, 2, 3, 4, 5};
String[] strings = {"沉", "默", "王二"};

// 转成 JSON 字符串
gson.toJson(ints);     // ==> [1,2,3,4,5]
gson.toJson(strings);  // ==> ["沉", "默", "王二"]

// 解析为数组
int[] ints2 = gson.fromJson("[1,2,3,4,5]", int[].class);
String[] strings2 = gson.fromJson("[\"沉\", \"默\", \"王二\"]", String[].class);
```

数组的处理仍然非常简单，调用的方法也仍然是 `toJson()` 和 `fromJson()` 方法。

**4）集合示例**

```java
Gson gson = new Gson();
List<String> list = new ArrayList<>(Arrays.asList("沉", "默", "王二"));
String json = gson.toJson(list); // ==> ["沉","默","王二"]
```

把集合转成 JSON 字符串并没有什么特别之处，不过，把 JSON 字符串解析为集合就和之前的方法有些不同了。

```java
Type collectionType = new TypeToken<ArrayList<String>>(){}.getType();
List<String> list2 = gson.fromJson(json, collectionType);
```

我们需要借助 `com.google.gson.reflect.TypeToken` 和 `java.lang.reflect.Type` 来获取集合的类型，再将其作为参数传递给 `formJson()` 方法，才能将 JSON 字符串解析为集合。

Gson 虽然可以将任意的 Java 对象转成 JSON 字符串，但将字符串解析为指定的集合类型时就需要花点心思了，因为涉及到了泛型——TypeToken 是解决这个问题的银弹。

关于 Gson，我们就先说到这吧，以后有机会的时候再和大家细说。

### 03、Jackson

Jackson 是基于 Stream 构建的一款用来序列化和反序列化 JSON 的 Java 开源库，社区非常活跃，其版本的更新速度也比较快。

- 截止到目前，GitHub 上已经星标 5.2K 了；
- Spring MVC 的默认 JSON 解析器；
- 与 Gson 相比，Jackson 在解析大的 JSON 文件时速度更快。
- 与 fastjson 相比，Jackson 更稳定。

在使用 Jackson 之前，需要先添加 Jackson 的依赖。

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.9.1</version>
</dependency>
```

Jackson 的核心模块由三部分组成。

- jackson-core，核心包，提供基于"流模式"解析的相关 API，它包括 JsonPaser 和 JsonGenerator。 
- jackson-annotations，注解包，提供标准注解功能。
- jackson-databind ，数据绑定包， 提供基于"对象绑定" 解析的相关 API （ ObjectMapper ） 和"树模型" 解析的相关 API （JsonNode）；基于"对象绑定" 解析的 API 和"树模型"解析的 API 依赖基于"流模式"解析的 API。

当添加 jackson-databind 之后， jackson-core 和 jackson-annotations 也随之添加到 Java 项目工程中。

这里顺带推荐一个 IDEA 插件：JsonFormat，可以将 JSON 字符串生成一个 JavaBean。怎么使用呢？可以新建一个类，然后调出  Generate 菜单。

![](http://www.itwanger.com/assets/images/2020/01/java-json-parse-03.png)

选择 JsonFormat，输入 JSON 字符串。

```
{
  "age" : 18,
  "name" : "沉默王二"
}
```

确认后生成 JavaBean，生成的内容如下所示。

```java
public class Cmower {
    private Integer age;
    private String name;

    public Cmower() {
    }

    public Cmower(Integer age, String name) {
        this.age = age;
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```


那怎么使用 Jackson 呢？上文已经提到，ObjectMapper 是 Jackson 最常用的 API，我们来看一个简单的示例。

```java
Cmower wanger = new Cmower(18,"沉默王二");
System.out.println(wanger);

ObjectMapper mapper = new ObjectMapper();
String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(wanger);

System.out.println(jsonString);

Cmower deserialize = mapper.readValue(jsonString,Cmower.class);
System.out.println(deserialize);
```

ObjectMapper 通过 `writeValue()` 的系列方法可以将 Java 对象序列化为 JSON，并将 JSON 存储成不同的格式。

- String（writeValueAsString）
- Byte Array（writeValueAsBytes）


ObjectMapper 通过 `readValue()` 系列方法可以从不同的数据源（String、Bytes）将 JSON 反序列化（解析）为 Java 对象。

程序输出结果为：

```
com.cmower.java_demo.jackson.Cmower@214c265e
{
  "age" : 18,
  "name" : "沉默王二"
}
com.cmower.java_demo.jackson.Cmower@612fc6eb
```


在调用 `writeValue()` 或者 `readValue()` 方法之前，往往需要对 JSON 和 JavaBean 之间进行一些定制化配置。

1）在反序列化时忽略在 JSON 中存在但 JavaBean 不存在的字段

```java
mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
```

2）在序列化时忽略值为 null 的字段

```java
apper.setSerializationInclusion(Include.NON_NULL); 
```

有些时候，这些定制化的配置对 JSON 和 JavaBean 之间的转化起着重要的作用。如果需要更多配置信息，查看 DeserializationFeature、SerializationFeature 和 Include 类的 Javadoc 即可。

关于 Jackson，我们就说到这吧，以后有机会的时候再和大家细说。

### 04、org.json

org.json 是 JSON 官方提供的一个开源库，不过使用起来就略显繁琐了。

使用 org.json 之前，需要先在项目中引入 org.json 的依赖。

```
<dependency>
    <groupId>org.json</groupId>
    <artifactId>json</artifactId>
    <version>20190722</version>
</dependency>
```

`org.json.JSONObject` 类可以通过 new 关键字将 JSON 字符串解析为 Java 对象，然后 get 的系列方法获取对应的键值，代码示例如下所示。

```java
String str = "{ \"name\": \"沉默王二\", \"age\": 18 }";
JSONObject obj = new JSONObject(str);
String name = obj.getString("name");
int age = obj.getInt("age");
```

调用 `org.json.JSONObject` 类的 `getJSONArray()` 方法可以返回一个表示数组的`org.json.JSONArray` 对象，再通过循环的方式可以获取数组中的元素，代码示例如下所示。

```java
String str = "{ \"number\": [3, 4, 5, 6] }";
JSONObject obj = new JSONObject(str);
JSONArray arr = obj.getJSONArray("number");
for (int i = 0; i < arr.length(); i++) {
    System.out.println(arr.getInt(i));
}
```

如果想获取 JSON 字符串，可以使用 `put()` 方法将键值对放入 `org.json.JSONObject` 对象中，再调用 `toString()` 方法即可，代码示例如下所示。

```java
JSONObject obj = new JSONObject();
obj.put("name","沉默王二");
obj.put("age",18);
System.out.println(obj.toString()); // {"name":"沉默王二","age":18}
```

相比较于 Gson 和 Jackson 来说，org.json 就要逊色多了，不仅不够灵活，API 也不够丰富。

### 05、fastjson

fastjson 是阿里巴巴开源的 JSON 解析库，它可以解析 JSON 格式的字符串，也支持将 Java Bean 序列化为 JSON 字符串。

fastjson 相对于其他 JSON 库的特点就是快，另外 API 使用起来也非常简单，更是在 2012 年被开源中国评选为最受欢迎的国产开源软件之一。

PS：尽管 fastjson 值得信赖，但也闹过不少腥风血雨，这里就不提了。

在使用 fastjson 之前，需要先添加 fastjson 的依赖。

```
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.61</version>
</dependency>
```

那怎么使用 fastjson 呢？我们来创建一个 Java Bean，有三个字段：年龄 age，名字 name，列表 books。

```java
class Cmower1 {
    private Integer age;
    private String name;
    private List<String> books = new ArrayList<>();

    public Cmower1(Integer age, String name) {
        this.age = age;
        this.name = name;
    }
   // getter/setter

    public void putBook(String bookname) {
        this.books.add(bookname);
    }
}
```

然后我们使用 `JSON.toJSONString()` 将 Java 对象序列化为 JSON 字符串，代码示例如下：

```java
Cmower1 cmower = new Cmower1(18,"沉默王二");
cmower.putBook("《Web全栈开发进阶之路》");
String jsonString = JSON.toJSONString(cmower);
System.out.println(jsonString);
```

程序输出：

```
{"age":18,"books":["《Web全栈开发进阶之路》"],"name":"沉默王二"}
```

那如何解析 JSON 字符串呢？使用 `JSON.parseObject()` 方法，代码示例如下所示。

```java
JSON.parseObject(jsonString, Cmower1.class)
```


### 06、总结

就我个人而言，我是比较推崇 Gson 的，毕竟是谷歌出品的，品质值得信赖，关键是用起来也确实比较得劲。
 
Jackson 呢，在解析大的 JSON 文件时速度更快，也比 fastjson 稳定。

fastjson 呢，作为我们国产开源软件中的骄傲，嗯，值得尊敬。

令我意外的是，org.json 在 StackOverflow 上一个 160 万浏览量的提问中，牢牢地占据头名答案。更令我想不到的是，老板竟然也选择了 org.json，说它比较原生，JSON 官方的亲儿子。

我。。。。。。

![](http://www.itwanger.com/assets/images/2020/01/java-json-parse-04.png)


### 07、鸣谢

好了，各位读者朋友们，以上就是本文的全部内容了。能看到这里的都是最优秀的程序员，升职加薪就是你了👍。如果觉得不过瘾，还想看到更多，我再推荐一篇给大家。

[还有一周就解放了，无心撸码，着急回家](https://mp.weixin.qq.com/s/67ENoPJx4qX29JU9O5NSpQ)

**原创不易，如果觉得有点用的话，请不要吝啬你手中点赞的权力**；如果想要第一时间看到二哥更新的文章，请扫描下方的二维码，关注沉默王二公众号。我们下篇文章见！

![](http://www.itwanger.com/assets/images/cmower_3.png)