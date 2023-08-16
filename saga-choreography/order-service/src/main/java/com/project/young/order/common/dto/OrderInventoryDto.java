package com.project.young.order.common.dto;

import com.project.young.common.events.inventory.InventoryStatus;
import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderInventoryDto(
        UUID orderId,
        UUID inventoryId,
        InventoryStatus status,
        String message
) {
}
