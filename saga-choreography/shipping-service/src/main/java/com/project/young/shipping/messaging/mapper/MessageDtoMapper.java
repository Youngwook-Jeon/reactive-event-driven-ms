package com.project.young.shipping.messaging.mapper;

import com.project.young.common.events.order.OrderEvent;
import com.project.young.common.events.shipping.ShippingEvent;
import com.project.young.shipping.common.dto.ScheduleRequest;
import com.project.young.shipping.common.dto.ShipmentDto;

import java.time.Instant;

public class MessageDtoMapper {

    public static ScheduleRequest toScheduleRequest(OrderEvent.OrderCreated event) {
        return ScheduleRequest.builder()
                .customerId(event.customerId())
                .productId(event.productId())
                .quantity(event.quantity())
                .orderId(event.orderId())
                .build();
    }

    public static ShippingEvent toShippingScheduledEvent(ShipmentDto dto) {
        return ShippingEvent.ShippingScheduled.builder()
                .shipmentId(dto.shipmentId())
                .orderId(dto.orderId())
                .createdAt(Instant.now())
                .expectedDelivery(dto.expectedDelivery())
                .build();
    }
}
