FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/ecommerce-payment-processing-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} ecommerce-payment-processing.jar
ENTRYPOINT ["java","-jar","/ecommerce-payment-processing.jar"]