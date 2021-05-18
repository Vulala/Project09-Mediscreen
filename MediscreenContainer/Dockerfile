FROM openjdk:15-jdk-alpine as build 
ARG JAR_FILE=target/*.jar 
COPY Project09-Mediscreen-1.0.0.jar Mediscreen-1.0.0.jar 
ENTRYPOINT ["java","-jar","/Mediscreen-1.0.0.jar"]
