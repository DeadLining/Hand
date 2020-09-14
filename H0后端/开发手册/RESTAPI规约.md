## 基本设计原则

### API根URL

如果预期系统非常庞大，则建议尽量将API部署到独立专用子域名（例如：“api.”）下；如果确定API很简单，不会进一步扩展，则可以考虑放到应用根域名下面（例如，“/api/”）。

- 独立子域名：`https://api.example.com/v1/*`
- 共享应用根域名：`https://example.org/api/v1/*`



### URI末尾不要添加“/”

多一个斜杠，语义完全不同，究竟是目录，还是资源，还是不确定而多做一次301跳转？尽量保持URI结构简洁、语义清晰。

- 负面case：`http://api.canvas.com/shapes/`
- 正面case：`http://api.canvas.com/shapes`



### 禁止在URL中使用“_”

目的是提高可读性，“_”可能被文本查看器中的下划线特效遮蔽。建议使用连字符“-”替代下划线“_”,使用“-”提高URI的可读性。

- 负面case：`http://api.example.com/blogs/my_first_post`
- 正面case：`http://api.example.com/blogs/my-first-post`



### 禁止使用大写字母

RFC 3986中规定URI区分大小写，但别用大写字母来为难程序员了，既不美观，又麻烦，同样的原则：建议使用连字符“-”连接不同单词。

- 负面case：`http://api.example.com/My-Folder/My-Doc`
- 正面case：`http://api.example.com/my-folder/my-doc`



### 不要在URI中包含扩展名

应鼓励REST API客户端使用HTTP提供的格式选择机制Accept request header。

- 负面case：`http://api.example.com/my-doc/hello.json`
- 正面case：`http://api.example.com/my-doc/hello`



### 建议URI中的名称使用复数

为了保持URI格式简洁统一，资源在URI中应统一使用复数形式，如需访问资源的一个实例，可以通过资源ID定位（@PathVariable）。

- 负面case：`http://api.college.com/student/3248234`
- 正面case：`http://api.college.com/students/3248234`

如何处理关联关系？

- `http://api.college.com/students/3248234/courses` - 检索学生3248234所学习的所有课程
- `http://api.college.com/students/3248234/courses/physics` - 检索学生3248234的所学习的物理课程



### 建议URI设计时只包含名词，不包含动词

每个URI代表一种资源或者资源集合，因此，建议只包含名词，不包含动词。

- 负面case：`http://api.example.com/get-all-employees`
- 正面case：`http://api.example.com/employees`

那么，如何告诉服务器端我们需要进行什么样的操作？CRUD？ 答案是由HTTP动词表示。

- `GET`（SELECT）：从服务器取出资源（一项或多项）。
- `POST`（CREATE）：在服务器新建一个资源。
- `PUT`（UPDATE）：在服务器更新资源（客户端提供改变后的完整资源）。
- `PATCH`（UPDATE）：在服务器更新资源（客户端提供改变的属性）。

