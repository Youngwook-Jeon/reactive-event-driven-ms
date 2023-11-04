package com.project.young.order.messaging.config;

import com.project.young.common.events.DomainEvent;
import com.project.young.common.events.inventory.InventoryEvent;
import com.project.young.common.events.order.OrderEvent;
import com.project.young.common.events.payment.PaymentEvent;
import com.project.young.common.events.shipping.ShippingEvent;
import com.project.young.common.listener.EventListener;
import com.project.young.common.processor.EventProcessor;
import com.project.young.common.publisher.EventPublisher;
import com.project.young.common.util.MessageConverter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
@RequiredArgsConstructor
public class EventListenerConfig {

    private static final Logger log = LoggerFactory.getLogger(EventListenerConfig.class);
    private final EventListener<InventoryEvent> inventoryEventListener;
    private final EventListener<PaymentEvent> paymentEventListener;
    private final EventListener<ShippingEvent> shippingEventListener;

    @Bean
    public Function<Flux<Message<InventoryEvent>>, Mono<Void>> inventoryListener(){
        return this.createConsumer(inventoryEventListener);
    }

    @Bean
    public Function<Flux<Message<PaymentEvent>>, Mono<Void>> paymentListener(){
        return this.createConsumer(paymentEventListener);
    }

    @Bean
    public Function<Flux<Message<ShippingEvent>>, Mono<Void>> shippingListener(){
        return this.createConsumer(shippingEventListener);
    }

    private <T extends DomainEvent> Function<Flux<Message<T>>, Mono<Void>> createConsumer(
            EventListener<T> eventListener
    ) {
        return flux -> flux.map(MessageConverter::toRecord)
                .doOnNext(r -> log.info("order service received {}", r.message()))
                .concatMap(r -> eventListener.listen(r.message())
                        .doOnSuccess(e -> r.acknowledgement().acknowledge())
                )
                .then();
    }

}
