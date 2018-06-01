package com.github.myth.demo.dubbo.account.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class AccountDTO implements Serializable {

    private static final long serialVersionUID = 7223470850578998427L;

    private String userId;

    /**
     * 扣款金额
     */
    private BigDecimal amount;
}
