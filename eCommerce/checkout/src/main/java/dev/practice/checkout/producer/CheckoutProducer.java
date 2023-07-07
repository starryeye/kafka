package dev.practice.checkout.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckoutProducer {

    private final static String CHECKOUT_COMPLETE_TOPIC_NAME = "checkout.complete.v1";

    private final KafkaTemplate<String, CheckoutEvent> kafkaTemplate;

    //kafka 의 Json Serializer 는 Record Typer 을 지원하는지 확인 필요
    //kafka 의 Json Serializer 는 LocalDateTime 을 지원하는지 확인 필요
    public void publishToKafka(CheckoutEvent checkoutEvent) {
        try{
            kafkaTemplate.send(CHECKOUT_COMPLETE_TOPIC_NAME, checkoutEvent);
            log.info("CheckoutProducer.publishToKafka Success");
        } catch (Exception e) {
            log.error("CheckoutProducer.publishToKafka Fail", e);
            e.printStackTrace();
        }
    }
}
