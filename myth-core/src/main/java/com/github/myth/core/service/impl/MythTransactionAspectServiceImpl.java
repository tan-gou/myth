package com.github.myth.core.service.impl;

import com.github.myth.common.bean.context.MythTransactionContext;
import com.github.myth.core.helper.SpringBeanUtils;
import com.github.myth.core.service.MythTransactionAspectService;
import com.github.myth.core.service.MythTransactionFactoryService;
import com.github.myth.core.service.MythTransactionHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  myth事务切面服务
 */
@Component
public class MythTransactionAspectServiceImpl implements MythTransactionAspectService {

    private final MythTransactionFactoryService mythTransactionFactoryService;

    @Autowired
    public MythTransactionAspectServiceImpl(MythTransactionFactoryService mythTransactionFactoryService) {
        this.mythTransactionFactoryService = mythTransactionFactoryService;
    }

    /**
     * myth事务切面服务
     *
     * @param   mythTransactionContext myth事务上下文对象
     * @param   point                  切点
     * @return  object
     * @throws  Throwable              异常信息
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(MythTransactionContext mythTransactionContext, ProceedingJoinPoint point) throws Throwable {
        final Class clazz = mythTransactionFactoryService.factoryOf(mythTransactionContext);
        final MythTransactionHandler mythTransactionHandler =
                (MythTransactionHandler) SpringBeanUtils.getInstance().getBean(clazz);
        return mythTransactionHandler.handler(point, mythTransactionContext);
    }
}
