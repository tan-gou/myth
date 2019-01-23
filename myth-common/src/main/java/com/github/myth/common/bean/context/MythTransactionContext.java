package com.github.myth.common.bean.context;

import lombok.Data;

import java.io.Serializable;


/**
 * 事务上下文
 */
@Data
public class MythTransactionContext implements Serializable {

    private static final long serialVersionUID = -5289080166922118073L;

    private String transId;

    /**
     * 事务参与的角色 {@linkplain com.github.myth.common.enums.MythRoleEnum}
     */
    private int role;
}
