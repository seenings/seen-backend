# 中间件

## 注册中心

### zk启动

```shell
docker-compose -f zookeeper.yaml up -d
```

### zk停止

```shell
docker-compose -f zookeeper.yaml down
```

## 数据库

### mysql启动

```shell
docker-compose -f mysql.yaml up -d
```

### mysql停止

```shell
docker-compose -f mysql.yaml down
```

## 内存数据库

### redis启动

```shell
docker-compose -f redis.yaml up -d
```

### redis停止

```shell
docker-compose -f redis.yaml down
```

## 文件对象存储

### minio启动

```shell
docker-compose -f minio.yaml up -d
```

### minio停止

```shell
docker-compose -f minio.yaml down
```
