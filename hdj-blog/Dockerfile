
FROM openjdk:8-jre as builder

WORKDIR application

COPY ./target/*.jar ./app.jar

RUN java -Djarmode=layertools -jar app.jar extract

FROM adoptopenjdk/openjdk8:jre8u-ubuntu-nightly

MAINTAINER jiajian.java@gmail.com

WORKDIR application

COPY --from=builder application/dependencies/ ./

COPY --from=builder application/spring-boot-loader/ ./

COPY --from=builder application/snapshot-dependencies/ ./

COPY --from=builder application/application/ ./

EXPOSE 8181

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]