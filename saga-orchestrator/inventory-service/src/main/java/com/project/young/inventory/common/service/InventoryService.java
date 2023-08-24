package com.project.young.inventory.common.service;

import com.project.young.inventory.common.dto.InventoryDeductRequest;
import com.project.young.inventory.common.dto.OrderInventoryDto;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface InventoryService {

    Mono<OrderInventoryDto> deduct(InventoryDeductRequest request);

    Mono<OrderInventoryDto> restore(UUID orderId);

}