package com.project.young.order.messaging.orchestrator;

import com.project.young.common.messages.Request;
import com.project.young.common.messages.Response;
import com.project.young.common.messages.inventory.InventoryResponse;
import com.project.young.common.messages.payment.PaymentResponse;
import com.project.young.common.messages.shipping.ShippingResponse;
import com.project.young.common.orchestrator.WorkflowOrchestrator;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

public interface OrderFulfillmentOrchestrator extends WorkflowOrchestrator {

    Publisher<Request> orderInitialRequests();

    @Override
    default Publisher<Request> orchestrate(Response response) {
        return switch (response) {
            case PaymentResponse r -> this.handle(r);
            case InventoryResponse r -> this.handle(r);
            case ShippingResponse r -> this.handle(r);
            default -> Mono.empty();
        };
    }

    Publisher<Request> handle(PaymentResponse r);

    Publisher<Request> handle(InventoryResponse r);

    Publisher<Request> handle(ShippingResponse r);
}
