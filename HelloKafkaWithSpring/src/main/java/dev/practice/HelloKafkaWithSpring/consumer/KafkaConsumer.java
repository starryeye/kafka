package dev.practice.HelloKafkaWithSpring.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.practice.HelloKafkaWithSpring.MyMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * TODO
 * - ObjectMapper 가 최선인가.. -> ConsumerConfig 에서 JsonDeserializer 를 사용하는 방법으로 시도해보자.
 * - pull 주기 설정과 같은 디테일한 추가 설정은 어떻게 하는가..
 * - 스레드 관점으로 보면 어떻게 동작 되는가..
 * - I/O 관점으로 블로킹인가 논블로킹인가..
 * - etc..
 */
@Component
public class KafkaConsumer {

    private static final String TOPIC_NAME = "topic5";

    /**
     * Kafka 에서 받은 메시지(Json) 을 객체로 변환하기 위해 ObjectMapper 를 사용
     */
    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @KafkaListener 어노테이션으로 Consumer 코드를 간단하게 작성할 수 있다.
     * - 이벤트 메시지 건건의 단위로 처리할 수 있다.
     * - TODO, Batch 단위의 처리도 할 수 있다.
     */
    @KafkaListener(topics = TOPIC_NAME)
    public void listenMessage(String jsonMessage) {
        try {
            MyMessage message = objectMapper.readValue(jsonMessage, MyMessage.class);
            System.out.printf("Received Message : %s%n", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
