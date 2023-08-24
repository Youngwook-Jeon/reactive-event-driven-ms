package com.project.young.shipping.messaging.processor;

import com.project.young.common.exception.EventAlreadyProcessedException;
import com.project.young.common.messages.shipping.ShippingRequest;
import com.project.young.common.messages.shipping.ShippingResponse;
import com.project.young.shipping.common.service.ShippingService;
import com.project.young.shipping.messaging.mapper.MessageDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.UnaryOperator;

@Service
@RequiredArgsConstructor
public class ShippingRequestProcessorImpl implements ShippingRequestProcessor {

    private final ShippingService service;

    @Override
    public Mono<ShippingResponse> handle(ShippingRequest.Schedule request) {
        var dto = MessageDtoMapper.toScheduleRequest(request);
        return this.service.schedule(dto)
                .map(MessageDtoMapper::toScheduledResponse)
                .transform(exceptionHandler(request));
    }

    private UnaryOperator<Mono<ShippingResponse>> exceptionHandler(ShippingRequest.Schedule request) {
        return mono -> mono.onErrorResume(EventAlreadyProcessedException.class, e -> Mono.empty())
                .onErrorResume(MessageDtoMapper.toShippingDeclinedResponse(request));
    }

}
