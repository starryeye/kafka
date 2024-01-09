# kafka
Kafka study

## Projects
- 1회차 study
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
- 2회차 study
  - Producer
    - Java with Kafka Producer 전반
  - Consumer
    - Java with Kafka Consumer 전반
  - Streams
    - Java with Kafka Streams 전반
      - StreamsDSL
        - KStream, KTable, GlobalKTable
       
- 타 리포지토리
  - [spring-webflux](https://github.com/starryeye/spring-webflux)
    - Spring Cloud Stream + kafka

## Dependency
- Java 17
- Spring Boot 3.x
- Spring for Apache Kafka
- Spring Web
- lombok
- Spring Data Jpa
- thymeleaf
- h2

## Posting
- [kafka 1](https://starryeye.tistory.com/183)
- [kafka 2](https://starryeye.tistory.com/184)
- [kafka 3](https://starryeye.tistory.com/185)
- [kafka 4](https://starryeye.tistory.com/188)
- [kafka 5](https://starryeye.tistory.com/189)
- [kafka 6](https://starryeye.tistory.com/190)
