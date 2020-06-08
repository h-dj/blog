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


### 正式环境打包(分离的依赖jar到lib文件夹)
```shell script
mvn clean compile package -P prod
```

### 启动
```shell script
# --mpw.key 使用了Mybatis-Plus 数据安全保护
java -jar hdj-blog-0.0.1-SNAPSHOT.jar  --mpw.key=秘钥
```