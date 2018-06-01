package com.github.myth.demo.dubbo.inventory.api.entity;

import lombok.Data;

import java.io.Serializable;


@Data
public class Inventory implements Serializable {

    private static final long serialVersionUID = 6957734749389133832L;

    private Integer id;

    private Integer productId;

    /**
     * 总库存
     */
    private Integer totalInventory;
}
