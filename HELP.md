# seen-backend 操作手册

本文档整理项目的环境配置、版本发布和站点文档发布流程。

## 目录

- [一、环境准备](#一环境准备)
- [二、版本管理](#二版本管理)
- [三、Git 版本发布](#三git-版本发布)
- [四、站点文档发布](#四站点文档发布)
- [五、GPG 签名配置](#五gpg-签名配置)

## 一、环境准备

执行发布或构建前，请先完成 JDK、项目版本号和环境变量配置。

### 1. macOS

读取项目根目录中的 `version.txt`，并配置 JDK 路径：

```shell
export SEEN_VERSION=$(cat version.txt | tr -d '\n' | tr -d ' ')
export JAVA_HOME="~/Library/Java/JavaVirtualMachines/openjdk-25/Contents/Home"

# 校验配置
echo "SEEN_VERSION=$SEEN_VERSION"
echo "JAVA_HOME=$JAVA_HOME"
"$JAVA_HOME/bin/java" -version
```

### 2. Windows 11 PowerShell

#### 配置环境变量

编辑本机私密环境变量文件：

```powershell
edit D:\Users\CXH\data\secret\.env.secret.txt
```

从文件中加载环境变量到当前 PowerShell 进程：

```powershell
$secretFile = "D:\Users\CXH\data\secret\.env.secret.txt";

Get-Content $secretFile | ForEach-Object {
    $line = $_.Trim()
    if ($line -and !$line.StartsWith("#")) {
        $index = $line.IndexOf("=")
        $key = $line.Substring(0, $index).Trim()
        $value = $line.Substring($index + 1).Trim()
        [Environment]::SetEnvironmentVariable($key, $value, "Process")
    }
};
$env:JAVA_HOME = "C:\Users\chixu\.jdks\openjdk-27.0.1.1";

# 校验配置
echo "已设置 JAVA_HOME：$env:JAVA_HOME";
& "$env:JAVA_HOME\bin\java.exe" -version;
echo "SEEN_VERSION=$env:SEEN_VERSION";
# 切换到项目目录
cd ~\IdeaProjects\seenings\seen-backend;
```

## 二、版本管理

在项目根目录执行以下命令，将所有 Maven 模块统一更新为环境变量中的版本号：

```powershell
./mvnw versions:set `
  --define "newVersion=$env:SEEN_VERSION" `
  -DgenerateBackupPoms=false
```

执行后检查变更，确认无误再提交。

## 三、Git 版本发布

### 1. 提交版本变更并拉取主线

```powershell
git add .;
git commit -m "升级包 #79";
git pull origin main --rebase;
$tag = "v$env:SEEN_VERSION";
git tag -a $tag -m "发布版本 $env:SEEN_VERSION";
```

### 3. 推送分支和 Tag

```powershell
git push origin v$env:SEEN_VERSION;
git push origin dev_chixh;
```

## 四、站点文档发布

在项目根目录依次执行：

```powershell
# 生成站点文档
./mvnw site:site;

# 在本地生成预览站点
./mvnw site:stage;

# 发布站点到配置的 SCM 仓库
./mvnw scm-publish:publish-scm;
```

发布完成后可清理构建产物：

```powershell
Remove-Item -Recurse -Force target;
```

## 五、GPG 签名配置

GPG 工具下载地址：[gpg4win](https://www.gpg4win.org/)

CI 流水线涉及的 GPG 密钥导入、公钥推送至密钥服务器，以及 Maven Central 签名校验，请参见项目 CI 配置中的相关注释。
