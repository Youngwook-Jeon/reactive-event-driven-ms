package com.project.young.order.common.service;

import com.project.young.order.common.dto.OrderShipmentSchedule;
import com.project.young.order.common.dto.PurchaseOrderDto;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface OrderFulfillmentService {

    Mono<PurchaseOrderDto> get(UUID orderId);

    Mono<PurchaseOrderDto> schedule(OrderShipmentSchedule shipmentSchedule);

    Mono<PurchaseOrderDto> complete(UUID orderId);

    Mono<PurchaseOrderDto> cancel(UUID orderId);
}
