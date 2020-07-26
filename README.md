### 简介

这是一个基于Springboot2.x，vue2.x的前后端分离的开源博客系统。创建这个项目的主要目的是为了学习从0到1 开发一款项目，整合自己的技术栈。

### 使用技术
- SpringBoot 2.x 后台基本框架
- Vue 2.x 前端基本框架
- ElementUI：页面UI库
- ElasticSearch 搜索层
- RabbitMQ 消息队列
- Shiro 鉴权层
- Redis 缓存层
- Swagger 文档
- Mybaits-Plus Mybatis增强版框架
- lombox getter setter插件
- druid 数据库连接池
- 七牛云 图床

### 站点演示

www.jiajianhuang.cn


### 项目开发部署

#### 服务端
- JDK1.8
- Mysql8.0
- Redis6.0
- IDEA编译器
- Lombox插件
- ElasticSearch7.x
- RabbitMQ3.x
- SpringBoot2.x

部署步骤：
1. 创建数据库blog, 并导入docker -> mysql里的所有sql文件
2. 修改hdj-blog -> application-*.yml的数据库连接、redis连接、ElasticSearch连接、RabbitMQ连接
3. 导入项目，并且运行hdj-blog -> HdjBlogApplication里的main方法

#### 前端
Node.js v12.13.0
Visual Studio Code编辑器


部署步骤：
1. 导入项目，运行 npm install
2. 启动项目：npm run dev
3. 前端地址：localhost:8282 管理界面地址：localhost:8383 账号admin@qq.com，密码123456

### TODOList
#### v1.0
- [x] 完成框架的搭建
- [x] Shiro 完成整合
- [x] Redis完成整合
- [x] SwaggerUI完成整合
- [x] 配置打包部署
- [x] 用户管理
- [x] 角色管理
- [x] 菜单管理
- [x] 文章管理
- [x] 标签管理
- [x] 分类管理
- [x] 加入缓存
- [x] ES搜索文章
- [x] 加入文件上传功能(七牛云)

### 界面预览

![a96FVH.png](https://s1.ax1x.com/2020/07/26/a96FVH.png)
![a96Pqe.png](https://s1.ax1x.com/2020/07/26/a96Pqe.png)
![a96kad.png](https://s1.ax1x.com/2020/07/26/a96kad.png)