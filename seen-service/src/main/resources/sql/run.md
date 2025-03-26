# 应用运行

## 启动

```shell
docker run -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=root@songchi -d mysql:9.1.0
```

## 停止

```shell
docker stop mysql 
```

## 移除

```shell
docker rm mysql 
```
