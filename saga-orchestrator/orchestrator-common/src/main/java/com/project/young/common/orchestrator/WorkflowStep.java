package com.project.young.common.orchestrator;

import com.project.young.common.messages.Response;

public interface WorkflowStep<T extends Response> extends
        RequestSender, RequestCompensator, WorkflowChain, ResponseProcessor<T> {
}
