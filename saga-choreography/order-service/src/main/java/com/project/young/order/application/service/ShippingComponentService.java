package com.project.young.order.application.service;

import com.project.young.common.events.order.OrderStatus;
import com.project.young.order.application.repository.PurchaseOrderRepository;
import com.project.young.order.common.dto.OrderShipmentSchedule;
import com.project.young.order.common.service.shipping.ShippingComponentStatusListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ShippingComponentService implements ShippingComponentStatusListener {

    private final PurchaseOrderRepository repository;

    @Override
    public Mono<Void> onSuccess(OrderShipmentSchedule message) {
        return this.repository.findByOrderIdAndStatus(message.orderId(), OrderStatus.COMPLETED)
                .doOnNext(e -> e.setDeliveryDate(message.deliveryDate()))
                .flatMap(this.repository::save)
                .then();
    }

    @Override
    public Mono<Void> onFailure(OrderShipmentSchedule message) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> onRollback(OrderShipmentSchedule message) {
        return Mono.empty();
    }
}
