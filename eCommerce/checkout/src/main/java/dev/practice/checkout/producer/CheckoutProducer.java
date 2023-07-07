package dev.practice.checkout.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class CheckoutProducer {

    private final static String CHECKOUT_COMPLETE_TOPIC_NAME = "checkout.complete.v1";

    private final KafkaTemplate<String, CheckoutEvent> kafkaTemplate;

    public void publishToKafka(CheckoutEvent checkoutEvent) {
        kafkaTemplate.send(CHECKOUT_COMPLETE_TOPIC_NAME, checkoutEvent);
    }
}
