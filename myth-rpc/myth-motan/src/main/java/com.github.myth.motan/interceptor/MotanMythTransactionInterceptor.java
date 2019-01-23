package com.github.myth.motan.interceptor;

import com.github.myth.common.bean.context.MythTransactionContext;
import com.github.myth.common.constant.CommonConstant;
import com.github.myth.common.utils.GsonUtils;
import com.github.myth.core.concurrent.threadlocal.TransactionContextLocal;
import com.github.myth.core.interceptor.MythTransactionInterceptor;
import com.github.myth.core.service.MythTransactionAspectService;
import com.weibo.api.motan.rpc.Request;
import com.weibo.api.motan.rpc.RpcContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 *
 */
@Component
public class MotanMythTransactionInterceptor implements MythTransactionInterceptor {

    private final MythTransactionAspectService mythTransactionAspectService;

    @Autowired
    public MotanMythTransactionInterceptor(MythTransactionAspectService mythTransactionAspectService) {
        this.mythTransactionAspectService = mythTransactionAspectService;
    }


    @Override
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        MythTransactionContext mythTransactionContext = null;

        final Request request = RpcContext.getContext().getRequest();
        if (Objects.nonNull(request)) {
            final Map<String, String> attachments = request.getAttachments();
            if (attachments != null && !attachments.isEmpty()) {
                String context = attachments.get(CommonConstant.MYTH_TRANSACTION_CONTEXT);
                mythTransactionContext =
                        GsonUtils.getInstance().fromJson(context, MythTransactionContext.class);
            }
        } else {
            mythTransactionContext = TransactionContextLocal.getInstance().get();
        }

        return mythTransactionAspectService.invoke(mythTransactionContext, pjp);
    }
}
