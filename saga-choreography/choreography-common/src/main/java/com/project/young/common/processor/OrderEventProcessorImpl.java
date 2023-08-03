package com.project.young.common.processor;

import com.project.young.common.events.order.OrderEvent;
import com.project.young.common.events.payment.PaymentEvent;
import reactor.core.publisher.Mono;

public class OrderEventProcessorImpl implements OrderEventProcessor<PaymentEvent> {
    @Override
    public Mono<PaymentEvent> handle(OrderEvent.OrderCreated e) {
        return null;
    }

    @Override
    public Mono<PaymentEvent> handle(OrderEvent.OrderCancelled e) {
        return null;
    }

    @Override
    public Mono<PaymentEvent> handle(OrderEvent.OrderCompleted e) {
        return null;
    }
}
