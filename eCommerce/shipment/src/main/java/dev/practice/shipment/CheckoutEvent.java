package dev.practice.shipment;

import java.time.LocalDateTime;

public record CheckoutEvent(
        Long checkoutId,
        Long memberId,
        Long productId,
        Long amount,
        String shippingAddress
//        LocalDateTime createdAt // 이벤트에는 생성일자가 필요 없다. DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES = false 설정으로 인해 에러가 발생하지 않는다.
) {

    public ShipmentEntity toEntity() {
        return ShipmentEntity.builder()
                .checkoutId(checkoutId)
                .memberId(memberId)
                .productId(productId)
                .amount(amount)
                .shippingAddress(shippingAddress)
                .build();
    }
}
