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


### zk集群启动

```shell
docker-compose -f zookeeper-cluster.yaml up -d
```

### zk集群停止

```shell
docker-compose -f zookeeper-cluster.yaml down
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

### 删除集群信息

```shell
rm -rf ~/seen/redis/
```

### 新建数据目录

```shell
mkdir -p ~/seen/redis/data_1 ~/seen/redis/data_2 ~/seen/redis/data_3 ~/seen/redis/data_4 ~/seen/redis/data_5 ~/seen/redis/data_6
```

### 本地Redis的结点的域名,并添加到/etc/hosts文件的末尾

```shell 
sudo vi /etc/hosts
```

### 域名映射

```text 
127.0.0.1 redis-node-1
127.0.0.1 redis-node-2
127.0.0.1 redis-node-3
127.0.0.1 redis-node-4
127.0.0.1 redis-node-5
127.0.0.1 redis-node-6
```

### 启动集群结点

```shell 
docker-compose -f redis-cluster.yaml up -d
```

### 创建集群

```shell 
docker exec -it redis-node-1 redis-cli --cluster create \
  redis-node-1:7001 redis-node-2:7002 redis-node-3:7003 \
  redis-node-4:7004 redis-node-5:7005 redis-node-6:7006 \
  --cluster-replicas 1
```

### 关闭集群结点

```shell 
docker-compose -f redis-cluster.yaml down
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
