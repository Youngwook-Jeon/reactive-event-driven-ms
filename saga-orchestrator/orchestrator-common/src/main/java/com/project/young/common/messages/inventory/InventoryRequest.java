package com.project.young.common.messages.inventory;

import com.project.young.common.messages.Request;
import lombok.Builder;

import java.util.UUID;

public sealed interface InventoryRequest extends Request {

    @Builder
    record Deduct(
            UUID orderId,
            Integer productId,
            Integer quantity
    ) implements InventoryRequest {}

    @Builder
    record Restore(
            UUID orderId
    ) implements InventoryRequest {}
}
