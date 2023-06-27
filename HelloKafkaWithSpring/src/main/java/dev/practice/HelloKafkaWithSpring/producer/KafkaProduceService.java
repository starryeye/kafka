package dev.practice.HelloKafkaWithSpring.producer;

import dev.practice.HelloKafkaWithSpring.MyMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class KafkaProduceService {

    private static final String TOPIC_NAME = "topic5";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, MyMessage> newKafkaTemplate;

    public void send(String message) {
        kafkaTemplate.send(TOPIC_NAME, message);
    }

    /**
     * KafkaTemplate 의 send 메서드는 리턴 타입으로 CompletableFuture 를 지원한다.
     * 그래서 호출 스레드입장에서는 비동기 논블로킹으로 동작한다.
     *
     * Apache Kafka Producer 는 기본적으로 하나의 I/O 스레드를 사용하여 모든 네트워크 통신을 처리한다.
     * 이 스레드는 클라이언트가 Kafka 브로커로 메시지를 전송하고 응답을 받는 모든 작업을 처리한다.
     * 그래서, Kafka producer client 가 관리하는 하나의 I/O 스레드는 비동기, 논블로킹 방식이라 볼 수 있다.
     *
     * addCallback 을 통해 콜백을 등록할 수 있고, 브로커 전송 결과를 비동기로 확인할 수 있다.
     */
    public void sendWithCallback(String message) {

        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(TOPIC_NAME, message);

        future
                .thenAccept(r -> System.out.printf("Sent message : %s, offset : %d%n", message, r.getRecordMetadata().offset()))
                .exceptionally(e -> {
                    System.out.printf("Failed to send message : %s, cause : %s%n", message, e.getMessage());
                    return null;
                });
    }

    /**
     * 객체를 Json 으로 변환하여 발행한다.
     */
    public void sendMyMessage(MyMessage message) {
        newKafkaTemplate.send(TOPIC_NAME, message);
    }
}
