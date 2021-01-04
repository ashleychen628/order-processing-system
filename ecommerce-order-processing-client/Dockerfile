FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/ecommerce-order-processing-client-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} ecommerce-order-processing-client.jar
ENTRYPOINT ["java","-jar","/ecommerce-order-processing-client.jar"]