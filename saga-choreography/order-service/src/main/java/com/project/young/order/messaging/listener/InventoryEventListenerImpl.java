package com.project.young.order.messaging.listener;

import com.project.young.common.events.inventory.InventoryEvent;
import com.project.young.common.listener.InventoryEventListener;
import com.project.young.order.common.service.inventory.InventoryComponentStatusListener;
import com.project.young.order.messaging.mapper.InventoryEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class InventoryEventListenerImpl implements InventoryEventListener {

    private final InventoryComponentStatusListener statusListener;

    @Override
    public Mono<Void> handle(InventoryEvent.InventoryDeducted event) {
        var dto = InventoryEventMapper.toDto(event);
        return this.statusListener.onSuccess(dto);
    }

    @Override
    public Mono<Void> handle(InventoryEvent.InventoryDeclined event) {
        var dto = InventoryEventMapper.toDto(event);
        return this.statusListener.onFailure(dto);
    }

    @Override
    public Mono<Void> handle(InventoryEvent.InventoryRestored event) {
        var dto = InventoryEventMapper.toDto(event);
        return this.statusListener.onRollback(dto);
    }
}
