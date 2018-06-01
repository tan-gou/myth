package com.github.myth.demo.dubbo.inventory.api.service;

import com.github.myth.annotation.Myth;
import com.github.myth.demo.dubbo.inventory.api.dto.InventoryDTO;
import com.github.myth.demo.dubbo.inventory.api.entity.Inventory;

public interface InventoryService {

    /**
     * 扣减库存操作
     */
    @Myth(destination = "ORDER_FLOW_BQ",tags = "inventory")
    Boolean decrease(InventoryDTO inventoryDTO);

    /**
     * 获取商品库存信息
     */
    Inventory findByProductId(String productId);
}
