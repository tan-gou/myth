package com.github.myth.core.service.impl;

import com.github.myth.common.bean.context.MythTransactionContext;
import com.github.myth.common.enums.MythRoleEnum;
import com.github.myth.common.utils.GsonUtils;
import com.github.myth.common.utils.LogUtil;
import com.github.myth.core.service.MythTransactionFactoryService;
import com.github.myth.core.service.engine.MythTransactionEngine;
import com.github.myth.core.service.handler.ActorMythTransactionHandler;
import com.github.myth.core.service.handler.LocalMythTransactionHandler;
import com.github.myth.core.service.handler.StartMythTransactionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 *
 */
@Component
public class MythTransactionFactoryServiceImpl implements MythTransactionFactoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MythTransactionFactoryServiceImpl.class);


    private final MythTransactionEngine mythTransactionEngine;

    @Autowired
    public MythTransactionFactoryServiceImpl(MythTransactionEngine mythTransactionEngine) {
        this.mythTransactionEngine = mythTransactionEngine;
    }

    /**
     * 返回 实现TxTransactionHandler类的名称
     *
     * @param   context     事务上下文
     * @return  Class<T>
     * @throws  Throwable   抛出异常
     */
    @Override
    public Class factoryOf(MythTransactionContext context) throws Throwable {
        //如果事务还没开启或者 myth事务上下文是空， 那么应该进入发起调用
        if (!mythTransactionEngine.isBegin() && Objects.isNull(context)) {

            LogUtil.debug(LOGGER, "factoryOf StartMythTransactionHandler, MythTransactionContext：{}",
                    () -> GsonUtils.getInstance().toJson(context));

            return StartMythTransactionHandler.class;
        } else {
            if (context.getRole() == MythRoleEnum.LOCAL.getCode()) {

                LogUtil.debug(LOGGER, "factoryOf LocalMythTransactionHandler, MythTransactionContext：{}",
                        () -> GsonUtils.getInstance().toJson(context));

                return LocalMythTransactionHandler.class;
            }

            LogUtil.debug(LOGGER, "factoryOf ActorMythTransactionHandler, MythTransactionContext：{}",
                    () -> GsonUtils.getInstance().toJson(context));

            return ActorMythTransactionHandler.class;
        }
    }
}
