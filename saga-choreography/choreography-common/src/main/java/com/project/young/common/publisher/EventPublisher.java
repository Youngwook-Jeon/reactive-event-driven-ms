package com.project.young.common.publisher;

import com.project.young.common.events.DomainEvent;
import reactor.core.publisher.Flux;

public interface EventPublisher<T extends DomainEvent> {

    Flux<T> publish();
}
