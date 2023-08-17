package com.project.young.order.messaging.processor;

import com.project.young.common.events.inventory.InventoryEvent;
import com.project.young.common.events.order.OrderEvent;
import com.project.young.common.processor.InventoryEventProcessor;
import com.project.young.order.common.service.OrderFulfillmentService;
import com.project.young.order.common.service.inventory.InventoryComponentStatusListener;
import com.project.young.order.messaging.mapper.InventoryEventMapper;
import com.project.young.order.messaging.mapper.OrderEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class InventoryEventProcessorImpl implements InventoryEventProcessor<OrderEvent> {

    private final OrderFulfillmentService fulfillmentService;
    private final InventoryComponentStatusListener statusListener;

    @Override
    public Mono<OrderEvent> handle(InventoryEvent.InventoryDeducted event) {
        var dto = InventoryEventMapper.toDto(event);
        return this.statusListener.onSuccess(dto)
                .then(this.fulfillmentService.complete(event.orderId()))
                .map(OrderEventMapper::toOrderCompletedEvent);
    }

    @Override
    public Mono<OrderEvent> handle(InventoryEvent.InventoryDeclined event) {
        var dto = InventoryEventMapper.toDto(event);
        return this.statusListener.onFailure(dto)
                .then(this.fulfillmentService.cancel(event.orderId()))
                .map(OrderEventMapper::toOrderCancelledEvent);
    }

    @Override
    public Mono<OrderEvent> handle(InventoryEvent.InventoryRestored event) {
        var dto = InventoryEventMapper.toDto(event);
        return this.statusListener.onRollback(dto)
                .then(Mono.empty());
    }
}
