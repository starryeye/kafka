package dev.practice;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;


public class SimpleProducer {

    private static final Logger logger = LoggerFactory.getLogger(SimpleProducer.class);

    private static final String TOPIC_NAME = "test";
    private static final String BOOTSTRAP_SERVERS = "my-kafka:9092";

    public static void main(String[] args) {

        // Producer 생성에 필요한 필수 옵션 설정
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Producer 생성
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // Producer 로 메시지를 보내기 위해 ProducerRecord 생성
        String messageValue = "testMessage";
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, messageValue);

        // Producer 로 ProducerRecord 전송, 이후 Partitioner, Accumulator 가 처리를 하고 Sender 에 의해 실제 전송
        producer.send(record);

        logger.info("record={}", record);

        // Producer Sender 로 하여금 강제 전송
        producer.flush();
        producer.close();
    }
}
