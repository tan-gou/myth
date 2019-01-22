package com.github.myth.core.service.mq.receive;

import com.github.myth.common.bean.context.MythTransactionContext;
import com.github.myth.common.bean.entity.MythInvocation;
import com.github.myth.common.bean.entity.MythTransaction;
import com.github.myth.common.bean.mq.MessageEntity;
import com.github.myth.common.config.MythConfig;
import com.github.myth.common.enums.EventTypeEnum;
import com.github.myth.common.enums.MythRoleEnum;
import com.github.myth.common.enums.MythStatusEnum;
import com.github.myth.common.exception.MythException;
import com.github.myth.common.exception.MythRuntimeException;
import com.github.myth.common.serializer.ObjectSerializer;
import com.github.myth.common.utils.LogUtil;
import com.github.myth.core.concurrent.threadlocal.TransactionContextLocal;
import com.github.myth.core.coordinator.CoordinatorService;
import com.github.myth.core.disruptor.publisher.MythTransactionEventPublisher;
import com.github.myth.core.helper.SpringBeanUtils;
import com.github.myth.core.service.MythMqReceiveService;
import com.github.myth.core.service.mq.send.MythSendMessageServiceImpl;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * myth框架处理发出的mq消息
 */
@Service("mythMqReceiveService")
public class MythMqReceiveServiceImpl implements MythMqReceiveService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MythMqReceiveServiceImpl.class);


    private volatile ObjectSerializer serializer;

    private static final Lock LOCK = new ReentrantLock();


    @Autowired
    private CoordinatorService coordinatorService;

    @Autowired
    private MythTransactionEventPublisher publisher;


    @Autowired
    private MythConfig mythConfig;


    /**
     * myth框架处理发出的mq消息
     *
     * @param   message 实体对象转换成byte[]后的数据
     * @return  true 成功 false 失败
     */
    @Override
    public Boolean processMessage(byte[] message) {
        try {
            MessageEntity entity;
            try {
                entity = getObjectSerializer().deSerialize(message, MessageEntity.class);
            } catch (MythException e) {
                e.printStackTrace();
                throw new MythRuntimeException(e.getMessage());
            }

            /**
             * 1 检查该事务有没被处理过，已经处理过的 则不处理
             * 2 发起反射调用，调用接口，进行处理
             * 3 记录本地日志
             */
            LOCK.lock();

            final String transId = entity.getTransId();
            final MythTransaction mythTransaction = coordinatorService.findByTransId(transId);

            //第一次调用 也就是服务down机，或者没有调用到的时候， 通过mq执行
            if (Objects.isNull(mythTransaction)) {
                try {
                    execute(entity);
                    //执行成功 保存成功的日志
                    final MythTransaction mythTransactionLog = buildTransactionLog(transId, "",
                            MythStatusEnum.COMMIT.getCode(),
                            entity.getMythInvocation().getTargetClass().getName(),
                            entity.getMythInvocation().getMethodName());

                    publisher.publishEvent(mythTransactionLog, EventTypeEnum.SAVE.getCode());
                } catch (Exception e) {
                    //执行失败保存失败的日志
                    final MythTransaction mythTransactionLog = buildTransactionLog(transId, e.getMessage(),
                            MythStatusEnum.FAILURE.getCode(),
                            entity.getMythInvocation().getTargetClass().getName(),
                            entity.getMythInvocation().getMethodName());

                    publisher.publishEvent(mythTransactionLog, EventTypeEnum.SAVE.getCode());

                    throw new MythRuntimeException(e);
                } finally {
                    TransactionContextLocal.getInstance().remove();
                }

            } else {
                //如果是执行失败的话
                if (mythTransaction.getStatus() == MythStatusEnum.FAILURE.getCode()) {
                    //如果超过了最大重试次数 则不执行
                    if (mythTransaction.getRetriedCount() >= mythConfig.getRetryMax()) {
                        LogUtil.error(LOGGER, () -> "此事务已经超过了最大重试次数:" + mythConfig.getRetryMax()
                                + " ,执行接口为:" + entity.getMythInvocation().getTargetClass() + " ,方法为:" +
                                entity.getMythInvocation().getMethodName() + ",事务id为：" + entity.getTransId());
                        return Boolean.FALSE;
                    }
                    try {
                        execute(entity);
                        //执行成功 更新日志为成功
                        mythTransaction.setStatus(MythStatusEnum.COMMIT.getCode());
                        publisher.publishEvent(mythTransaction, EventTypeEnum.UPDATE_STATUS.getCode());

                    } catch (Throwable e) {
                        //执行失败，设置失败原因和重试次数
                        mythTransaction.setErrorMsg(e.getCause().getMessage());
                        mythTransaction.setRetriedCount(mythTransaction.getRetriedCount() + 1);
                        publisher.publishEvent(mythTransaction, EventTypeEnum.UPDATE_FAIR.getCode());

                        throw new MythRuntimeException(e);
                    } finally {
                        TransactionContextLocal.getInstance().remove();
                    }
                }
            }

        } finally {
            LOCK.unlock();
        }
        return Boolean.TRUE;
    }


    private void execute(MessageEntity entity) throws Exception {
        // 设置事务上下文，这个类会传递给远端
        MythTransactionContext context = new MythTransactionContext();
        //设置事务id
        context.setTransId(entity.getTransId());
        //设置为 本地执行者
        context.setRole(MythRoleEnum.LOCAL.getCode());

        TransactionContextLocal.getInstance().set(context);

        executeLocalTransaction(entity.getMythInvocation());
    }


    /**
     * 反射调用方法，执行本地事务
     */
    @SuppressWarnings("unchecked")
    private void executeLocalTransaction(MythInvocation mythInvocation) throws Exception {
        if (Objects.nonNull(mythInvocation)) {
            final Class clazz = mythInvocation.getTargetClass();
            final String method = mythInvocation.getMethodName();
            final Object[] args = mythInvocation.getArgs();
            final Class[] parameterTypes = mythInvocation.getParameterTypes();

            final Object bean = SpringBeanUtils.getInstance().getBean(clazz);
            MethodUtils.invokeMethod(bean, method, args, parameterTypes);

            LogUtil.debug(LOGGER, "Myth执行本地协调事务:{}",
                    () -> mythInvocation.getTargetClass() + ":" + mythInvocation.getMethodName());
        }
    }

    private MythTransaction buildTransactionLog(String transId, String errorMsg, Integer status,
                                                String targetClass, String targetMethod) {
        MythTransaction logTransaction = new MythTransaction(transId);
        logTransaction.setRetriedCount(1);
        logTransaction.setStatus(status);
        logTransaction.setErrorMsg(errorMsg);
        // 事务提供者
        logTransaction.setRole(MythRoleEnum.PROVIDER.getCode());
        logTransaction.setTargetClass(targetClass);
        logTransaction.setTargetMethod(targetMethod);
        return logTransaction;
    }


    private synchronized ObjectSerializer getObjectSerializer() {
        if (serializer == null) {
            synchronized (MythSendMessageServiceImpl.class) {
                if (serializer == null) {
                    serializer = SpringBeanUtils.getInstance().getBean(ObjectSerializer.class);
                }
            }
        }
        return serializer;
    }
}
