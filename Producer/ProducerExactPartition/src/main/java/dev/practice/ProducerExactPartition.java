package dev.practice;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerExactPartition {

    private static final Logger logger = LoggerFactory.getLogger(ProducerExactPartition.class);

    private static final String TOPIC_NAME = "test";
    private static final String BOOTSTRAP_SERVERS = "my-kafka:9092";

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        int partitionNo = 0;
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, partitionNo, "ProducerExactPartition", "ProducerExactPartition");

        producer.send(record);

        producer.flush();
        producer.close();
    }
}
