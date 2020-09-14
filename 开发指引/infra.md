# 前置条件

在开发之前，请确保

- 本地项目已经创建成功，详见 [新建项目](http://hzerodoc.saas.hand-china.com/zh/docs/development-guide/backend-develop-guid/demo/create_project/)
- 数据库创建成功，详见 [初始化数据库](http://hzerodoc.saas.hand-china.com/zh/docs/development-guide/backend-develop-guid/demo/init_db/)

## 介绍

此 `demo` 需涉及到 `infra` 层的 `mapper` 类及 `repository` 实现类。

## Mapper

## .1 `mapper` 接口类

- `mapper` 接口类即为传统意义上的 `DAO`，但与 `interface` 不同，`mapper` 本身就是对数据访问的具体实现，所以属于供应方的服务实现层。创建在 项目模块 的 `xxx.infra.mapper` 包下。
- 每一个 `mapper` 接口类封装了对数据库表的操作，每一个 `mapper` 对应一个 `实体` 类，命名为 `实体` 类名尾缀替换为 `Mapper` 。如：`TaskMapper` 对应`实体`为 `Task` 类。
- 基础的 `CRUD` 操作不需要再次实现，通过继承 `BaseMapper<T>` 类实现。其中 `T` 为 对应 `实体` 的泛型。
- 复杂的数据库操作需要定义具体的接口方法。

## .2 `mapper.xml`

- `Mapper`的`xml`文件 是数据库的的具体映射，与 `Mapper` 接口同级，创建在 项目模块 `resources` 目录下的 `mapper` 目录下。
- `Mapper`的`xml`文件，与 `Mapper` 接口对应。所以命名与 `Mapper` 接口类相同。
- `Mapper`的`xml`文件非必须，由于继承BaseMapper类后基本的 `CRUD` 不需要进行配置，所以只有`CRUD`操作时不需要创建对应的 `xml` 文件。
- 对于自定义的数据库方法，需要创建对应的 `mapper.xml` 文件。
- `Mapper`的`xml` 中的操作 `id` 对应 `Mapper` 接口类的方法名。

## .3 `UserMapper.java` 代码

```java
package org.hzero.todo.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.hzero.todo.domain.entity.User;

/**
 * UserMapper
 */
public interface UserMapper extends BaseMapper<User> {

}
```

## .4 `TaskMapper.java` 代码

```java
package org.hzero.todo.infra.mapper;

import java.util.List;
import io.choerodon.mybatis.common.BaseMapper;
import org.hzero.todo.domain.entity.Task;

/**
 * TaskMapper
 */
public interface TaskMapper extends BaseMapper<Task> {

    /**
     * 查询任务
     * 
     * @param params 任务查询参数
     * @return Task
     */
    List<Task> selectTask(Task params);
}
```

## .5 `UserMapper.xml` 文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
        <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="org.hzero.todo.infra.mapper.UserMapper">

</mapper>
```

## .6 `TaskMapper.xml` 文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
        <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="org.hzero.todo.infra.mapper.TaskMapper">

    <select id="selectTask" resultType="org.hzero.todo.domain.entity.Task">
        select
            tt.id,
            tt.employee_id,
            tt.state,
            tt.task_number,
            tt.task_description,
            tt.tenant_id,
            tt.object_version_number,
            tu.employee_name,
            tu.employee_number
        from todo_task tt join todo_user tu on tt.employee_id = tu.id
        <where>
            <if test="taskNumber != null and taskNumber != ''">
                and tt.task_number = #{taskNumber}
            </if>
            <if test="taskDescription != null and taskDescription != ''">
                <bind name="taskDescriptionLike" value="'%' + taskDescription + '%'" />
                and tt.task_description like #{taskDescriptionLike}
            </if>
        </where>
    </select>

</mapper>
```

## Repository

- `Repository` 接口的具体实现。创建在项目模块的 `xxx.infra.repository.impl` 包下。
- 每一个 `Repository` 实现类对应一个 `Repository` 接口类，命名为 `Repository 接口类名 + Impl`。如：`TaskRepositoryImpl` 对应 `TaskRepository` 。
- `Repository` 继承 `BaseRepositoryImpl<T>` 类，该类是 `BaseRepository<T>` 的实现。
- 需要通过`@Component`纳入`spring`管理。

## .1 `UserRepositoryImpl.java` 代码

```java
package org.hzero.todo.infra.repository.impl;

import org.springframework.stereotype.Component;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.todo.domain.entity.User;
import org.hzero.todo.domain.repository.UserRepository;

/**
 * 用户资源库实现
 */
@Component
public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements UserRepository {

}
```

## .2 `TaskRepositoryImpl.java` 代码

```java
package org.hzero.todo.infra.repository.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.mybatis.common.Criteria;
import org.hzero.todo.domain.entity.Task;
import org.hzero.todo.domain.repository.TaskRepository;
import org.hzero.todo.infra.mapper.TaskMapper;

/**
 * 任务资源库实现
 */
@Component
public class TaskRepositoryImpl extends BaseRepositoryImpl<Task> implements TaskRepository {

    private final TaskMapper taskMapper;

    public TaskRepositoryImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public Page<Task> pageTask(Task task, PageRequest pageRequest) {
        return PageHelper.doPage(pageRequest, () -> taskMapper.selectTask(task));
    }

    @Override
    public List<Task> selectByEmployeeId(Long employeeId) {
        Task task = new Task();
        task.setEmployeeId(employeeId);
        return this.selectOptional(task, new Criteria()
                .select(Task.FIELD_ID, Task.FIELD_EMPLOYEE_ID, Task.FIELD_STATE, Task.FIELD_TASK_DESCRIPTION)
                .where(Task.FIELD_EMPLOYEE_ID)
        );
    }

    @Override
    public Task selectDetailByTaskNumber(String taskNumber) {
        Task params = new Task();
        params.setTaskNumber(taskNumber);
        List<Task> tasks = taskMapper.selectTask(params);
        return CollectionUtils.isNotEmpty(tasks) ? tasks.get(0) : null;
    }
}
```