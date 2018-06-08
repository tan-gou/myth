package com.github.myth.core.disruptor.handler;

import com.github.myth.common.bean.entity.MythTransaction;
import com.github.myth.common.enums.EventTypeEnum;
import com.github.myth.core.coordinator.CoordinatorService;
import com.github.myth.core.disruptor.event.MythTransactionEvent;
import com.lmax.disruptor.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MythTransactionEventHandler implements EventHandler<MythTransactionEvent> {

    @Autowired
    private CoordinatorService coordinatorService;

    @Override
    public void onEvent(MythTransactionEvent mythTransactionEvent,
                        long sequence, boolean endOfBatch) {

        if (mythTransactionEvent.getType() == EventTypeEnum.SAVE.getCode()) {
            coordinatorService.save(mythTransactionEvent.getMythTransaction());
        } else if (mythTransactionEvent.getType() == EventTypeEnum.UPDATE_PARTICIPANT.getCode()) {
            coordinatorService.updateParticipant(mythTransactionEvent.getMythTransaction());
        } else if (mythTransactionEvent.getType() == EventTypeEnum.UPDATE_STATUS.getCode()) {
            final MythTransaction mythTransaction = mythTransactionEvent.getMythTransaction();
            coordinatorService.updateStatus(mythTransaction.getTransId(), mythTransaction.getStatus());
        }
        else if (mythTransactionEvent.getType() == EventTypeEnum.UPDATE_FAIR.getCode()) {
            coordinatorService.updateFailTransaction(mythTransactionEvent.getMythTransaction());
        }
        mythTransactionEvent.clear();
    }
}
