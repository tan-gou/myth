package com.github.myth.demo.dubbo.account.api.service;

import com.github.myth.annotation.Myth;
import com.github.myth.demo.dubbo.account.api.dto.AccountDTO;
import com.github.myth.demo.dubbo.account.api.entity.AccountDO;

public interface AccountService {

    /**
     * 扣款支付
     */
    @Myth(destination = "ORDER_FLOW_BQ",tags = "account")
    boolean payment(AccountDTO accountDTO);


    /**
     * 获取用户资金信息
     */
    AccountDO findByUserId(String userId);
}
