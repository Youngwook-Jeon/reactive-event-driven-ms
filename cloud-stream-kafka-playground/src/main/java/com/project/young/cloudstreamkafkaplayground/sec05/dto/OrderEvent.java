package com.project.young.cloudstreamkafkaplayground.sec05.dto;

public record OrderEvent(
        int customerId,
        int productId,
        OrderType orderType
) {
}
