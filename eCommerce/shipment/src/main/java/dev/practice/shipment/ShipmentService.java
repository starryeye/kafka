package dev.practice.shipment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    public Long saveShipmentData(CheckoutEvent checkoutEvent) {
        log.info("saveToDataBase");
        ShipmentEntity shipmentEntity = shipmentRepository.save(checkoutEvent.toEntity());
        return shipmentEntity.getShipmentId();
    }
}
