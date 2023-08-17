package com.project.young.order.messaging.mapper;

import com.project.young.common.events.shipping.ShippingEvent;
import com.project.young.order.common.dto.OrderShipmentSchedule;

public class ShippingEventMapper {

    public static OrderShipmentSchedule toDto(ShippingEvent.ShippingScheduled event) {
        return OrderShipmentSchedule.builder()
                .orderId(event.orderId())
                .deliveryDate(event.expectedDelivery())
                .build();
    }
}
