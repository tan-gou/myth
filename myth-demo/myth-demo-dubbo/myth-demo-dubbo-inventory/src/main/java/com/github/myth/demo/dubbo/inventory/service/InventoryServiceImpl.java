package com.github.myth.demo.dubbo.inventory.service;

import com.github.myth.annotation.Myth;
import com.github.myth.common.exception.MythRuntimeException;
import com.github.myth.demo.dubbo.inventory.api.dto.InventoryDTO;
import com.github.myth.demo.dubbo.inventory.api.entity.Inventory;
import com.github.myth.demo.dubbo.inventory.api.service.InventoryService;
import com.github.myth.demo.dubbo.inventory.mapper.InventoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("inventoryService")
public class InventoryServiceImpl implements InventoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryServiceImpl.class);

    private final InventoryMapper inventoryMapper;

    @Autowired(required = false)
    public InventoryServiceImpl(InventoryMapper inventoryMapper) {
        this.inventoryMapper = inventoryMapper;
    }


    /**
     * 扣减库存操作
     */
    @Override
    @Myth(destination = "ORDER_FLOW_BQ",tags = "inventory")
    @Transactional(rollbackFor = Exception.class)
    public Boolean decrease(InventoryDTO inventoryDTO) {
        final Inventory entity = inventoryMapper.findByProductId(inventoryDTO.getProductId());
        if (entity.getTotalInventory() < inventoryDTO.getCount()) {
            throw new MythRuntimeException("库存不足");
        }
        entity.setTotalInventory(entity.getTotalInventory() - inventoryDTO.getCount());
        final int decrease = inventoryMapper.decrease(entity);
        if (decrease != 1) {
            throw new MythRuntimeException("库存不足");
        }
        return true;
    }


    /**
     * 获取商品库存信息
     */
    @Override
    public Inventory findByProductId(String productId) {
        return inventoryMapper.findByProductId(productId);
    }
}
