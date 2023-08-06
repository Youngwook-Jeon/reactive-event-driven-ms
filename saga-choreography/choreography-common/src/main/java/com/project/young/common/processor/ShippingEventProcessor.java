package com.project.young.common.processor;

import com.project.young.common.events.DomainEvent;
import com.project.young.common.events.shipping.ShippingEvent;
import reactor.core.publisher.Mono;

public interface ShippingEventProcessor<R extends DomainEvent> extends EventProcessor<ShippingEvent, R> {

    @Override
    default Mono<R> process(ShippingEvent event) {
        return switch (event) {
            case ShippingEvent.ShippingScheduled e -> this.handle(e);
        };
    }

    Mono<R> handle(ShippingEvent.ShippingScheduled event);
}
