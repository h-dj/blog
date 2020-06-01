### 博客后端服务

#### 技术选型
- Spring-Boot , Spring ,SpringMVC
- MyBatis and MyBatis-Plus
- MySQL.8.0
- Shiro + JWT 权限验证
- maven 
- logback 日志
- API文档Swagger
- lombok 
- 消息队列RabbitMQ
- 搜索引擎ElasticSearch
- NoSQL Redis
- Docker化部署


### 打包
```shell script
mvn clean compile package -P prod
```

### 部署
```shell script

#上传 
rsync -vcr --progress -e "ssh -p 端口" ./target/hdj-blog-0.0.1-SNAPSHOT.jar ./target/lib ./Dockerfile   username@host:/opt/blog/app 

#启动
docker-compose -f /opt/blog/docker-compose.yml up -d blog-api  --build
```