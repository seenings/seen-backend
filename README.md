# 1 后端工程

1. 随机拿10个人
2. 第二天再随机拿

## 1.1 技术储备

1. minio分布式文件存储
2. zookeeper分布式注册中心
3. kafka分布式消息中心
4. redis分布式缓存中间件
5. mysql持久化关系数据库

## 1.2 计划

除非正式上线前有功能实现不了，否则目前的技术架构在商业上线时不做修改

## 1.3 安装minikube

curl -Lo minikube https://github.com/kubernetes/minikube/releases/download/v1.27.1/minikube-linux-amd64 && chmod +x
minikube && sudo mv minikube /usr/local/bin/

## 1.4 数据库存储新思路

1. 区块链式存储
    1. 小粒度保持过往的数据
2. 带时态的数据存储方法

## 1.5 图片压缩

1. webp图片不可压缩；
2. 图片压缩问题多多，重新整理。

## 1.6 maven设置版本

### Maco OS预先配置

```shell
SEEN_VERSION="0.1.74"
JAVA_HOME="~/Library/Java/JavaVirtualMachines/openjdk-25/Contents/Home"
$SEEN_VERSION
$JAVA_HOME
```

### Windows 11预先配置

```shell
$$Env:SEEN_VERSION = "0.1.74"
$$Env:SEEN_VERSION
$$Env:JAVA_HOME = "C:\Users\PC\.jdks\openjdk-25"
$$Env:JAVA_HOME
```

### 开始新的开发

```shell
./mvnw clean
./mvnw versions:set --define newVersion=$Env:SEEN_VERSION
./mvnw versions:commit
```

### 版本升级检查

```shell
./mvnw versions:display-dependency-updates
```

```shell
git add .
```

```shell
git commit -m "#74 增加应用二维码分享"
```

```shell
git tag -a v$Env:SEEN_VERSION -m "发布版本$Env:SEEN_VERSION"
```

```shell
git push origin v$Env:SEEN_VERSION
```

```shell
git push origin dev_chixh

```

### 文档发布

```shell
./mvnw site:site
```

```shell
./mvnw site:stage
```

```shell
./mvnw scm-publish:publish-scm
```

## 1.7 启动

[启动说明](./chixuehuidocker/README.md)

分布式中间件,放在内部网络时,使用宣告域名和端口控制

## 1.8 安装安全工具

gpg工具下载地址https://gpgtools.org/

## 1.9 照片ID就是文件ID

# 2 问题记录

| 序号 | 项                                  | 解决        |
|----|------------------------------------|-----------|
| 1  | 数据库ID自增失效                          | 清理数据库存储介质 |
| 2  | 业务有类型交易没有类型，一笔业务对应多笔币交易            |           |
| 3  | 同意加好友是否可以获得玫瑰币                     |           |
| 4  | url生成二维码链接                         |           |
| 5  | sse连接绘画，注销时要退出，打开聊天窗口时应该判断是否有过连接会话 |           |
