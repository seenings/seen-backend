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
export PATH1="~/seen/minio/data-1/"
export PATH2="~/seen/minio/data-2/"
export PATH3="~/seen/minio/data-3/"
export PATH4="~/seen/minio/data-4/"

docker run -dt                                  \
  -p 9000:9000 -p 9001:9001                     \
  -v PATH1:/data-1                              \
  -v PATH2:/data-2                              \
  -v PATH3:/data-3                              \
  -v PATH4:/data-4                              \
  -v ./minio.conf:/etc/config.env         \
  -e "MINIO_CONFIG_ENV_FILE=/etc/config.env"    \
  --name "minio_local"                          \
  quay.io/minio/minio server --console-address ":9001"
```

### minio停止

```shell
docker stop minio_local
docker rm minio_local
```
