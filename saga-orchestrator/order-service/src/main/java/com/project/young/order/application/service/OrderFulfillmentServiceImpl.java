package com.project.young.order.application.service;

import com.project.young.order.application.entity.PurchaseOrder;
import com.project.young.order.application.mapper.EntityDtoMapper;
import com.project.young.order.application.repository.PurchaseOrderRepository;
import com.project.young.order.common.dto.OrderShipmentSchedule;
import com.project.young.order.common.dto.PurchaseOrderDto;
import com.project.young.order.common.enums.OrderStatus;
import com.project.young.order.common.service.OrderFulfillmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class OrderFulfillmentServiceImpl implements OrderFulfillmentService {

    private final PurchaseOrderRepository repository;

    @Override
    public Mono<PurchaseOrderDto> get(UUID orderId) {
        return this.repository.findById(orderId)
                .map(EntityDtoMapper::toPurchaseOrderDto);
    }

    @Override
    public Mono<PurchaseOrderDto> schedule(OrderShipmentSchedule shipmentSchedule) {
        return this.update(shipmentSchedule.orderId(), e -> e.setDeliveryDate(shipmentSchedule.deliveryDate()));
    }

    @Override
    public Mono<PurchaseOrderDto> complete(UUID orderId) {
        return this.update(orderId, e -> e.setStatus(OrderStatus.COMPLETED));
    }

    @Override
    public Mono<PurchaseOrderDto> cancel(UUID orderId) {
        return this.update(orderId, e -> e.setStatus(OrderStatus.CANCELLED));
    }

    private Mono<PurchaseOrderDto> update(UUID orderId, Consumer<PurchaseOrder> consumer) {
        return this.repository.findByOrderIdAndStatus(orderId, OrderStatus.PENDING)
                .doOnNext(consumer)
                .flatMap(this.repository::save)
                .map(EntityDtoMapper::toPurchaseOrderDto);
    }

}
