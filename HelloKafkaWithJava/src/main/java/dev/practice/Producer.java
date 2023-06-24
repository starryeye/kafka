package dev.practice;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.PrintStream;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Producer {

    private static final String BOOTSTRAP_SERVER = "localhost:9092";
    private static final String TOPIC_NAME = "topic5";

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        /**
         * producer 설정
         *
         * mandatory option : BOOTSTRAP_SERVERS_CONFIG, KEY_SERIALIZER_CLASS_CONFIG, VALUE_SERIALIZER_CLASS_CONFIG
         * optional option : ACKS_CONFIG, RETRIES_CONFIG, 그 외
         */
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        /**
         * producer 가 발행한 메시지는 네트워크를 통해 전송되므로 직렬화 방법을 설정해줘야한다.
         * 여기서는 String 만을 사용하므로 카프카 라이브러리에 포함된 StringSerializer 을 이용한다.
         */
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.RETRIES_CONFIG, "100");

        /**
         * 카프카 프로듀서 인스턴스 생성
         */
        @SuppressWarnings("resource")
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

        String msg = "First message";

        /**
         * 카프카 브로커로 전송할 메시지는 ProducerRecord 객체를 이용한다.
         * 제네릭은 Producer 설정에서의 Serializer key, value 제네릭과 일치 시킨다.
         */
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, msg);

        /**
         * send 메서드를 통해 프로듀서에게 메세지 전송을 요청한다.
         * 프로듀서는 내부의 배치 프로세스에 따라 메시지를 전송해준다.
         *
         * RecordMetadata 는 파티션 번호, 오프셋 번호 등의 전송 결과 정보를 포함한다.
         */
        RecordMetadata recordMetadata = kafkaProducer.send(record).get();
        System.out.printf("Send message : %s, partition number : %d, offset number : %d",
                msg, recordMetadata.partition(), recordMetadata.offset());

        kafkaProducer.flush();
        kafkaProducer.close();
    }
}
