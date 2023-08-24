package com.project.young.common.orchestrator;

import com.project.young.common.messages.Request;
import com.project.young.common.messages.Response;
import org.reactivestreams.Publisher;

public interface ResponseProcessor<T extends Response> {

    Publisher<Request> process(T response);
}
