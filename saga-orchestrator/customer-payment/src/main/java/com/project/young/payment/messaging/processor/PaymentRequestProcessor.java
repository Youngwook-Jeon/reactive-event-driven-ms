package com.project.young.payment.messaging.processor;

import com.project.young.common.messages.payment.PaymentRequest;
import com.project.young.common.messages.payment.PaymentResponse;
import com.project.young.common.processor.RequestProcessor;
import reactor.core.publisher.Mono;

public interface PaymentRequestProcessor extends RequestProcessor<PaymentRequest, PaymentResponse> {

    @Override
    default Mono<PaymentResponse> process(PaymentRequest request) {
        return switch (request) {
            case PaymentRequest.Process p -> this.handle(p);
            case PaymentRequest.Refund p -> this.handle(p);
        };
    }

    Mono<PaymentResponse> handle(PaymentRequest.Process request);

    Mono<PaymentResponse> handle(PaymentRequest.Refund request);
}
