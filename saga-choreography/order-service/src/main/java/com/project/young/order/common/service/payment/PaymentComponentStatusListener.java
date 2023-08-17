package com.project.young.order.common.service.payment;

import com.project.young.order.common.dto.OrderPaymentDto;
import com.project.young.order.common.service.OrderComponentStatusListener;

public interface PaymentComponentStatusListener extends OrderComponentStatusListener<OrderPaymentDto> {
}
