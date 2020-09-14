# 前置条件

在开发之前，请确保

- 本地项目已经创建成功，详见 [新建项目](http://hzerodoc.saas.hand-china.com/zh/docs/development-guide/backend-develop-guid/demo/create_project/)
- 数据库创建成功，详见 [初始化数据库](http://hzerodoc.saas.hand-china.com/zh/docs/development-guide/backend-develop-guid/demo/init_db/)

## 介绍

此 `demo` 需涉及到 `domain` 层的 `entity`、多 `entity` 的 `service`、`repository` 接口类以及 `infra` 层的 `repository` 实现类

## 编写`entity`

## .1 实体规范

- 实体继承 `AuditDomain`，AuditDomain 包含标准的审计字段
- 使用 `@Table` (javax.persistence.Table) 映射表名
- 使用 `@ModifyAudit` 注解标明在更新数据时需要更新 lastUpdateDate、lastUpdatedBy 两个审计字段
- 使用 `@VersionAudit` 注解标明在更新数据时需要更新版本号 objectVersionNumber
- 使用 `@ApiModel` 注解说明实体含义，在 Swagger 文档上就可以看到实体说明。
- 实体主键使用 `@Id` (javax.persistence.Id) 注解标注
- 对于自增张、序列（SEQUENCE）类型的主键，需要添加注解 `@GeneratedValue`。 序列命名规范：`表名_S`。例如：表`SYS_USER`对应的序列为 `SYS_USER_S`。
- 实体字段使用 `@ApiModelProperty` 说明字段含义，在 Swagger 文档上可以看到字段说明。
- 非数据库字段使用 `@Transient` 注解标注，如果页面用到的非数据库字段比较多，建议使用 DTO 封装数据。
- 所有属性均为`private`属性，每一个属性需要生成对应的 `getter`、`setter` 方法。
- 字段名称应根据驼峰命名规则从数据库列名转换过来。例如：数据库列名为 `USER_NAME` ，则字段名为 `userName`，特殊字段名称，可以在字段在添加 `@Column(name = "xxx")`注解，指定数据库列名。
- 不使用基本类型，全部使用基本类型的包装类，如 `Long` 对应数据库中的 `INTEGER`，而不是使用 `long`。
- 数字类型主键统一采用 `Long`。金额、数量 等精度严格浮点类型采用 `BigDecimal` (注意：BigDecimal 在计算、比较方面的特殊性)
- 实体中可以包含一些实体自治的方法，这些方法通常用于对本身的属性做一些计算、操作等。

## .2 `User.java` 代码

```java
package org.hzero.todo.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;

import org.hzero.core.util.Regexs;

@ApiModel("用户信息") // Swagger 实体描述
@ModifyAudit //在类上使用，启用审计字段支持，实体类加上该注解后，插入和更新会启动对 lastUpdateDate、lastUpdatedBy 自维护字段支持
@VersionAudit //在类上使用，启用objectVersionNumber自维护支持，插入一条数据objectVersionNumber默认为1，每次update后objectVersionNumber自增1
@Table(name = "todo_user") // 表映射
@JsonInclude(JsonInclude.Include.NON_NULL) // 数据返回前端时，排除为空的字段
public class User extends AuditDomain { //AuditDomain包含5个自维护字段，使用@ModifyAudit和@VersionAudit的实体类要继承该类

    @Id // 主键主键，注意是 javax.persistence.Id
    @GeneratedValue //对于自增张、序列（SEQUENCE）类型的主键，需要添加该注解
    private Long id;
    @Length(max = 30) // 长度控制
    @NotBlank // 非空控制
    @ApiModelProperty("员工姓名") // Swagger 字段描述
    private String employeeName;
    @Length(max = 30)
    @NotBlank
    @Pattern(regexp = Regexs.CODE, message = "htdo.warn.user.numberFormatIncorrect") // 格式控制
    @ApiModelProperty("员工编号")
    private String employeeNumber;
    @Length(max = 60)
    @Pattern(regexp = Regexs.EMAIL, message = "htdo.warn.user.emailFormatIncorrect")
    @ApiModelProperty("员工邮箱")
    private String email;

    // 省略 getter/setter 方法
}
```

## .3 `Task.java` 代码

```java
package org.hzero.todo.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;

@ApiModel("任务信息")
@ModifyAudit
@VersionAudit
@Table(name = "todo_task")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Task extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_STATE = "state";
    public static final String FIELD_TASK_DESCRIPTION = "taskDescription";

    @Id
    @GeneratedValue
    private Long id;
    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty("用户ID")
    private Long employeeId;
    @ApiModelProperty("任务状态")
    private String state;
    @ApiModelProperty("任务编号")
    private String taskNumber;
    @Length(max = 240)
    @ApiModelProperty("任务描述")
    private String taskDescription;
    @NotNull
    @ApiModelProperty("租户ID")
    private Long tenantId;

    @Transient
    @ApiModelProperty("员工编号")
    private String employeeNumber;
    @Transient
    @ApiModelProperty("员工姓名")
    private String employeeName;

    /**
     * 生成任务编号
     */
    public void generateTaskNumber() {
        this.taskNumber = UUID.randomUUID().toString().replace("-", "");
    }

    // 省略 getter/setter
}
```

## 编写Repository

## .1 `Repository` 接口类

- `Repository` 接口类定义了数据操作的一系列接口，并不提供实现，具体实现需要通过 `Repository`实现层提供。创建在项目模块的 `xxx.domain.repository` 包下。
- 每一个 `Repository` 对应一个 `entity` ，命名为 `entity` 类名尾缀替换为 `Repository`。如：`TaskRepository` 对应 `Task` 。
- `Repository` 继承 `BaseRepository<T>`，`BaseRepository` 封装了基本的资源库增删改查、批量增删改等。单表查询基本就不需要我们再写方法了。

## .2 `UserRepository.java` 代码

```java
package org.hzero.todo.domain.repository;

import org.hzero.mybatis.base.BaseRepository;
import org.hzero.todo.domain.entity.User;

/**
 * 用户资源库
 */
public interface UserRepository extends BaseRepository<User> {

}
```

## .3 `TaskRepository.java` 代码

```java
package org.hzero.todo.domain.repository;

import java.util.List;
import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.mybatis.base.BaseRepository;
import org.hzero.todo.domain.entity.Task;

/**
 * 任务资源库
 */
public interface TaskRepository extends BaseRepository<Task> {

    /**
     * 分页查询任务
     * 
     * @param task Task
     * @param pageRequest 分页参数
     * @return Page<Task>
     */
    Page<Task> pageTask(Task task, PageRequest pageRequest);

    /**
     * 根据员工ID查询任务
     * 
     * @param employeeId 员工ID
     * @return List<Task>
     */
    List<Task> selectByEmployeeId(Long employeeId);

    /**
     * 根据任务编号查询任务详细(包含员工姓名)
     * 
     * @param taskNumber 任务编号
     * @return Task
     */
    Task selectDetailByTaskNumber(String taskNumber);
}
```

## 编写Service

## .1 `Domain Service` 接口类

- 领域层的`Service` 不是程序必要组成，如没有特殊或复杂的业务逻辑处理，则可以不需要领域服务类。
- 领域层的`Service` 是业务软件的核心，是反应多个领域模型的业务情况的具体实现，是领域模型对外提供的实际服务。
- `Service` 接口类定义了业务操作的一系列接口，并不提供实现，具体实现需要通过服务实现层提供，所以属于供应方的服务接口层。创建在项目模块 的 `xxx.domain.service` 包下。
- 每一个 `Service` 对应多个 `entity` 类，因需要与`app`层`service`区分，所以规定命名为 `I + 涉及主要entity类名 + Service`。如：`ITaskService`。

## .2 Service 实现类

- `Service` 接口的具体实现通过服务实现层提供，所以属于供应方的服务实现层。创建在项目模块的 `xxx.domian.service.impl` 包下。
- 实现类，如无特殊情况，需要用 `@Service` 标注，以自动扫描注册