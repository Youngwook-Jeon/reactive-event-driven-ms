package com.project.young.order.common.service;

import com.project.young.order.common.dto.OrderCreateRequest;
import com.project.young.order.common.dto.OrderDetails;
import com.project.young.order.common.dto.PurchaseOrderDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface OrderService {

    Mono<PurchaseOrderDto> placeOrder(OrderCreateRequest request);

    Flux<PurchaseOrderDto> getAllOrders();

    Mono<OrderDetails> getOrderDetails(UUID orderId);
}
