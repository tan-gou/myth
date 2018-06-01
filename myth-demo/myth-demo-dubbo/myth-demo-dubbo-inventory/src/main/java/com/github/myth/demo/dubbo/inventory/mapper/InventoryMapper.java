package com.github.myth.demo.dubbo.inventory.mapper;

import com.github.myth.demo.dubbo.inventory.api.entity.Inventory;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface InventoryMapper {

    /**
     * 库存扣减
     */
    @Update("update inventory set total_inventory =#{totalInventory}" +
            " where product_id =#{productId}  and  total_inventory >0  ")
    int decrease(Inventory inventory);

    /**
     * 根据商品id找到库存信息
     */
    @Select("select * from inventory where product_id =#{productId}")
    Inventory findByProductId(String productId);
}
