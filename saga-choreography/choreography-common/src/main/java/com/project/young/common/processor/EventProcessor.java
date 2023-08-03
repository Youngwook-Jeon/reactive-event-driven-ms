package com.project.young.common.processor;

import com.project.young.common.events.DomainEvent;
import reactor.core.publisher.Mono;

public interface EventProcessor<T extends DomainEvent, R extends DomainEvent> {
    Mono<R> process(T event);
}
