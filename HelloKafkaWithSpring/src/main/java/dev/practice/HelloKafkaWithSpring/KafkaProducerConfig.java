package dev.practice.HelloKafkaWithSpring;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    private static final String BOOTSTRAP_SERVER = "localhost:9092";

    /**
     * ProducerFactory 빈 등록
     *
     * ProducerFactory 를 통해 KafkaTemplate 을 만들고
     * 메시지 발송 모듈에서는 KafkaTemplate 을 통해 메시지를 발행할 수 있다.
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {

        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * KafkaTemplate 빈 등록
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }


    /**
     * ProducerFactory 빈 등록
     * DTO 객체를 Kafka 로 전송하기 위해서 JsonSerializer 를 사용한다.
     *
     * ProducerFactory 를 통해 KafkaTemplate 을 만들고
     * 메시지 발송 모듈에서는 KafkaTemplate 을 통해 메시지를 발행할 수 있다.
     *
     * 스프링 빈은 동일 타입 빈이 여러개 존재할 경우, 타입으로 빈을 구분할 수 없기 때문에
     * 빈의 이름(newProducerFactory)을 통해 구분하여 주입받도록 스프링이 지원한다.
     */
    @Bean
    public ProducerFactory<String, MyMessage> newProducerFactory() {

        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * KafkaTemplate 빈 등록
     */
    @Bean
    public KafkaTemplate<String, MyMessage> newKafkaTemplate() {
        return new KafkaTemplate<>(newProducerFactory());
    }
}
