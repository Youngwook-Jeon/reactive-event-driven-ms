package com.project.young.common.listener;

import com.project.young.common.events.payment.PaymentEvent;
import reactor.core.publisher.Mono;

public interface PaymentEventListener extends EventListener<PaymentEvent> {

    @Override
    default Mono<Void> listen(PaymentEvent event) {
        return switch (event) {
            case PaymentEvent.PaymentDeducted e -> this.handle(e);
            case PaymentEvent.PaymentDeclined e -> this.handle(e);
            case PaymentEvent.PaymentRefunded e -> this.handle(e);
        };
    }

    Mono<Void> handle(PaymentEvent.PaymentDeducted event);

    Mono<Void> handle(PaymentEvent.PaymentDeclined event);

    Mono<Void> handle(PaymentEvent.PaymentRefunded event);
}
