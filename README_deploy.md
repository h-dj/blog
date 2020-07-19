### 部署项目
1. 使用Docker 运行MySQL,Redis,RabbitMQ,Elasticsearch
2. 初始化数据库
3. 打包前端项目

```shell script
##打包
npm run build:prod 
##压缩
tar -cvzf ./hdj-blog-front.tar.gz  ./dist/**
##上传到服务器,解压
```

5. 后台打包
```shell script
mvn clean compile package -P prod

##上传到服务器

```

6. 拷贝docker-compose.yaml 文件到./blog目录下, 启动
```shell script
docker-compose -f /opt/blog/docker-compose.yaml up -d  --build
#关闭
docker-compose down
```