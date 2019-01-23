package com.github.myth.core.service;

import com.github.myth.common.bean.context.MythTransactionContext;
import org.aspectj.lang.ProceedingJoinPoint;


@FunctionalInterface
public interface MythTransactionAspectService {

    /**
     * myth事务切面服务
     *
     * @param   mythTransactionContext  myth事务上下文对象
     * @param   point                   切点
     * @return  object
     * @throws  Throwable 异常信息
     */
    Object invoke(MythTransactionContext mythTransactionContext, ProceedingJoinPoint point) throws Throwable;
}
