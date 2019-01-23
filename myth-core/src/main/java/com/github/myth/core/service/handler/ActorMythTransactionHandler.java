package com.github.myth.core.service.handler;

import com.github.myth.common.bean.context.MythTransactionContext;
import com.github.myth.common.enums.MythStatusEnum;
import com.github.myth.common.utils.LogUtil;
import com.github.myth.core.concurrent.threadlocal.TransactionContextLocal;
import com.github.myth.core.service.MythTransactionHandler;
import com.github.myth.core.service.engine.MythTransactionEngine;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Myth分布式事务参与者， 参与分布式事务的接口会进入该handler
 */
@Component
public class ActorMythTransactionHandler implements MythTransactionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorMythTransactionHandler.class);


    private final MythTransactionEngine mythTransactionEngine;


    @Autowired
    public ActorMythTransactionHandler(MythTransactionEngine mythTransactionEngine) {
        this.mythTransactionEngine = mythTransactionEngine;
    }


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
        try {
            //先保存事务日志
            mythTransactionEngine.actorTransaction(point, mythTransactionContext);

            //发起调用 执行try方法
            final Object proceed = point.proceed();

            //执行成功 更新状态为commit
            mythTransactionEngine.updateStatus(MythStatusEnum.COMMIT.getCode());

            return proceed;
        } catch (Throwable throwable) {
            LogUtil.error(LOGGER, "执行分布式事务接口失败,事务id：{}", mythTransactionContext::getTransId);
            mythTransactionEngine.failTransaction(throwable.getMessage());
            throw throwable;
        } finally {
            TransactionContextLocal.getInstance().remove();
        }
    }
}
