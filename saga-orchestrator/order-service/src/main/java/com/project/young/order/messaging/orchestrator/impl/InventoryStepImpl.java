package com.project.young.order.messaging.orchestrator.impl;

import com.project.young.common.messages.Request;
import com.project.young.common.messages.inventory.InventoryResponse;
import com.project.young.common.orchestrator.RequestCompensator;
import com.project.young.common.orchestrator.RequestSender;
import com.project.young.order.common.enums.WorkflowAction;
import com.project.young.order.common.service.OrderFulfillmentService;
import com.project.young.order.common.service.WorkflowActionTracker;
import com.project.young.order.messaging.mapper.MessageDtoMapper;
import com.project.young.order.messaging.orchestrator.InventoryStep;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryStepImpl implements InventoryStep {

    private final WorkflowActionTracker tracker;
    private final OrderFulfillmentService service;
    private RequestCompensator previousStep;
    private RequestSender nextStep;

    @Override
    public Publisher<Request> compensate(UUID orderId) {
        return this.tracker.track(orderId, WorkflowAction.INVENTORY_RESTORE_INITIATED)
                .thenReturn(MessageDtoMapper.toInventoryRestoreRequest(orderId))
                .cast(Request.class)
                .concatWith(this.previousStep.compensate(orderId));
    }

    @Override
    public Publisher<Request> send(UUID orderId) {
        return this.tracker.track(orderId, WorkflowAction.INVENTORY_REQUEST_INITIATED)
                .then(this.service.get(orderId))
                .map(MessageDtoMapper::toInventoryDeductRequest);
    }

    @Override
    public void setPreviousStep(RequestCompensator previousStep) {
        this.previousStep = previousStep;
    }

    @Override
    public void setNextStep(RequestSender nextStep) {
        this.nextStep = nextStep;
    }

    @Override
    public Publisher<Request> onSuccess(InventoryResponse.Deducted response) {
        return this.tracker.track(response.orderId(), WorkflowAction.INVENTORY_DEDUCTED)
                .thenMany(this.nextStep.send(response.orderId()));
    }

    @Override
    public Publisher<Request> onFailure(InventoryResponse.Declined response) {
        return this.tracker.track(response.orderId(), WorkflowAction.INVENTORY_DECLINED)
                .thenMany(this.previousStep.compensate(response.orderId()));
    }
}
