package com.project.young.order.application.entity;

import com.project.young.common.events.inventory.InventoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class OrderInventory {

    @Id
    private Integer id;
    private UUID orderId;
    private UUID inventoryId;
    private InventoryStatus status;
    private String message;
    private Boolean success;

}
