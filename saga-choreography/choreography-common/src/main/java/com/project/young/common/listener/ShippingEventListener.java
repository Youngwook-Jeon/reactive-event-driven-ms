package com.project.young.common.listener;

import com.project.young.common.events.shipping.ShippingEvent;
import reactor.core.publisher.Mono;

public interface ShippingEventListener extends EventListener<ShippingEvent> {

    @Override
    default Mono<Void> listen(ShippingEvent event) {
        return switch (event) {
            case ShippingEvent.ShippingScheduled e -> this.handle(e);
        };
    }

    Mono<Void> handle(ShippingEvent.ShippingScheduled event);
}
