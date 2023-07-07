package dev.practice.checkout.repository;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Table(name = "checkoutTable")
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckoutEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkoutId;

    private Long memberId;

    private Long productId;
    private Long amount;

    private String shippingAddress;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public CheckoutEntity(Long memberId, Long productId, Long amount, String shippingAddress) {
        this.checkoutId = null;
        this.memberId = memberId;
        this.productId = productId;
        this.amount = amount;
        this.shippingAddress = shippingAddress;
        this.createdAt = null;
    }
}
