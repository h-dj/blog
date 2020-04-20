### 开发环境搭建(mysql, redis,es,rabbitmq)
1. 创建目录(在项目blog 根目录下执行命令)
```shell script
mkdir -vp build/{mysql/{init,conf,data},nginx/{conf,wwww},es/{plugins,data},redis/{conf,data}}
```
2. 拷贝相关文件
```shell script
cp ./docker/mysql/blog_init.sql build/mysql/init
cp ./docker/mysql/my.cnf  build/mysql/conf
cp ./docker/redis/redis.conf  build/redis/conf
cp ./docker/nginx/nginx.conf  build/nginx/conf/nginx.conf
cp ./docker/docker-compose-dev-env.yaml  build/

#安装分词插件(可选)
# 也可到gitee 仓库下载自己打包 https://gitee.com/mirrors/elasticsearch-analysis-ik?_from=gitee_search
wget  https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.1.0/elasticsearch-analysis-ik-7.1.0.zip -O build/es/plugins/elasticsearch-analysis-ik-7.1.0.zip
cd build/es/plugins/
unzip elasticsearch-analysis-ik-7.1.0.zip
```

3. 启动
```shell script
docker-compose -f ./build/docker-compose-dev-env.yaml  up -d

#卸载
docker-compose -f ./build/docker-compose-dev-env.yaml  down
```

### 部署项目
首先需要安装docker 和 docker-compose

1. 下载所需docker镜像
```shell script
docker pull mysql:8.0
docker pull redis:5
docker pull nginx:1.16-alpine
docker pull rabbitmq:3.8.2-management-alpine
docker pull elasticsearch:7.1.0
```
2. 到服务器创建存放目录, 并拷贝本地docker 目录下的配置到服务器指定目录
```shell script
mkdir -vp /opt/blog/{mysql/{init,conf,data},nginx/{conf,wwww/{front,admin}},es/{plugins,data},redis/{conf,data},app}
```

3. 拷贝相关文件和下载IK 分词到 es/plugins目录下解压
```shell script

scp ./docker/mysql/blog_init.sql  username@host:/opt/blog/mysql/init
scp ./docker/mysql/my.cnf  username@host:/opt/blog/mysql/conf
scp ./docker/redis/redis.conf  username@host:/opt/blog/redis/conf
scp ./docker/nginx/nginx.conf  username@host:/opt/blog/nginx/conf/nginx.conf
scp ./docker/docker-compose.yaml  username@host:/opt/blog

wget  https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.1.0/elasticsearch-analysis-ik-7.1.0.zip -O ./es/plugins/elasticsearch-analysis-ik-7.1.0.zip && unzip ./es/plugins/elasticsearch-analysis-ik-7.1.0.zip
cd es/plugins/
unzip elasticsearch-analysis-ik-7.1.0.zip

```

4. 打包前端项目
```shell script
npm run build:prod 
#前台
scp ./dist/**  username@host:/opt/blog/nginx/wwww/front
或者 , 如果修改了ssh端口 -e "ssh -p 端口" 
rsync -avu --progress  ./dist/**  username@host:/opt/blog/nginx/wwww/front

#后台
scp ./dist/**  username@host:/opt/blog/nginx/wwww/admin
或者 , 如果修改了ssh端口 -e "ssh -p 端口" 
rsync -avu --progress  ./dist/**  username@host:/opt/blog/nginx/wwww/admin
```

5. 后台打包
```shell script
mvn clean compile package -P prod

### 拷贝Dockerfile 文件 和jar 包到服务器
scp ./target/hdj-blog-0.0.1-SNAPSHOT.jar  ./Dockerfile  username@host:/opt/blog/app
或者 , 如果修改了ssh端口 -e "ssh -p 端口" 
rsync -avu --progress  ./target/hdj-blog-0.0.1-SNAPSHOT.jar  ./Dockerfile   username@host:/opt/blog/app 

```

6. 拷贝docker-compose.yaml 文件到./blog目录下, 启动
```shell script
docker-compose -f ./blog/docker-compose.yaml up -d
#关闭
docker-compose down
```


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
- [ ] 批量导入markdown 文章
- [ ] 加入文件上传功能(本地和七牛云)
