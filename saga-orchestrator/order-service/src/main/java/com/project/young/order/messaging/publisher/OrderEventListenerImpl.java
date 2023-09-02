package com.project.young.order.messaging.publisher;

import com.project.young.common.publisher.EventPublisher;
import com.project.young.order.common.dto.PurchaseOrderDto;
import com.project.young.order.common.service.OrderEventListener;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.UUID;

@RequiredArgsConstructor
public class OrderEventListenerImpl implements OrderEventListener, EventPublisher<UUID> {

    private final Sinks.Many<UUID> sink;
    private final Flux<UUID> flux;

    @Override
    public Flux<UUID> publish() {
        return this.flux;
    }

    @Override
    public void emitOrderCreated(PurchaseOrderDto dto) {
        this.sink.emitNext(
                dto.orderId(),
                Sinks.EmitFailureHandler.busyLooping(Duration.ofSeconds(1))
        );
    }

}
