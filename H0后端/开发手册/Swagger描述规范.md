## Swagger描述规范

### 总体要求

> 由于HZERO平台是产品或项目实施的基础平台，通过Swagger进行管理的API说明对使用极为重要。
> 主要分三块：
>
> 1. API目录描述（必须）
> 2. API描述（必须）
> 3. API参数描述（可选）

**1.Swagger中API目录描述结构（英文描述：中文描述）**

<img src="http://hzerodoc.saas.hand-china.com/img/docs/development-specification/backend-development-specification/swagger/swagger01.png" alt="img" style="zoom:80%;" />

**2.Swagger中API描述结构**

<img src="http://hzerodoc.saas.hand-china.com/img/docs/development-specification/backend-development-specification/swagger/swagger02.png" alt="img" style="zoom:80%;" />

**3.Swagger中API参数描述结构**

**URL参数**

<img src="http://hzerodoc.saas.hand-china.com/img/docs/development-specification/backend-development-specification/swagger/swagger03.png" alt="img" style="zoom:80%;" /> 

**Body参数**

<img src="http://hzerodoc.saas.hand-china.com/img/docs/development-specification/backend-development-specification/swagger/swagger04.png" alt="img" style="zoom:80%;" />

### 使用方式

> 以下实例均来源于hzero-file文件服务

**1.Swagger中API目录描述**
每个微服务应用增加一个SwaggerApi配置类，用于配置API目录的Tag，保持API的Controller对应，另外在API的Controller中用注解进行申明。

![img](http://hzerodoc.saas.hand-china.com/img/docs/development-specification/backend-development-specification/swagger/swagger05.png)
![img](http://hzerodoc.saas.hand-china.com/img/docs/development-specification/backend-development-specification/swagger/swagger06.png)

**2.Swagger中API描述**

<img src="http://hzerodoc.saas.hand-china.com/img/docs/development-specification/backend-development-specification/swagger/swagger07.png" alt="img" style="zoom:80%;" />

**3.Swagger中API参数描述**

URL参数

<img src="http://hzerodoc.saas.hand-china.com/img/docs/development-specification/backend-development-specification/swagger/swagger08.png" alt="img" style="zoom:80%;" />

Body参数

<img src="http://hzerodoc.saas.hand-china.com/img/docs/development-specification/backend-development-specification/swagger/swagger09.png" alt="img" style="zoom:80%;" />