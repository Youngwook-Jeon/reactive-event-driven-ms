package com.project.young.inventory.application.entity;

import com.project.young.common.messages.inventory.InventoryStatus;
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
    private UUID inventoryId;

    private UUID orderId;

    private Integer productId;

    private Integer quantity;

    private InventoryStatus status;

}
