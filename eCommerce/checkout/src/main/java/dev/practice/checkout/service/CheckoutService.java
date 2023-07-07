package dev.practice.checkout.service;

import dev.practice.checkout.producer.CheckoutProducer;
import dev.practice.checkout.repository.CheckoutEntity;
import dev.practice.checkout.repository.CheckoutRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CheckoutRepository checkoutRepository;
    private final CheckoutProducer checkoutProducer;

    public Long saveCheckoutData(CheckoutDto checkoutDto) {

        saveToDataBase(checkoutDto);

        publishToKafka(checkoutDto);

        return checkoutDto.getCheckoutId();
    }

    private void saveToDataBase(CheckoutDto checkoutDto) {
        log.info("saveToDataBase");
        CheckoutEntity persistenceEntity = checkoutRepository.save(checkoutDto.toEntity());

        checkoutDto.setCheckoutId(persistenceEntity.getCheckoutId());
        checkoutDto.setCreatedAt(persistenceEntity.getCreatedAt());
    }

    private void publishToKafka(CheckoutDto checkoutDto) {
        log.info("publishToKafka");
        checkoutProducer.publishToKafka(checkoutDto.toEvent());
    }
}
