package com.github.myth.core.service;

import com.github.myth.common.bean.context.MythTransactionContext;
import org.aspectj.lang.ProceedingJoinPoint;


@FunctionalInterface
public interface MythTransactionHandler {

    /**
     * Myth分布式事务处理接口
     *
     * @param   point                  point 切点
     * @param   mythTransactionContext myth事务上下文
     * @return  Object
     * @throws  Throwable 异常
     */
    Object handler(ProceedingJoinPoint point, MythTransactionContext mythTransactionContext) throws Throwable;
}
