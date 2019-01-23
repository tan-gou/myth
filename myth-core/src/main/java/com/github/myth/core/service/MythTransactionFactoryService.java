package com.github.myth.core.service;

import com.github.myth.common.bean.context.MythTransactionContext;

@FunctionalInterface
public interface MythTransactionFactoryService<T> {

    /**
     * 返回 实现TxTransactionHandler类的名称
     *
     * @param   context     事务上下文
     * @return  Class<T>
     * @throws  Throwable   抛出异常
     */
    Class<T> factoryOf(MythTransactionContext context) throws Throwable;
}
