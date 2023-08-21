package com.project.young.common.messages.shipping;

import com.project.young.common.messages.Request;
import lombok.Builder;

import java.util.UUID;

public sealed interface ShippingRequest extends Request {

    @Builder
    record Schedule(
            UUID orderId,
            Integer customerId,
            Integer productId,
            Integer quantity
    ) implements ShippingRequest {}
}
