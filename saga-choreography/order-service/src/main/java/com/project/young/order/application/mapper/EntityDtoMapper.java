package com.project.young.order.application.mapper;

import com.project.young.common.events.order.OrderStatus;
import com.project.young.order.application.entity.OrderInventory;
import com.project.young.order.application.entity.OrderPayment;
import com.project.young.order.application.entity.PurchaseOrder;
import com.project.young.order.common.dto.OrderCreateRequest;
import com.project.young.order.common.dto.OrderInventoryDto;
import com.project.young.order.common.dto.OrderPaymentDto;
import com.project.young.order.common.dto.PurchaseOrderDto;

public class EntityDtoMapper {

    public static PurchaseOrder toPurchaseOrder(OrderCreateRequest request) {
        return PurchaseOrder.builder()
                .status(OrderStatus.PENDING)
                .customerId(request.customerId())
                .productId(request.productId())
                .quantity(request.quantity())
                .unitPrice(request.unitPrice())
                .amount(request.quantity() * request.unitPrice())
                .build();
    }

    public static PurchaseOrderDto toPurchaseOrderDto(PurchaseOrder purchaseOrder) {
        return PurchaseOrderDto.builder()
                .orderId(purchaseOrder.getOrderId())
                .unitPrice(purchaseOrder.getUnitPrice())
                .quantity(purchaseOrder.getQuantity())
                .productId(purchaseOrder.getProductId())
                .amount(purchaseOrder.getAmount())
                .customerId(purchaseOrder.getCustomerId())
                .status(purchaseOrder.getStatus())
                .deliveryDate(purchaseOrder.getDeliveryDate())
                .build();
    }

    public static OrderPayment toOrderPayment(OrderPaymentDto dto) {
        return OrderPayment.builder()
                .status(dto.status())
                .paymentId(dto.paymentId())
                .orderId(dto.orderId())
                .message(dto.message())
                .build();
    }

    public static OrderPaymentDto toOrderPaymentDto(OrderPayment entity) {
        return OrderPaymentDto.builder()
                .status(entity.getStatus())
                .paymentId(entity.getPaymentId())
                .orderId(entity.getOrderId())
                .message(entity.getMessage())
                .build();
    }

    public static OrderInventory toOrderInventory(OrderInventoryDto dto) {
        return OrderInventory.builder()
                .inventoryId(dto.inventoryId())
                .orderId(dto.orderId())
                .status(dto.status())
                .message(dto.message())
                .build();
    }

    public static OrderInventoryDto toOrderInventoryDto(OrderInventory entity) {
        return OrderInventoryDto.builder()
                .inventoryId(entity.getInventoryId())
                .orderId(entity.getOrderId())
                .status(entity.getStatus())
                .message(entity.getMessage())
                .build();
    }

//    public static OrderDetails toOrderDetails(PurchaseOrderDto orderDto,
//                                              OrderPaymentDto paymentDto,
//                                              OrderInventoryDto inventoryDto) {
//        return OrderDetails.builder()
//                .order(orderDto)
//                .payment(paymentDto)
//                .inventory(inventoryDto)
//                .build();
//    }

}
