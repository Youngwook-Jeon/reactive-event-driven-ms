package com.project.young.common.messages.shipping;

import com.project.young.common.messages.Response;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

public sealed interface ShippingResponse extends Response {

    @Builder
    record Scheduled(
            UUID orderId,
            UUID shipmentId,
            Instant deliveryDate
    ) implements ShippingResponse {}

    @Builder
    record Declined(
            UUID orderId,
            String message
    ) implements ShippingResponse {}
}
