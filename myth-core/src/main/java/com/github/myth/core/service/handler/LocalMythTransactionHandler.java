package com.github.myth.core.service.handler;

import com.github.myth.common.bean.context.MythTransactionContext;
import com.github.myth.core.service.MythTransactionHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;


@Component
public class LocalMythTransactionHandler implements MythTransactionHandler {

    /**
     * Myth分布式事务处理接口
     *
     * @param   point                  point 切点
     * @param   mythTransactionContext myth事务上下文
     * @return  Object
     * @throws  Throwable 异常
     */
    @Override
    public Object handler(ProceedingJoinPoint point, MythTransactionContext mythTransactionContext) throws Throwable {

        return point.proceed();
    }
}
