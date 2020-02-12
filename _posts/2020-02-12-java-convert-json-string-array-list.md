---
layout: post
category: java
title: Jackson：将JSON格式的字符串数组转成List
tagline: by 沉默王二
tag: java
---

首先，在项目中引入 Jackson 的依赖：


<!--more-->


```java
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.9.1</version>
</dependency>
```

再来看 JSON 格式的字符串数组：

```
[{"name":"沉默王二", "age":18}, {"name":"沉默王三", "age":16}]
```

根据 JSON 的字段创建一个对象：


```java
public class Cmower {
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Cmower{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

```

准备转：

```java
ObjectMapper mapper = new ObjectMapper();
String json = "[{\"name\":\"沉默王二\", \"age\":18}, {\"name\":\"沉默王三\", \"age\":16}]";

try {

    // 1. 把 JSON 数组转成对象数组
    Cmower[] pp1 = mapper.readValue(json, Cmower[].class);

    System.out.println("JSON 数组转成对象数组...");
    for (Cmower cmower : pp1) {
        System.out.println(cmower);
    }

    // 2. 把 JSON 数组转成 List
    List<Cmower> ppl2 = Arrays.asList(mapper.readValue(json, Cmower[].class));

    System.out.println("\nJSON 数组转成 List");
    ppl2.stream().forEach(x -> System.out.println(x));

    // 3. TypeReference
    List<Cmower> pp3 = mapper.readValue(json, new TypeReference<List<Cmower>>() {});

    System.out.println("\nTypeReference...");
    pp3.stream().forEach(x -> System.out.println(x));

} catch (
        IOException e) {
    e.printStackTrace();
}
```

输出：

```
JSON 数组转成对象数组...
Cmower{name='沉默王二', age=18}
Cmower{name='沉默王三', age=16}

JSON 数组转成 List
Cmower{name='沉默王二', age=18}
Cmower{name='沉默王三', age=16}

TypeReference...
Cmower{name='沉默王二', age=18}
Cmower{name='沉默王三', age=16}
```