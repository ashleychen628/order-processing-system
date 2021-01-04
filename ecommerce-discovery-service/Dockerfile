FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/ecommerce-discovery-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} ecommerce-discovery-service.jar
ENTRYPOINT ["java","-jar","/ecommerce-discovery-service.jar"]