package com.project.young.order.messaging.processor;

import com.project.young.common.events.order.OrderEvent;
import com.project.young.common.events.payment.PaymentEvent;
import com.project.young.common.processor.PaymentEventProcessor;
import com.project.young.order.common.service.OrderFulfillmentService;
import com.project.young.order.common.service.payment.PaymentComponentStatusListener;
import com.project.young.order.messaging.mapper.OrderEventMapper;
import com.project.young.order.messaging.mapper.PaymentEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PaymentEventProcessorImpl implements PaymentEventProcessor<OrderEvent> {

    private final OrderFulfillmentService fulfillmentService;
    private final PaymentComponentStatusListener statusListener;

    @Override
    public Mono<OrderEvent> handle(PaymentEvent.PaymentDeducted event) {
        var dto = PaymentEventMapper.toDto(event);
        return this.statusListener.onSuccess(dto)
                .then(this.fulfillmentService.complete(event.orderId()))
                .map(OrderEventMapper::toOrderCompletedEvent);
    }

    @Override
    public Mono<OrderEvent> handle(PaymentEvent.PaymentDeclined event) {
        var dto = PaymentEventMapper.toDto(event);
        return this.statusListener.onFailure(dto)
                .then(this.fulfillmentService.cancel(event.orderId()))
                .map(OrderEventMapper::toOrderCancelledEvent);
    }

    @Override
    public Mono<OrderEvent> handle(PaymentEvent.PaymentRefunded event) {
        var dto = PaymentEventMapper.toDto(event);
        return this.statusListener.onRollback(dto)
                .then(Mono.empty());
    }
}
