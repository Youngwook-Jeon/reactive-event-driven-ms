package com.project.young.order.messaging.listener;

import com.project.young.common.events.payment.PaymentEvent;
import com.project.young.common.listener.PaymentEventListener;
import com.project.young.order.common.service.payment.PaymentComponentStatusListener;
import com.project.young.order.messaging.mapper.PaymentEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PaymentEventListenerImpl implements PaymentEventListener {

    private final PaymentComponentStatusListener statusListener;

    @Override
    public Mono<Void> handle(PaymentEvent.PaymentDeducted event) {
        var dto = PaymentEventMapper.toDto(event);
        return this.statusListener.onSuccess(dto);
    }

    @Override
    public Mono<Void> handle(PaymentEvent.PaymentDeclined event) {
        var dto = PaymentEventMapper.toDto(event);
        return this.statusListener.onFailure(dto);
    }

    @Override
    public Mono<Void> handle(PaymentEvent.PaymentRefunded event) {
        var dto = PaymentEventMapper.toDto(event);
        return this.statusListener.onRollback(dto);
    }
}
