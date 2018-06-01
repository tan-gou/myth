package com.github.myth.demo.dubbo.account.service;

import com.github.myth.annotation.Myth;
import com.github.myth.demo.dubbo.account.api.dto.AccountDTO;
import com.github.myth.demo.dubbo.account.api.entity.AccountDO;
import com.github.myth.demo.dubbo.account.api.service.AccountService;
import com.github.myth.demo.dubbo.account.mapper.AccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service("accountService")
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);


    private final AccountMapper accountMapper;

    @Autowired(required = false)
    public AccountServiceImpl(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    /**
     * 扣款支付 真实的场景请保证幕等性
     */
    @Override
    @Myth(destination = "ORDER_FLOW_BQ",tags = "account")
    @Transactional(rollbackFor = Exception.class)
    public boolean payment(AccountDTO accountDTO) {
        final AccountDO accountDO = accountMapper.findByUserId(accountDTO.getUserId());
        if(accountDO.getBalance().compareTo(accountDTO.getAmount()) <=0 ){
            throw new RuntimeException("资金不足！");
        }
        accountDO.setBalance(accountDO.getBalance().subtract(accountDTO.getAmount()));
        accountDO.setUpdateTime(new Date());
        final int update = accountMapper.update(accountDO);
        if (update != 1) {
            throw new RuntimeException("资金不足！");
        }
        return Boolean.TRUE;
    }

    @Override
    public AccountDO findByUserId(String  userId) {
        return accountMapper.findByUserId(userId);
    }

}
