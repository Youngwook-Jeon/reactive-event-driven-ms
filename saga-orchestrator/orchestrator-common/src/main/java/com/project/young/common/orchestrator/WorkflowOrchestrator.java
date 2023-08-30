package com.project.young.common.orchestrator;

import com.project.young.common.messages.Request;
import com.project.young.common.messages.Response;
import org.reactivestreams.Publisher;

public interface WorkflowOrchestrator {

    Publisher<Request> orchestrate(Response response);
}
