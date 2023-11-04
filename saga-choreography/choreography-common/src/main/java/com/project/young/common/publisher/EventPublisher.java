package com.project.young.common.publisher;

import com.project.young.common.events.DomainEvent;
import com.project.young.common.outbox.Outbox;
import reactor.core.publisher.Flux;

public interface EventPublisher<T extends DomainEvent> {

    Flux<Outbox<T>> publish();
}
