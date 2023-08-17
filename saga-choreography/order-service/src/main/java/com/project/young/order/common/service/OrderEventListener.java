package com.project.young.order.common.service;

import com.project.young.order.common.dto.PurchaseOrderDto;

public interface OrderEventListener {

    void emitOrderCreated(PurchaseOrderDto dto);
}
