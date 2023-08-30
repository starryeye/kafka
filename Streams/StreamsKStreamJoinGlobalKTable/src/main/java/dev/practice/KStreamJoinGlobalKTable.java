package dev.practice;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class KStreamJoinGlobalKTable {

    private static final String BOOTSTRAP_SERVERS = "my-kafka:9092";
    private static final String APPLICATION_NAME = "global-table-join-application";

    private static final String ADDRESS_GLOBAL_TABLE = "address_v2";
    private static final String ORDER_STREAM = "order";
    private static final String ORDER_JOIN_STREAM = "order_join";

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_NAME);

        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> orderStream = builder.stream(ORDER_STREAM);
        GlobalKTable<String, String> addressGlobalTable = builder.globalTable(ADDRESS_GLOBAL_TABLE);

        orderStream.join(
                addressGlobalTable,
                (orderKey, orderValue) -> orderKey, //orderStream 의 어떤 것을 key 로 하여 조인할 것인지 정해줘야한다.
                (order, address) -> order + " send to " + address
        ).to(ORDER_JOIN_STREAM);

        KafkaStreams streams = new KafkaStreams(builder.build(), properties);
        streams.start();
    }

    // StreamsKStreamsJoinKTable 실습에서 이어진다.
    // KStreams 토픽(order) 의 파티션 개수(3) 과 다름을 알 수 있다.
    // $ bin/kafka-topics.sh --bootstrap-server my-kafka:9092 --create --partitions 2 --topic address_v2

    //
    // $ bin/kafka-console-producer.sh --bootstrap-server my-kafka:9092 --topic address_v2 --property "parse.key=true" --property "key.separator=:"
    // >tom:Jeju
    //
    // $ bin/kafka-console-producer.sh --bootstrap-server my-kafka:9092 --topic order --property "parse.key=true" --property "key.separator=:"
    // >tom:iPhone
    //
    // $ bin/kafka-console-consumer.sh --bootstrap-server my-kafka:9092 --topic order_join --from-beginning
    // iPhone send to Jeju
}
