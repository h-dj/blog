# 基础镜像 表示基镜像是java8
FROM openjdk:8-jdk-alpine
# 表示 指定临时文件目录为/tmp。其效果是在主机 /var/lib/docker 目录下创建了一个临时文件,
# 并链接到容器的/tmp。该步骤是可选的
VOLUME /tmp
ARG JAR_FILE=hdj-blog-0.0.1-SNAPSHOT.jar
# 表示将jar包添加到镜像中，并重命名app.jar
COPY ${JAR_FILE} app.jar
RUN sh -c 'touch /app.jar'
# 代表的是jvm的参数，如果有需要可以写在这里
ENV JAVA_OPTS=""

EXPOSE 8181

# 表示启动时运行 java -jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
