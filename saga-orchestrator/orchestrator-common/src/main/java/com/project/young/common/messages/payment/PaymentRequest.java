package com.project.young.common.messages.payment;

import com.project.young.common.messages.Request;
import lombok.Builder;

import java.util.UUID;

public sealed interface PaymentRequest extends Request {

    @Builder
    record Process(
            UUID orderId,
            Integer customerId,
            Integer amount
    ) implements PaymentRequest {}

    @Builder
    record Refund(
            UUID orderId
    ) implements PaymentRequest {}
}
