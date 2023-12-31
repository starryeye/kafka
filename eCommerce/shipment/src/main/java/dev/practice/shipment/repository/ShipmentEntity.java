package dev.practice.shipment.repository;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Table(name = "shipment")
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class ShipmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipmentId;

    private Long checkoutId;

    private Long memberId;

    private Long productId;
    private Long amount;

    private String shippingAddress;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public ShipmentEntity(Long checkoutId, Long memberId, Long productId, Long amount, String shippingAddress) {
        this.shipmentId = null;
        this.checkoutId = checkoutId;
        this.memberId = memberId;
        this.productId = productId;
        this.amount = amount;
        this.shippingAddress = shippingAddress;
        this.createdAt = null;
    }
}
