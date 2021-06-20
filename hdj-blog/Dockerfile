FROM openjdk:8-jre as builder

WORKDIR application

ADD ./target/hdj-blog-v1.0.1-dev.jar ./app.jar

RUN java -Djarmode=layertools -jar app.jar extract

FROM openjdk:8-jre

MAINTAINER jiajian.java@gmail.com

WORKDIR application

COPY --from=builder application/dependencies/ ./

COPY --from=builder application/spring-boot-loader/ ./

COPY --from=builder application/snapshot-dependencies/ ./

COPY --from=builder application/application/ ./

EXPOSE 8181

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]