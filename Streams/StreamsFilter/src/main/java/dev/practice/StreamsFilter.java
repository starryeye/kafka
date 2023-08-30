package dev.practice;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class StreamsFilter {

    private static final String BOOTSTRAP_SERVERS = "my-kafka:9092";

    private static final String FROM_TOPIC_NAME = "stream_log";
    private static final String APPLICATION_NAME = "streams-filter-application";
    private static final String TO_TOPIC_NAME = "stream_log_filter";

    public static void main(String[] args) {

        //mandatory 설정
        Properties properties = new Properties();
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_NAME);

        //optional 설정
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        //행동 정의
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> from = builder.stream(FROM_TOPIC_NAME); // 소스 프로세서로 부터 스트림 데이터를 가져온다.

        from.filter(((key, value) -> value.length() > 5)) // 스트림 프로세서, 데이터 처리 (필터링)
                .to(TO_TOPIC_NAME); // 싱크 프로세서로 스트림 데이터를 보낸다.


        // 설정과 행동으로 카프카 스트림즈를 생성하고 시작
        KafkaStreams streams = new KafkaStreams(builder.build(), properties);
        streams.start();
    }
}
