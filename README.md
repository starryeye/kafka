# kafka
Kafka study

## Projects
- HelloKafkaWithJava
  - Java and Kafka
  - Producer, Consumer
- HelloKafkaWithSpring
  - Spring and Kafka
  - Producer, Consumer
- eCommerce
  - kafka cluster, 3개의 Broker
  - checkout
    - client 의 요청을 받아 DB에 적재하고 kafka 에 메시지를 produce
  - shipment
    - checkout 이 produce 한 kafka 메시지를 consume 하여 DB에 적재
  - kafkastreams
    - kafka streams 기능을 구현
    - checkout 이 produce 한 kafka 메시지를 consume 하여 새로운 메시지를 produce

## Dependency
- Java 17
- Spring Boot 3.x
- Spring for Apache Kafka
- Spring Web
- lombok
- Spring Data Jpa
- thymeleaf
- h2
