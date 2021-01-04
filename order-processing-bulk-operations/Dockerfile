FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/order-processing-bulk-operations-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} order-processing-bulk-operations.jar
ENTRYPOINT ["java","-jar","/order-processing-bulk-operations.jar"]