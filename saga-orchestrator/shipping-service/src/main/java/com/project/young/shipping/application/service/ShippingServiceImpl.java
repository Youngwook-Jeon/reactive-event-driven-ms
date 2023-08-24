package com.project.young.shipping.application.service;

import com.project.young.common.messages.shipping.ShippingStatus;
import com.project.young.common.util.DuplicateEventValidator;
import com.project.young.shipping.application.entity.Shipment;
import com.project.young.shipping.application.mapper.EntityDtoMapper;
import com.project.young.shipping.application.repository.ShipmentRepository;
import com.project.young.shipping.common.dto.ScheduleRequest;
import com.project.young.shipping.common.dto.ShipmentDto;
import com.project.young.shipping.common.exception.ShipmentQuantityLimitExceededException;
import com.project.young.shipping.common.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShippingServiceImpl implements ShippingService {

    private static final Mono<Shipment> LIMIT_EXCEEDED = Mono.error(new ShipmentQuantityLimitExceededException());
    private final ShipmentRepository repository;

    @Override
    public Mono<ShipmentDto> schedule(ScheduleRequest request) {
        return DuplicateEventValidator.validate(
                        this.repository.existsByOrderId(request.orderId()),
                        Mono.just(request)
                )
                .filter(r -> r.quantity() < 10)
                .map(EntityDtoMapper::toShipment)
                .switchIfEmpty(LIMIT_EXCEEDED)
                .flatMap(this::schedule);
    }

    private Mono<ShipmentDto> schedule(Shipment shipment) {
        shipment.setDeliveryDate(Instant.now().plus(Duration.ofDays(3)));
        shipment.setStatus(ShippingStatus.SCHEDULED);
        return this.repository.save(shipment)
                .map(EntityDtoMapper::toDto);
    }
}
