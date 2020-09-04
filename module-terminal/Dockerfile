FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} terminal.jar
ENTRYPOINT ["java","-jar","/terminal.jar"]