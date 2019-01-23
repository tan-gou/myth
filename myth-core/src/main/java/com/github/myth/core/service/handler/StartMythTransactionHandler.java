package com.github.myth.core.service.handler;

import com.github.myth.common.bean.context.MythTransactionContext;
import com.github.myth.core.concurrent.threadlocal.TransactionContextLocal;
import com.github.myth.core.service.MythTransactionHandler;
import com.github.myth.core.service.engine.MythTransactionEngine;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 分布式事务发起者 handler
 */
@Component
public class StartMythTransactionHandler implements MythTransactionHandler {

    private final MythTransactionEngine mythTransactionEngine;

    @Autowired
    public StartMythTransactionHandler(MythTransactionEngine mythTransactionEngine) {
        this.mythTransactionEngine = mythTransactionEngine;
    }


    /**
     * Myth分布式事务处理接口
     *
     * @param   point                   point 切点
     * @param   mythTransactionContext  myth事务上下文
     * @return  Object
     * @throws  Throwable               异常
     */
    @Override
    public Object handler(ProceedingJoinPoint point, MythTransactionContext mythTransactionContext) throws Throwable {

        try {
            mythTransactionEngine.begin(point);
            return point.proceed();
        } catch (Throwable throwable) {
            //更新失败的日志信息
            mythTransactionEngine.failTransaction(throwable.getMessage());
            throw throwable;
        } finally {
            //发送消息
            mythTransactionEngine.sendMessage();
            mythTransactionEngine.cleanThreadLocal();
            TransactionContextLocal.getInstance().remove();
        }
    }
}
