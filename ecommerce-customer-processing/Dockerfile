FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/ecommerce-customer-processing-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} ecommerce-customer-processing.jar
ENTRYPOINT ["java","-jar","/ecommerce-customer-processing.jar"]