package com.project.young.order.messaging.listener;

import com.project.young.common.events.shipping.ShippingEvent;
import com.project.young.common.listener.ShippingEventListener;
import com.project.young.order.common.service.shipping.ShippingComponentStatusListener;
import com.project.young.order.messaging.mapper.ShippingEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ShippingEventListenerImpl implements ShippingEventListener {

    private final ShippingComponentStatusListener statusListener;

    @Override
    public Mono<Void> handle(ShippingEvent.ShippingScheduled event) {
        var dto = ShippingEventMapper.toDto(event);
        return this.statusListener.onSuccess(dto);
    }

}
