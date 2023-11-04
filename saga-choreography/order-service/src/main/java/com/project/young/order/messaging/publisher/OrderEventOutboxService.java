package com.project.young.order.messaging.publisher;

import com.project.young.common.events.order.OrderEvent;
import com.project.young.common.publisher.EventPublisher;
import reactor.core.publisher.Mono;

import java.util.List;

public interface OrderEventOutboxService extends EventPublisher<OrderEvent> {

    Mono<Void> deleteEvents(List<Long> ids);
}
