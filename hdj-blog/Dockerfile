
# java -Djarmode=layertools -jar app.jar extract

FROM openjdk:8-jre

MAINTAINER jiajian.java@gmail.com

WORKDIR application

COPY ./target/dependencies/ ./

COPY ./target/spring-boot-loader/ ./

COPY ./target/snapshot-dependencies/ ./

COPY ./target/application/ ./

EXPOSE 8181

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]