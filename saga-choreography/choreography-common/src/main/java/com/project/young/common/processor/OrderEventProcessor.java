package com.project.young.common.processor;

import com.project.young.common.events.DomainEvent;
import com.project.young.common.events.order.OrderEvent;
import reactor.core.publisher.Mono;

public interface OrderEventProcessor<R extends DomainEvent> extends EventProcessor<OrderEvent, R> {

    @Override
    default Mono<R> process(OrderEvent event) {
        return switch (event) {
            case OrderEvent.OrderCreated e -> this.handle(e);
            case OrderEvent.OrderCancelled e -> this.handle(e);
            case OrderEvent.OrderCompleted e -> this.handle(e);
        };
    }

    Mono<R> handle(OrderEvent.OrderCreated e);

    Mono<R> handle(OrderEvent.OrderCancelled e);

    Mono<R> handle(OrderEvent.OrderCompleted e);
}
