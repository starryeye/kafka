package dev.practice.HelloKafkaWithSpring.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Consumer 설정에서는 @EnableKafka 어노테이션을 적용해줘야한다.
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    private static final String BOOTSTRAP_SERVER = "localhost:9092";
    private static final String GROUP_ID = "group_two";

    /**
     * ConsumerFactory 빈 등록
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {

        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    /**
     * ConcurrentKafkaListenerContainerFactory 빈 등록
     * ConcurrentKafkaListenerContainerFactory 빈을 통해 이벤트를 수신할 수 있다.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
