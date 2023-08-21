package com.project.young.payment.messaging.mapper;

import com.project.young.common.messages.payment.PaymentRequest;
import com.project.young.common.messages.payment.PaymentResponse;
import com.project.young.payment.common.dto.PaymentDto;
import com.project.young.payment.common.dto.PaymentProcessRequest;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class MessageDtoMapper {
    public static PaymentProcessRequest toProcessRequest(PaymentRequest.Process request) {
        return PaymentProcessRequest.builder()
                .orderId(request.orderId())
                .amount(request.amount())
                .customerId(request.customerId())
                .build();
    }

    public static PaymentResponse toProcessedResponse(PaymentDto dto) {
        return PaymentResponse.Processed.builder()
                .orderId(dto.orderId())
                .paymentId(dto.paymentId())
                .amount(dto.amount())
                .customerId(dto.customerId())
                .build();
    }

    public static Function<Throwable, Mono<PaymentResponse>> toPaymentDeclinedResponse(PaymentRequest.Process request) {
        return ex -> Mono.fromSupplier(() -> PaymentResponse.Declined.builder()
                .orderId(request.orderId())
                .message(ex.getMessage())
                .build());
    }
}
