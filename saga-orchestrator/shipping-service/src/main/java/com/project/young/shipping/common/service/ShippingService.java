package com.project.young.shipping.common.service;

import com.project.young.shipping.common.dto.ScheduleRequest;
import com.project.young.shipping.common.dto.ShipmentDto;
import reactor.core.publisher.Mono;

public interface ShippingService {

    Mono<ShipmentDto> schedule(ScheduleRequest request);
}
