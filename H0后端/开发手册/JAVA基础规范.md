# 注释规约

## 代码注释说明

> 良好的注释不仅可以帮助自己和其他开发者理解代码结构，也可以用在项目编译时生成javadoc，避免重复工作。
>
> 现代IDE可以通过模板的方式自动生成一些格式化的注释，在此我们提供模板的规范。请不要使用非Javadoc标准的注解，如@date。

#### 类注释

- 所有的类都必须使用 `Javadoc`，添加创建者和创建日期及描述信息，不得使用 // xxx 方式。

```java
 /**
  * <p>
  * description
  * </p>
  * 
  * @author xxxx@hand-china.com 2018/06/07 13:48
  */
 public class Demo {

 }
```



#### 方法注释

- 所有的抽象类、接口中的方法必须要用 `Javadoc` 注释、除了返回值、参数、异常说明外，还必须指出该方法做什么事情，实现什么功能。对子类的实现要求，或者调用注意事项，请一并说明。

```java
/**
 * <p>description<p/>
 *
 * @param name meaning
 * @param list meaning
 * @return the return
 * @throws RuntimeException exception description
 */
String test(String name, List<String> list) throws RuntimeException;
```

- 方法内部单行注释，在被注释语句上方另起一行，使用`//`注释。方法内部多行注释使用`/* */`注释，注意与代码对齐。

```java
public void test(){
    // 单行注释
    String single = "";

    /*
    * 多行注释
    * 多行注释
    */
    String multi = "";
}
```

- 私有方法可以使用 // xxx 注释，也可以使用Javadoc注释

```java
// description
private void test3 () {
    // ...

    /* ... */
}
```



#### 字段注释

- 实体属性使用Javadoc注释，标明字段含义，这样getter/setter会含有注释。
- public 属性必须使用Javadoc注释

```java
/**
 * 静态字段描述
 */
public static final String STATIC_FIELD = "DEMO";

/**
 * 姓名
 */
private String name;
```



#### 特殊注释标记

- 待办事宜（TODO）:（ 标记人，标记时间，[预计处理时间]）表示需要实现，但目前还未实现的功能。
- 错误（FIXME）:（标记人，标记时间，[预计处理时间]）在注释中用 FIXME 标记某代码是错误的，而且不能工作，需要及时纠正的情况。

```java
public void test3 () {
    // TODO 待完成 [author, time]

    // FIXME 待修复 [author, time]
}
```



#### 分隔符

- 有时需要划分区块，可以使用分隔符进行分隔

```java
//
// 说明
// ------------------------------------------------------------------------------

//===============================================================================
//  说明
//===============================================================================
```



#### 其它

- 同一个类多个人开发，如有必要请在类、方法上加上负责人、时间信息
- 代码修改的同时，注释也要进行相应的修改，尤其是参数、返回值、异常、核心逻辑等的修改
- 对于注释的要求：第一、能够准确反应设计思想和代码逻辑；第二、能够描述业务含义，使别的程序员能够迅速了解到代码背后的信息。
- 好的命名、代码结构是自解释的，注释力求精简准确、表达到位。避免出现注释的一个极端：过多过滥的注释，代码的逻辑一旦修改，修改注释是相当大的负担。
- 大段代码注释请注意格式化，可以使用`<pre>`、`<ul>`、`<li>`、`<b>`、`<strong>`等常用标签美化下注释。

------

## IntelliJ IDEA 注释模板

