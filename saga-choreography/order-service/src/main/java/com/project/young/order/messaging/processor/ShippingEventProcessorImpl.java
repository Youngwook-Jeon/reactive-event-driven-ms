package com.project.young.order.messaging.processor;

import com.project.young.common.events.order.OrderEvent;
import com.project.young.common.events.shipping.ShippingEvent;
import com.project.young.common.processor.ShippingEventProcessor;
import com.project.young.order.common.service.shipping.ShippingComponentStatusListener;
import com.project.young.order.messaging.mapper.ShippingEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ShippingEventProcessorImpl implements ShippingEventProcessor<OrderEvent> {

    private final ShippingComponentStatusListener statusListener;

    @Override
    public Mono<OrderEvent> handle(ShippingEvent.ShippingScheduled event) {
        var dto = ShippingEventMapper.toDto(event);
        return this.statusListener.onSuccess(dto)
                .then(Mono.empty());
    }

}
