package com.github.myth.core.disruptor.publisher;

import com.github.myth.common.bean.entity.MythTransaction;
import com.github.myth.core.disruptor.event.MythTransactionEvent;
import com.github.myth.core.disruptor.factory.MythTransactionEventFactory;
import com.github.myth.core.disruptor.handler.MythTransactionEventHandler;
import com.github.myth.core.disruptor.translator.MythTransactionEventTranslator;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;


@Component
public class MythTransactionEventPublisher implements DisposableBean {

    private Disruptor<MythTransactionEvent> disruptor;

    @Autowired
    private MythTransactionEventHandler mythTransactionEventHandler;


    public void start(int bufferSize) {
        disruptor =
                new Disruptor<>(new MythTransactionEventFactory(),
                        bufferSize, r -> {
                    AtomicInteger index = new AtomicInteger(1);
                    return new Thread(null, r, "disruptor-thread-" + index.getAndIncrement());
                }, ProducerType.MULTI, new YieldingWaitStrategy());

        disruptor.handleEventsWith(mythTransactionEventHandler);
        disruptor.start();
    }

    public void publishEvent(MythTransaction mythTransaction, int type) {
        final RingBuffer<MythTransactionEvent> ringBuffer = disruptor.getRingBuffer();
        ringBuffer.publishEvent(new MythTransactionEventTranslator(type), mythTransaction);
    }


    /**
     * Invoked by a BeanFactory on destruction of a singleton.
     *
     * @throws Exception in case of shutdown errors.
     *                   Exceptions will get logged but not rethrown to allow
     *                   other beans to release their resources too.
     */
    @Override
    public void destroy() {
        disruptor.shutdown();
    }
}
