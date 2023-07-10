package dev.practice.kafkastreams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class StreamListener {

    /**
     * kStream 빈을 등록한다.
     * StreamsBuilder 를 파라미터로 받는다.
     * - StreamsBuilder 는 Spring Kafka 에서 제공하는 Kafka Streams API 를 사용하기 위한 빌더 클래스이다.
     * - KStream, KTable, GlobalKTable 등을 생성할 수 있다.
     *
     * TODO, kStream 를 생성하는 아래 과정 서치
     */
    @Bean
    public KStream<String, String> kStream(StreamsBuilder builder) {
        final String inputTopic = "checkout.complete.v1";
        final String outputTopic = "checkout.productId.aggregated.v1";

        KStream<String, String> inputStream = builder.stream(inputTopic);
        inputStream
                // Json String 을 KeyValue 로 변환 (productId, amount)
                .map((k, v) -> new KeyValue<>(JsonUtils.getProductId(v), JsonUtils.getAmount(v)))
                // 키 값인 productId 로 Group by 를 하기 위한 설정
                .groupByKey(Grouped.with(Serdes.Long(), Serdes.Long()))
                // 1분짜리 시간 Window 설정, 지난 1분간의 메시지를 집계한다.
                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofMinutes(1)))
                // reduce 메서드를 활용하여 values 를 합산한다.
                .reduce(Long::sum)
                // output topic 으로 메시지를 내보내기 위해 toStream 메서드를 사용해서 stream 을 생성한다. map the window key
                .toStream((key, value) -> key.key())
                // outputTopic 에 보낼 Json String 으로 Generate
                .mapValues(JsonUtils::getSendingJson)
                // outputTopic 으로 보낼 kafka message 의 key 값을 null 설정
                .selectKey((key, value) -> null)
                // outputTopic 으로 메세지(null, jsonString) 전송 설정
                .to(outputTopic, Produced.with(null, Serdes.String()));

        return inputStream;
    }
}
