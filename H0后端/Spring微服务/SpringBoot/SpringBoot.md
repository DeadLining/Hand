#### Spring Boot的组成部分

### Spring Boot 的特性

- 方便地创建可独立运行的 Spring 应用程序
- 直接内嵌 Tomcat、Jetty 或 ndertow
- 简化了项目的构建配置
- 为 Spring 及第三方库提供自动配置
- 提供生产级特性
- 无需生成代码或进行XML 配置

### Spring Boot 四大核心

- 自动配置 - Ato Configration
- 起步依赖 - Starter Dependency
- 命令行界面 - Spring Boot CLI
- Actator

### IDEA构建项目

1. 选择 **File -> New —> Project**… 弹出新建项目的框
2. 选择 **Spring** **Initializr**，Next 也会出现上述类似的配置界面，Idea 帮我们做了集成
3. 填写相关内容后，点击 Next 选择依赖的包再点击 Next，最后确定信息无误点击 Finish

### Spring Boot 的基础结构共三个文件

- src/main/java 程序开发以及主程序入口
- src/main/resources 配置文件
- src/test/java 测试程序

Spring Boot 的目录结构：

<img src="SpringBoot.assets\image-20200713161754951.png" alt="image-20200713161754951" style="zoom:80%;" />

root package 结构：com.example.myproject

### Spring Boot 入门

**引入** **web** **模块**

1、pom.xml中添加支持web的模块：

<img src="SpringBoot.assets\image-20200713165811113.png" alt="image-20200713165811113" style="zoom:67%;" />

2、编写 Controller 内容：

<img src="SpringBoot.assets\image-20200713165816253.png" alt="image-20200713165816253" style="zoom:67%;" />

3、启动主程序

打开浏览器访问 http://localhost:8080/hello

<img src="SpringBoot.assets\image-20200713165820517.png" alt="image-20200713165820517" style="zoom:67%;" />

### Spring Boot热部署

1、在pom.xml中做如图配置

<img src="SpringBoot.assets\image-20200713170253643.png" alt="image-20200713170253643" style="zoom:67%;" />

<img src="SpringBoot.assets\image-20200713170300797.png" alt="image-20200713170300797" style="zoom:67%;" />

2、Edit Configuration….

<img src="SpringBoot.assets\image-20200713170309070.png" alt="image-20200713170309070" style="zoom:67%;" />

### Spring Boot 定时任务

**引入** **web** **模块**

1、pom.xml中添加

<img src="SpringBoot.assets\image-20200713170450613.png" alt="image-20200713170450613" style="zoom:67%;" />

2、在启动类上面加上@EnableScheduling：

<img src="SpringBoot.assets\image-20200713170532704.png" alt="image-20200713170532704" style="zoom:67%;" />

3、创建定时任务实现类Scheduler2Task和  SchedulerTask

<img src="SpringBoot.assets\image-20200713172607398.png" alt="image-20200713172607398" style="zoom:67%;" />![image-20200713172613987](SpringBoot.assets\image-20200713172613987.png)

![image-20200713172613987](SpringBoot.assets\image-20200713172613987.png)

可以查看执行结果如下

<img src="SpringBoot.assets\image-20200713172619858.png" alt="image-20200713172619858"  />

### 认识可执行Jar

**其中包含**

- Jar描述，META-INF/MANIFEST.MF
- Spring Boot Loader，org/springframework/boot/loader
- 项目内容，BOOT-INF/classes
- 项目依赖，BOOT-INF/lib

**不包含**

- JDK/JRE

<img src="SpringBoot.assets\image-20200713172815434.png" alt="image-20200713172815434" style="zoom:67%;" />

### 如何找到程序的入口

**Jar的启动类**

- MANIFEST.MF
  - Main-Class: org.springframework.boot.loader.JarLauncher

**项目的主类**

- @SpringApplication
- MANIFEST.MF
  - Start-Class: xxx.yyy.zzz

### Spring Boot 如何测试打包部署

cd 项目跟目录（和pom.xml同级）

- mvn clean package

\## 或者执行下面的命令,排除测试代码后进行打包

- mvn clean package -Dmaven.test.skip=true

启动 jar 包命令: java -jar xxxxx.jar

<font color='red'>这种方式，只要控制台关闭，服务就不能访问了。下面我们使用在后台运行的方式来启动:</font>

- nohup java -jar xxx.jar &

### Maven 依赖管理的一些小技巧

**了解你的依赖**

- mvn dependency:tree
- IDEA Maven Helper 插件

**排除特定依赖**

- exclusion