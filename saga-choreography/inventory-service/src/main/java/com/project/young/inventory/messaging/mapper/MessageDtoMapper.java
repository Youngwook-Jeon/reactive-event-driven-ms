package com.project.young.inventory.messaging.mapper;

import com.project.young.common.events.inventory.InventoryEvent;
import com.project.young.common.events.order.OrderEvent;
import com.project.young.inventory.common.dto.InventoryDeductRequest;
import com.project.young.inventory.common.dto.OrderInventoryDto;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.function.Function;

public class MessageDtoMapper {

    public static InventoryDeductRequest toInventoryDeductRequest(OrderEvent.OrderCreated event) {
        return InventoryDeductRequest.builder()
                .orderId(event.orderId())
                .productId(event.productId())
                .quantity(event.quantity())
                .build();
    }

    public static InventoryEvent toInventoryDeductedEvent(OrderInventoryDto orderInventoryDto) {
        return InventoryEvent.InventoryDeducted.builder()
                .orderId(orderInventoryDto.orderId())
                .inventoryId(orderInventoryDto.inventoryId())
                .productId(orderInventoryDto.productId())
                .quantity(orderInventoryDto.quantity())
                .createdAt(Instant.now())
                .build();
    }

    public static InventoryEvent toInventoryRestoredEvent(OrderInventoryDto orderInventoryDto) {
        return InventoryEvent.InventoryRestored.builder()
                .orderId(orderInventoryDto.orderId())
                .inventoryId(orderInventoryDto.inventoryId())
                .productId(orderInventoryDto.productId())
                .quantity(orderInventoryDto.quantity())
                .createdAt(Instant.now())
                .build();
    }

    public static Function<Throwable, Mono<InventoryEvent>> toInventoryDeclinedEvent(OrderEvent.OrderCreated event) {
        return ex -> Mono.fromSupplier(() -> InventoryEvent.InventoryDeclined.builder()
                .orderId(event.orderId())
                .productId(event.productId())
                .quantity(event.quantity())
                .createdAt(Instant.now())
                .message(ex.getMessage())
                .build()
        );
    }
}
