
### 项目开发部署

首先需要安装docker 和 docker-compose

#### 开发环境搭建(docker 安装mysql, redis,es,rabbitmq, 可选)
1. 创建目录(在项目blog 根目录下执行命令)
```shell script
base_path="build"
mkdir -vp $base_path/mysql/{init,conf,data}
mkdir -vp $base_path/nginx/{conf,wwww}
mkdir -vp $base_path/es/{plugins,data}
mkdir -vp $base_path/,redis/{conf,data}
```
2. 拷贝相关文件
```shell script
base_path="build"
cp ./docker/mysql/blog_init.sql $base_path/mysql/init
cp ./docker/mysql/my.cnf  $base_path/mysql/conf
cp ./docker/redis/redis.conf  $base_path/redis/conf
cp ./docker/nginx/nginx.conf  $base_path/nginx/conf/nginx.conf
cp ./docker/docker-compose-dev-env.yaml  $base_path/

#安装分词插件(可选)
# 也可到gitee 仓库下载自己打包 https://gitee.com/mirrors/elasticsearch-analysis-ik?_from=gitee_search
wget  https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.1.0/elasticsearch-analysis-ik-7.1.0.zip -O build/es/plugins/elasticsearch-analysis-ik-7.1.0.zip
cd build/es/plugins/
unzip elasticsearch-analysis-ik-7.1.0.zip -d elasticsearch-analysis-ik-7.1.0
```

3. 启动
```shell script
docker-compose -f ./build/docker-compose-dev-env.yaml  up -d

#卸载
docker-compose -f ./build/docker-compose-dev-env.yaml  down
```

### 部署项目
1. 到服务器创建存放目录, 并拷贝本地docker 目录下的配置到服务器指定目录
```shell script
mkdir -vp $PWD/{mysql/{init,conf,data},nginx/{conf,logs,wwww/{front,admin}},es/{plugins,data},redis/{conf,data},app}
```

3. 拷贝相关文件和下载IK 分词到 es/plugins目录下解压
```shell script
scp -r ./docker/**  username@host:/opt/blog


wget  https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.1.0/elasticsearch-analysis-ik-7.1.0.zip -O ./es/plugins/elasticsearch-analysis-ik-7.1.0.zip
cd es/plugins/
unzip elasticsearch-analysis-ik-7.1.0.zip -d elasticsearch-analysis-ik-7.1.0

```

4. 打包前端项目
```shell script
npm run build:prod 
#前台
scp -r ./dist/**  username@host:/opt/blog/nginx/www/front


#后台
scp -r ./dist/**  username@host:/opt/blog/nginx/www/admin

```

5. 后台打包
```shell script
mvn clean compile package -P prod

### 拷贝Dockerfile 文件 和jar 包到服务器
scp -r ./target/hdj-blog-0.0.1-SNAPSHOT.jar ./target/lib ./Dockerfile  username@host:/opt/blog/app

```

6. 拷贝docker-compose.yaml 文件到./blog目录下, 启动
```shell script
docker-compose -f /opt/blog/docker-compose.yaml up -d  --build
#关闭
docker-compose down
```