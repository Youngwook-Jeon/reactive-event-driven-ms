package com.project.young.shipping.messaging.processor;

import com.project.young.common.messages.shipping.ShippingRequest;
import com.project.young.common.messages.shipping.ShippingResponse;
import com.project.young.common.processor.RequestProcessor;
import reactor.core.publisher.Mono;

public interface ShippingRequestProcessor extends RequestProcessor<ShippingRequest, ShippingResponse> {

    @Override
    default Mono<ShippingResponse> process(ShippingRequest request) {
        return switch (request){
            case ShippingRequest.Schedule s -> this.handle(s);
        };
    }

    Mono<ShippingResponse> handle(ShippingRequest.Schedule request);

}
