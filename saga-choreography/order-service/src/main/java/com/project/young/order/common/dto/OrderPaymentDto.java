package com.project.young.order.common.dto;

import com.project.young.common.events.payment.PaymentStatus;
import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderPaymentDto(
        UUID orderId,
        UUID paymentId,
        PaymentStatus status,
        String message
) {
}
