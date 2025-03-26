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
