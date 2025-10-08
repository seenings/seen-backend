# 应用

## 创建网络

```shell
docker network create chixuehuidocker
```

### Maco OS预先配置

```shell
SEEN_VERSION="0.1.70"
JAVA_HOME="~/Library/Java/JavaVirtualMachines/openjdk-25/Contents/Home"
$SEEN_VERSION
$JAVA_HOME
```

### Windows 11预先配置

```shell
$$Env:SEEN_VERSION = "0.1.70"
$$Env:SEEN_VERSION
$$Env:JAVA_HOME = "C:\Users\PC\.jdks\openjdk-25"
$$Env:JAVA_HOME
```

## 应用启动

```shell
cd ..
./mvnw package
```

```shell
docker compose up -d
```

## 应用停止

```shell
docker-compose down
```