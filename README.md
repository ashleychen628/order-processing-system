# Egen Project Exercise : Ecommerce Order Processing

## Description
Order processing system is developed following a micro-service architecture where order processing API's communicates with each other and other api's running on different machine.
This system is developed using PostgreSQL database, Spring Boot, Spring Data JPA, Spring Cloud Netflix Eureka service registry, used Spring RESTfull micro-services, 
Spring Batch and Apache Kafka for asynchronous and batch/bulk order create and update.

Order processing System communicates with other processing systems like customer-processing, payment-processing using Eureka service registration and discovery.
Bulk create and update operations are performed using spring batch and these internal API's are asynchronous in nature where as API's developed for cline facing order processing system
are synchronous in nature.

This processing system focuses mainly on developing micro-services for order processing which will be client facing api's and bulk operations are developed for internal services.

## Assumptions Made
Since focus of this application is mainly developing order processing api's like creating orders, updating orders, getting order details for customer and updating order statuses. An
assumption is made that registered customer can only place the orders (i,e customer-id sent in request should already exist in the system). If customer is not registered then, appropriate error is sent back as response and 
appropriates api's are developed to add customer details as described below is API's section.
After creating customers, order's can be placed using multiple payment methods like : DEBIT, CREDIT, GIFTCARD for single order and also supports 
multiple order items.

## Services and API's
1. ecommerce-discovery-service
This service is running eureka server which is used for service registry and discovery using Spring Cloud Netflix package.

