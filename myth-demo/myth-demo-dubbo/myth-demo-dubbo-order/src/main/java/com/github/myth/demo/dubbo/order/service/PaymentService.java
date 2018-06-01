package com.github.myth.demo.dubbo.order.service;

import com.github.myth.demo.dubbo.order.entity.Order;

public interface PaymentService {

    /**
     * 订单支付
     */
    void makePayment(Order order);
}