[PATCH和PUT方法的区别?][https://segmentfault.com/q/1010000005685904]

- `DELETE`（DELETE）：从服务器删除资源。



### 尽量减少对第三方开发人员的随意约束

非常重要的一点：让第三方开发人员自己指定排序过滤器、返回结果集的约束条件；但强烈建议服务器端设置默认单页数量，否则，如果无限制，很可能造成服务器资源及网络资源过度消耗，响应缓慢，网络丢包等异常情况；同时，需要在API文档中明确默认约束条件。

------

## HZERO 设计规范

### 租户级URI设计时需要包含租户ID

存在版本段时，租户ID放在版本段后面，不存在则放在版本段位置。Controller 方法中需通过 `@PathVariable` 获取租户ID参数。另外平台级的API操作多租户，在参数中传递租户ID即可，不在URI中区分。

- 租户级例子：

  - `http://api.example.com/v1/{organizationId}/users`

  ```java
  @Api(tags = SwaggerApiConfig.USER)
  @RestController("userController.v1")
  @RequestMapping("/v1/{organizationId}/users")
  public class UserController extends BaseController {
      private UserRepository userRepository;
  
      public UserController(UserRepository userRepository) {
          this.userRepository = userRepository;
      }
  
      @Permission(level = ResourceLevel.ORGANIZATION)
      @ApiOperation(value = "租户级-用户查询")
      @GetMapping
      public ResponseEntity<Page<UserVO>> listUser(@PathVariable("organizationId") Long tenantId, UserVO params,
                                                   @SortDefault(value = User.FIELD_ID) PageRequest pageRequest) {
          params.setTenantId(tenantId);
          return Results.success(userRepository.selectAllocateUsers(params, pageRequest));
      }
  }
  ```

- 平台级例子：

  - `http://api.example.com/v1/users`

  ```java
  @Api(tags = SwaggerApiConfig.SITE_USER)
  @RestController("userSiteController.v1")
  @RequestMapping("/v1/users")
  public class UserSiteController extends BaseController {
      private UserRepository userRepository;
          
      public UserSiteController(UserRepository userRepository) {
          this.userRepository = userRepository;
      }
  
      @Permission(level = ResourceLevel.ORGANIZATION)
      @ApiOperation(value = "平台级-用户查询")
      @GetMapping
      public ResponseEntity<Page<UserVO>> listUser(@RequestParam Long tenantId, UserVO params,
                                                   @SortDefault(value = User.FIELD_ID) PageRequest pageRequest) {
          params.setTenantId(tenantId);
          return Results.success(userRepository.selectAllocateUsers(params, pageRequest));
      }
  }
  ```



### 平台级、租户级API类名规范

微服务中可能会同时存在平台级、租户级的功能，为了便于管理和维护方便，建议如下：

- 租户级API类结构：`UserController.java`
- 平台级API类结构：`UserSiteController.java`

**注意：**一个功能的API如果没有同时存在平台级和租户级的API，可以不进行区分



### 对外提供接口的方法用 @Permission 注解标注

Permission 注解包含如下参数：

| 参数             | 说明                                                         |
| :--------------- | :----------------------------------------------------------- |
| code             | 权限编码，默认取方法名称                                     |
| level            | 接口层级，ResourceLevel.SITE(平台级)、ResourceLevel.ORGANIZATION(租户级) |
| permissionLogin  | 是否登录可访问，设置为 true 时，只要用户登录就能访问，默认 false |
| permissionPublic | 是否公开接口，设置为 true 时，不需要登录就能访问，默认 false |
| permissionWithin | 是否内部接口，设置为 true 时，只允许服务内部调用，默认false  |
| tags             | 权限标签                                                     |

> **设计自检：**

- 1、哪些能是public的，比如登录、注册页面获取验证码
- 2、哪些能是login的，比如查询导航栏菜单、公告、个人中心信息等
- 3、哪些租户级会用全局，主要针对查询功能，比如租户需要查全局币种，平台也要查，这是两个controller，可调用同一个查询方法，服务端代码实现复用

------

## 服务合并API规范

考虑到未来服务合并的可能性，尽量在开发 API 时，加上服务路由前缀，以避免服务合并时API冲突。

例如合并 hzero-iam 服务和 hzero-platform 服务：

```
@RequestMapping("/iam/v1/users")
public class UserController {
	
}

@RequestMapping("/hpfm/v1/companies")
public class CompanyController {
	
}
```

服务合并后，需禁止去掉服务路由前缀：

<img src="http://hzerodoc.saas.hand-china.com/img/docs/development-specification/backend-development-specification/api/api_1577264752.png" alt="img" style="zoom:80%;" />

------

## HTTP响应设计

当客户端通过 API 向服务器发起请求时，无论请求是成功、失败还是错误，客户端都应该获得反馈。HTTP 状态码是一堆标准化的数值码，在不同的情况下具有不同的解释。服务器应始终返回正确的状态码。
完整状态码参见：[Status Code Definitions](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html)

#### 2xx (成功类别)

这些状态代码表示请求的操作已被服务器接收到并成功处理。

- `200` OK - [GET]：服务器成功返回用户请求的数据，该操作是幂等的（Idempotent）。
- `201` CREATED - [POST/PUT/PATCH]：用户创建或修改实例成功时，应返回此状态代码。例如，使用 POST 方法创建一个新的实例，应该始终返回 201 状态码。
- `202` Accepted - [*]：表示一个请求已经进入后台排队（异步任务）
- `204` NO CONTENT - [DELETE]：内容不存在，表示请求已被成功处理，但并未返回任何内容。例如，DELETE算是其中一个很好的例子，用户删除数据成功。*`API DELETE /companies/43/employees/2`* 将删除员工 2，作为响应，我们不需要在该 API 的响应正文中的任何数据，因为我们明确地要求系统将其删除。如果有任何错误发生，例如，如果员工 2 在数据库中不存在，那么响应码将不是 2xx 对应的成功类别，而是 4xx 客户端错误类别。

#### 3xx (重定向类别)

- `304` Not Modified - [GET]：未修改，表示客户端的响应已经在其缓存中。 因此，不需要再次传送相同的数据。

#### 4xx (客户端错误类别)

这些状态代码表示客户端发起了错误的请求。

- `400` INVALID REQUEST - [POST/PUT/PATCH]：用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的。
- `401` Unauthorized - [*]：未授权：表示客户端不被允许访问该资源，需要使用指定凭证重新请求（令牌、用户名、密码等）。
- `403` Forbidden - [*]：禁止访问，表示请求是有效的并且客户端已通过身份验证（与401错误相对），但客户端不被允许以任何理由访问对应页面或资源。 例如，有时授权的客户端不被允许访问服务器上的目录。
- `404` NOT FOUND - [*]：未找到，表示所请求的资源现在不可用。例如，用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
- `410` Gone - [GET]： 资源不可用，表示所请求的资源后续不再可用，该资源已被永久移动。
- `422` Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。

#### 5xx（服务器错误类别）

表示服务器端发生异常。

- `500` 服务器内部错误，表示请求已经被接收到了，但服务器被要求处理某些未预设的请求而完全混乱。
- `503` 服务不可用表示服务器已关闭或无法接收和处理请求。大多数情况是服务器正在进行维护。

------

## API版本管理

#### 总体建议

1. 建议通过URI指定服务版本，版本采用字符“v”+数字主版本号，例如，/v1/xxxs
2. 建议版本控制在资源层面，也即Controller维度
3. 服务后端分包建议规则如下：
   - Domain: `com.xxx.[user].domain.entity`
   - Api:
     - `com.xxx.api.[user].controller.v1`
     - `com.xxx.api.[user].controller.v2`
4. API升级建议
   - 尽量做兼容性设计，不要随便升级版本
   - 升级可分步实施，达到最终整个资源整体升级的目的，不同版本相互独立，便于后续拆分独立部署、独立维护等（可能存在代码重复，尽可能抽象通用服务或方法）
     - 先将资源的部分方法升级至高版本，并进行灰度发布，例如，/v1/users/check升级至 /v2/users/check，其他API版本维持v1不变
     - 升级资源的所有方法至高版本，并发布公告，要求客户限期升级API
     - 停止旧版本服务

#### URL中指定版本

1、URI上添加版本号：例如，`https://api.example.org/v1/users`
2、参数中添加版本号： 例如，`https://api.example.org/users?v=1.0`

好处：

- 直接可以在URI中直观的看到API版本；
- 可以直接在浏览器中查看各个版本API的结果；

坏处：

- 版本号在URI中破坏了REST的HATEOAS（hypermedia as the engine of application state）规则。版本号和资源之间并无直接关系。

------

## Action 命名规范

#### 类别

| Description  | Action Name | HTTP Mapping | HTTP Request Body | HTTP Response Body |
| :----------- | :---------- | :----------- | :---------------- | :----------------- |
| 查询所有     | list        | GET          | N/A               | Resource* list     |
| 获取单个资源 | query       | GET          | N/A               | Resource*          |
| 创建单个资源 | create      | POST         | Resource          | Resource*          |
| 更新单个资源 | update      | PUT          | Resource          | Resource*          |
| 删除单个资源 | delete      | DELETE       | N/A               | Empty              |

#### List

`List` 方法接受一个 `Collection id` 和0或多个参数作为输入,并返回一个列表的资源。

- `List` 必须使用 `GET` 方法
- 接口必须以 `collection id` 结尾。
- 其他的请求字段必须作为 url 查询参数。
- 没有请求体，接口上必须不能包含request body。
- 响应体应该包含一组资源和一些可选项元数据。
- 没有资源可返回时，需返回一个空列表，禁止直接返回null。
- 响应HttpStatus 200。

#### Query

`Query` 方法接受一个 `Resource name` 和0或多个参数,并返回指定的资源。

- `Query` 必须使 `GET` 方法。
- 请求url 必须包含 `Resource name`。
- 其他的请求字段必须作为 url 查询参数。
- 没有请求体，接口上必须不能包含request body。
- 响应体应该返回整个资源对象。
- 响应HttpStatus 200。

#### Create

`Create` 方法接受一个 `Collection id` ,一个资源,和0或多个参数。它创建一个新的资源在指定的父资源下,并返回新创建的资源。

- `Create` 必须使用 `POST` 方法。
- 应该包含父资源名用于指定资源创建的位置。
- 创建的资源应该对应在request body。
- 响应体应该返回整个资源对象。
- `Create` 必须传递一个resource，这样即使resource 的结构发生变化，也不需要去更新方法或者资源结构，那些弃用的字段则需要标识为“只读”。
- 响应HttpStatus 201。

#### Update

`Update` 方法接受一个资源和0或多个参数。更新指定的资源和其属性,并返回更新的资源。

- 除了`Resource Name` 和其父资源之外，这个资源的所有属性应该是可以更新的。资源的重命名和移动则需要自定义方法。
- 如果只支持一个完整对象的更新，`Update` 必须使用 `PUT` 方法。
- `Resource Name`必须包含在请求的url中。
- 资源应该对应在request body。
- 响应HttpStatus 200。

#### Delete

`Delete` 方法接受一个`Resource Name` 和0或多个参数,并删除指定的资源。

- `Delete` 必须使用 `DELETE` 方法。
- `Resource Name` 必须包含在请求的url中。
- 没有请求体，接口上必须不能包含request body。
- 如果是立即删除，应该返回空
- 如果是启动一个删除操作，应该返回一个删除操作。
- 如果只是标识某个资源是“被删除的”，应该返回一个更新后的资源。
- 如果多个删除请求删除同一资源，那么只有第一个请求才应该成功，其他的返回not found。
- 响应HttpStatus 204。

------

## 自定义方法

自定义的方法应该参考5个基本方法。应该用于基本方法不能实现的功能性方法。可能需要一个任意请求并返回一个任意的响应,也可能是流媒体请求和响应。

可以对应a resource, a collection 甚至 a service。

- 自定义方法应该**使用 `POST` 方法**。不应该使用`PATCH` 方法。
- 自定义方法对应的 `Resource Name` 或者 `Collection id` 必须包含在请求的url中。
- 如果使用的HTTP 方法接受request body，则需要对应一个请求体。
- 如果使用的HTTP 方法不接受request body，则需要声明不使用body，并且参数应该作为url查询参数。

#### 批量添加

| Description | Action Name | HTTP Mapping       | HTTP Request Body | HTTP Response Body |
| :---------- | :---------- | :----------------- | :---------------- | :----------------- |
| 批量添加    | batchCreate | POST /batch-create | Resource* list    | Resource IDS       |

#### 批量删除

| Description | Action Name | HTTP Mapping       | HTTP Request Body | HTTP Response Body |
| :---------- | :---------- | :----------------- | :---------------- | :----------------- |
| 批量删除    | batchDelete | POST /batch-delete | Resource IDS      | Empty              |

#### 更新单个资源中的属性

| Description    | Action Name     | HTTP Mapping                  | HTTP Request Body | HTTP Response Body         |
| :------------- | :-------------- | :---------------------------- | :---------------- | :------------------------- |
| 更新资源的状态 | updateAttribute | POST /:attribute?value=       | N/A               | {“key”:“”,“value”:“”}      |
| 更新用户的年龄 | updateAge       | POST /v1/users/1/age?value=20 | N/A               | {“key”:“age”,“value”:“20”} |

#### 对资源执行某一动作

- 比如发送消息，启用什么功能。
- 如果是针对资源，则`Action Name`为动词。
- 如果是针对资源的属性，则`Action Name`为动词+属性名。请求以动词结尾，属性作为参数。

| Description            | Action Name | HTTP Mapping                    | HTTP Request Body | HTTP Response Body |
| :--------------------- | :---------- | :------------------------------ | :---------------- | :----------------- |
| 对资源执行某一动作     | customVerb  | POST /custom-verb               | N/A               | *                  |
| 取消某种操作           | cancel      | POST /cancel                    | N/A               | Boolean            |
| 从回收站中恢复一个资源 | undelete    | POST /v1/projects/1/undelete    | N/A               | Boolean            |
| 检查项目是否重名       | checkName   | POST /v1/projects/1/check?name= | N/A               |                    |

#### 查询某一资源的单个属性

- 对于单个资源的所有的查询`Action Name`，都需要以query开头。
- `Action Name`以query+属性名结尾

| Description      | Action Name    | HTTP Mapping             | HTTP Request Body | HTTP Response Body            |
| :--------------- | :------------- | :----------------------- | :---------------- | :---------------------------- |
| 查询资源的某属性 | queryAttribute | GET /:attribute          | N/A               | {“key”:“”,“value”:“”}         |
| 查询用户的年龄   | queryAge       | GET /v1/users/1/age      | N/A               | {“key”:“age”,“value”:“25”}    |
| 查询用户下的项目 | queryProjects  | GET /v1/users/1/projects | N/A               | {“key”:“projects”,“value”:[]} |

#### 查询 collection 的数量

- 计算集合自身的数量，使用count作为`Action Name`
- 计算资源中子集合的数量，使用count+集合名作为`Action Name`

| Description              | Action Name   | HTTP Mapping                   | HTTP Request Body | HTTP Response Body                    |
| :----------------------- | :------------ | :----------------------------- | :---------------- | :------------------------------------ |
| 查询Collection 的数量    | count         | GET /count                     | N/A               | {“key”:“”,“count”:“”}                 |
| 查询组织的数目           | count         | GET /v1/organizations/count    | N/A               | {“key”:“organizations”,“count”:“100”} |
| 查询用户下的所有项目数量 | countProjects | GET /v1/users/1/projects/count | N/A               | {“key”:“projects”,“count”:“100”}      |

#### 复杂条件查询

- 对于collection的所有查询`Action Name`，都需要以list开头。
- 查询的条件中，如果条件为一到两个，使用`By`和`And`。eg.: listByUserIdAndName
- 如果查询条件大于3个，则使用`ByOptions`，查询条件作为请求体传入。eg.: `listByOptions`

------

## Action Demo

```java
@RestController("/v1/users")
public class UserController {

    @GetMapping
    public ResponseEntity<?> list() {
        return Results.success(new ArrayList<User>());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> query(@PathVariable("id") String id) {
        return Results.success(new User(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) {
        return Results.success(user);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody User user) {
        return Results.success(user);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody User user) {
        return Results.success();
    }

    @PostMapping("/batch-create")
    public ResponseEntity<?> batchCreate(@RequestBody List<User> users) {
        return Results.success(users);
    }

    @PostMapping("/batch-delete")
    public ResponseEntity<>> batchDelete(@RequestBody List<User> users) {
        return Results.success();
    }

    @PostMapping("/age")
    public ResponseEntity<?> updateAge(@RequestBody User user) {
        return Results.success(user);
    }

    @PostMapping("/undelete")
    public ResponseEntity<?> undelete(@RequestBody User user) {
        return Results.success();
    }

    @PostMapping("/check")
    public ResponseEntity<?> checkName(@RequestParam("name") String name) {
        return Results.success();
    }

    @GetMapping("/{id}/age")
    public ResponseEntity<?> queryAge(@PathVariable("id") String id) {
        return Results.success(18);
    }

    @GetMapping("/{id}/name")
    public ResponseEntity<?> queryByUserIdAndName(@PathVariable("id") String id, @RequestParam("name") String name) {
        return Results.success(new User(id, name));
    }

    @GetMapping("/{id}/projects/count")
    public ResponseEntity<?> countProjects(@PathVariable("id") String id, @RequestParam("name") String name) {
        return Results.success(1);
    }

    @GetMapping
    public ResponseEntity<?> listByOptions(@RequestBody Map<String, Object> options) {
        return Results.success(new ArrayList<User>());
    }

}
```

------

## 数据返回及异常规范

- 数据统一使用 `org.springframework.http.ResponseEntity` 封装返回到前端。
- 使用 `io.choerodon.core.exception.ExceptionResponse(boolean failed, String message)` 封装异常或错误消息，凡是可预见的异常或返回， `ResponseEntity` 的状态码始终是 `200`。
- 前端通过判断 `failed=true` 来判断是否失败，请求成功则没有 `failed` 属性。因此不允许使用 `failed` 作为表字段或正常情况的返回字段。
- 后端必须对传入的数据做合法性校验，可以包装异常抛出，程序会自动捕获。
- 程序自主抛出的异常统一使用 `io.choerodon.core.exception.CommonException` 封装对应的错误码抛出。
- 程序中可预见的及捕获的异常尽量封装成 `CommonException` 抛出，由程序自动处理。
- 后端不需要返回细节的异常信息，如某个字段不为空。只需返回大范围上的异常信息，如`数据校验不通过`，`程序错误，请联系管理员` 。
- 前端应该对数据做好校验工作，按正常情况，视前端到后端的数据都是合法的。