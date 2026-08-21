# seen-backend 后端项目说明

## 1 后端工程架构与规划

### 1.1 业务规则

1. 随机抽取10人数据；
2. 次日重新随机抽取人员。

### 1.2 技术储备栈

分布式基础中间件整套架构：

1. MinIO：分布式文件存储
2. Zookeeper：分布式注册中心
3. Kafka：分布式消息队列
4. Redis：分布式缓存
5. MySQL：关系型持久化数据库

### 1.3 架构变更计划

除非上线前存在功能无法实现，正式商业上线不修改现有技术架构。

### 1.4 数据库存储设计新思路

1. 区块链式存储
    - 小粒度完整留存全量历史数据
2. 时态数据存储方案（支持数据时间回溯）

### 1.5 图片处理规范

1. WebP格式图片不做二次压缩；
2. 当前图片压缩存在较多缺陷，需重新梳理优化方案。

### 1.6 文件存储规则

照片ID 与 文件ID 保持一一对应。

### 1.7 分布式中间件部署规范

内网环境部署中间件时，通过自定义宣告域名+端口进行服务访问控制。

## 2 业务问题记录表

| 序号 | 业务项描述                                            | 解决方案           |
|------|-------------------------------------------------------|--------------------|
| 1    | 数据库自增ID失效                                      | 清理数据库存储介质 |
| 2    | 业务交易缺少类型，一笔业务关联多币种交易              | 待完善             |
| 3    | 同意好友申请是否发放玫瑰币                            | 待完善             |
| 4    | 根据URL生成二维码链接                                 | 待完善             |
| 5    | SSE绘画长连接：注销需断开连接；打开聊天窗口需校验会话 | 待完善             |

## 3 项目基础信息

### 包名说明

项目原始包名 `io.github.seenings.demo-home` 非法，自动替换为 `io.github.seenings.demo_home`。

### 参考文档

1. [Apache Maven 官方文档](https://maven.apache.org/guides/index.html)
2. [Spring Boot Maven 插件文档](https://docs.spring.io/spring-boot/3.4.1/maven-plugin)
3. [OCI 容器镜像构建](https://docs.spring.io/spring-boot/3.4.1/maven-plugin/build-image.html)
4. [GraalVM Native 镜像开发指南](https://docs.spring.io/spring-boot/3.4.1/reference/packaging/native-image/introducing-graalvm-native-images.html)
5. [响应式Web开发](https://docs.spring.io/spring-boot/3.4.1/reference/web/reactive.html)

### 开发示例教程

[构建响应式REST服务](https://spring.io/guides/gs/reactive-rest-service/)

### 扩展配置参考

[AOT 编译插件配置](https://docs.spring.io/spring-boot/3.4.1/how-to/aot.html)

## 4 GraalVM Native 原生镜像支持

本项目支持两种原生打包方式：轻量容器镜像 / 独立原生可执行文件，同时支持原生镜像测试。

### 4.1 Cloud Native Buildpacks 容器镜像

前置：本地安装 Docker 打包命令：

```shell
./mvnw spring-boot:build-image -Pnative
```

运行容器：

```shell
docker run --rm demo-home:0.0.1-SNAPSHOT
```

### 4.2 Native Build Tools 独立可执行文件

前置：本地安装 GraalVM 22.3+

```shell
# Mac 配置GraalVM环境变量
export JAVA_HOME=/Users/chixuehui/Library/Java/JavaVirtualMachines/graalvm-jdk-21.0.5/Contents/Home
# 编译原生程序
./mvnw native:compile -Pnative
# 运行程序
target/seen-backend
```

### 4.3 Native 镜像单元测试

```shell
./mvnw test -PnativeTest
```

## 5 Maven 父POM继承说明

Maven机制会自动继承父POM配置（license、developers等），当前子项目POM做了空覆盖屏蔽继承。 若更换父工程并需要继承父配置，删除子POM内空覆盖节点即可。

```