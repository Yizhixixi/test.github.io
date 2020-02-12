---
layout: post
category: java
title: Java：如何从 Map 中获取键和值？
tagline: by 沉默王二
tag: java
---

在 Java 中，可以通过 `map.entrySet()` 获取键和值，就像下面这样：

<!--more-->


```java
Map<String, String> map = new HashMap<>();

// 获取键和值
for (Map.Entry<String, String> entry : map.entrySet()) {
    String k = entry.getKey();
    String v = entry.getValue();
    System.out.println("键: " + k + ", 值: " + v);
}

// Java 8 Lambda 
map.forEach((k, v) -> {
    System.out.println("键: " + k + ", 值: " + v);
});
```

来看完整的例子：

```java
Map<String, String> map = new HashMap<>();
map.put("db", "MySQL");
map.put("username", "cmower");
map.put("password", "123456");

// 获取键和值
for (Map.Entry<String, String> entry : map.entrySet()) {
    String k = entry.getKey();
    String v = entry.getValue();
    System.out.println("键: " + k + ", 值: " + v);
}

// 获取所有键
Set<String> keys = map.keySet();
for (String k : keys) {
    System.out.println("键: " + k);
}
// 获取所有值
Collection<String> values = map.values();
for (String v : values) {
    System.out.println("值: " + v);
}

// Java 8 Lambda
map.forEach((k, v) -> {
    System.out.println("键: " + k + ", 值: " + v);
});
```

输出：

```
键: password, 值: 123456
键: db, 值: MySQL
键: username, 值: cmower

键: password
键: db
键: username

值: 123456
值: MySQL
值: cmower

键: password, 值: 123456
键: db, 值: MySQL
键: username, 值: cmower
```