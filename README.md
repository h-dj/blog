### 开发环境搭建(mysql, redis,es,rabbitmq)
1. 创建目录(在项目blog 根目录下执行命令)
```shell script
mkdir -vp .runtime/{mysql/{init,conf,data},nginx/{conf,wwww},es/{plugins,data},redis/{conf,data}}
```
2. 拷贝相关文件
```shell script
cp ./docker/mysql/blog_init.sql .runtime/mysql/init
cp ./docker/mysql/my.cnf  .runtime/mysql/conf
cp ./docker/redis/redis.conf  .runtime/redis/conf
cp ./docker/docker-compose-dev.yaml  .runtime

#安装分词插件(可选)
# 也可到gitee 仓库下载自己打包 https://gitee.com/mirrors/elasticsearch-analysis-ik?_from=gitee_search
wget  https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.1.0/elasticsearch-analysis-ik-7.1.0.zip -O .runtime/es/plugins/elasticsearch-analysis-ik-7.1.0.zip
cd .runtime/es/plugins/
unzip elasticsearch-analysis-ik-7.1.0.zip
```

3. 启动
```shell script
docker-compose -f .runtime/docker-compose-dev.yaml  up -d

#卸载
docker-compose -f .runtime/docker-compose-dev.yaml  down
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
mkdir -vp ./blog/{mysql/{init,conf,data},nginx/{conf,wwww},es/{plugins,data},redis/{conf,data},app}
```

3. 下载IK 分词到 es/plugins目录下解压
```shell script
wget  https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.1.0/elasticsearch-analysis-ik-7.1.0.zip -O ./es/plugins && unzip ./es/plugins/elasticsearch-analysis-ik-7.1.0.zip
```

4. 打包前端项目
```shell script
npm run build:prod 

#前台项目，把./dist/** 拷贝到nginx/www/front
#后台项目，把./dist/** 拷贝到nginx/www/admin
```

5. 后台打包
```shell script
mvn clean compile package

#把jar包和Dockerfile文件 放到 blog/app下
```

6. 拷贝docker-compose.yaml 文件到./blog目录下, 启动
```shell script
docker-compose -f ./blog/docker-compose.yaml up -d
#关闭
docker-compose down
```


### ToDoList


