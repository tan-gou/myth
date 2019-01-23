package com.github.myth.demo.springcloud.order.service.impl;


import com.github.myth.annotation.Myth;
import com.github.myth.common.exception.MythRuntimeException;
import com.github.myth.demo.springcloud.account.api.dto.AccountDTO;
import com.github.myth.demo.springcloud.account.api.entity.AccountDO;
import com.github.myth.demo.springcloud.inventory.api.dto.InventoryDTO;
import com.github.myth.demo.springcloud.inventory.api.entity.InventoryDO;
import com.github.myth.demo.springcloud.order.client.AccountClient;

import com.github.myth.demo.springcloud.order.entity.Order;
import com.github.myth.demo.springcloud.order.enums.OrderStatusEnum;
import com.github.myth.demo.springcloud.order.mapper.OrderMapper;
import com.github.myth.demo.springcloud.order.service.PaymentService;
import com.github.myth.demo.springcloud.order.client.InventoryClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final OrderMapper orderMapper;


    private final AccountClient accountClient;

    private final InventoryClient inventoryClient;

    @Autowired(required = false)
    public PaymentServiceImpl(OrderMapper orderMapper, AccountClient accountClient, InventoryClient inventoryClient) {
        this.orderMapper = orderMapper;
        this.accountClient = accountClient;
        this.inventoryClient = inventoryClient;
    }


    @Override
    @Myth(destination = "")
    public void makePayment(Order order) {


        //检查数据 这里只是demo 只是demo 只是demo

        final AccountDO accountDO =
                accountClient.findByUserId(order.getUserId());

        if(accountDO.getBalance().compareTo(order.getTotalAmount())<= 0){
            throw new MythRuntimeException("余额不足！");
        }

        final InventoryDO inventoryDO =
                inventoryClient.findByProductId(order.getProductId());

        if(inventoryDO.getTotalInventory() < order.getCount()){
            throw new MythRuntimeException("库存不足！");
        }

        order.setStatus(OrderStatusEnum.PAY_SUCCESS.getCode());
        orderMapper.update(order);
        //扣除用户余额
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAmount(order.getTotalAmount());
        accountDTO.setUserId(order.getUserId());

        accountClient.payment(accountDTO);

        //进入扣减库存操作
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setCount(order.getCount());
        inventoryDTO.setProductId(order.getProductId());
        inventoryClient.decrease(inventoryDTO);
    }

}
