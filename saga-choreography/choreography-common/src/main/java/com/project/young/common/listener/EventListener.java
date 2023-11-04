package com.project.young.common.listener;

import com.project.young.common.events.DomainEvent;
import reactor.core.publisher.Mono;

public interface EventListener<T extends DomainEvent> {
    Mono<Void> listen(T event);
}
