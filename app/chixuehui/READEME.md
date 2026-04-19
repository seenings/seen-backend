# 应用

## 创建网络

```shell
docker network create chixuehui
```

### 中间件启动

```shell
docker compose -f compose-middle.yml up -d
```

```shell
docker logs -t -f chixuehui-zk-1
```

### 前端停止

```shell
docker compose -f compose-middle.yml down
```