2. ecommerce-customer-processing
This service is developed to add new customers and also provides and API like '/isRegisteredCustomer' which is used by order-processing service to check if customer is registered or not.

    API's:
    
     **/createNewCustomer** -> POST api to create new customer. Customer request consists of customer details, payment details and billing details. Sample json data in /data/CreateNewCustomer.txt
     
     /getCustomer/{customerId} -> GET api to get customer details.
     
     /isRegisteredCustomer/{customerId} -> GET api which is used by order-processing services using discovery services(no hardcoded url's)

3. ecommerce-payment-processing
This service is developed to get payment details of customer like card details and billing address associated with payment card.

    API's:
    
    getCustomerPayment/{customerId} -> GET api call used by order-processing system to check/validate payment details and billing address 
    provided in the order request with existing payment details and billing address for the existing customer.

4. ecommerce-order-processing-client
This is the client facing service which provides many api's using different http methods like GET, PUT, POST, DELETE. This service is registered on eureka discovery server just like
customer-processing and payment-processing system in order to communicate with each other using discovery service.

    API's:
    
    /createOrder -> POST api which is used to create order. As soon as request is sent, the order-processing system makes api's call internally to customer processing system to check
    if customerId present in the request is of existing customer or not. If api returns true then it proceeds and make another api call to payment micro-service to match the payment details
    like card type and billing address details of the one present in the request with existing customer records.
    
    /getOrder/{orderNumber} -> GET api call to get the order details by providing order number in the api url.
    
    /cancelOrder/{orderNumber} -> PUT api call to change the status of existing order to CANCELLED. Order number is sent in the api url.
    
    /updateOrderStatus -> PUT api call to update the order status as provided in the request body.
    
    /getOrderByCustomerIDAndOrderID/{customerId}/{orderNumber} -> GET api call to get the order details of the given customer and given order id.

5. order-processing-bulk-operations
This is the service developed for internal clients like POS Terminals, fulfilment systems, warehouse picking systems etc. This service exposes 2 api calls which allows
to send bulk order details in json format for order creation and updates. As soon as bulk request is sent, the back-end of this system publishes the messages on kafka topics
which later will be consumed by bulk-create batch job and bulk-update batch job developed separately.

    API's:
    
    /bulk-order-create -> POST api which takes list of order details and published message to kafka topic.
    
    /bulk-order-update -> POST api call which is used to udpate order status in bulk/batch. The request is the list of order number and statuses as shown in sample file. 

6. order-processing-bulk-create-async-batch-job
This is the batch processing job which can be scheduled to trigger any number of times in a day by providing @Schedule parameters. This system is not scheduled
currently so this need to be triggered as and when we want to perform bulk create. This job looks for messages on kafka topics and perform bulk create as soon as message is sent
using bulk-order-create api as described above.

7. order-processing-bulk-update-async-batch-job
This batch processing job is similar to above job which reads data from kafka topic as soon as data is available and perform batch udaptes.

## Steps to run the application
Requirements: Java 8, Install PostgreSQL, Install Apache Kafka 2.13.

** PostgreSQL access details used in application.properties **

** db user: postgres **

** db password: root **
1. Create database named ecommerce_db in postgresql using command
    "create database ecommerce_db;"

2. Create the below ENUMs in PostgreSQL 'ecommerce_db' database using below commands:

    create type shipping_status_info as enum('PICKUP', 'SHIPPED', 'CANCELLED','DELIVERED');
    
    create type order_status_info as enum('PLACED', 'SHIPPED','DECLINED', 'CANCELLED','DELIVERED');
    
    create type shipping_method_info as enum('STORE_PICKUP', 'CURBSIDE','DELIVERY');
    
    create type payment_method_info as enum('DEBIT', 'CREDIT','GIFTCARD');

3. Install Kafka and run below commands from kafka home dir:
    To start zookeeper server:
    
    $ bin/zookeeper-server-start.sh config/zookeeper.properties
    
    To start kafka server
    
    $ bin/kafka-server-start.sh config/server.properties
    
    After successfully starting both the servers, create 2 new topics each for specific bulk operations:
    
    $ bin/kafka-topics.sh --create --topic create-bulk-orders --bootstrap-server localhost:9092
    
    $ bin/kafka-topics.sh --create --topic update-bulk-orders --bootstrap-server localhost:9092
    
    You can view messages from kafta topics using:
    
    $ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic create-bulk-orders --from-beginning
    
    $ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic update-bulk-orders --from-beginning

4. Run the ecommerce-discovery-service spring boot application either from command line(command: mvn spring-boot:run)
or IDE. The application will be hosted on port 8761. After successfully bringing up the application, you should be able to access home page:
http://localhost:8761/

5. Run the ecommerce-customer-processing and ecommerce-payment-processing spring boot either from command line using command: mvn spring-boot:run 
or using IDE. ecommerce-customer-processing is configured to run on port 8082 and ecommerce-payment-processing  is configured to run on port 8083.
After successfully bringing up the application, create new customer using the end point:
http://localhost:8082/createNewCustomer

    Sample post data can be found in /data/CreateNewCustomer.txt
    
    Test: http://localhost:8082/getCustomer/{customerId} to check if persisted data is retrieved.

6. Run ecommerce-order-processing-client spring boot applcaition which is configured to run on port 8081.
    After successfully bringing up the application, create new order details using the end point:
    http://localhost:8081/createOrder
    
    Sample post data can be found in /data/CreateNewOrders.txt
    
    Also we can perform other order processing operations using the other api's.
    
    http://localhost:8081/updateOrderStatus
    
    Sample data found in /data/UpdateOrderStatus.txt

7. Run the order-processing-bulk-operations spring boot application either using IDE or command line. This application is
configured to run on port 9081 and has only one API to creat bulk orders. After successfully bringing up the application, 

    create new order details using the end point and sending list of order details:
    
    http://localhost:9081/bulk-order-create
    
    Sample post data can be found in /data/BulkCreateNewOrder.txt
    
    create new order details using the end point and sending list of order details:
    http://localhost:9081/bulk-order-update
    
    Sample post data can be found in /data/BulkUpdateOrder.txt

8. Run order-processing-bulk-create-async-batch-job and order-processing-bulk-update-async-batch-job using command line or
IDE. As soon as the application runs, it starts reading kafta topic messages and then performs bulk create by order-processing-bulk-create-async-batch-job and 
performs bulk update by order-processing-bulk-update-async-batch-job

## Steps to run using Docker
From command line:

    1. docker run --name postgres_db -p 5432:5432 -e POSTGRES_PASSWORD=root -d postgres

    2. docker exec -it postgres_db psql -U postgres -P

    3. postgres=# create database ecommerce_db;

    cd project parent dir (\egen-project-exercise)
    4. From dir:  ecommerce-discovery-service run below commands
        mvn install
        ./mvnw package && java -jar target/ecommerce-discovery-service-0.0.1-SNAPSHOT.jar
        docker build -t ecommerce-discovery-service .
        docker run -it -p8761:8761 ecommerce-discovery-service
	
    5. From dir:  ecommerce-customer-processing run below commands
        mvn install
        ./mvnw package && java -jar target/ecommerce-customer-processing-0.0.1-SNAPSHOT.jar
        docker build -t ecommerce-customer-processing .
        docker run -it -p8082:8082 ecommerce-customer-processing
	
    6. From dir:  ecommerce-payment-processing run below commands
        mvn install
        ./mvnw package && java -jar target/ecommerce-payment-processing-0.0.1-SNAPSHOT.jar
        docker build -t ecommerce-payment-processing .
        docker run -it -p8083:8083 ecommerce-payment-processing
	
    7. From dir:  ecommerce-order-processing-client run below commands
        mvn install
        ./mvnw package && java -jar target/ecommerce-order-processing-client-0.0.1-SNAPSHOT.jar
        docker build -t ecommerce-order-processing-client .
        docker run -it -p8081:8081 ecommerce-order-processing-client
	
    8. From dir:  order-processing-bulk-operations run below commands
        mvn install
        ./mvnw package && java -jar target/order-processing-bulk-operations-0.0.1-SNAPSHOT.jar
        docker build -t order-processing-bulk-operations .
        docker run -it -p9081:9081 order-processing-bulk-operations

    Above steps are to run each docker container individually. In order to run all containers together run below commands
    From: Project parent dir (\egen-project-exercise)
        docker-compose config
        docker-compose up
