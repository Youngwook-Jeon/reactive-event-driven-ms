package com.project.young.order.common.dto;

import com.project.young.common.events.order.OrderStatus;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record PurchaseOrderDto(
        UUID orderId,
        Integer customerId,
        Integer productId,
        Integer unitPrice,
        Integer quantity,
        Integer amount,
        OrderStatus status,
        Instant deliveryDate
) {
}
