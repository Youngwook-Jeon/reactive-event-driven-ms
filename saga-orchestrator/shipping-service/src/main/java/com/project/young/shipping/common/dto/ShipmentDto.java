package com.project.young.shipping.common.dto;

import com.project.young.common.messages.shipping.ShippingStatus;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record ShipmentDto(
        UUID shipmentId,
        UUID orderId,
        Integer productId,
        Integer customerId,
        Integer quantity,
        Instant deliveryDate,
        ShippingStatus status
) {
}
