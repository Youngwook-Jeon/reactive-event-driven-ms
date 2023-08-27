package com.project.young.order.common.service;

import com.project.young.order.common.dto.OrderWorkflowActionDto;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface WorkflowActionRetriever {

    Flux<OrderWorkflowActionDto> retrieve(UUID orderId);
}
