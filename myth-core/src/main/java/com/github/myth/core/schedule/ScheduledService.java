package com.github.myth.core.schedule;

import com.github.myth.common.bean.entity.MythTransaction;
import com.github.myth.common.config.MythConfig;
import com.github.myth.common.enums.EventTypeEnum;
import com.github.myth.common.enums.MythStatusEnum;
import com.github.myth.common.utils.LogUtil;
import com.github.myth.core.concurrent.threadpool.MythTransactionThreadFactory;
import com.github.myth.core.coordinator.CoordinatorService;
import com.github.myth.core.disruptor.publisher.MythTransactionEventPublisher;
import com.github.myth.core.service.MythSendMessageService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledService.class);

    @Autowired
    private MythSendMessageService mythSendMessageService;

    @Autowired
    private CoordinatorService coordinatorService;

    @Autowired
    private MythTransactionEventPublisher publisher;

    public void scheduledAutoRecover(MythConfig mythConfig) {

        new ScheduledThreadPoolExecutor(1,
                MythTransactionThreadFactory.create("MythAutoRecoverService",
                        true)).scheduleWithFixedDelay(() -> {
            LogUtil.debug(LOGGER, "auto recover execute delayTime:{}", mythConfig::getScheduledDelay);

            try {
                final List<MythTransaction> mythTransactionList =
                        coordinatorService.listAllByDelay(acquireData(mythConfig));
                if (CollectionUtils.isNotEmpty(mythTransactionList)) {
                    mythTransactionList
                            .forEach(mythTransaction -> {
                                final Boolean success = mythSendMessageService.sendMessage(mythTransaction);
                                //发送成功 ，更改状态
                                if (success) {
                                    mythTransaction.setStatus(MythStatusEnum.COMMIT.getCode());
                                    publisher.publishEvent(mythTransaction, EventTypeEnum.UPDATE_STATUS.getCode());
                                }
                            });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 30, mythConfig.getScheduledDelay(), TimeUnit.SECONDS);

    }


    private Date acquireData(MythConfig mythConfig) {
        return new Date(LocalDateTime.now().atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli() - (mythConfig.getRecoverDelayTime() * 1000));
    }

}
