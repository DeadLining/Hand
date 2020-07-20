- @SpringBootApplication

  入口类

  包含@ComponentScan和@EnableAutoConfiguration

- @ImportResource（location=""）

  扫描xml配置文件中的内容

- @RestController

  控制类

- @RequestMapping

  映射路径

- @Configuration

  配置类

- @Bean

  bean对象自动创建

- @Component

  bean对象自动创建

  [link]: https://blog.csdn.net/junge1545/article/details/94013608	"@Bean VS @Component"

- @Test

  测试方法

- @RequestBody

  响应体

- @ResponseBody

  请求体

  [link]: https://blog.csdn.net/justry_deng/article/details/80972817	"@RequestBody VS @ResponseBody"

- @Data

  注解在类上, 为类提供读写属性, 此外还提供了 equals()、hashCode()、toString() 方法

  > **条件：**
  >
  > 1. IDEA需要安装Lombok插件
  >
  >    <img src="SB+MyBatis.assets/image-20200714143722389.png" alt="image-20200714143722389" style="zoom:80%;" />
  >
  > 2. pom.xml文件中需要导入lombok依赖
  >
  >    <img src="SB+MyBatis.assets/image-20200714143803063.png" alt="image-20200714143803063" style="zoom:80%;" />

- @Value("${XXX}")

  通过@Value注解来读取application.properties里面的配置

- @Autowired

  自动装配Bean对象

- @Resource

  自动装配对象

  [link]: https://blog.csdn.net/h2453532874/article/details/83098504	"@Resource VS @AutoWired"

