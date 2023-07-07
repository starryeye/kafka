package dev.practice.checkout.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepository extends JpaRepository<CheckoutEntity, Long> {
}
