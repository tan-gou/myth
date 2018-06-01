package com.github.myth.core.disruptor.event;

import com.github.myth.common.bean.entity.MythTransaction;
import lombok.Data;

import java.io.Serializable;

@Data
public class MythTransactionEvent implements Serializable {

    private MythTransaction mythTransaction;

    private int type;

    public void clear() {
        mythTransaction = null;
    }
}
