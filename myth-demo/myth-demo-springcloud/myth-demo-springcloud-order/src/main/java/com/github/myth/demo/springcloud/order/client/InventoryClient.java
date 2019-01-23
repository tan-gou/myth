package com.github.myth.demo.springcloud.order.client;

import com.github.myth.annotation.Myth;
import com.github.myth.demo.springcloud.inventory.api.dto.InventoryDTO;
import com.github.myth.demo.springcloud.inventory.api.entity.InventoryDO;
import com.github.myth.demo.springcloud.inventory.api.service.InventoryService;
import com.github.myth.demo.springcloud.order.configuration.MyConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "inventory-service", configuration = MyConfiguration.class)
public interface InventoryClient {

    /**
     * 库存扣减
     *
     * @param inventoryDTO 实体对象
     * @return true 成功
     */
    @Myth(destination = "inventory",target = InventoryService.class)
    @RequestMapping("/inventory-service/inventory/decrease")
    Boolean decrease(@RequestBody InventoryDTO inventoryDTO);


    /**
     * 获取商品库存
     *
     * @param productId 商品id
     * @return InventoryDO
     */
    @RequestMapping("/inventory-service/inventory/findByProductId")
    InventoryDO findByProductId(@RequestParam("productId") String productId);

}
