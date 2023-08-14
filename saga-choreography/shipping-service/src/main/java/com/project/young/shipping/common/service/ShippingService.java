package com.project.young.shipping.common.service;

import com.project.young.shipping.common.dto.ScheduleRequest;
import com.project.young.shipping.common.dto.ShipmentDto;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ShippingService {

    Mono<Void> addShipment(ScheduleRequest request);

    Mono<Void> cancel(UUID orderId);

    Mono<ShipmentDto> schedule(UUID orderId);
}
