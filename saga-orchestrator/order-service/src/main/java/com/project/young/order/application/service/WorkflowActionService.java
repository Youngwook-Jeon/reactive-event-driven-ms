package com.project.young.order.application.service;

import com.project.young.common.util.DuplicateEventValidator;
import com.project.young.order.application.mapper.EntityDtoMapper;
import com.project.young.order.application.repository.OrderWorkflowActionRepository;
import com.project.young.order.common.dto.OrderWorkflowActionDto;
import com.project.young.order.common.enums.WorkflowAction;
import com.project.young.order.common.service.WorkflowActionRetriever;
import com.project.young.order.common.service.WorkflowActionTracker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkflowActionService implements WorkflowActionTracker, WorkflowActionRetriever {

    private final OrderWorkflowActionRepository repository;

    @Override
    public Flux<OrderWorkflowActionDto> retrieve(UUID orderId) {
        return this.repository.findByOrderIdOrderByCreatedAt(orderId)
                .map(EntityDtoMapper::toOrderWorkflowActionDto);
    }

    @Override
    public Mono<Void> track(UUID orderId, WorkflowAction action) {
        return DuplicateEventValidator.validate(
                this.repository.existsByOrderIdAndAction(orderId, action),
                this.repository.save(EntityDtoMapper.toOrderWorkflowAction(orderId, action)) // defer if required
        ).then();
    }
}
