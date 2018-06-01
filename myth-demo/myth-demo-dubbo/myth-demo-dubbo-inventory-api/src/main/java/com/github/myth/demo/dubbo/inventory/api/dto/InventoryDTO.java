package com.github.myth.demo.dubbo.inventory.api.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class InventoryDTO implements Serializable {

    private static final long serialVersionUID = 8229355519336565493L;

    private String productId;

    /**
     * 数量
     */
    private Integer count;
}
