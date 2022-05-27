FROM openjdk:11-jre-slim-buster
ARG JAR_FILE=Front/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]