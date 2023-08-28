package dev.practice;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerWithShutdownHook {

    private final static Logger logger = LoggerFactory.getLogger(ConsumerWithShutdownHook.class);

    private final static String TOPIC_NAME = "test";
    private final static String BOOTSTRAP_SERVERS = "my-kafka:9092";
    private final static String GROUP_ID = "test-group";

    private static KafkaConsumer<String, String> consumer;


    public static void main(String[] args) {

        /**
         * KafkaConsumer 의 wakeup 메서드로 KafkaConsumer 를 안전하게 종료시킬 수 있다.
         * wakeup 메서드가 실행된 이후..
         * poll 메서드를 호출하면 WakeupException 예외가 발생한다.
         * WakeupException 예외를 캐치하여 자원들을 해제한다.
         *
         *
         * <실습>
         *
         * IDE 로 인스턴스를 실행하고
         * $ ps -ef | grep ConsumerWithShutdownHook
         * 명령어를 통해 프로세스 아이디를 확인한다.
         *
         * $ kill -term {프로세스 아이디}
         * 명령어로 인스턴스를 종료시켜보자..
         *
         * 아래 로그가 순서대로 찍힌다.
         * 1. shutdown hook
         * 2. wakeup consumer
         * 3. consumer close
         */

        // shutdown hook 등록
        Runtime.getRuntime().addShutdownHook(new ShutdownThread());

        Properties configs = new Properties();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        consumer = new KafkaConsumer<>(configs);
        consumer.subscribe(Arrays.asList(TOPIC_NAME));

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                for (ConsumerRecord<String, String> record : records) {
                    logger.info("record={}", record);
                }
                consumer.commitSync();
            }
        } catch (WakeupException e) {
            logger.warn("Wakeup consumer");
        } finally {
            logger.warn("Consumer close");
            consumer.close();
        }
    }

    static class ShutdownThread extends Thread {
        public void run() {
            logger.info("Shutdown hook");
            consumer.wakeup(); // KafkaConsumer 를 안전하게 종료 시킬 수 있다.
        }
    }
}
