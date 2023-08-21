package dev.practice;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerWithSyncCallback {

    private final static Logger logger = LoggerFactory.getLogger(ProducerWithSyncCallback.class);

    private final static String TOPIC_NAME = "test";
    private final static String BOOTSTRAP_SERVERS = "my-kafka:9092";

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        properties.put(ProducerConfig.ACKS_CONFIG, "0");
// acks 값을 0 으로 설정하면 프로듀서 입장에서는 응답에 오프셋 값이 없어서.. metadata 에서 -1 로 의미 없는 값이 할당 되어있다.

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, "ProducerWithSyncCallback", "ProducerWithSyncCallback");

        try {
            // send 메서드는 future (비동기 결과) 를 반환한다.
            RecordMetadata metadata = producer.send(record).get(); // future 에 get 메서드로 동기적으로 반환 값을 받는다.
            logger.info("metadata={}", metadata.toString()); // 토픽 이름, 메시지가 적재된 파티션 번호, 오프셋 의 정보가 출력된다.
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally {
            producer.flush();
            producer.close();
        }
    }

}
