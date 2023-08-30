package dev.practice;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

import java.util.Properties;

public class KStreamsJoinKTable {

    private static final String BOOTSTRAP_SERVERS = "my-kafka:9092";
    private static final String APPLICATION_NAME = "order-join-application";

    private static final String ADDRESS_TABLE = "address"; // KTable, Topic name
    private static final String ORDER_STREAM = "order"; // KStream, Topic name
    private static final String ORDER_JOIN_STREAM = "order_join"; // KStream, Topic name

    public static void main(String[] args) {

        // 설정
        Properties properties = new Properties();
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_NAME);

        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // topology
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> orderStream = builder.stream(ORDER_STREAM); // 소스 프로세서, KStream
        KTable<String, String> addressTable = builder.table(ADDRESS_TABLE); // 소스 프로세서, KTable

        orderStream
                .join(
                        addressTable,
                        (order, address) -> order + " send to " + address
                ) // 스트림 프로세서
                .to(ORDER_JOIN_STREAM); // 싱크 프로세서

        KafkaStreams streams = new KafkaStreams(builder.build(), properties);
        streams.start();
    }

    // KTable, KStream 이라 해서 특별한 토픽을 만드는건 아님을 아래로 알수 있다.
    // 코파티셔닝을 만들기 위해, 파티션 개수와 파티션 생성 설정을 동일하게(동일한 브로커에서 생성하므로 설정이 동일) 해준다.
    // $ bin/kafka-topics.sh --bootstrap-server my-kafka:9092 --partitions 3 --topic address -create
    // $ bin/kafka-topics.sh --bootstrap-server my-kafka:9092 --partitions 3 --topic order -create
    // $ bin/kafka-topics.sh --bootstrap-server my-kafka:9092 --partitions 3 --topic order_join -create

    //
    // $ bin/kafka-console-producer.sh --bootstrap-server my-kafka:9092 --topic address --property "parse.key=true" --property "key.separator=:"
    // >alice:Seoul
    // >bob:Newyork
    //
    // $ bin/kafka-console-producer.sh --bootstrap-server my-kafka:9092 --topic order --property "parse.key=true" --property "key.separator=:"
    // >bob:cup
    // >bob:cup
    // >alice:iPhone
    //
    // $ bin/kafka-console-consumer.sh --bootstrap-server my-kafka:9092 --topic order_join --from-beginning
    // cup send to Newyork
    // cup send to Newyork
    // iPhone send to Seoul

    //
    // $ bin/kafka-console-producer.sh --bootstrap-server my-kafka:9092 --topic address --property "parse.key=true" --property "key.separator=:"
    // >alice:Daegu
    //
    // $ bin/kafka-console-producer.sh --bootstrap-server my-kafka:9092 --topic order --property "parse.key=true" --property "key.separator=:"
    // >alice:Tesla
    //
    // $ bin/kafka-console-consumer.sh --bootstrap-server my-kafka:9092 --topic order_join --from-beginning
    // Tesla send to Daegu
}
