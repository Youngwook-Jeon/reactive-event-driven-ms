package com.project.young.inventory.common.dto;

import com.project.young.common.events.inventory.InventoryStatus;
import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderInventoryDto(
        UUID inventoryId,
        UUID orderId,
        Integer productId,
        Integer quantity,
        InventoryStatus status
) {
}
