# seen-backend 操作运维手册

## 1 多系统环境预先配置

### 1.1 Mac OS 环境预配置

读取版本文件并配置JDK环境变量

```shell
export SEEN_VERSION=$(cat version.txt | tr -d '\n' | tr -d ' ')
JAVA_HOME="~/Library/Java/JavaVirtualMachines/openjdk-25/Contents/Home"
# 校验变量
echo SEEN_VERSION
echo $JAVA_HOME
```

### 1.2 Windows 11 PowerShell 环境预配置

读取.env.secret.txt环境变量文件并注入进程环境

```shell
Get-Content D:\Users\CXH\data\secret\.env.secret.txt | ForEach-Object {$l=$_.Trim();if($l -and !$l.StartsWith("#")){$i=$l.IndexOf('=');$k=$l.Substring(0,$i).Trim();$v=$l.Substring($i+1).Trim();[Environment]::SetEnvironmentVariable($k,$v,"Process")}};
# 设置JDK
$$env:JAVA_HOME = "C:\Users\chixu\.jdks\openjdk-26.0.2" ; echo "已设置JAVA_HOME：$env:JAVA_HOME";
# 校验环境变量与Java版本
echo "SEEN_VERSION=$env:SEEN_VERSION" ; & "$env:JAVA_HOME\bin\java.exe" -version;
# 切换项目目录
cd ~\IdeaProjects\seenings\seen-backend;
```

## 2 版本管理操作流程

### 2.1 修改项目全模块版本

PowerShell

```shell
./mvnw versions:set --define newVersion=$env:SEEN_VERSION -DgenerateBackupPoms=false
```

### 2.2 检查依赖可更新版本

```shell
./mvnw versions:dependency-updates-report
```

## 3 Git 版本发布流程

```shell
# 提交版本变更
git add . ; git commit -m "#95 清理spring boot包" ;
# 拉取主线代码变基
git pull origin main --rebase ;
# 创建版本Tag
git tag -a v$env:SEEN_VERSION -m "发布版本$env:SEEN_VERSION" ;
```

```shell
# 推送Tag与开发分支
git push origin v$env:SEEN_VERSION ; git push origin dev_chixh ;
```

## 4 项目站点文档打包发布

```shell
# 生成站点文档
./mvnw site:site
# 本地预览站点
./mvnw site:stage
# 推送文档至代码仓库
./mvnw scm-publish:publish-scm
```

## 5 Maven 打包发布避坑参数

### 跳过Spring Boot repackage重打包（4.1.0专用）

```shell
./mvnw clean deploy -Dmaven.test.skip=true -DskipNativeBuild -Dspring-boot.repackage.skip=true
```

### 无备份修改版本号

```shell
./mvnw versions:set -DnewVersion=$SEEN_VERSION -DgenerateBackupPoms=false
```

## 6 GPG 安全工具安装

GPG工具下载地址：[gpg4win](https://www.gpg4win.org/)
配套CI流水线操作：GPG密钥导入、公钥推送密钥服务器、Maven Central签名校验配套操作见项目CI配置注释。