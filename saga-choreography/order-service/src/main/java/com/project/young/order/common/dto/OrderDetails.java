package com.project.young.order.common.dto;

import lombok.Builder;

@Builder
public record OrderDetails(
        PurchaseOrderDto order,
        OrderPaymentDto payment,
        OrderInventoryDto inventory
) {
}
