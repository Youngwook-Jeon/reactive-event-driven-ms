package com.project.young.payment.common.dto;

import com.project.young.common.messages.payment.PaymentStatus;
import lombok.Builder;

import java.util.UUID;

@Builder
public record PaymentDto(
        UUID paymentId,
        UUID orderId,
        Integer customerId,
        Integer amount,
        PaymentStatus status
) {
}
