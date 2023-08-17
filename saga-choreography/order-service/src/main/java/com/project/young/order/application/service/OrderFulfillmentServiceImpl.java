package com.project.young.order.application.service;

import com.project.young.common.events.order.OrderStatus;
import com.project.young.order.application.mapper.EntityDtoMapper;
import com.project.young.order.application.repository.PurchaseOrderRepository;
import com.project.young.order.common.dto.PurchaseOrderDto;
import com.project.young.order.common.service.OrderFulfillmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderFulfillmentServiceImpl implements OrderFulfillmentService {

    private final PurchaseOrderRepository repository;

    @Override
    public Mono<PurchaseOrderDto> complete(UUID orderId) {
        return null;
    }

    @Override
    public Mono<PurchaseOrderDto> cancel(UUID orderId) {
        return this.repository.findByOrderIdAndStatus(orderId, OrderStatus.PENDING)
                .doOnNext(e -> e.setStatus(OrderStatus.CANCELLED))
                .flatMap(this.repository::save)
                .map(EntityDtoMapper::toPurchaseOrderDto);
    }
}
