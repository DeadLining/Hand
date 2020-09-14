## Swageer的使用

[link]: https://www.jianshu.com/p/a0caf58b3653	"Swagger"



### maven导入Swagger

```java
<dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger2</artifactId>
      <version>2.6.1</version>
</dependency>
<dependency>
     <groupId>io.springfox</groupId>
     <artifactId>springfox-swagger-ui</artifactId>
     <version>2.6.1</version>
</dependency>

```



### 创建Swagger2配置类

```java
/**
 * @program: jpademo
 * @description: Swagger
 * @author: ZengGuangfu
 * @create 2018-10-24 10:12
 */
@Configuration
@EnableSwagger2
public class Swagger {

	@Bean
	public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
.apis(RequestHandlerSelectors.basePackage("com.example.springbootjpa.jpademo.controller"))
            .paths(PathSelectors.any())
            .build();
	}

	public ApiInfo apiInfo(){
		return new ApiInfoBuilder()
            .title("利用swagger2构建的API文档")
            .description("用restful风格写接口")
            .termsOfServiceUrl("")
            .version("1.0")
            .build();
    }
}

```

如上所示，docket() 方法创建Docket的Bean对象，apiInfo()则是创建ApiInfo的基本信息



### 注解及其说明

- @Api : 用在类上，说明该类的主要作用。
- @ApiOperation：用在方法上，给API增加方法说明。
- @ApiImplicitParams : 用在方法上，包含一组参数说明。
- @ApiImplicitParam：用来注解来给方法入参增加说明。



### 测试登录

```java
localhost:8080/swagger-ui.html
```



### API详细说明

注释汇总

| 作用范围           | API                | 使用位置                         |
| :----------------- | :----------------- | :------------------------------- |
| 对象属性           | @ApiModelProperty  | 用在出入参数对象的字段上         |
| 协议集描述         | @Api               | 用于controller类上               |
| 协议描述           | @ApiOperation      | 用在controller的方法上           |
| Response集         | @ApiResponses      | 用在controller的方法上           |
| Response           | @ApiResponse       | 用在 @ApiResponses里边           |
| 非对象参数集       | @ApiImplicitParams | 用在controller的方法上           |
| 非对象参数描述     | @ApiImplicitParam  | 用在@ApiImplicitParams的方法里边 |
| 描述返回对象的意义 | @ApiModel          | 用在返回对象类上                 |

**注：**`@RequestMapping`此注解的推荐配置 

> value 
>  method 
>  produces