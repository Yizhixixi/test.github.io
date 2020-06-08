---
layout: post
category: java
title: 恕我直言，我怀疑你并不会用 Java 枚举
tagline: by 沉默王二
tags: 
  - java
---

开门见山地说吧，enum（枚举）是 Java 1.5 时引入的关键字，它表示一种特殊类型的类，默认[继承](https://mp.weixin.qq.com/s/q-dMxOXxT8N3W6ftmNWkWQ)自 java.lang.Enum。

<!--more-->

为了证明这一点，我们来新建一个枚举 PlayerType：

```java
public enum PlayerType {
    TENNIS,
    FOOTBALL,
    BASKETBALL
}
```

两个关键字带一个类名，还有大括号，以及三个大写的单词，但没看到继承 Enum 类啊？别着急，心急吃不了热豆腐啊。使用 JAD 查看一下反编译后的字节码，就一清二楚了。

```java
public final class PlayerType extends Enum
{

    public static PlayerType[] values()
    {
        return (PlayerType[])$VALUES.clone();
    }

    public static PlayerType valueOf(String name)
    {
        return (PlayerType)Enum.valueOf(com/cmower/baeldung/enum1/PlayerType, name);
    }

    private PlayerType(String s, int i)
    {
        super(s, i);
    }

    public static final PlayerType TENNIS;
    public static final PlayerType FOOTBALL;
    public static final PlayerType BASKETBALL;
    private static final PlayerType $VALUES[];

    static 
    {
        TENNIS = new PlayerType("TENNIS", 0);
        FOOTBALL = new PlayerType("FOOTBALL", 1);
        BASKETBALL = new PlayerType("BASKETBALL", 2);
        $VALUES = (new PlayerType[] {
            TENNIS, FOOTBALL, BASKETBALL
        });
    }
}
```

看到没？PlayerType 类是 final 的，并且继承自 Enum 类。这些工作我们程序员没做，编译器帮我们悄悄地做了。此外，它还附带几个有用静态方法，比如说 `values()` 和  `valueOf(String name)`。

### 01、内部枚举

好的，小伙伴们应该已经清楚枚举长什么样子了吧？既然枚举是一种特殊的类，那它其实是可以定义在一个类的内部的，这样它的作用域就可以限定于这个外部类中使用。

```java
public class Player {
    private PlayerType type;
    public enum PlayerType {
        TENNIS,
        FOOTBALL,
        BASKETBALL
    }
    
    public boolean isBasketballPlayer() {
      return getType() == PlayerType.BASKETBALL;
    }

    public PlayerType getType() {
        return type;
    }

    public void setType(PlayerType type) {
        this.type = type;
    }
}
```

PlayerType 就相当于 Player 的内部类，`isBasketballPlayer()` 方法用来判断运动员是否是一个篮球运动员。

由于枚举是 final 的，可以确保在 Java 虚拟机中仅有一个常量对象（可以参照反编译后的[静态代码块](https://mp.weixin.qq.com/s/24EdEt3UyP7aKZStvvuDgQ)「static 关键字带大括号的那部分代码」），所以我们可以很安全地使用“==”运算符来比较两个枚举是否相等，参照 `isBasketballPlayer()` 方法。

那为什么不使用 `equals()` 方法判断呢？

```java
if(player.getType().equals(Player.PlayerType.BASKETBALL)){};
if(player.getType() == Player.PlayerType.BASKETBALL){};
```

“==”运算符比较的时候，如果两个对象都为 null，并不会发生 `NullPointerException`，而 `equals()` 方法则会。

另外， “==”运算符会在编译时进行检查，如果两侧的类型不匹配，会提示错误，而 `equals()` 方法则不会。

![](http://www.itwanger.com/assets/images/2020/06/java-meiju-01.png)

### 02、枚举可用于 switch 语句

这个我在之前的一篇[我去](https://mp.weixin.qq.com/s/1BDDLDSKDGwQAfIFMyySdg)的文章中详细地说明过了，感兴趣的小伙伴可以点击链接跳转过去看一下。

```java
switch (playerType) {
        case TENNIS:
            return "网球运动员费德勒";
        case FOOTBALL:
            return "足球运动员C罗";
        case BASKETBALL:
            return "篮球运动员詹姆斯";
        case UNKNOWN:
            throw new IllegalArgumentException("未知");
        default:
            throw new IllegalArgumentException(
                    "运动员类型: " + playerType);

    }
```

### 03、枚举可以有构造方法

如果枚举中需要包含更多信息的话，可以为其添加一些字段，比如下面示例中的 name，此时需要为枚举添加一个带参的构造方法，这样就可以在定义枚举时添加对应的名称了。

```java
public enum PlayerType {
    TENNIS("网球"),
    FOOTBALL("足球"),
    BASKETBALL("篮球");

    private String name;

    PlayerType(String name) {
        this.name = name;
    }
}
```

### 04、EnumSet

EnumSet 是一个专门针对枚举类型的 Set 接口的实现类，它是处理枚举类型数据的一把利器，非常高效（内部实现是位向量，我也搞不懂）。

因为 EnumSet 是一个抽象类，所以创建 EnumSet 时不能使用 new 关键字。不过，EnumSet 提供了很多有用的静态工厂方法：

![](http://www.itwanger.com/assets/images/2020/06/java-meiju-02.png)

下面的示例中使用 `noneOf()` 创建了一个空的 PlayerType 的 EnumSet；使用 `allOf()` 创建了一个包含所有 PlayerType 的 EnumSet。

```java
public class EnumSetTest {
    public enum PlayerType {
        TENNIS,
        FOOTBALL,
        BASKETBALL
    }

    public static void main(String[] args) {
        EnumSet<PlayerType> enumSetNone = EnumSet.noneOf(PlayerType.class);
        System.out.println(enumSetNone);

        EnumSet<PlayerType> enumSetAll = EnumSet.allOf(PlayerType.class);
        System.out.println(enumSetAll);
    }
}
```

程序输出结果如下所示：

```java
[]
[TENNIS, FOOTBALL, BASKETBALL]
```

有了 EnumSet 后，就可以使用 Set 的一些方法了：

![](http://www.itwanger.com/assets/images/2020/06/java-meiju-03.png)

### 05、EnumMap

EnumMap 是一个专门针对枚举类型的 Map 接口的实现类，它可以将枚举常量作为键来使用。EnumMap 的效率比 HashMap 还要高，可以直接通过数组下标（枚举的 ordinal 值）访问到元素。

和 EnumSet 不同，EnumMap 不是一个抽象类，所以创建 EnumMap 时可以使用 new 关键字：

```java
EnumMap<PlayerType, String> enumMap = new EnumMap<>(PlayerType.class);
```

有了 EnumMap 对象后就可以使用 Map 的一些方法了：

![](http://www.itwanger.com/assets/images/2020/06/java-meiju-04.png)

和 HashMap 的使用方法大致相同，来看下面的例子：

```java
EnumMap<PlayerType, String> enumMap = new EnumMap<>(PlayerType.class);
enumMap.put(PlayerType.BASKETBALL,"篮球运动员");
enumMap.put(PlayerType.FOOTBALL,"足球运动员");
enumMap.put(PlayerType.TENNIS,"网球运动员");
System.out.println(enumMap);

System.out.println(enumMap.get(PlayerType.BASKETBALL));
System.out.println(enumMap.containsKey(PlayerType.BASKETBALL));
System.out.println(enumMap.remove(PlayerType.BASKETBALL));
```

程序输出结果如下所示：

```
{TENNIS=网球运动员, FOOTBALL=足球运动员, BASKETBALL=篮球运动员}
篮球运动员
true
篮球运动员
```

### 06、单例

通常情况下，实现一个单例并非易事，不信，来看下面这段代码

```java
public class Singleton {  
    private volatile static Singleton singleton; 
    private Singleton (){}  
    public static Singleton getSingleton() {  
    if (singleton == null) {
        synchronized (Singleton.class) { 
        if (singleton == null) {  
            singleton = new Singleton(); 
        }  
        }  
    }  
    return singleton;  
    }  
}
```

但枚举的出现，让代码量减少到极致：

```java
public enum EasySingleton{
    INSTANCE;
}
```

完事了，真的超级短，有没有？枚举默认实现了 Serializable 接口，因此 Java 虚拟机可以保证该类为单例，这与传统的实现方式不大相同。传统方式中，我们必须确保单例在反序列化期间不能创建任何新实例。

### 07、枚举可与数据库交互

我们可以配合 Mybatis 将数据库字段转换为枚举类型。现在假设有一个数据库字段 check_type 的类型如下：

```sql
`check_type` int(1) DEFAULT NULL COMMENT '检查类型（1：未通过、2：通过）',
```

它对应的枚举类型为 CheckType，代码如下：

```java
public enum CheckType {
	NO_PASS(0, "未通过"), PASS(1, "通过");
	private int key;

	private String text;

	private CheckType(int key, String text) {
		this.key = key;
		this.text = text;
	}

	public int getKey() {
		return key;
	}

	public String getText() {
		return text;
	}

	private static HashMap<Integer,CheckType> map = new HashMap<Integer,CheckType>();
	static {
		for(CheckType d : CheckType.values()){
			map.put(d.key, d);
		}
	}
	
	public static CheckType parse(Integer index) {
		if(map.containsKey(index)){
			return map.get(index);
		}
		return null;
	}
}
```

1）CheckType 添加了构造方法，还有两个字段，key 为 int 型，text 为 String 型。

2）CheckType 中有一个`public static CheckType parse(Integer index)`方法，可将一个 Integer 通过 key 的匹配转化为枚举类型。

那么现在，我们可以在 Mybatis 的配置文件中使用 `typeHandler` 将数据库字段转化为枚举类型。

```xml
<resultMap id="CheckLog" type="com.entity.CheckLog">
  <id property="id" column="id"/>
  <result property="checkType" column="check_type" typeHandler="com.CheckTypeHandler"></result>
</resultMap>
```

其中 checkType 字段对应的类如下：

```java
public class CheckLog implements Serializable {

    private String id;
    private CheckType checkType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CheckType getCheckType() {
        return checkType;
    }

    public void setCheckType(CheckType checkType) {
        this.checkType = checkType;
    }
}
```

CheckTypeHandler 转换器的类源码如下：

```java
public class CheckTypeHandler extends BaseTypeHandler<CheckType> {

	@Override
	public CheckType getNullableResult(ResultSet rs, String index) throws SQLException {
		return CheckType.parse(rs.getInt(index));
	}

	@Override
	public CheckType getNullableResult(ResultSet rs, int index) throws SQLException {
		return CheckType.parse(rs.getInt(index));
	}

	@Override
	public CheckType getNullableResult(CallableStatement cs, int index) throws SQLException {
		return CheckType.parse(cs.getInt(index));
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int index, CheckType val, JdbcType arg3) throws SQLException {
		ps.setInt(index, val.getKey());
	}
}
```

CheckTypeHandler 的核心功能就是调用 CheckType 枚举类的 `parse()` 方法对数据库字段进行转换。

![](http://www.itwanger.com/assets/images/2020/06/java-meiju-05.gif)

恕我直言，这篇文章看完后，我觉得小伙伴们肯定会用 Java 枚举了，如果还不会，就过来砍我！

如果觉得文章对你有点帮助，请微信搜索「 **沉默王二** 」第一时间阅读，回复「**并发**」更有一份阿里大牛重写的 Java 并发编程实战，从此再也不用担心面试官在这方面的刁难了。

>本文已收录 GitHub，[**传送门~**](https://github.com/qinggee/itwanger.github.io) ，里面更有大厂面试完整考点，欢迎 Star。

我是沉默王二，一枚有颜值却靠才华苟且的程序员。**关注即可提升学习效率，别忘了三连啊，点赞、收藏、留言，我不挑，嘻嘻**。