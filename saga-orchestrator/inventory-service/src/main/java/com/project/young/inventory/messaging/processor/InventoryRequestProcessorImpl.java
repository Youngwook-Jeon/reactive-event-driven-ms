package com.project.young.inventory.messaging.processor;

import com.project.young.common.exception.EventAlreadyProcessedException;
import com.project.young.common.messages.inventory.InventoryRequest;
import com.project.young.common.messages.inventory.InventoryResponse;
import com.project.young.inventory.common.service.InventoryService;
import com.project.young.inventory.messaging.mapper.MessageDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.UnaryOperator;

@Service
@RequiredArgsConstructor
public class InventoryRequestProcessorImpl implements InventoryRequestProcessor {

    private final InventoryService service;

    @Override
    public Mono<InventoryResponse> handle(InventoryRequest.Deduct request) {
        return this.service.deduct(MessageDtoMapper.toInventoryDeductRequest(request))
                .map(MessageDtoMapper::toInventoryDeductedResponse)
                .transform(exceptionHandler(request));
    }

    @Override
    public Mono<InventoryResponse> handle(InventoryRequest.Restore request) {
        return this.service.restore(request.orderId())
                .then(Mono.empty());
    }

    private UnaryOperator<Mono<InventoryResponse>> exceptionHandler(InventoryRequest.Deduct request) {
        return mono -> mono.onErrorResume(EventAlreadyProcessedException.class, e -> Mono.empty())
                .onErrorResume(MessageDtoMapper.toInventoryDeclinedResponse(request));
    }

}
