package com.project.young.cloudstreamkafkaplayground.sec06.processor;

import com.project.young.cloudstreamkafkaplayground.common.MessageConverter;
import com.project.young.cloudstreamkafkaplayground.common.Record;
import com.project.young.cloudstreamkafkaplayground.sec06.dto.DigitalDelivery;
import com.project.young.cloudstreamkafkaplayground.sec06.dto.OrderEvent;
import com.project.young.cloudstreamkafkaplayground.sec06.dto.PhysicalDelivery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Configuration
public class OrderRouter {

    private static final Logger log = LoggerFactory.getLogger(OrderRouter.class);

    // Routing our messages via the header
    private static final String DESTINATION_HEADER = "spring.cloud.stream.sendto.destination";
    private static final String DIGITAL_DELIVERY_CHANNEL = "digital-topic";
    private static final String PHYSICAL_DELIVERY_CHANNEL = "physical-topic";

    @Bean
    public Function<Flux<Message<OrderEvent>>, Flux<Message<?>>> processor() {
        return flux -> flux
                .map(MessageConverter::toRecord)
                .map(this::route);
    }

    private Message<?> route(Record<OrderEvent> record) {
        return switch (record.message().orderType()) {
            case DIGITAL -> this.toDigitalDelivery(record.message());
            case PHYSICAL -> this.toPhysicalDelivery(record.message());
        };
    }

    private Message<DigitalDelivery> toDigitalDelivery(OrderEvent event) {
        var dd = new DigitalDelivery(event.productId(), "%s@gmail.com".formatted(event.customerId()));
        return MessageBuilder
                .withPayload(dd)
                .setHeader(DESTINATION_HEADER, DIGITAL_DELIVERY_CHANNEL)
                .build();
    }

    private Message<PhysicalDelivery> toPhysicalDelivery(OrderEvent event) {
        var pd = new PhysicalDelivery(
                event.productId(),
                "%s street".formatted(event.customerId()),
                "%s city".formatted(event.customerId()),
                "some country"
        );
        return MessageBuilder
                .withPayload(pd)
                .setHeader(DESTINATION_HEADER, PHYSICAL_DELIVERY_CHANNEL)
                .build();
    }
}
