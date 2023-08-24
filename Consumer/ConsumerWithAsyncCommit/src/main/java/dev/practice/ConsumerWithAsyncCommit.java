package dev.practice;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ConsumerWithAsyncCommit {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerWithAsyncCommit.class);

    private static final String TOPIC_NAME = "test";
    private static final String BOOTSTRAP_SERVERS = "my-kafka:9092";
    private static final String GROUP_ID = "test-group";

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false); // 수동 커밋 모드

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(List.of(TOPIC_NAME));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, String> record : records) {
                logger.info("record={}", record);
            }

            // 오프셋 커밋을 비동기로 수행한다.(별도의 스레드)
            // 파라미터로 콜백 함수를 전달할 수 있다.
            consumer.commitAsync((offsets, exception) -> {
                if(exception != null)
                    System.err.println("Commit failed");
                else
                    System.err.println("Commit succeeded");

                if(exception != null)
                    logger.error("Commit failed for offsets {}", offsets, exception);
            });
        }

    }
}
