package com.project.young.order.application.service;

import com.project.young.order.application.mapper.EntityDtoMapper;
import com.project.young.order.application.repository.PurchaseOrderRepository;
import com.project.young.order.common.dto.OrderCreateRequest;
import com.project.young.order.common.dto.OrderDetails;
import com.project.young.order.common.dto.PurchaseOrderDto;
import com.project.young.order.common.service.OrderEventListener;
import com.project.young.order.common.service.OrderService;
import com.project.young.order.common.service.inventory.InventoryComponentFetcher;
import com.project.young.order.common.service.payment.PaymentComponentFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final PurchaseOrderRepository repository;
    private final OrderEventListener eventListener;
    private final PaymentComponentFetcher paymentComponentFetcher;
    private final InventoryComponentFetcher inventoryComponentFetcher;

    @Override
    @Transactional
    public Mono<PurchaseOrderDto> placeOrder(OrderCreateRequest request) {
        var entity = EntityDtoMapper.toPurchaseOrder(request);
        return this.repository.save(entity).map(EntityDtoMapper::toPurchaseOrderDto)
                .flatMap(dto -> this.eventListener.onOrderCreated(dto).thenReturn(dto));
    }

    @Override
    public Flux<PurchaseOrderDto> getAllOrders() {
        return this.repository.findAll().map(EntityDtoMapper::toPurchaseOrderDto);
    }

    @Override
    public Mono<OrderDetails> getOrderDetails(UUID orderId) {
        return this.repository.findById(orderId)
                .map(EntityDtoMapper::toPurchaseOrderDto)
                .flatMap(dto -> this.paymentComponentFetcher.getComponent(orderId)
                        .zipWith(this.inventoryComponentFetcher.getComponent(orderId))
                        .map(t -> EntityDtoMapper.toOrderDetails(dto, t.getT1(), t.getT2()))
                );
    }
}

