package dev.practice;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class ConsumerWithRebalanceListener {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerWithRebalanceListener.class);

    private static final String TOPIC_NAME = "test";
    private static final String BOOTSTRAP_SERVERS = "my-kafka:9092";
    private static final String GROUP_ID = "test-group";

    public static void main(String[] args) {
        /**
         * IDE(Allow multifle instance) 로 간단하게 인스턴스를 여러개를 띄워보고
         * 인스턴스를 한개씩 종료 시키면서 출력되는 로그를 확인해보자.
         */

        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);

        // 컨슈머에 리밸런싱 리스너를 등록
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(
                List.of(TOPIC_NAME),
                new RebalanceListener()
        );

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, String> record : records) {
                logger.info("record={}", record);
            }
        }
    }
}
