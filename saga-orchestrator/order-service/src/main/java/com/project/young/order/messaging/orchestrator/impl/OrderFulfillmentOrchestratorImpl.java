package com.project.young.order.messaging.orchestrator.impl;

import com.project.young.common.messages.Request;
import com.project.young.common.messages.inventory.InventoryResponse;
import com.project.young.common.messages.payment.PaymentResponse;
import com.project.young.common.messages.shipping.ShippingResponse;
import com.project.young.order.common.service.OrderFulfillmentService;
import com.project.young.order.messaging.orchestrator.InventoryStep;
import com.project.young.order.messaging.orchestrator.OrderFulfillmentOrchestrator;
import com.project.young.order.messaging.orchestrator.PaymentStep;
import com.project.young.order.messaging.orchestrator.ShippingStep;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderFulfillmentOrchestratorImpl implements OrderFulfillmentOrchestrator {

    private final PaymentStep paymentStep;
    private final InventoryStep inventoryStep;
    private final ShippingStep shippingStep;
    private final OrderFulfillmentService service;
    private Workflow workflow;

    @PostConstruct
    private void init() {
        this.workflow = Workflow.startWith(paymentStep)
                .thenNext(inventoryStep)
                .thenNext(shippingStep)
                .doOnFailure(id -> this.service.cancel(id).then())
                .doOnSuccess(id -> this.service.complete(id).then());

//        this.paymentStep.setPreviousStep(id -> this.service.cancel(id).then(Mono.empty()));
//        this.paymentStep.setNextStep(inventoryStep);
//        this.inventoryStep.setPreviousStep(paymentStep);
//        this.inventoryStep.setNextStep(shippingStep);
//        this.shippingStep.setPreviousStep(inventoryStep);
//        this.shippingStep.setNextStep(id -> this.service.complete(id).then(Mono.empty()));
    }

    @Override
    public Publisher<Request> orderInitialRequests() {
        return null;
    }

    @Override
    public Publisher<Request> handle(PaymentResponse response) {
        return this.paymentStep.process(response);
    }

    @Override
    public Publisher<Request> handle(InventoryResponse response) {
        return this.inventoryStep.process(response);
    }

    @Override
    public Publisher<Request> handle(ShippingResponse response) {
        return this.shippingStep.process(response);
    }
}
