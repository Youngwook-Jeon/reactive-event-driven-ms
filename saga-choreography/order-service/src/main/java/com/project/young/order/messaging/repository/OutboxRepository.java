package com.project.young.order.messaging.repository;

import com.project.young.order.messaging.entity.OrderOutbox;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OutboxRepository extends ReactiveCrudRepository<OrderOutbox, Long> {

    Flux<OrderOutbox> findAllByOrderById();
}