IntelliJ IDEA 只能通过设置注释模板来实现，请先导入[IDEA注释模板](http://hzerodoc.saas.hand-china.com/files/docs/development-specification/backend-development-specification/basic/comment/FileTemplate.jar)。*File -> Import Settings*

该模板内置了几种常用注释：

- 创建类时默认生成标准的Javadoc注释
- 使用 `cc + Enter` 生成类注释
- 使用 `mc + Enter` 生成方法注释，这个需要在方法内部生成，然后剪贴到方法上。
- 使用 `hc + Enter` / `dc + Enter` 生成区块注释

------

# 日志规范

## 异常类型

1.`debug` 非常具体的信息，只能用于开发调试使用。部署到生产环境后，这个级别的信息只能保持很短的时间。这些信息只能临时存在，并将最终被关闭。要区分`DEBUG`和`TRACE`会比较困难，对一个在开发及测试完成后将被删除的`LOG输出`，可能会比较适合定义为`TRACE`级别.

2.`info` 重要的业务处理已经结束。在实际环境中，系统管理员或者高级用户要能理解`INFO`输出的信息并能很快的了解应用正在做什么。比如，一个和处理机票预订的系统，对每一张票要有且只有一条`INFO信`息描述 “`[Who] booked ticket from [Where] to [Where]`”。另外一种对`INFO`信息的定义是：记录显著改变应用状态的每一个`action`，比如：数据库更新、外部系统请求。

3.`warn` 发生这个级别问题时，处理过程可以继续，但必须对这个问题给予额外关注。这个问题又可以细分成两种情况：

- 第一种是存在严重的问题但有应急措施（比如数据库不可用，使用Cache）；
- 第二种是潜在问题及建议（ATTENTION）。

比如生产环境的应用运行在`Development`模式下、管理控制台没有密码保护等。系统可以允许这种错误的存在，但必须及时做跟踪检查用户参数错误。可以使用`warn`日志级别来记录用户输入参数错误的情况，避免用户投诉时，无所适从。

4.`error` 系统中发生了非常严重的问题，必须马上有人进行处理。没有系统可以忍受这个级别的问题的存在。比如：NPEs（空指针异常），数据库不可用，关键业务流程中断等等。

------

## 异常规范

1.返回给前端接口统一抛出`io.choerodon.core.exception.CommonException`异常，这样返回给前端的状态码为`200`，前端通过`failed`是否为`true`判断成功与否。

2.内部接口(用于被其他服务通过feign或ribbon调用的)，统一抛出`io.choerodon.core.exception.FeignException`异常，抛出异常时状态码为`500`，方便接口调用端“`感知`”异常。

3.手动抛异常时应该把`exception`一块抛出，可以保留异常堆栈。 

```java
try {
    String input = mapper.writeValueAsString(projectEventMsg); 
    sagaClient.startSaga(PROJECT_UPDATE, new StartInstanceDTO(input, “project”, “” + projectDO.getId()));
} catch (Exception e) {
    throw new CommonException(“error.projectService.update.event”, e);
}
```

4.不允许记录日志后又抛出异常，因为这样会多次记录日志，只允许记录一次日志，应尽量抛出异常，顶层打印一次日志。

5.使用SLF4J中的API进行日志打印, 在一个对象中通常只使用一个`Logger`对象，`Logger`应该是`static final`的。 `private static final Logger LOGGER= LoggerFactory.getLogger(Abc.class)。`

6.对`trace/debug/info`级别的日志输出，**必须使用占位符的方式**。 

错误：`logger.debug(“Processing trade with id: “ + id + ” symbol: “ + symbol);`

如果日志级别是 `warn`，上述日志不会打印，但是会执行字符串拼接操作，如果`symbol`是对象，会执行 toString()方法，浪费了系统资源，执行了上述操作，最终日志却没有打印。所以应该使用：logger.debug(“Processing trade with id:{} and symbol : {} “, id, symbol);

7.输出的`POJO`类必须重写`toString`方法，否则只输出此对象的`hashCode`值（地址值），没啥参考意义。

8.输出`Exceptions`的全部`Throwable`信息。

- LOGGER.error(e.getMessage()); 错误,失掉StackTrace信息
- LOGGER.error(“Bad things : {}“,e.getMessage()); 错误,失掉StackTrace信息
- LOGGER.error(“Bad things : {}“,e); 正确

9.不允许出现`System print`(包括System.out.println和System.error.println)语句。

10.不允许出现`printStackTrace`。堆栈打印应该`LOGGER.error`(“Bad things : {}“,e)。

------

## 日志格式

1.将附件中的`logback-spring.xml`放入`src/main/resources/`中。

2.系统抛出的异常不会附带`traceid`，如果需要输出`traceid`应该使用如下方式输出异常信息`logger.error`(“internal server error”, e)。

------

# 异常规范

## 后端异常规范

在开发使用中，异常应该能够很好地帮助我们定位到问题的所在。如果使用一种错误的方式，则bug很难被找到。

------

## 异常的分类

JAVA中有三种一般类型的可抛类: **检查性异常(checked exceptions)**、**非检查性异常(unchecked Exceptions)** 和 **错误(errors)**。

1.`Checked exceptions`：必须通过方法进行声明。这些异常都继承自`Exception`类。一个`Checked exception`声明了一些预期可能发生的异常。

2.`Unchecked exceptions`：不需要声明的异常。大多继承自`RuntimeException`。例如`NullPointerException`，`ArrayOutOfBoundsException`。同时这样的异常不应该捕获，而应该打印出堆栈信息。

3.`Errors`：大多是一些运行环境的问题，这些问题可能会导致系统无法运行。例如`OutOfMemoryError`，`StackOverflowError`。

------

## 用户自定义异常

我们应该遵循如下的规范。

1.当应用程序出现问题时，直接抛出自定义异常。

```java
 throw new DaoObjectNotFoundException("Couldn't find dao with id " + id);
```

2.将自定义异常中的原始异常包装并抛出。

```java
catch (NoSuchMethodException e) {
  throw new DaoObjectNotFoundException("Couldn't find dao with id " + id, e);
}
```

错误的做法：

1.不要吞下`catch`的异常。

```java
try {
    System.out.println("Never do that!");
} catch (AnyException exception) {
    // Do nothing
}
```

- 这样的捕获毫无意义。我们应该使用一定的日志输出来定位到问题。

2.方法上应该抛出具体的异常。而不是`Exception`。

```java
public void foo() throws Exception { //错误方式
}
public void foo() throws SQLException { //正确方式
}
```

3.要捕获异常的**子类**，而不是直接捕获`Exception`。

```java
catch (Exception e) { //错误方式
}
```

4.**永远不要捕获`Throwable`类。**

5.不要只是抛出一个新的异常，而应该包含堆栈信息。错误的做法：

```java
try {
    // Do the logic
} catch (BankAccountNotFoundException exception) {
    throw new BusinessException();
    // or
    throw new BusinessException("Some information: " + e.getMessage());
}
```
```java
try {
    // Do the logic
} catch (BankAccountNotFoundException exception) {
    throw new BusinessException(exception);
    // or
    throw new BusinessException("Some information: " ,exception);
}
```

6.要么记录异常要么抛出异常，但不要一起执行。

```java
catch (NoSuchMethodException e) {  
//错误方式 
   LOGGER.error("Some information", e);
   throw e;
}
```

7.不要在`finally`中再抛出异常。

```java
try {
  someMethod();  //Throws exceptionOne
} finally {
  cleanUp();    //如果finally还抛出异常，那么exceptionOne将永远丢失
}
```

- 如果`someMethod` 和 `cleanUp` 都抛出异常，那么程序只会把第二个异常抛出来，原来的第一个异常（正确的原因）将永远丢失。

8.始终只捕获实际可处理的异常。

```java
catch (NoSuchMethodException e) {
   throw e; //避免这种情况，因为它没有任何帮助
}
```

- 不要为了捕捉异常而捕捉，只有在想要处理异常时才捕捉异常。

9.不要使用`printStackTrace()`语句或类似的方法。

10.如果你不打算处理异常，请使用`finally`块而不是`catch`块。

11.应该尽快抛出(throw)异常，并尽可能晚地捕获(catch)它。你应该做两件事：封装你的异常在最外层进行捕获，并且处理异常。

12.在捕获异常之后，需要通过finally 进行收尾。在使用io或者数据库连接等，最终需要去关闭并释放它。

13.不要使用`if-else` 来控制异常的捕获。

14.一个异常只能包含在一个日志中。

```java
// 错误
LOGGER.debug("Using cache sector A");
LOGGER.debug("Using retry sector B");

// 正确
LOGGER.debug("Using cache sector A, using retry sector B");
```

15.将所有相关信息尽可能地传递给异常。有用且信息丰富的异常消息和堆栈跟踪也非常重要。

16.在`JavaDoc`中记录应用程序中的所有异常。应该用`javadoc`来记录为什么定义这样一个异常。

17.异常应该有具体的层次结构。如果异常没有层次的话，则很难管理系统中异常的依赖关系。

类似这样

```java
class Exception {}
class BusinessException extends Exception {}
class AccountingException extends BusinessException {}
class BillingCodeNotFoundException extends AccountingException {}
class HumanResourcesException extends BusinessException {}
class EmployeeNotFoundException extends HumanResourcesException {}
```