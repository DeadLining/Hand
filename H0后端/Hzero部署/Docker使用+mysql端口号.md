CentOS7虚拟机上的MySQL用户：

> root
>
> root

> hzero
>
> hzero



Docker容器中的mysql数据库的端口号：3307

用户名：root

密码：admin

### Ubuntu：

###### 启动docker

`sudo service docker start`

###### 停止docker

`sudo service docker stop`

###### 重启docker
`sudo service docker restart`

###### 列出镜像
`docker image ls`

###### 拉取镜像
`docker image pull library/hello-world`

###### 删除镜像
`docker image rm 镜像id/镜像ID`

###### 创建容器
`docker run [选项参数] 镜像名 [命令]`

###### 停止一个已经在运行的容器
`docker container stop 容器名或容器id`

###### 启动一个已经停止的容器
`docker container start 容器名或容器id`

###### kill掉一个已经在运行的容器
`docker container kill 容器名或容器id`

###### 删除容器
`docker container rm 容器名或容器id`

### Centos：

拉取docker镜像

`docker pull XXX:latest`
查看镜像

`docker images`



### Docker创建Container：

挂载映射关系：

> 宿主机 : Docker

docker启动mysql镜像

`docker run -d -e MYSQL_ROOT_PASSWORD=admin --name mysql -v /data/mysql/data:/var/lib/mysql -p 3306:3306 mysql:5.7.26 `

`docker run -d -e MYSQL_ROOT_PASSWORD=admin --name mysql -v /data/mysql/mysql.conf.d/mysqld.cnf:/etc/mysql/mysql.conf.d/mysqld.cnf -v /data/mysql/data:/var/lib/mysql -p 3306:3306 mysql:5.7.26 `

**注：**使用`mysql:5.7.26`版本，不要使用`mysql:latest`版本

> 原因：`mysql:latest`不支持`lower_case_table_names=1`的设置
>
> [link]: https://www.cnblogs.com/kevingrace/p/6150748.html	"MySQL 表名忽略大小写问题记录"

若端口被宿主机占用：

`docker run -d -e MYSQL_ROOT_PASSWORD=admin --name mysql -v /data/mysql/data:/var/lib/mysql -p 3307:3306 mysql `

docker启动redies镜像

`docker run -d --name redis -p 6379:6379 -v /data/hzero/data-server/redis/data:/data redis`

docker启动Jenkins镜像

`docker run -d -u root --name jenkins -p 9090:8080 -p 50000:50000 -v /data/hzero/data-server/jenkins:/var/jenkins_home --privileged=true jenkins`

进入容器

`docker exec -it (容器name) /bin/bash`

退出容器

`exit`

### mysql：

登录mysql

`mysql -uroot -p`
	`admin`

设置mysql远程权限

`ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'admin';`

> 密码格式不正确的问题解决：
>
> [link]: https://blog.csdn.net/hello_world_qwp/article/details/79551789	"ERROR 1819 (HY000): Your password does not satisfy the current policy requirements"

刷新权限

`flush privileges;`
（备注 ：记得开启开启对外访问端口，防火墙设置3306外网访问）