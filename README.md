<p align="center">
  <img src="https://koolhaas.top/img/shiba.png" width="68">
</p>
<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-2.3.6-green" alt="SpringBoot"/>
  <img src="https://img.shields.io/badge/Beetl-3.2.4-brightgreen" alt="Beetl"/>
</p>

### 项目简介
用户纬度统计Gitlab提交记录

### 快速开始

#### 初始化数据库

1. 在项目 `db` 文件夹中找到初始化 `code-analysis.sql` 初始化数据库以及表结构；
2. 修改配置文件 `application.yml`。
    - 配置 `gitlab` 地址；
    - 配置 `gitlab` 个人访问令牌（在gitlab用户设置中点击左侧访问令牌设置即可，需要 [api、read_user、read_repository] 三个权限，在Gitlab个人设置中获取。）；
    - 配置项目的域名，如果不想给此项目配置域名则使用IP即可；
    - 配置 `MySQL` 数据库链接以及账号密码。

```yaml
gitlab:
  api:
    # Gitlab 域名 举个例子：http://gitlab.test.com 或者 http://127.0.0.1:9999
    hostUrl: http://gitlab.test.com
    # Gitlab 个人访问令牌，需要 api read_user read_repository 权限，在Gitlab个人设置中获取。
    privateToken: 8TJ8gzJBEZn2zR-TesT
    
tinybee:
  # Tinybee项目的域名 举个例子：http://tinybee.com 或者 http://127.0.0.1:8080
  domain: http://127.0.0.1:8080

spring:
  datasource:
    # 数据库 IP 端口
    url: jdbc:mysql://127.0.0.1:3306/code-analysis?characterEncoding=utf8&serverTimezone=GMT%2B8
    hikari:
      # 数据库账号
      username: root
      # 数据库密码
      password: 1234567890
```

#### 如何获取Gitlab访问令牌
![gitlab2.png](https://koolhaas.top/img/gitlab3.png)


### 界面展示
![gitlab1.png](https://koolhaas.top/img/gitlab1.png)
![gitlab2.png](https://koolhaas.top/img/gitlab1.png)