package dev.practice.shipment.consumer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.practice.shipment.service.CheckoutEvent;
import dev.practice.shipment.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumer {

    private static final String TOPIC_NAME = "checkout.complete.v1";
    private static final String GROUP_ID = "shipment.group.v1";

    private final ShipmentService shipmentService;

    /**
     * ObjectMapper 는 Java 객체를 JSON 으로 변환하거나(JSON Serialization), JSON 을 Java 객체로 변환하는데(JSON Deserialization) 사용된다.
     * - ObjectMapper 의 configure 메서드는 특정 기능을 활성화하거나 비활성화하는데 사용된다.
     * - DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES 는 JSON 에서 Java 객체로 변환할 때,
     * JSON 에는 있는데 Java 객체에는 매칭되는 프로퍼티가 없을 경우, JsonMappingException 예외가 발생한다. (기본 값, true)
     */
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @KafkaListener(topics = TOPIC_NAME, groupId = GROUP_ID)
    public void listenMessage(String jsonMessage) {
        log.info("Received Message : {}", jsonMessage);

        try {
            CheckoutEvent checkoutEvent = objectMapper.readValue(jsonMessage, CheckoutEvent.class);
            log.info("CheckoutDto : {}", checkoutEvent);
            shipmentService.saveShipmentData(checkoutEvent);
        }catch (Exception e) {
            log.error("KafkaConsumer.listenMessage Fail", e);
            e.printStackTrace();
        }
    }

}
