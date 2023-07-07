package dev.practice.checkout.service;

import dev.practice.checkout.producer.CheckoutEvent;
import dev.practice.checkout.repository.CheckoutEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class CheckoutDto {

    private Long checkoutId; //입력 X

    private Long memberId;

    private Long productId;
    private Long amount;

    private String shippingAddress;

    private LocalDateTime createdAt; //입력 X

    public CheckoutEntity toEntity() {
        return CheckoutEntity.builder()
                .memberId(memberId)
                .productId(productId)
                .amount(amount)
                .shippingAddress(shippingAddress)
                .build();
    }

    public CheckoutEvent toEvent() {
        return new CheckoutEvent(checkoutId, memberId, productId, amount, shippingAddress, createdAt);
    }
}
