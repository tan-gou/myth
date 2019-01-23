package com.github.myth.dubbo.interceptor;

import com.alibaba.dubbo.rpc.RpcContext;
import com.github.myth.common.bean.context.MythTransactionContext;
import com.github.myth.common.constant.CommonConstant;
import com.github.myth.common.utils.GsonUtils;
import com.github.myth.core.concurrent.threadlocal.TransactionContextLocal;
import com.github.myth.core.interceptor.MythTransactionInterceptor;
import com.github.myth.core.service.MythTransactionAspectService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class DubboMythTransactionInterceptor implements MythTransactionInterceptor {

    private final MythTransactionAspectService mythTransactionAspectService;

    @Autowired
    public DubboMythTransactionInterceptor(MythTransactionAspectService mythTransactionAspectService) {
        this.mythTransactionAspectService = mythTransactionAspectService;
    }


    @Override
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        final String context = RpcContext.getContext().getAttachment(CommonConstant.MYTH_TRANSACTION_CONTEXT);
        MythTransactionContext mythTransactionContext;
        if (StringUtils.isNoneBlank(context)) {
            mythTransactionContext =
                    GsonUtils.getInstance().fromJson(context, MythTransactionContext.class);
        } else {
            mythTransactionContext= TransactionContextLocal.getInstance().get();
        }
        return mythTransactionAspectService.invoke(mythTransactionContext, pjp);
    }
}
