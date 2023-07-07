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

    public void publishToKafka(CheckoutEvent checkoutEvent) {
        try{
            kafkaTemplate.send(CHECKOUT_COMPLETE_TOPIC_NAME, checkoutEvent);
            log.info("CheckoutProducer.publishToKafka Success");
        } catch (Exception e) {
            log.error("CheckoutProducer.publishToKafka", e);
            e.printStackTrace();
        }

    }
}
