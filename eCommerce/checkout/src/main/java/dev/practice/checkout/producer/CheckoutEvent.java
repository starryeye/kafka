package dev.practice.checkout.producer;

import java.time.LocalDateTime;

public record CheckoutEvent(
        Long checkoutId,
        Long memberId,
        Long productId,
        Long amount,
        String shippingAddress,
        LocalDateTime createdAt
) {
}
