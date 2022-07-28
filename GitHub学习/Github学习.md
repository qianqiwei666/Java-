# 一、准备

## 一、学习Github仓库的基本使用

> [连接直达](https://docs.github.com/cn/get-started)

## 二、Git使用准备

### 一、设置本地全局用户名,密码,邮箱

> 1. 设置用户名: git config --global  user.name "[你的姓名]"
> 2. 设置密码: git config --global user.password "[你的密码]"
> 3. 设置邮箱:  git config --global  user.email  "[你的邮箱]"

### 二、设置公钥私钥(RSA)

#### 一、前置

> 1. 私钥:又称对称加密算法,主要用来加密跟解密。这种密钥一般自己保存。
> 2. 公钥:又称非对称加密算法,被公钥加密过的文件,必须由私钥解密。

#### 二、Git设置公钥私钥

#####  一、生成公钥与私钥

> 1. ssh-keygen -t rsa -C "[你的邮箱]"
> 2. 设置的时候一路回车就行。

##### 二、找到生成.ssh文件夹

> 1. id_rsa:这里存放的是私钥
> 2. id_rsa.pub:这里存放的是公钥

##### 三、设置公钥到Github

###### 一、到自己的github账号中![Github_for_ssh](C:\Users\15915\Desktop\GitHub学习\Github_for_ssh.png)

###### 二、设置公钥

![github_public_key](C:\Users\15915\Desktop\GitHub学习\github_public_key.png)

###### 三、大功告成!

## 三、基本常识

### 一、仓库,暂存区,版本库

> 1. 工作区:就是我们所看到的项目文件。
> 2. 暂存区:就是index索引,命令:git add,目录:.git/index
> 3. 版本库:就是存放文件提交版本。
> 4. 仓库:真正提交上去的文件存储的地方。

![git提交流程图](C:\Users\15915\Desktop\GitHub学习\git提交流程图.png)

# 二、Git的基本使用

### 一、暂存区基本操作

#### 一、提交到缓存区

```bash
git add -A  #表示上传所有改变的文件,包括删除,增加。
git add -u  #表示上传所有改变的文件,包括删除,不包括增加
git add .   #跟git add -A一样
```

#### 二、查看缓存区内容

```bash
git ls-files
```

#### 三、删除缓存区中的内容

```bash
git rm  --cached [文件名称] # 1.为*表示删除暂存区中所有的文件
git add . #尽量用这个命令
```

#### 四、注意

> 重复提交会覆盖暂存区中的文件(前提内容不同),重复提交相同内容的文件不会提交。

### 二、提交暂存区文件到仓库

#### 一、命令说明

```bash
git commit -m "[提交的描述信息]"
```

### 三、工作区状态

#### 一、命令说明

```bash
git status #这里比对的是工作区与暂存区中的文件,红色显示表示工作区与暂存区不同的文件。
```

### 四、文件比对

#### 一、命令说明

```bash
git diff #这里展示工作区与暂存区不同的内容。前提暂存区有此文件。
```

### 五、查看提交日志

#### 一、命令说明

```bash
git log  [--oneline]#可以查看提交的时间,作者跟邮箱,以及提交的说明。  --oneline:代表简介查看
```

### 六、查看提交版本日志

#### 一、命令说明

```bash
git reflog #可以查看提交说明,并且可以获取id来进行版本回滚。
```

### 七、版本回滚

#### 一、命令说明

```bash
git reset --hard "[版本id]" #非常实用的一个功能。
```

# 三、 远程Git仓库操作

> 1. 在git中添加远程仓库地址
>
>    ```bash
>    git remote add [别名名称] [仓库地址]
>    ```
>
> 2. git中删除远程仓库地址
>
>    ```bash
>    git remote rm [别名名称]
>    ```
>
> 3. git查看远程地址
>
>    ```bash
>    git remote -v 
>    ```
>
> 4. git查看远程地址信息
>
>    ```bash
>    git remote show "[别名名称]"
>    ```
>
> 5. 将数据提交到远程仓库
>
>    ```bash
>    git push [-f] [远程仓库别名] [分支] #-f代表强制上传 
>    ```

# 四、分支管理

> 1. 创建分支
>
>    ```bash
>    git branch [分支名称]  #当前分支有什么内容分配的分支就有什么,数据建立在当前分支之上。
>    ```
>
> 2. 查看分支
>
>    ```bash
>    git branch  -v
>    ```
>
> 3. 选择分支
>
>    ```bash
>    git checkout [分支名称]
>    ```
>
> 4. 删除分支
>
>    ```bash
>    git branch -d [分支名称] #注意:主节点也可被删除
>    ```
>
> 5. 合并分支
>
>    ```bash
>    git merge [分支名称] #注意:合并的时候一定要对冲突的地方进行处理。然后commit
>    ```
>
>    
