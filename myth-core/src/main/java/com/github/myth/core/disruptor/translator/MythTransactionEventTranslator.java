package com.github.myth.core.disruptor.translator;


import com.github.myth.common.bean.entity.MythTransaction;
import com.github.myth.core.disruptor.event.MythTransactionEvent;
import com.lmax.disruptor.EventTranslatorOneArg;


public class MythTransactionEventTranslator implements EventTranslatorOneArg<MythTransactionEvent, MythTransaction> {

    private int type;

    public MythTransactionEventTranslator(int type) {
        this.type = type;
    }

    @Override
    public void translateTo(MythTransactionEvent mythTransactionEvent, long l,
                            MythTransaction mythTransaction) {
        mythTransactionEvent.setMythTransaction(mythTransaction);
        mythTransactionEvent.setType(type);
    }
}
