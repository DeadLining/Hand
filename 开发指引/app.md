# 前置条件

在开发之前，请确保

- 本地项目已经创建成功，详见 [新建项目](http://hzerodoc.saas.hand-china.com/zh/docs/development-guide/backend-develop-guid/demo/create_project/)
- 数据库创建成功，详见 [初始化数据库](http://hzerodoc.saas.hand-china.com/zh/docs/development-guide/backend-develop-guid/demo/init_db/)

## 介绍

此 `demo` 需涉及到 `app` 层的 `service` 接口类与其实现类。

```text
特别说明  

为了Demo的完整性，这里使用了`app`层的服务类，如业务十分简单，`api`层亦可直接调用通用的`repository`相关方法，不需要 `app` 服务
```

`service` 调用领域对象或服务来解决问题，应用层Service主要有以下特性：

1. 负责事务处理，所以事务的注解可以在这一层的`service`中使用。
2. 只处理非业务逻辑，重点是调度业务处理流程。业务逻辑处理一定要放在领域层处理。
3. 不做单元测试，只做验收测试。
4. 可能会有比较多的依赖组件(领域服务)，使用`field`注入依赖的组件。

## `Service` 接口类

- `Service` 接口类定义了业务操作的一系列接口，并不提供实现，具体实现需要通过服务实现层提供，所以属于供应方的服务接口层。创建在项目模块的 `xxx.app.service` 包下。

## .1 `UserService.java` 代码

```java
package org.hzero.todo.app.service;

import org.hzero.todo.domain.entity.User;

/**
 * 用户应用服务
 */
public interface UserService {

    /**
     * 创建用户
     * 
     * @param user User
     * @return User
     */
    User create(User user);

    /**
     * 删除用户(同时删除任务)
     *
     * @param userId 用户ID
     */
    void delete(Long userId);
}
```

## .2 `TaskService.java` 代码

```java
package org.hzero.todo.app.service;

import org.hzero.todo.domain.entity.Task;

/**
 * 任务应用服务
 */
public interface TaskService {

    /**
     * 创建任务
     *
     * @param task 任务
     * @return Task
     */
    Task create(Task task);

    /**
     * 更新任务
     *
     * @param task 任务
     * @return Task
     */
    Task update(Task task);

    /**
     * 根据任务编号删除
     *
     * @param taskNumber 任务编号
     */
    void deleteByTaskNumber(String taskNumber);
}
```

## `Service` 实现类

- `Service` 接口的具体实现通过服务实现层提供，所以属于供应方的服务实现层。创建在项目模块的 `xxx.app.service.impl` 包下。
- 实现类，需要用 `@Service` 标注

## .1 `UserServiceImpl.java` 代码

```java
package org.hzero.todo.app.service.impl;

import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.choerodon.core.exception.CommonException;
import org.hzero.todo.app.service.UserService;
import org.hzero.todo.domain.entity.Task;
import org.hzero.todo.domain.entity.User;
import org.hzero.todo.domain.repository.TaskRepository;
import org.hzero.todo.domain.repository.UserRepository;

/**
 * 用户应用服务实现
 */
@Service
public class UserServiceImpl implements UserService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public UserServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User create(User user) {
        userRepository.insert(user);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long userId) {
        User exist = userRepository.selectByPrimaryKey(userId);
        if (exist == null) {
            throw new CommonException("htdo.warn.user.notFound");
        }
        // 删除用户
        userRepository.deleteByPrimaryKey(userId);
        // 删除与用户关联的任务
        List<Task> tasks = taskRepository.selectByEmployeeId(userId);
        if (CollectionUtils.isNotEmpty(tasks)) {
            taskRepository.batchDelete(tasks);
        }
    }
}
```

## .2 `TaskServiceImpl.java` 代码

```java
package org.hzero.todo.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.choerodon.core.exception.CommonException;
import org.hzero.todo.app.service.TaskService;
import org.hzero.todo.domain.entity.Task;
import org.hzero.todo.domain.repository.TaskRepository;

/**
 * 任务应用服务实现
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Task create(Task task) {
        // 生成任务编号
        task.generateTaskNumber();
        // 插入数据
        taskRepository.insert(task);
        return task;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Task update(Task task) {
        Task exist = taskRepository.selectByPrimaryKey(task);
        if (exist == null) {
            throw new CommonException("htdo.warn.task.notFound");
        }
        // 更新指定字段
        taskRepository.updateOptional(task,
                Task.FIELD_STATE,
                Task.FIELD_TASK_DESCRIPTION
        );
        return task;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByTaskNumber(String taskNumber) {
        Task task = new Task();
        task.setTaskNumber(taskNumber);
        taskRepository.delete(task);
    }
}
```