FROM eclipse-temurin:21-jdk-alpine
LABEL authors="nld-juandediosh"
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/HomeworkSpringBoot-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","--enable-preview", "-jar","/app.jar"]