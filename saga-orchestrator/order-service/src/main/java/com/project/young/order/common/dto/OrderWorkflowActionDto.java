package com.project.young.order.common.dto;

import com.project.young.order.common.enums.WorkflowAction;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record OrderWorkflowActionDto(
        UUID id,
        UUID orderId,
        WorkflowAction action,
        Instant createdAt
) {
}
