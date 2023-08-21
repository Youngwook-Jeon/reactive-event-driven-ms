package com.project.young.common.processor;

import com.project.young.common.messages.Request;
import com.project.young.common.messages.Response;
import reactor.core.publisher.Mono;

public interface RequestProcessor<T extends Request, R extends Response> {

    Mono<R> process(T request);
}
