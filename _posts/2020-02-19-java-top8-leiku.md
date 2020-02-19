---
layout: post
category: java
title: 隆重向你推荐这 8 个开源 Java 类库
tagline: by 沉默王二
tag: java
---

昨天在青铜时代群里看到读者朋友们在讨论 Java 最常用的工具类，我觉得大家推荐的确实都挺常见的，我自己用的频率也蛮高的。恰好我在 programcreek 上看到过一篇类似的文章，就想着梳理一下分享给大家。

<!--more-->


![](http://www.itwanger.com/assets/images/2020/02/java-top8-leiku-01.png)


在 Java 中，工具类通常用来定义一组执行通用操作的方法。本篇文章将会向大家展示 8 个工具类以及它们最常用的方法，类的排名和方法的排名均来自可靠的数据，从 GitHub 上最受欢迎的 50000 个开源 Java 项目中精心挑选。

为了方便大家的理解，我会在介绍每个类的方法时写一段小 Demo，这样大家就能知道每个方法该怎么用。放心吧，方法是干嘛的我也不会保密的。😄

### 1）IOUtils

`org.apache.commons.io.IOUtils`，操作 IO 流的工具类，下面是其常用的方法。

- `closeQuietly()`，关闭 IO 流，并且忽略 null 值和异常。

```java
IOUtils.closeQuietly(output);
```

- `copy()`，将字节从输入流复制到输出流。

```java
IOUtils.copy(inputStream, new FileOutputStream(File));
```

- `toByteArray()`，以 `byte[]` 的形式获取输入流中的内容。

```java
URLConnection conn = new URL(url).openConnection();
InputStream is = conn.getInputStream();
byte[] result = IOUtils.toByteArray(is);
```

- `write()`，将字符或者字节写入输出流中。

```java
IOUtils.write("沉默王二", response.getOutputStream(), "UTF-8");
```

- `toInputStream()`，将指定的字符转成输入流。

```java
String content=req.getParameter("content");
InputStream inputStream=IOUtils.toInputStream(content,"utf-8");
```

- `readLines()`，从输入流中一行一行地读取，并按照指定的字符编码返回字符串列表。

```java
List<String> lines = IOUtils.readLines(new InputStreamReader(new FileInputStream(file), "utf-8"));
```

- `copyLarge()`，从输入流中复制内容到输出流，超过 2GB。

```java
private File downloadFile(HttpResponse response) {
    File dir = new File("downloadedFiles");
    if (!dir.exists()) {
        dir.mkdir();
    }
    File outputFile = new File("downloadedFiles/temp" + RandomStringUtils.randomAlphanumeric(3));
    try {
        IOUtils.copyLarge(response.getEntity().getContent(), new FileOutputStream(outputFile));
        return outputFile;
    } catch (Exception e) {
        throw new RuntimeException(e);
    } finally {
        request.releaseConnection();
    }
}
```

- `readFully()`，把输入流中的内容读入到字节数组中。

```java
byte[] intArray = new byte[Bytes.SIZEOF_INT];
IOUtils.readFully(in, intArray);
```

2）FileUtils

`org.apache.commons.io.FileUtils`，操作文件或者目录的工具类，下面是其常用的方法。

- `deleteDirectory()`，删除目录。

```java
FileUtils.deleteDirectory(file);
```

- `readFileToString()`，把文件的内容读入到字符串中。

```java
String fileAsString = FileUtils.readFileToString(reportFile);
```

- `deleteQuietly()`，删除文件，但不抛出异常。

```java
 FileUtils.deleteQuietly(outputFile);
```

- `copyFile()`，把文件复制到一个新的位置。

```java
FileUtils.copyFile(source, dest);
```

- `writeStringToFile()`，把字符串写入到文件。

```java
FileUtils.writeStringToFile(templateFile, generatedText, Charset.forName("UTF-8"));
```

- `forceMkdir()`，强制创建目录，包括任何必需但不存在的父目录。

```java
File uploadDirectory = new File(this.uploadPath);
if (!uploadDirectory.exists()) {
    FileUtils.forceMkdir(uploadDirectory);
}
```

- `write()`，把字符或者字节写入到文件。

```java
FileUtils.write(new File("C:\\Users\\cmower\\test.txt"), "沉默王二", "utf-8");
```

- `listFiles()`，列出指定目录下的所有文件。

```java
public void processResultsDirectory(String dirName) {
    File root = new File(dirName);
    try {
        Collection<File> files = FileUtils.listFiles(root,
                new RegexFileFilter(jmeterJTLFileName),
                DirectoryFileFilter.DIRECTORY);

        for (Iterator<File> iterator = files.iterator(); iterator.hasNext();) {
            File file = (File) iterator.next();
            parse(file);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

- `copyDirectory()`，将目录下的所有子目录及文件复制到新的目录。

```java
 FileUtils.copyDirectory(source, dest);
```

- `forceDelete()`，强制删除文件或者目录及其所有子目录和文件。

```java
 FileUtils.forceDelete(tmpFile);
```

### 3）StringUtils

`org.apache.commons.lang3.StringUtils`，操作字符串的工具类，并且是 null 安全的，下面是其常用的方法。

- `isBlank()`，检查字符是否为空字符串""，或者 null，或者空格。

```java
if (StringUtils.isBlank(name))
{
throw new IllegalArgumentException("姓名不能为空");
}
```

检查结果如下所示。

```java
StringUtils.isBlank(null)      = true
StringUtils.isBlank("")        = true
StringUtils.isBlank(" ")       = true
StringUtils.isBlank("沉默王二")     = false
StringUtils.isBlank("  沉默王二  ") = false
```

- `isNotBlank()`，与 `isBlank()` 检查的结果相反。

- `isEmpty()`，检查字符是否为空字符串""，或者 null；和 `isBlank()` 不同，不包括空格的检查。

```java
if (StringUtils.isEmpty(name)) {
    throw new IllegalArgumentException("姓名不能为 null 或者空字符串");
}
```

检查结果如下所示。

```java
StringUtils.isEmpty(null)      = true
StringUtils.isEmpty("")        = true
StringUtils.isEmpty(" ")       = false
StringUtils.isEmpty("沉默王二")     = false
StringUtils.isEmpty("  沉默王二  ") = false
```

- `isNotEmpty()`，与 `isEmpty()` 检查的结果相反。


- `join()`，将多个元素连接成一个字符串。

```java
StringUtils.join(null)            = null
StringUtils.join([])              = ""
StringUtils.join([null])          = ""
StringUtils.join(["沉默", "王二"]) = "沉默王二"
StringUtils.join([null, "", "一枚有趣的程序员"]) = "一枚有趣的程序员"
```

- `equals()`，比较两个字符序列是否相等。

```java
StringUtils.equals(null, null)   = true
StringUtils.equals(null, "沉默王二")  = false
StringUtils.equals("沉默王二", null)  = false
StringUtils.equals("沉默王二", "沉默王二") = true
StringUtils.equals("cmower", "CMOWER") = false
```

- `split()`，把字符串拆分为数组，拆分符为空白字符。

```java
 StringUtils.split(null)       = null
 StringUtils.split("")         = []
 StringUtils.split("沉默王二 沉默王三")  = ["沉默王二", "沉默王三"]
 StringUtils.split("沉默王二  沉默王三") = ["沉默王二", "沉默王三"]
 StringUtils.split(" 沉默王二 ")    = ["沉默王二"]
```

- `replace()`，替换另一个字符串中所有出现的字符串。

```java
StringUtils.replace(null, *, *)        = null
StringUtils.replace("", *, *)          = ""
StringUtils.replace("any", null, *)    = "any"
StringUtils.replace("any", *, null)    = "any"
StringUtils.replace("any", "", *)      = "any"
StringUtils.replace("沉默王二", "二", null)  = "沉默王二"
StringUtils.replace("沉默王二", "二", "")    = "沉默王"
StringUtils.replace("沉默王二", "二", "三")   = "沉默王三"
```

### 4）FilenameUtils

`org.apache.commons.io.FilenameUtils`，操作文件名或者路径的工具类，下面是其常用的方法。

- `getExtension()`，获取文件的扩展名。

```java
FilenameUtils.getExtension("牛逼.txt");        --> "txt"
FilenameUtils.getExtension("a/b/牛逼.jpg");    --> "jpg"
FilenameUtils.getExtension("a/牛逼.txt/c");    --> ""
FilenameUtils.getExtension("a/b/c");           --> ""
```

- `getBaseName()`，获取单纯的文件名或者路径名，文件时去掉路径和扩展名；路径时去掉父级路径。

```java
FilenameUtils.getBaseName("a/b/牛逼.txt");        --> "牛逼"
FilenameUtils.getBaseName("牛逼.txt");            --> "牛逼"
FilenameUtils.getBaseName("a/b/c");               --> c
FilenameUtils.getBaseName("a/b/c/");              --> ""
```

- `getName()`，如果是文件时，获取文件名和后缀，去掉路径；如果是路径时，去掉父级路径。

```java
FilenameUtils.getName("a/b/牛逼.txt");        --> "牛逼.txt"
FilenameUtils.getName("牛逼.txt");            --> "牛逼.txt"
FilenameUtils.getName("a/b/c");               --> c
FilenameUtils.getName("a/b/c/");              --> ""
```


- `concat()`，将路径和文件名连接在一起。

```java
FilenameUtils.concat("/foo/", "bar");          -->   /foo/bar
FilenameUtils.concat("/foo", "bar");           -->   /foo/bar
FilenameUtils.concat("/foo", "/bar");          -->   /bar
FilenameUtils.concat("/foo", "C:/bar");        -->   C:/bar
FilenameUtils.concat("/foo", "C:bar");         -->   C:bar (*)
FilenameUtils.concat("/foo/a/", "../bar");     -->   foo/bar
FilenameUtils.concat("/foo/", " ../../bar");    -->   null
FilenameUtils.concat("/foo/", "/bar");        -->   /bar
FilenameUtils.concat("/foo/.. ", "/bar");       -->   /bar
FilenameUtils.concat("/foo", " bar/c.txt");     -->   /foo/bar/c.txt
FilenameUtils.concat("/foo/c.txt", "bar");     -->   /foo/c.txt/bar (!)
```



- `wildcardMatch()`，检查文件名是否匹配指定的格式。

```java
wildcardMatch("c.txt", "*.txt")      --> true
wildcardMatch("c.txt", "*.jpg")      --> false
wildcardMatch("a/b/c.txt", "a/b/*")  --> true
wildcardMatch("c.txt", "*.???")      --> true
wildcardMatch("c.txt", "*.????")     --> false
```


- `separatorsToUnix()`，将所有分隔符转换为正斜杠的 Unix 分隔符。

```java
FilenameUtils.separatorsToUnix("my/unix/filename");
```


- `getFullPath()`，获取文件的完整路径。

```java
getFullPath("C:\a\b\c.txt" --> C:\a\b\
getFullPath("~/a/b/c.txt"  --> ~/a/b/
getFullPath("a.txt"        --> ""
```

### 5）ArrayUtils

`org.apache.commons.lang3.ArrayUtils`，操作数组的工具类，下面是其常用的方法。

- `contains()`，检查数组中是否包含某个值

```java
public static boolean containsAll(Object[] one, Object[] two) {
    for (Object b : two) {
        if (!ArrayUtils.contains(one, b)) {
            return false;
        }
    }
    return true;
}
```

- `addAll()`，将所有元素添加到一个数组中。


```java
ArrayUtils.addAll(null, null)     = null
ArrayUtils.addAll(array1, null)   = cloned copy of array1
ArrayUtils.addAll(null, array2)   = cloned copy of array2
ArrayUtils.addAll([], [])         = []
ArrayUtils.addAll([null], [null]) = [null, null]
ArrayUtils.addAll(["a", "b", "c"], ["1", "2", "3"]) = ["a", "b", "c", "1", "2", "3"]
```

- `clone()`，浅拷贝一个数组。

```java
public QualityGateTask[] getQueue() {
    return (QualityGateTask[]) ArrayUtils.clone(queue);
}
```

- `isEmpty()`，检查数组是否为 null 或者没有元素。

```java
if (ArrayUtils.isEmpty(objectIds)) {
    throw new IllegalArgumentException("对象的ID不能为空");
}
```

- `add()`，在数组中添加一个新的元素，原数组不变。

```java
ArrayUtils.add(null, true)          = [true]
ArrayUtils.add([true], false)       = [true, false]
ArrayUtils.add([true, false], true) = [true, false, true]
```

- `subarray()`，根据起始下标和结束下标截取一个子数组。

```java
public byte[] fetchData(String blobKey, long startIndex, long l) {
  CountingInputStream inputStream = new CountingInputStream(getInputStream(blobKey));
  byte[] bytes = new byte[(int) l];
  try {
    int readSize = inputStream.read(bytes, (int) startIndex, (int) l);
    if (readSize < l) {
      bytes = ArrayUtils.subarray(bytes, 0, readSize - 1);
    }
  } catch (IOException e) {
    LOGGER.warn("Failed to read bytes", e);
  } finally {
    try {
      inputStream.close();
    } catch (IOException ignored) {
      LOGGER.warn("Exception while closing inputStream", ignored);
    }
  }
  return bytes;
}
```

- `indexOf()`，找出指定数组的下标。

```java
ArrayUtils.indexOf(idxVal, i);
```


### 6）DigestUtils

`org.apache.commons.codec.digest.DigestUtils`，加密的工具类，下面是其常用的方法。

- `md5Hex()`，计算字符串的 MD5 摘要，并返回 32 位的十六进制字符。

```java
DigestUtils.md5Hex("沉默王二");
```


- `md5()`，计算字符串的 MD5 摘要，并返回 16 个元素的字节数组。

```java
DigestUtils.md5("沉默王二");
```

### 7）StringEscapeUtils

`org.apache.commons.text.StringEscapeUtils`，字符串的转义和反转义工具类，下面是其常用的方法。

- `unescapeHtml4()`，反转义 HTML。

```java
StringEscapeUtils.unescapeHtml4("&lt;div&gt;&lt;/div&gt;");-->   <div></div>
```

- `escapeHtml4()`，转义 HTML。


```java
StringEscapeUtils.escapeHtml4("<div></div>");-->   &lt;div&gt;&lt;/div&gt;
```

- `escapeJava()`，转义 Java。

```java
StringEscapeUtils.escapeJava("沉默王二");-->   \u6C89\u9ED8\u738B\u4E8C
```

- `unescapeJava()`

```java
StringEscapeUtils.escapeJava("\u6C89\u9ED8\u738B\u4E8C");-->   沉默王二
```

### 8）BeanUtils

大多数 Java 开发人员习惯于创建 `getter/setter` 的JavaBean，然后通过调用相应的 `getXxx` 和 `setXxx` 方法访问对应字段。但在某些情况下，需要动态访问 Java 对象的属性，`org.apache.commons.beanutils.BeanUtils` 就派上用场了。

- `copyProperties()`，拷贝所有属性。

```java
private static void dto2Entity() {
    UserDTO user = new UserDTO();
    user.setId(1l);
    user.setUsername("joking");
    user.setCreationDate("2016-04-20");
    
    EUser u = new EUser();
    ConvertUtils.register(new DateStringConverter(), Date.class);
    try {
        BeanUtils.copyProperties(u, user);
    } catch (IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
    }
}
```

其中 UserDTO  类的源码如下所示。

```java
public class UserDTO implements Serializable {
    
    private static final long serialVersionUID = 2963408818099106614L;

    private long id;

    private String username;

    private String creationDate;

    // getter/setter

    @Override
    public String toString() {
        return "UserDTO [id=" + id + ", username=" + username + ", creationDate=" + creationDate + "]";
    }

}
```

其中 EUser 类的源码如下所示。

```java
public class EUser implements Serializable {
    private static final long serialVersionUID = -692192937932555368L;

    private long id;

    private String username;

    private Date creationDate;

    //getter/setter

    @Override
    public String toString() {
        return "EUser [id=" + id + ", username=" + username + ", creationDate=" + creationDate + "]";
    }

}
```



其中 DateStringConverter 类的源码如下所示。

```java
public class DateStringConverter implements Converter {

    @SuppressWarnings("unchecked")
    @Override
    public <T> T convert(Class<T> type, Object value) {
        if(type.equals(Date.class) && String.class.isInstance(value)) {
            return (T)DateUtils.convert((String)value);
        } else if(type.equals(String.class) && Date.class.isInstance(value)){
            return (T)DateUtils.format((Date)value);
        } else {
            return (T)value;
        }
    }

}
```

- `getProperty()`，返回 bean 指定的属性值。

```java
String fieldValue = BeanUtils.getProperty(value, fieldName);
```

- `setProperty()`，设置 bean 指定的属性值。

```java
BeanUtils.setProperty(object, propertyName, value);
```

![](http://www.itwanger.com/assets/images/2020/02/java-top8-leiku-02.png)

说实在的，没想到整理起来这么费事，不知不觉 5 个小时过去了。每个类都有很多方法，还要为每个方法敲一个 Demo，真的是很辛苦。但为了你们，再苦再累二哥也心甘情愿啊。

虽然是技术文，但我想大家应该仍然能感受到我的文风比较幽默风趣。上次和纯洁的微笑、江南一点雨他们在南京小聚的时候，他们也感慨说：“之前一直未曾谋面，这次一见，二哥真心文如其人啊，骚气。”这话真不是贬义词，这年头，生活压力这么大，像我这样能够对生活保持乐观的人不多了，希望你们在阅读我的文章时也能够感受到快乐，我是认真的！

**原创不易，如果觉得有点用的话，请不要吝啬你手中点赞的权力**；如果想要第一时间看到二哥更新的文章，请扫描下方的二维码，关注沉默王二公众号。我们下篇文章见！

![](http://www.itwanger.com/assets/images/cmower_5.png)