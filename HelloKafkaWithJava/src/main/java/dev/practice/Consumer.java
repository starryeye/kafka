package dev.practice;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class Consumer {

    private static final String BOOTSTRAP_SERVER = "localhost:9092";
    private static final String TOPIC_NAME = "topic5";
    private static final String GROUP_ID = "group_one";

    public static void main(String[] args) {

        /**
         * Consumer 설정
         *
         * mandatory option : BOOTSTRAP_SERVERS_CONFIG, KEY_DESERIALIZER_CLASS_CONFIG, VALUE_DESERIALIZER_CLASS_CONFIG
         * optional option : GROUP_ID_CONFIG, AUTO_OFFSET_RESET_CONFIG, 그 외
         */
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);

        /**
         * consumer 가 소비할 메시지는 네트워크를 통해 전송되므로 역직렬화 방법을 설정해줘야한다.
         * 여기서는 String 만을 사용하므로 카프카 라이브러리에 포함된 StringDeserializer 를 이용한다.
         */
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        /**
         * Consumer 를 Kafka 에서 구분하기 위해 그룹 아이디 설정을 해준다.
         */
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);

        /**
         * java kafka client 에서는 from-beginning 옵션 설정을 아래와 같이 한다.
         */
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        /**
         * kafka consumer 인스턴스 생성
         */
        @SuppressWarnings("resource")
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);

        /**
         * subscribe 메서드로 토픽의 이름을 설정한다.
         * 일반적으로는 한개의 토픽만 셋팅하면 되지만, 필요에 따라 여러개의 토픽을 한번에 지정할 수 도 있다.(Collection)
         */
        kafkaConsumer.subscribe(List.of(TOPIC_NAME));

        //
        while (true) {
            /**
             * consumer 는 브로커에 폴링 방식으로 데이터를 가져온다. 여기서는 while loop 를 통해 받아오겠다.
             * Consumer 의 poll 메서드는 ConsumerRecord 리스트를 반환한다.
             * poll 메서드의 파라미터는 consumer side 의 버퍼에 데이터를 모으기 위해 기다리는 타임아웃 간격이다.
             * 모인 데이터는 리스트 형태이다.
             */
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(1L));

            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                System.out.println("Received message : " + consumerRecord);
            }
        }
    }
}
