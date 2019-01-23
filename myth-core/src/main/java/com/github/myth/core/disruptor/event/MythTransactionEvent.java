package com.github.myth.core.disruptor.event;

import com.github.myth.common.bean.entity.MythTransaction;
import lombok.Data;

import java.io.Serializable;

/**
 * 本地事务事件
 */
@Data
public class MythTransactionEvent implements Serializable {

    private MythTransaction mythTransaction;

    /**
     * @see com.github.myth.common.enums.EventTypeEnum
     */
    private int type;

    public void clear() {
        mythTransaction = null;
    }
}
