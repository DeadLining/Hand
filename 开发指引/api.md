# 前置条件

在开发之前，请确保

- 本地项目已经创建成功，详见 [新建项目](http://hzerodoc.saas.hand-china.com/zh/docs/development-guide/backend-develop-guid/demo/create_project/)
- 数据库创建成功，详见 [初始化数据库](http://hzerodoc.saas.hand-china.com/zh/docs/development-guide/backend-develop-guid/demo/init_db/)

## 介绍

此 `demo` 需涉及 `api` 层的 `controller` 。

## 编写 `Controller`

- `Controller` 负责对 `Model` 和 `View` 的处理，创建在项目模块的 `xxx.api.controller.v1` 包下。如 `xxx.api.controller.v1`。
- 需要通过 `@Controller` 指定该类为一个 `Controller` 类。

## 编写 SwaggerApiConfig

- Controller 在 Swagger 上的描述需要定义在配置文件中
- 一般会把配置相关的建在 `config` 包下，如 `xxx.config`
- `SwaggerApiConfig` 需要使用 `@Configuration` 注解标注

## .1 `SwaggerApiConfig.java` 代码

```java
package org.hzero.todo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.Tag;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger Api 描述配置
 */
@Configuration
public class SwaggerApiConfig {
    public static final String USER = "User";
    public static final String TASK = "Task";


    @Autowired
    public SwaggerApiConfig(Docket docket) {
        docket.tags(
                new Tag(USER, "用户信息"),
                new Tag(TASK, "任务信息")
        );
    }
}
```

## `Controller` 类相关标签

- `@RestController`，是一个组合注解，是 `@ResponseBody` 和 `@Controller` 的组合。

- ```
  @Permission
  ```

  ，设置API访问权限，常用有三种属性

  - level ：设置访问资源层级，包括 `site`，`organization` 两种层级
- permissionLogin ：设置是否需要登录访问
  - permissionPublic ：设置任意访问。
  
- `@ApiOperation` ，显示在swagger ui上的接口注释，同时与该接口对应的权限表中的描述字段对应(iam_permission.description)

- `@GetMapping` ，是`@RequestMapping(mathod = RequestMethod.GET)`的缩写，`@PostMapping`等同理。

- `@Api(tags = SwaggerApiConfig.TASK)`，在类上对类进行说明，显示在 Swagger 文档上

- `@ApiImplicitParams`，在方法上对方法参数进行说明，显示在 Swagger 文档上

- `@ApiParam`，在方法参数上对参数进行说明，显示在 Swagger 文档上

## .1 `UserController.java`代码

```java
package org.hzero.todo.api.controller.v1;

import ....;

/**
 * 用户接口
 */
@Api(tags = SwaggerApiConfig.USER)
@RestController("userController.v1")
@RequestMapping("/v1/users")
public class UserController extends BaseController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Permission(level = ResourceLevel.SITE)
    @ApiOperation(value = "分页查询用户")
    @GetMapping
    public ResponseEntity<Page<User>> list(User user, PageRequest pageRequest) {
        return Results.success(userRepository.pageAndSort(pageRequest, user));
    }

    @Permission(level = ResourceLevel.SITE)
    @ApiOperation(value = "创建 todo 用户")
    @PostMapping
    private ResponseEntity<User> create(@RequestBody User user) {
        // 简单数据校验
        this.validObject(user);
        // 创建用户
        return Results.success(userService.create(user));
    }

    @Permission(level = ResourceLevel.SITE)
    @ApiOperation(value = "删除 todo 用户")
    @DeleteMapping
    private ResponseEntity<User> delete(@RequestBody User user) {
        // 数据防篡改校验
        SecurityTokenHelper.validToken(user);
        // 删除用户
        userService.delete(user.getId());
        return Results.success();
    }
}
```

## .2 `TaskController.java`代码

```java
package org.hzero.todo.api.controller.v1;

import ....;

/**
 * 任务接口(全是租户级接口)
 */
@Api(tags = SwaggerApiConfig.TASK)
@RestController("taskController.v1")
@RequestMapping("/v1/{organizationId}/tasks")
public class TaskController extends BaseController {

    private final TaskService taskService;
    private final TaskRepository taskRepository;

    public TaskController(TaskService taskService, TaskRepository taskRepository) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }

    /**
     * 注意分页参数是 io.choerodon.mybatis.pagehelper.domain.PageRequest;
     */
    @Permission(level = ResourceLevel.ORGANIZATION)
    @ApiOperation(value = "根据taskNumber分页查询task")
    @GetMapping
    public ResponseEntity<Page<Task>> list(@PathVariable("organizationId") Long tenantId, Task task, PageRequest pageRequest) {
        task.setTenantId(tenantId);
        return Results.success(taskRepository.pageTask(task, pageRequest));
    }

    @Permission(level = ResourceLevel.ORGANIZATION)
    @ApiOperation(value = "创建task")
    @PostMapping
    public ResponseEntity<Task> create(@PathVariable("organizationId") Long tenantId, @RequestBody Task task) {
        task.setTenantId(tenantId);
        // 简单数据校验
        this.validObject(task);
        return Results.success(taskService.create(task));
    }

    @Permission(level = ResourceLevel.ORGANIZATION)
    @ApiOperation(value = "更新task")
    @PutMapping
    public ResponseEntity<Task> update(@PathVariable("organizationId") Long tenantId, @RequestBody Task task) {
        // 简单数据校验
        this.validObject(task);
        // 数据防篡改校验
        SecurityTokenHelper.validToken(task);
        return Results.success(taskService.update(task));
    }

    @Permission(level = ResourceLevel.ORGANIZATION)
    @ApiOperation(value = "根据taskNumber查询task")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "任务编号", paramType = "string")
    })
    @GetMapping("/{taskNumber}")
    public ResponseEntity<Task> query(@PathVariable Long organizationId, @PathVariable String taskNumber) {
        return Results.success(taskRepository.selectDetailByTaskNumber(taskNumber));
    }

    @Permission(level = ResourceLevel.ORGANIZATION)
    @ApiOperation(value = "根据taskNumber删除task")
    @DeleteMapping("/{taskNumber}")
    public void delete(@PathVariable Long organizationId, @PathVariable @ApiParam(value = "任务编号") String taskNumber) {
        taskService.deleteByTaskNumber(taskNumber);
    }
}
```

## 编写`DTO`

- `DTO` 类用来封装用户请求的数据信息，可以用来屏蔽一些程序交互细节。
- 创建在项目模块的 `xxx.api.dto` 包下，DTO不是必要选项，需要根据需求自行决定。