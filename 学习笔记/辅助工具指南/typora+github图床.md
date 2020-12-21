# 1 图床工具准备

​	picgo客户端 + github账户 + typora客户端



# 2 github仓库设置

## 2.1 创建仓库

​	创建仓库名称，级别需要选择为public（后面需要客户端访问，设置为private的话，只能存储不能访问）

![img](https://cdn.jsdelivr.net/gh/lxj53/markdownPictures@main/img/20201221103734.png)



## 2.2 创建token并复制

1. 点击setting

![img](https://cdn.jsdelivr.net/gh/lxj53/markdownPictures@main/img/20201221103936.png)

2. 选择开发者选项Developer settings

   

![img](https://cdn.jsdelivr.net/gh/lxj53/markdownPictures@main/img/20201221104103.png)

3. 创建token，勾选复选框repo，点击页面下端的Generate token。然后复制生成一串字符 token，这个 token 只出现一次，所以要保存一下。

   ![img](https://cdn.jsdelivr.net/gh/lxj53/markdownPictures@main/img/20201221104257.png)

![img](https://cdn.jsdelivr.net/gh/lxj53/markdownPictures@main/img/20201221104313.png)



# 3 picgo客户端设置

## 3.1 下载并安装picgo客户端

​		直接百度下载客户端即可，下载比较新的

## 3.2 picgo配置

- 仓库名 即你的仓库名
- 分支名 默认 `main`
- Token 就是刚刚复制的那一串字符
- 存储路径 这个可以填也可以不填，填了的话图片就上传到 git 中 `img` 这个文件夹
- 域名 `https://cdn.jsdelivr.net/gh/lxj53/markdownPictures@main`这个是加速后的地址，github原域名格式 `https://raw.githubusercontent.com/[username]/[仓库名]/master`

![image-20201221105454586](https://cdn.jsdelivr.net/gh/lxj53/markdownPictures@main/img/20201221105454.png)

## 3.3 服务代理和时间戳设置

​	设置picgo的代理server（默认已经开启）、打开时间戳开关

![image-20201221105813899](https://cdn.jsdelivr.net/gh/lxj53/markdownPictures@main/img/20201221105813.png)

![image-20201221105842565](https://cdn.jsdelivr.net/gh/lxj53/markdownPictures@main/img/20201221105842.png)

# 4 typora设置

​	设置typora的偏好设置，当插入图片时上传图片，上传的服务选择picgo。当设置结束后，验证上传功能。

![image-20201221110129842](https://cdn.jsdelivr.net/gh/lxj53/markdownPictures@main/img/20201221110129.png)

# 5 常见问题解决

## 5.1 github图片无法显示

​	github仓库中图片已存在，但在仓库中打开文件无法展示时，是因为github屏蔽了图片。找到本机的host文件，增加以下代码

```
C:\Windows\System32\drivers\etc\hosts
```

```
# GitHub Start 
140.82.113.3      github.com
140.82.114.20     gist.github.com
151.101.184.133    assets-cdn.github.com
151.101.184.133    raw.githubusercontent.com
151.101.184.133    gist.githubusercontent.com
151.101.184.133    cloud.githubusercontent.com
151.101.184.133    camo.githubusercontent.com
151.101.184.133    avatars0.githubusercontent.com
199.232.68.133     avatars0.githubusercontent.com
199.232.28.133     avatars1.githubusercontent.com
151.101.184.133    avatars1.githubusercontent.com
151.101.184.133    avatars2.githubusercontent.com
199.232.28.133     avatars2.githubusercontent.com
151.101.184.133    avatars3.githubusercontent.com
199.232.68.133     avatars3.githubusercontent.com
151.101.184.133    avatars4.githubusercontent.com
199.232.68.133     avatars4.githubusercontent.com
151.101.184.133    avatars5.githubusercontent.com
199.232.68.133     avatars5.githubusercontent.com
151.101.184.133    avatars6.githubusercontent.com
199.232.68.133     avatars6.githubusercontent.com
151.101.184.133    avatars7.githubusercontent.com
199.232.68.133     avatars7.githubusercontent.com
151.101.184.133    avatars8.githubusercontent.com
199.232.68.133     avatars8.githubusercontent.com
# GitHub End
```

# 6 文件云同步

​	此处选择的是github实现云同步，每次需要下载github上的笔记文件，修改后然后提交

# 7 参考链接

[picgo图床配置](https://blog.csdn.net/yefcion/article/details/88412025)

[github图片无法展示](https://blog.csdn.net/weixin_41279876/article/details/109040379)

