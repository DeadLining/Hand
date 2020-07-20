### IDE中Maven设置

`File->Settings->Build->Build Tools->Maven`中设置`Maven home directory`

<img src="SpringBoot知识点.assets\image-20200713200901079.png" alt="image-20200713200901079" style="zoom:80%;" />



### Controller类

```
@RestController
@EnableAutoConfiguration
@RequestMapping("/XXX")
public class DemoController {
}
```



### `pom.xml`

```java
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-devtools</artifactId>
		<optional>true</optional>
	</dependency>
</dependencies>
<build>
	<plugins>
		<plugin>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-maven-plugin</artifactId>
			<configuration>
				<fork>true</fork>
			</configuration>
		</plugin>
	</plugins>
</build>
```



### 定时任务

```java
//test1
@Component
public class SchedulerTask {

    private int count = 0;

    @Scheduled(cron = "*/6 * * * * ?")
    private void process(){
        System.out.println("this is scheduler task running " + (count++));
    }
}
//test2
@Component
public class Scheduler2Task {

    private static final SimpleDateFormat dataFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 6000)
    public void reportCurrentTime(){
        System.out.println("现在时间：" + dataFormat.format(new Date()));
    }
}
```

#### `@Scheduled`的三个参数

- `cron = "分(最小的单位是秒) 时 日 月 周 任务"`
- `fixdRate`
- `fixdDelay`

`fixedRate`任务两次执行时间间隔是任务的开始点，而`fixedDelay`的间隔是前次任务的结束与下次任务的开始



### 设置网页

网页输出的内容为`return`的内容

```java
@RestController
public class HelloWordController {
    @RequestMapping("/hello")
    public String index(){
        return "Hello World";
    }
}
```



### 设置服务的端口号

```java
//application.properties
server.port=8080
```



### 注解

**自动部署**

`@EnableAutoConfiguration`

**自动拦截Request网页请求：**

`@RequestMapping("/hello")`



### spring-boot-maven-plugin not found的解决方案

spring-boot-maven-plugin指定版本后成功解决。 修改后的pom.xml文件

```java
<plugin>  
 <groupId>org.springframework.boot</groupId>  
 <artifactId>spring-boot-maven-plugin</artifactId>  
 <version>2.2.2.RELEASE</version>
</plugin>
```



### Springboot 中@Component 跟@Bean的区别（装配Bean实例）

1. 注解的作用

   -  @Component注解表明一个类会作为组件类，并告知Spring要为这个类创建bean，@Component（@Controller、@Service、@Repository）通常是通过类路径扫描来自动侦测以及自动装配到Spring容器中。
   - @Bean注解告诉Spring这个方法将会返回一个对象，这个对象要注册为Spring应用上下文中的bean。通常方法体中包含了最终产生bean实例的逻辑，并且实例名就是方法名。

2. 举例

   - @Component

   ```java
   //eg1：
   @Component
   public class PushMsgQuartzJob extends QuartzJobBean {}
   //eg2:
   @Controller
   //在这里用Component，Controller，Service，Repository都可以起到相同的作用。@RequestMapping(″/web/controller1″)
   public class WebController {
   	.....
   }
   
   ```

   - 而@Bean的用途则更加灵活

     当我们引用第三方库中的类需要装配到Spring容器时，则只能通过@Bean来实现,如下代码段，对象名称是userExtendService

   ```java
   @Bean
   public UserExtendService userExtendService() {
       UserExtendServiceImpl userExtendService = new UserExtendServiceImpl();
       userExtendService.setConfig(config());
       userExtendService.setAMSService(amsService());
       userExtendService.setRestClient(restClient());
       return userExtendService;
   }
   
   ```

**总结**：@Component和@Bean都是用来注册Bean并装配到Spring容器中，但是Bean比Component的自定义性更强。可以实现一些Component实现不了的自定义装载类。



### SpringBoot中`@RequestBody`和`@RequestParam`的区别

[link]: https://blog.csdn.net/justry_deng/article/details/80972817

补充：注：如果参数前不写@RequestParam(xxx)的话，那么就前端可以有可以没有对应的xxx名字才行，如果有xxx名的话，那么就会自动匹配；没有的话，请求也能正确发送，只是参数的值为<font color='red'>默认值</font>（NULL）



### Spring注解之 @SuppressWarnings注解

简介：java.lang.SuppressWarnings是J2SE5.0中标准的Annotation之一。可以标注在类、字段、方法、参数、构造方法，以及局部变量上。
作用：告诉编译器忽略指定的警告，不用在编译完成后出现警告信息。

[link]: https://www.cnblogs.com/liaojie970/p/9009199.html

