package com.project.young.order.messaging.publisher;

import com.project.young.common.events.order.OrderEvent;
import com.project.young.common.publisher.EventPublisher;
import com.project.young.order.common.dto.PurchaseOrderDto;
import com.project.young.order.common.service.OrderEventListener;
import com.project.young.order.messaging.mapper.OrderEventMapper;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@RequiredArgsConstructor
public class OrderEventListenerImpl implements OrderEventListener, EventPublisher<OrderEvent> {

    private final Sinks.Many<OrderEvent> sink;
    private final Flux<OrderEvent> flux;

    @Override
    public Flux<OrderEvent> publish() {
        return this.flux;
    }

    @Override
    public void emitOrderCreated(PurchaseOrderDto dto) {
        var event = OrderEventMapper.toOrderCreatedEvent(dto);
        this.sink.emitNext(
                event,
                Sinks.EmitFailureHandler.busyLooping(Duration.ofSeconds(1))
        );
    }

}
