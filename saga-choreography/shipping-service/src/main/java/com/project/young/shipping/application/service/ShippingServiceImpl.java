package com.project.young.shipping.application.service;

import com.project.young.common.events.shipping.ShippingStatus;
import com.project.young.common.util.DuplicateEventValidator;
import com.project.young.shipping.application.entity.Shipment;
import com.project.young.shipping.application.mapper.EntityDtoMapper;
import com.project.young.shipping.application.repository.ShipmentRepository;
import com.project.young.shipping.common.dto.ScheduleRequest;
import com.project.young.shipping.common.dto.ShipmentDto;
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

    private final ShipmentRepository repository;

    @Override
    @Transactional
    public Mono<Void> addShipment(ScheduleRequest request) {
        return DuplicateEventValidator.validate(
                this.repository.existsByOrderId(request.orderId()),
                Mono.defer(() -> this.add(request))
        );
    }

    private Mono<Void> add(ScheduleRequest request) {
        var shipment = EntityDtoMapper.toShipment(request);
        shipment.setStatus(ShippingStatus.PENDING);
        return this.repository.save(shipment).then();
    }

    @Override
    @Transactional
    public Mono<Void> cancel(UUID orderId) {
        return this.repository.deleteByOrderId(orderId);
    }

    @Override
    @Transactional
    public Mono<ShipmentDto> schedule(UUID orderId) {
        return this.repository.findByOrderIdAndStatus(orderId, ShippingStatus.PENDING)
                .flatMap(this::schedule);
    }

    private Mono<ShipmentDto> schedule(Shipment shipment) {
        shipment.setDeliveryDate(Instant.now().plus(Duration.ofDays(3)));
        shipment.setStatus(ShippingStatus.SCHEDULED);
        return this.repository.save(shipment)
                .map(EntityDtoMapper::toDto);
    }
}
