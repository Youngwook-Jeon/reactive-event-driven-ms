package com.project.young.order.messaging.config;

import com.project.young.common.events.inventory.InventoryEvent;
import com.project.young.common.events.order.OrderEvent;
import com.project.young.common.events.payment.PaymentEvent;
import com.project.young.common.events.shipping.ShippingEvent;
import com.project.young.common.processor.EventProcessor;
import com.project.young.common.publisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;

import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
@RequiredArgsConstructor
public class ProcessorConfig extends AbstractOrderEventRouterConfig {

    private final EventProcessor<InventoryEvent, OrderEvent> inventoryEventProcessor;
    private final EventProcessor<PaymentEvent, OrderEvent> paymentEventProcessor;
    private final EventProcessor<ShippingEvent, OrderEvent> shippingEventProcessor;
    private final EventPublisher<OrderEvent> eventPublisher;

    @Bean
    public Function<Flux<Message<InventoryEvent>>, Flux<Message<OrderEvent>>> inventoryProcessor(){
        return this.processor(inventoryEventProcessor);
    }

    @Bean
    public Function<Flux<Message<PaymentEvent>>, Flux<Message<OrderEvent>>> paymentProcessor(){
        return this.processor(paymentEventProcessor);
    }

    @Bean
    public Function<Flux<Message<ShippingEvent>>, Flux<Message<OrderEvent>>> shippingProcessor(){
        return this.processor(shippingEventProcessor);
    }

    @Bean
    public Supplier<Flux<Message<OrderEvent>>> orderEventProducer() {
        return () -> this.eventPublisher.publish()
                .map(this::toMessage);
    }

}