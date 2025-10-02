# 应用

## Mac OS启动

```shell
export SEEN_VERSION=0.1.68
docker-compose -f seen.yaml up -d
```
## Mac OS停止

```shell
export SEEN_VERSION=0.1.68
docker-compose -f seen.yaml down
```
## Windows 11启动

```shell
$$Env:SEEN_VERSION="0.1.68"
docker compose -f seen.yaml up -d
```

## indows 11停止

```shell
$$Env:SEEN_VERSION="0.1.68"
docker-compose -f seen.yaml down
```