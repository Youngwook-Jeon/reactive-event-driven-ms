package com.project.young.payment.application.repository;

import com.project.young.common.events.payment.PaymentStatus;
import com.project.young.payment.application.entity.CustomerPayment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface PaymentRepository extends ReactiveCrudRepository<CustomerPayment, UUID> {

    Mono<Boolean> existsByOrderId(UUID orderId);

    Mono<CustomerPayment> findByOrderIdAndStatus(UUID orderId, PaymentStatus status);
}
