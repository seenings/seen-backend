# 1 后端工程

1、随机拿10个人
2、第二天再随机拿

## 1.1 技术储备

1. minio分布式文件存储
2. zookeeper分布式注册中心
3. git外部配置中心
4. kafka分布式消息中心
5. redis分布式缓存中间件

## 1.2 计划，除非正式上线前有功能实现不了，否则目前的技术架构在商业上线时不做修改

## 1.3 安装minikube

curl -Lo minikube https://github.com/kubernetes/minikube/releases/download/v1.27.1/minikube-linux-amd64 && chmod +x
minikube && sudo mv minikube /usr/local/bin/

## 1.4 数据库存储新思路

1. 区块链式存储
    1. 小粒度保持过往的数据
2. 带时态的数据存储方法

## 1.5 图片压缩

1. webp图片不可压缩

## 1.6 maven设置版本

```shell
./mvnw clean
./mvnw versions:set --define newVersion=0.1.50
```