package com.project.young.common.messages.payment;

import com.project.young.common.messages.Response;
import lombok.Builder;

import java.util.UUID;

public sealed interface PaymentResponse extends Response {

    @Builder
    record Processed(
            UUID orderId,
            UUID paymentId,
            Integer customerId,
            Integer amount
    ) implements PaymentResponse {}

    @Builder
    record Declined(
            UUID orderId,
            String message
    ) implements PaymentResponse {}
}
