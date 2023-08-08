package com.project.young.payment.common.service;

import com.project.young.payment.common.dto.PaymentDto;
import com.project.young.payment.common.dto.PaymentProcessRequest;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PaymentService {

    Mono<PaymentDto> process(PaymentProcessRequest request);

    Mono<PaymentDto> refund(UUID orderId);
}
