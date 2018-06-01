package com.github.myth.core.disruptor.factory;

import com.github.myth.core.disruptor.event.MythTransactionEvent;
import com.lmax.disruptor.EventFactory;

public class MythTransactionEventFactory implements EventFactory<MythTransactionEvent> {

    @Override
    public MythTransactionEvent newInstance() {
        return new MythTransactionEvent();
    }
}
