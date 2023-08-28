package dev.practice;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class RebalanceListener implements ConsumerRebalanceListener {

    private static final Logger logger = LoggerFactory.getLogger(RebalanceListener.class);

    /**
     * 리밸런싱은 컨슈머가 하나씩 추가되거나 삭제될때 발생된다.
     * (최초 한개의 컨슈머가 생성될때도 물론 리밸런싱이다.)
     */

    // 리밸런싱 직전 호출
    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        logger.warn("Partitions are revoked : {}", partitions.toString()); // 현재 파티션 정보 출력
        // 여기다가 커밋을 해주면 graceful..
    }

    // 리밸런싱 이후 호출
    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        logger.warn("Partitions are assigned : {}", partitions.toString()); // 현재 파티션 정보 출력
    }
}
